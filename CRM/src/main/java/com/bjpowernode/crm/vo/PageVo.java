package com.bjpowernode.crm.vo;

import java.util.List;

public class PageVo<T> {

    private String total;
    private Integer skipCount;
    private List<T> pageList;
    private Integer pageSize;

    public PageVo() {
    }

    public PageVo(String total, Integer skipCount, List<T> pageList, Integer pageSize) {
        this.total = total;
        this.skipCount = skipCount;
        this.pageList = pageList;
        this.pageSize = pageSize;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Integer getSkipCount() {
        return skipCount;
    }

    public void setSkipCount(Integer skipCount) {
        this.skipCount = skipCount;
    }

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
