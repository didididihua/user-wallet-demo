package cn.chong.model.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: tangchongjie
 * @creattime: 2023--05--18 16:15
 * @description 用户钱包vo
 */
@Data
public class UserWalletVo {

    /**
     * 钱包id
     */private Long id;
    /**
     * 钱包所属的用户id
     */
    private Long userId;
    /**
     * 总金额
     */
    private BigDecimal totalAmount;

}
