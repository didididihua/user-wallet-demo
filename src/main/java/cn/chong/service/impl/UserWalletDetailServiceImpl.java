package cn.chong.service.impl;

import cn.chong.mapper.UserWalletDetailMapper;
import cn.chong.model.entity.UserWalletDetailEntity;
import cn.chong.service.UserWalletDetailService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



@Service("userWalletDetailService")
public class UserWalletDetailServiceImpl extends ServiceImpl<UserWalletDetailMapper, UserWalletDetailEntity> implements UserWalletDetailService {


}