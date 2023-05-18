package cn.chong.service;


import cn.chong.model.dto.userWallet.UserConsumerRequest;
import cn.chong.model.dto.userWalletDetail.UserWalletDetailRequest;
import cn.chong.model.entity.UserWalletDetailEntity;
import cn.chong.model.entity.UserWalletEntity;
import cn.chong.model.vo.UserWalletVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 
 *
 * @author tangchongjie
 * @email 1514176166@qq.com
 * @date 2023-05-18 15:50:54
 */
public interface UserWalletService extends IService<UserWalletEntity> {

    /**
     * 根据用户id查询用户钱包数据
     * @param userId 用户id
     * @return 用户钱包vo
     */
    UserWalletVo getUserWalletVo(Long userId);

    /**
     * 用户钱包进行消费方法，新增一条userWalletDetail(用户钱包变动明细)的数据，更改userWallet的总金额
     * @param consumerRequest
     */
    void consumerAmount(UserConsumerRequest consumerRequest);


}

