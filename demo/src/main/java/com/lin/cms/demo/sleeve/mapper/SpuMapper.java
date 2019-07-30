package com.lin.cms.demo.sleeve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.cms.demo.sleeve.model.Spu;
import com.lin.cms.demo.sleeve.model.SpuWithNamesDO;
import com.lin.cms.demo.sleeve.model.SuggestionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author pedro
 * @since 2019-07-23
 */
public interface SpuMapper extends BaseMapper<Spu> {

    SpuWithNamesDO getWithNames(@Param("id") Long id);

    List<SuggestionDO> getSuggestions(@Param("id") Long id);
}
