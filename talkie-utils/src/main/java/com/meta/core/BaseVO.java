package com.meta.core;

/**
 * 抽象类用于权限用户
 */
public class BaseVO implements java.io.Serializable{
	
	/**
	 * 序列号
	 */
	protected static final long serialVersionUID = 1L;
	
	 /**
	 * 用户userid
	 */
	protected Long userid;

	 /**
	 * 用户id
	 */
	protected Long id;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
