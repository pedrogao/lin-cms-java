package com.lin.cms.demo.sleeve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.cms.demo.sleeve.model.Category;
import com.lin.cms.demo.sleeve.model.CategorySuggestionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author pedro
 * @since 2019-07-23
 */
public interface CategoryMapper extends BaseMapper<Category> {
    List<CategorySuggestionDO> getSuggestions(@Param("id") Long id);

    Category getById(@Param("id") Long id);
}
