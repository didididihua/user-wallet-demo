package cn.chong.model.dto.userWallet;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: tangchongjie
 * @creattime: 2023--05--18 17:01
 * @description 用户消费时的请求参数
 */
@Data
public class UserConsumerRequest {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 更改金额
     */
    private BigDecimal updateAmount;
    /**
     * 更改操作类型（0.收入 1.支出）
     */
    private Integer updateType;
    /**
     * 更改操作来源（0.商品购买 1.退款 2.提现 3.充值）
     */
    private Integer updateSource;
    /**
     * 订单号,如这次用户钱包金额的更改时因为购买商品，那么就需要传入商品服务产生的订单信息的订单号
     */
    private Long orderId;

}
