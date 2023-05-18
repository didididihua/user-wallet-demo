package cn.chong.userWallet;

import cn.chong.model.dto.userWallet.UserConsumerRequest;
import cn.chong.model.enums.WalletUpdateSourceEnum;
import cn.chong.model.enums.WalletUpdateTypeEnum;
import cn.chong.model.vo.UserWalletVo;
import cn.chong.service.UserWalletService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author: tangchongjie
 * @creattime: 2023--05--18 16:32
 * @description
 */
@SpringBootTest
public class UserWalletTest {


    @Resource
    private UserWalletService userWalletService;


    @Test
    public void testGetUserWalletData(){
        UserWalletVo userWalletVo = userWalletService.getUserWalletVo(1l);

        System.out.println(userWalletVo);
    }

    @Test
    public void testConsumer(){
        UserConsumerRequest userConsumerRequest = new UserConsumerRequest();
        userConsumerRequest.setUserId(1l);
        userConsumerRequest.setOrderId(12345l);
        userConsumerRequest.setUpdateSource(WalletUpdateSourceEnum.BUY.getValue());
        userConsumerRequest.setUpdateType(WalletUpdateTypeEnum.EXPENSE.getValue());
        userConsumerRequest.setUpdateAmount(new BigDecimal(20));
        userWalletService.consumerAmount(userConsumerRequest);

    }

}
