package cn.chong.service.impl;

import cn.chong.constant.RedisKeyConstant;
import cn.chong.mapper.UserWalletMapper;
import cn.chong.model.dto.userWallet.UserConsumerRequest;
import cn.chong.model.dto.userWalletDetail.UserWalletDetailRequest;
import cn.chong.model.entity.UserEntity;
import cn.chong.model.entity.UserWalletDetailEntity;
import cn.chong.model.entity.UserWalletEntity;
import cn.chong.model.enums.WalletUpdateTypeEnum;
import cn.chong.model.vo.UserWalletVo;
import cn.chong.service.UserService;
import cn.chong.service.UserWalletDetailService;
import cn.chong.service.UserWalletService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Service("userWalletService")
@Slf4j
public class UserWalletServiceImpl extends ServiceImpl<UserWalletMapper, UserWalletEntity> implements UserWalletService {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private UserService userService;

    @Resource
    private UserWalletDetailService walletDetailService;

    @Override
    public UserWalletVo getUserWalletVo(Long userId) {

        log.info("查询用户：{}的钱包数据库数据 start", userId);

        //查询数据库获取到对象userId的userWallet对象数据
        LambdaQueryWrapper<UserWalletEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserWalletEntity::getUserId, userId);
        UserWalletEntity userWalletEntity = this.getOne(wrapper);

        log.info("查询用户：{}的钱包数据库数据 end, 数据为{}", userId, userWalletEntity);

        //没有数据直接返回空
        if(ObjUtil.isEmpty(userWalletEntity)){
            return null;
        }

        //将查询到的数据封装成vo
        UserWalletVo userWalletVo = new UserWalletVo();
        //拷贝属性
        BeanUtil.copyProperties(userWalletEntity, userWalletVo);

        log.info("查询用户：{}钱包数据完成，返回数据：{}", userId, userWalletVo);
        System.out.println(userWalletEntity);

        return userWalletVo;
    }


    @Override
    @Transactional
    public void consumerAmount(UserConsumerRequest consumerRequest) {

        //使用redisson分布式锁锁一下：
        RLock lock = redissonClient.getLock(RedisKeyConstant.UPDATE_WALLET_LOCK_KEY);
        lock.lock(RedisKeyConstant.EXPIRE_TIME, TimeUnit.SECONDS);

        log.info("用户：{} 钱包数据更改 start", consumerRequest.getUserId());

        //更改总金额
        LambdaQueryWrapper<UserWalletEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserWalletEntity::getUserId, consumerRequest.getUserId());
        UserWalletEntity userWalletEntity = this.getOne(wrapper);
            //计算更改后的金额
        BigDecimal afterAmount = calculatedAmount(userWalletEntity.getTotalAmount(), consumerRequest);

        UserWalletEntity afterUserWallet = new UserWalletEntity();
        afterUserWallet.setId(userWalletEntity.getId());
        afterUserWallet.setUpdateTime(new Date());
        afterUserWallet.setTotalAmount(afterAmount);
        baseMapper.updateById(afterUserWallet);
        log.info("修改用户：{}钱包总金额，即修改UserWalletEntity数据完成，修改后的金额为：{}",consumerRequest.getUserId(), afterAmount);

        //获取一个UserWalletDetailEntity对象
        UserWalletDetailEntity userWalletDetailEntity = getUserWalletDetail(consumerRequest, userWalletEntity.getTotalAmount(), afterAmount);
        //新增userWallet的数据
        walletDetailService.save(userWalletDetailEntity);
        log.info("新增用户：{}的用户钱包更改明细，明细：{}",consumerRequest.getUserId(), userWalletDetailEntity);

        //解锁
        lock.unlock();
        log.info("用户：{} 钱包数据更改 end", consumerRequest.getUserId());
    }

    /**
     * 计算用户钱包更改后的金额
     * @param totalAmount 更改前的金额
     * @param consumerRequest 消费的请求参数
     * @return
     */
    private BigDecimal calculatedAmount(BigDecimal totalAmount, UserConsumerRequest consumerRequest) {

        //根据传入的更改类型判断更改的数据是正还是负（是增还是减）
        BigDecimal newUpdateAmount =  UpdateAmountIfAddOrDel(consumerRequest.getUpdateType(), consumerRequest.getUpdateAmount());
        return totalAmount.add(newUpdateAmount);
    }

    /**
     * 根据传入的枚举值类型，判断这次更改是加还是减
     * @param updateType 用户金额更改类型（0 -- 收入 1 -- 支出）
     * @param updateAmount
     * @return
     */
    private BigDecimal UpdateAmountIfAddOrDel(Integer updateType, BigDecimal updateAmount) {

        //收入
        if(WalletUpdateTypeEnum.INCOME.getValue() == updateType){
            return updateAmount.abs();
        }
        //支出，返回负数
        else{
            return updateAmount.abs().negate();
        }

    }


    /**
     * 用于获取一个UserWalletDetailEntity对象，为其填充属性数据
     *
     * @param consumerRequest
     * @param totalAmount
     * @param afterAmount
     * @return
     */
    private UserWalletDetailEntity getUserWalletDetail(UserConsumerRequest consumerRequest, BigDecimal totalAmount, BigDecimal afterAmount) {

        log.info("填充UserWalletDetailEntity数据 start");

        UserWalletDetailEntity userWalletDetailEntity = new UserWalletDetailEntity();
        //填充数据
        userWalletDetailEntity.setUserId(consumerRequest.getUserId());
        userWalletDetailEntity.setOrderId(consumerRequest.getOrderId());
        userWalletDetailEntity.setUpdateType(consumerRequest.getUpdateType());
        userWalletDetailEntity.setUpdateSource(consumerRequest.getUpdateSource());

        //获取姓名,填充用户姓名
        LambdaQueryWrapper<UserEntity> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(UserEntity::getId, consumerRequest.getUserId());
        UserEntity user = userService.getOne(userWrapper);

        userWalletDetailEntity.setUserName(user.getUserName());

        //判断这次更改是加还是减,即判断传入的操作数是正还是负
        BigDecimal newUpdateAmount = UpdateAmountIfAddOrDel(consumerRequest.getUpdateType(), consumerRequest.getUpdateAmount());

        //继续填充金额相关数据
        userWalletDetailEntity.setAfterAmount(totalAmount.add(newUpdateAmount));
        userWalletDetailEntity.setBeforeAmount(totalAmount);
        userWalletDetailEntity.setUpdateAmount(newUpdateAmount);

        log.info("填充UserWalletDetailEntity数据 end,数据为:{}", userWalletDetailEntity);

        return userWalletDetailEntity;
    }


}