package com.lin.cms.demo.sleeve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.cms.demo.sleeve.model.SuggestionDO;
import com.lin.cms.demo.sleeve.model.ThemeSpu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author pedro
 * @since 2019-07-23
 */
public interface ThemeSpuMapper extends BaseMapper<ThemeSpu> {

    List<SuggestionDO> getSpuSuggestion(@Param("id") Long id);

}
