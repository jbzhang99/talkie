package com.meta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller对象返回值
 * @author lhq
 * @created 2016.08.08 13:54
 */
//@ApiModel(value = "返回結果集信息",description = "返回結果集信息")
public class Result<T> implements Serializable{

    /**
	 * 序列號
	 */
	private static final long serialVersionUID = 1L;

//	@ApiModelProperty(value="是否成功 [布尔类型 true-成功,false-失败]")
	private boolean successFlg = false;

//	@ApiModelProperty(value="每页条数")
    private int pageSize = 15;

//	@ApiModelProperty(value="当前页码")
    private int currPage = 1;

//	@ApiModelProperty(value="总页码数")
    private int totalPage;

//	@ApiModelProperty(value="总记录数")
    private long totalCount;

//    @ApiModelProperty(value = "返回结果集列表")
    private List<T> detailModelList = new ArrayList<T>();

//	@ApiModelProperty(value="返回结果集对象")
    private T obj;

//	@ApiModelProperty(value="错误提示信息")
    private String errorMsg;

//	@ApiModelProperty(value="错误代码")
    private Integer errorCode;

    public T getObj() {
        return obj;
    }

    public T setObj(T obj) {
        this.obj = obj;
        return this.obj;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public Result<T> setTotalCount(long totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public boolean isSuccessFlg() {
        return successFlg;
    }

    public Result<T> setSuccessFlg(boolean successFlg) {
        this.successFlg = successFlg;
        return this;
    }

    public List<T> getDetailModelList() {
        return detailModelList;
    }

    public Result<T> setDetailModelList(List<T> detailModelList) {
        this.detailModelList = detailModelList;
        return this;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Result<T> setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public Result<T> setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Result<T> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public int getCurrPage() {
        return currPage;
    }

    public Result<T> setCurrPage(int currPage) {
        this.currPage = currPage;
        return this;
    }

    public long getTotalPage() {
        if (totalCount % (long) pageSize == 0) {
            totalPage = (int)totalCount / pageSize;
        } else {
            totalPage = (int)totalCount / pageSize + 1;
        }
        return totalPage;
    }

    public Result<T> setTotalPage(int totalPage) {
        this.totalPage = totalPage;
        return this;
    }

    public Result() {
    }

    public Result(boolean successFlg) {
        this.successFlg = successFlg;
    }
}