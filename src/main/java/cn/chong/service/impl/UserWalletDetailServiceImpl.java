package cn.chong.service.impl;

import cn.chong.constant.CommonConstant;
import cn.chong.mapper.UserWalletDetailMapper;
import cn.chong.model.dto.userWalletDetail.UserWalletDetailRequest;
import cn.chong.model.entity.UserWalletDetailEntity;
import cn.chong.service.UserWalletDetailService;
import cn.chong.utils.SqlUtils;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



@Service("userWalletDetailService")
@Slf4j
public class UserWalletDetailServiceImpl extends ServiceImpl<UserWalletDetailMapper, UserWalletDetailEntity> implements UserWalletDetailService {
    @Override
    public Page<UserWalletDetailEntity> getUserWalletDetailList(UserWalletDetailRequest request) {

        log.info("查询用户钱包数据明显 start");
        long current = request.getCurrent();
        long pageSize = request.getPageSize();

        Page<UserWalletDetailEntity> page = baseMapper.selectPage(new Page<>(current, pageSize),
                getQueryWrapper(request));
        log.info("查询用户钱包数据明显 end");

        return page;
    }


    @Override
    public QueryWrapper<UserWalletDetailEntity> getQueryWrapper(UserWalletDetailRequest request) {

        QueryWrapper<UserWalletDetailEntity> queryWrapper = new QueryWrapper<>();
        if (request == null) {
            return queryWrapper;
        }
        Long userId = request.getUserId();
        Long id = request.getId();
        String sortOrder = request.getSortOrder();
        String sortField = request.getSortField();

        queryWrapper.eq(ObjUtil.isNotEmpty(userId), "user_id", userId);
        queryWrapper.eq(ObjUtil.isNotEmpty(id), "id", id);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);

        return queryWrapper;
    }

}