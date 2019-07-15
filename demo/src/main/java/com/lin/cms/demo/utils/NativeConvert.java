package com.lin.cms.demo.utils;


import com.google.common.base.CaseFormat;
import com.lin.cms.core.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class NativeConvert {

    /**
     * 将 native query 返回的 tuple 类型转换给通用的DO模型
     *
     * @param tuples 数据库查询结果
     * @param clazz  DO 类型
     * @param <T>    类
     * @return List<T>
     */
    public static <T> List convertTuple(List<Tuple> tuples, Class<T> clazz) {
        List<T> res = new ArrayList();
        for (int i = 0; i < tuples.size(); i++) {
            T instance;
            try {
                instance = clazz.newInstance();
                List<TupleElement<?>> elements = tuples.get(i).getElements();
                Object[] objects = tuples.get(i).toArray();
                for (int j = 0; j < objects.length; j++) {
                    String property = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, elements.get(j).getAlias());
                    BeanUtil.setProperty(instance, property, objects[j]);
                }
                res.add(instance);
            } catch (InstantiationException | IllegalAccessException e) {
            }
        }
        return res;
    }

    public static <T> List convertMaps(List<Map> maps, Class<T> clazz) {
        List<T> res = new ArrayList();
        for (int i = 0; i < maps.size(); i++) {
            T instance;
            try {
                instance = clazz.newInstance();
                Map<String, Object> ele = maps.get(i);
                ele.forEach((k, v) -> {
                    String property = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, k);
                    BeanUtil.setProperty(instance, property, v);
                });
                res.add(instance);
            } catch (InstantiationException | IllegalAccessException e) {
            }
        }
        return res;
    }
}
