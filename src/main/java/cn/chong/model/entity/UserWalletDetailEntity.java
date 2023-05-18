package cn.chong.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * 
 * @author tangchongjie
 * @email 1514176166@qq.com
 * @date 2023-05-18 15:50:54
 */
@Data
@TableName("user_wallet_detail")
public class UserWalletDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
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
	 * 更改前金额
	 */
	private BigDecimal beforeAmount;
	/**
	 * 更改后金额
	 */
	private BigDecimal afterAmount;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;
	/**
	 * 修改时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date updateTime;
	/**
	 * 逻辑删除
	 */
	private Integer isDelete;
	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 订单号,如这次用户钱包金额的更改时因为购买商品，那么就需要传入商品服务产生的订单信息的订单号
	 */
	private Long orderId;

}
