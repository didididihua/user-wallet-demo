package cn.chong.controller;

import cn.chong.annotation.Limit;
import cn.chong.common.BaseResponse;
import cn.chong.common.ErrorCode;
import cn.chong.common.ResultUtils;
import cn.chong.constant.RedisKeyConstant;
import cn.chong.exception.BusinessException;
import cn.chong.model.dto.userWallet.UserConsumerRequest;
import cn.chong.model.vo.UserWalletVo;
import cn.chong.service.UserWalletService;
import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.ObjUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * @author: tangchongjie
 * @creattime: 2023--05--18 15:08
 * @description 实现Java面试题，对用户钱包进行操作的几个api接口
 *              1.  查询用户钱包余额
 *              2. 用户消费100元的接口
 *              3. 用户退款20元接口
 *              4. 查询用户钱包金额变动明细的接口
 */
@RestController
@RequestMapping("/userWallet")
@Slf4j
public class UserWalletController {

    @Resource
    private UserWalletService userWalletService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 1. 查询用户钱包余额
     * @param UserId 用户id
     * @return 用户钱包vo
     */
    @GetMapping("/getUserWallet/userId")
    public BaseResponse<UserWalletVo> getUserWallet(@PathVariable Long UserId){
        //校验用户id是否为空
        if(ObjUtil.isEmpty(UserId)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户id为空");
        }
        //根据用户id查询他的钱包
        UserWalletVo userWalletVo = userWalletService.getUserWalletVo(UserId);

        return ResultUtils.success(userWalletVo);
    }


    /**
     * 2. 用户消费100元的接口
     * 3. 用户退款20元接口
     * 因为需要传入更改类型（收入、支出）与更改来源（消费、退款、充值、提醒按），那么可以由这个参数来判断更改数据是增加还是减少，是进行什么操作
     * 那么将这两个接口合一即可
     * @param consumerRequest 用户钱包数据更改的请求参数
     * @return String
     */
    @PostMapping("/updateWallet")
    @Limit(period = 60, count = 20, name = "更改用户钱包数据", prefix = "updateWallet")
    public BaseResponse<String> consumerAmount(@RequestBody UserConsumerRequest consumerRequest){

        if(!validConsumerRequest(consumerRequest)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        };

        //幂等判断，判断是否为重复请求，不是重复请求才能进行处理
        MDNWithRedis(consumerRequest);

        //开始数据处理
        try{
            userWalletService.consumerAmount(consumerRequest);
        }catch (Exception e) {
            log.info("用户钱包数据更改失败，已经进行回滚操作");
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "操作失败，请重试");
        }

        return ResultUtils.success("操作成功");
    }


    /**
     * 使用传入的consumerRequest对象属性进行Hash来,UserConsumerRequest使用了@Data注解，重写了获取Hash值的方法
     * @param consumerRequest
     */
    private void MDNWithRedis(UserConsumerRequest consumerRequest) {
        //计算Hash值
        int hashCode = consumerRequest.hashCode();
        String valueOf = String.valueOf(hashCode);
        log.info("判断幂等开始，请求对象的hash值为:{}", valueOf);

        //使用redis的setnx原子操作进行幂等判断
        Boolean ifAbsent = stringRedisTemplate.opsForValue().setIfAbsent(RedisKeyConstant.MDN_CONSUMER_KEY, valueOf,
                RedisKeyConstant.MDN_EXPIRE_TIME, TimeUnit.MICROSECONDS);

        //setnx失败，表示该请求是重复请求，返回提示
        if(!ifAbsent){
            log.info("此次更改用户钱包的请求为重复请求，需要等待重试，予以拦截");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "重复请求，请等下再试");
        }

        log.info("此次更改用户钱包的请求不为重复请求，予以放行，幂等判断结束");
    }

    /**
     * 校验消费请求的请求参数数据是否不为空
     * @param consumerRequest
     * @return 有空则返回false反之为true
     */
    private boolean validConsumerRequest(UserConsumerRequest consumerRequest) {
        Long userId = consumerRequest.getUserId();
        Long orderId = consumerRequest.getOrderId();
        BigDecimal updateAmount = consumerRequest.getUpdateAmount();
        Integer updateSource = consumerRequest.getUpdateSource();
        Integer updateType = consumerRequest.getUpdateType();

        //有空则返回false
        if(ObjUtil.hasEmpty(userId, orderId, updateAmount, updateSource, updateType)){
            return false;
        }

        return true;
    }

}
