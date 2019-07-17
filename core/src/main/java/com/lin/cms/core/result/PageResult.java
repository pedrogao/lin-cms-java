package com.lin.cms.core.result;

import java.util.List;

public class PageResult<T> {

    private long totalNums;

    private List<T> collection;

    public PageResult(long totalNums, List<T> collection) {
        this.totalNums = totalNums;
        this.collection = collection;
    }

    public PageResult() {
    }

    public static PageResult genPageResult(long totalNums, List collection) {
        return new PageResult(totalNums, collection);
    }

    public long getTotalNums() {
        return totalNums;
    }

    public void setTotalNums(long totalNums) {
        this.totalNums = totalNums;
    }

    public List<T> getCollection() {
        return collection;
    }

    public void setCollection(List<T> collection) {
        this.collection = collection;
    }
}
