package com.meta;

/**
  *@author: lhq
  *@Date: 2016/12/19
  *@Time: 16:14
  *
  *Description: 请求异常  网关调用服务无法调用时出现的异常
  */
  
public class RequestException extends RuntimeException {

    /** serialVersionUID */
    private static final long serialVersionUID = 6417641452178955756L;
    
	/**
	 * 错误码
	 */
    private Integer errorCode;

    public RequestException() {
        super();
    }

    public RequestException(String message) {
        super(message);
    }
    
    public RequestException(String message,Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public RequestException(Throwable cause) {
        super(cause);
    }
    
    public RequestException(Throwable cause,Integer errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public RequestException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public RequestException(String message,Integer errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
    
    
}
