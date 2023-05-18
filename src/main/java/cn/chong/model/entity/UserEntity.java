package cn.chong.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

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
@TableName("user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;
	/**
	 * 用户账号
	 */
	private String userAccount;
	/**
	 * 用户密码
	 */
	private String userPassword;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 用户电话
	 */
	private String userPhone;
	/**
	 * 用户角色
	 */
	private String userRole;
	/**
	 * 用户身份证
	 */
	private String userIdentify;
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
