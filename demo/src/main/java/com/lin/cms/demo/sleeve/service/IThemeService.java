package com.lin.cms.demo.sleeve.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.sleeve.dto.ThemeCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.Theme;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pedro
 * @since 2019-07-23
 */
public interface IThemeService extends IService<Theme> {

    void createTheme(ThemeCreateOrUpdateDTO dto);

    void updateTheme(ThemeCreateOrUpdateDTO dto, Integer id);

    void deleteTheme(Integer id);

    PageResult<Theme> getThemeByPage(Integer count, Integer page);
}
