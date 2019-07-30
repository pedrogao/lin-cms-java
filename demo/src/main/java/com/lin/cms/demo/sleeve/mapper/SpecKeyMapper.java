package com.lin.cms.demo.sleeve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.cms.demo.sleeve.model.SpecKey;
import com.lin.cms.demo.sleeve.model.SpecKeySuggestionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author pedro
 * @since 2019-07-23
 */
public interface SpecKeyMapper extends BaseMapper<SpecKey> {

    List<SpecKeySuggestionDO> getSuggestions(@Param("id") Long id);
}
