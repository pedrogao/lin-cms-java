package com.lin.cms.interfaces;

public interface BaseLogMapper<T> {
    int insertSelective(T record);
}
