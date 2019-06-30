package com.lin.cms.struct.service.base;


import com.lin.cms.db.CrudMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
@Slf4j
public abstract class AbstractService<T> implements Service<T> {

    @Autowired
    protected CrudMapper<T> crudMapper;

    private Class<T> modelClass;    // 当前泛型真实类型的Class

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    public void save(T model) {
        crudMapper.insertSelective(model);
    }

    public void saveWithTimeCreate(T model) {
        // 在save层面上支持createTime的自动创建
        try {
            Field createTime = model.getClass().getDeclaredField("createTime");
            Field updateTime = model.getClass().getDeclaredField("updateTime");
            Date now = new Date();
            createTime.setAccessible(true);
            createTime.set(model, now);
            updateTime.setAccessible(true);
            updateTime.set(model, now);
            crudMapper.insertSelective(model);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            crudMapper.insertSelective(model);
        }
    }

    public void save(List<T> models) {
        crudMapper.insertList(models);
    }

    public void deleteById(Integer id) {
        crudMapper.deleteByPrimaryKey(id);
    }

    public void deleteByIds(String ids) {
        crudMapper.deleteByIds(ids);
    }

    public void update(T model) {
        crudMapper.updateByPrimaryKeySelective(model);
    }

    public T findById(Integer id) {
        return crudMapper.selectByPrimaryKey(id);
    }

    @Override
    public T findBy(String fieldName, Object value) {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return crudMapper.selectOne(model);
        } catch (Exception e) {
            if (e instanceof TooManyResultsException) {
                log.error("查到了太多结果：  " + e.getMessage());
            }
            return null;
        }
    }

    public List<T> findByIds(String ids) {
        return crudMapper.selectByIds(ids);
    }

    public List<T> findByCondition(Condition condition) {
        return crudMapper.selectByCondition(condition);
    }

    public List<T> findAll() {
        return crudMapper.selectAll();
    }
}
