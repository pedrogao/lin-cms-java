package com.lin.cms.core.result;

import java.util.List;

public class PageResult<T> {

    private long totalNums;

    private List<T> collection;

    private long page;

    private long count;

    public PageResult(long totalNums, List<T> collection, long page, long count) {
        this.totalNums = totalNums;
        this.collection = collection;
        this.page = page;
        this.count = count;
    }

    public PageResult() {
    }

    public static PageResult genPageResult(long totalNums, List collection, long page, long count) {
        return new PageResult(totalNums, collection, page, count);
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

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
