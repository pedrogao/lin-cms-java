package com.lin.cms.demo.sleeve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.cms.demo.sleeve.model.SimpleSpuDO;
import com.lin.cms.demo.sleeve.model.Theme;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author pedro
 * @since 2019-07-23
 */
public interface ThemeMapper extends BaseMapper<Theme> {

    List<SimpleSpuDO> getSpus(@Param("id") Long id);
}
