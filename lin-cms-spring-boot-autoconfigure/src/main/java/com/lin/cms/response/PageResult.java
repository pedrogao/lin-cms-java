package com.lin.cms.response;

import java.util.List;

public class PageResult<T> {

    private long total;

    private List<T> items;

    private long page;

    private long count;

    public PageResult(long total, List<T> items, long page, long count) {
        this.total = total;
        this.items = items;
        this.page = page;
        this.count = count;
    }

    public PageResult() {
    }

    public static PageResult genPageResult(long total, List items, long page, long count) {
        return new PageResult(total, items, page, count);
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

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
