package cn.chong.service;


import cn.chong.model.dto.userWalletDetail.UserWalletDetailRequest;
import cn.chong.model.entity.UserWalletDetailEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 
 *
 * @author tangchongjie
 * @email 1514176166@qq.com
 * @date 2023-05-18 15:50:54
 */
public interface UserWalletDetailService extends IService<UserWalletDetailEntity> {

    /**
     * 分页查询用户钱包明细数据
     * @param request
     * @return
     */
    Page<UserWalletDetailEntity> getUserWalletDetailList(UserWalletDetailRequest request);

    /**
     * 获取查询条件
     *
     * @param request
     * @return
     */
    QueryWrapper<UserWalletDetailEntity> getQueryWrapper(UserWalletDetailRequest request);
}

