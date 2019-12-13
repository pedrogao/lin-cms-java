package com.lin.cms.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResult<T> {

    private long total;

    private List<T> items;

    private long page;

    private long count;

    public static PageResult genPageResult(long total, List items, long page, long count) {
        return new PageResult(total, items, page, count);
    }
}
