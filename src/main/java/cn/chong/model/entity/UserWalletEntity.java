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
@TableName("user_wallet")
public class UserWalletEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 钱包id
	 */
	@TableId
	private Long id;
	/**
	 * 钱包所属的用户id
	 */
	private Long userId;
	/**
	 * 总金额
	 */
	private BigDecimal totalAmount;
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

}
