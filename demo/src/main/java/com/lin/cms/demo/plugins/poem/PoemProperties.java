package com.lin.cms.demo.plugins.poem;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("lin.cms.poem")
public class PoemProperties {

    private Integer limit;


    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
