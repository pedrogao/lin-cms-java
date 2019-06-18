package com.lin.cms.core.result;

import java.util.List;

public class PageResult<T> {

    private Integer totalNums;

    private List<T> collection;

    public PageResult(Integer totalNums, List<T> collection) {
        this.totalNums = totalNums;
        this.collection = collection;
    }

    public PageResult() {
    }

    public static PageResult genPageResult(Integer totalNums, List collection) {
        return new PageResult(totalNums, collection);
    }

    public Integer getTotalNums() {
        return totalNums;
    }

    public void setTotalNums(Integer totalNums) {
        this.totalNums = totalNums;
    }

    public List<T> getCollection() {
        return collection;
    }

    public void setCollection(List<T> collection) {
        this.collection = collection;
    }
}
