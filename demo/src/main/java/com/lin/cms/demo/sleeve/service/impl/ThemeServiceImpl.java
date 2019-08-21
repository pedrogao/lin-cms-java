package com.lin.cms.demo.sleeve.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.dto.ThemeCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.dto.ThemeSpuCreateDTO;
import com.lin.cms.demo.sleeve.mapper.ThemeMapper;
import com.lin.cms.demo.sleeve.mapper.ThemeSpuMapper;
import com.lin.cms.demo.sleeve.model.SimpleSpuDO;
import com.lin.cms.demo.sleeve.model.SuggestionDO;
import com.lin.cms.demo.sleeve.model.Theme;
import com.lin.cms.demo.sleeve.model.ThemeSpu;
import com.lin.cms.demo.sleeve.service.IThemeService;
import com.lin.cms.exception.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pedro
 * @since 2019-07-23
 */
@Service
public class ThemeServiceImpl extends ServiceImpl<ThemeMapper, Theme> implements IThemeService {

    @Autowired
    private ThemeSpuMapper themeSpuMapper;

    @Override
    public void createTheme(ThemeCreateOrUpdateDTO dto) {
        Theme theme = new Theme();
        BeanUtils.copyProperties(dto, theme);
        this.save(theme);
    }

    @Override
    public void updateTheme(ThemeCreateOrUpdateDTO dto, Long id) {
        Theme exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的主题");
        }
        BeanUtils.copyProperties(dto, exist);
        this.updateById(exist);
    }

    @Override
    public void deleteTheme(Long id) {
        Theme exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的主题");
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public PageResult<Theme> getThemeByPage(Long count, Long page) {
        Page pager = new Page(page, count);
        IPage<Theme> iPage = this.getBaseMapper().selectPage(pager, null);
        List<Theme> categories = iPage.getRecords();
        return PageResult.genPageResult(iPage.getTotal(), categories);
    }

    @Override
    public List<SimpleSpuDO> getSpus(Long id) {
        return this.getBaseMapper().getSpus(id);
    }

    @Override
    public void deleteThemeSpu(Long id) {
        themeSpuMapper.deleteById(id);
    }

    @Override
    public List<SuggestionDO> getSpuSuggestion(Long id) {
        return themeSpuMapper.getSpuSuggestion(id);
    }

    @Override
    public void addThemeSpu(ThemeSpuCreateDTO dto) {
        ThemeSpu themeSpu = new ThemeSpu();
        themeSpu.setThemeId(dto.getThemeId());
        themeSpu.setSpuId(dto.getSpuId());
        themeSpuMapper.insert(themeSpu);
    }
}
