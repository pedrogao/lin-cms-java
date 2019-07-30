package com.lin.cms.demo.sleeve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.cms.demo.sleeve.model.Brand;
import com.lin.cms.demo.sleeve.model.BrandSuggestionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author pedro
 * @since 2019-07-23
 */
public interface BrandMapper extends BaseMapper<Brand> {

    List<BrandSuggestionDO> getSuggestions(@Param("id") Long id);
}
