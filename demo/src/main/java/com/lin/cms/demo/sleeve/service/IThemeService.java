package com.lin.cms.demo.sleeve.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.sleeve.dto.ThemeCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.dto.ThemeSpuCreateDTO;
import com.lin.cms.demo.sleeve.model.SimpleSpuDO;
import com.lin.cms.demo.sleeve.model.SuggestionDO;
import com.lin.cms.demo.sleeve.model.Theme;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author pedro
 * @since 2019-07-23
 */
public interface IThemeService extends IService<Theme> {

    void createTheme(ThemeCreateOrUpdateDTO dto);

    void updateTheme(ThemeCreateOrUpdateDTO dto, Long id);

    void deleteTheme(Long id);

    PageResult<Theme> getThemeByPage(Long count, Long page);

    List<SimpleSpuDO> getSpus(Long id);

    void deleteThemeSpu(Long id);

    List<SuggestionDO> getSpuSuggestion(Long id);

    void addThemeSpu(ThemeSpuCreateDTO dto);
}
