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
public class PageResultVO<T> {

    private long total;

    private List<T> items;

    private long page;

    private long count;

    public static PageResultVO genPageResult(long total, List items, long page, long count) {
        return new PageResultVO(total, items, page, count);
    }
}
