package com.lin.cms.demo.sleeve.controller;


import com.lin.cms.core.annotation.GroupRequired;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.core.result.Result;
import com.lin.cms.demo.sleeve.dto.ThemeCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.dto.ThemeSpuCreateDTO;
import com.lin.cms.demo.sleeve.model.SimpleSpuDO;
import com.lin.cms.demo.sleeve.model.SuggestionDO;
import com.lin.cms.demo.sleeve.model.Theme;
import com.lin.cms.demo.sleeve.service.IThemeService;
import com.lin.cms.exception.NotFound;
import com.lin.cms.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author pedro
 * @since 2019-07-23
 */
@RestController
@RequestMapping("/sleeve/theme")
@Validated
public class ThemeController {

    @Autowired
    private IThemeService themeService;

    @PostMapping("")
    @RouteMeta(module = "主题", auth = "创建主题", mount = true)
    @GroupRequired
    public Result create(@RequestBody @Validated ThemeCreateOrUpdateDTO dto) {
        themeService.createTheme(dto);
        return ResultGenerator.genSuccessResult("创建商品主题成功！");
    }

    @PutMapping("/{id}")
    @RouteMeta(module = "主题", auth = "更新主题", mount = true)
    @GroupRequired
    public Result update(@RequestBody @Validated ThemeCreateOrUpdateDTO dto, @PathVariable @Positive(message = "id必须为正整数") Long id) {
        themeService.updateTheme(dto, id);
        return ResultGenerator.genSuccessResult("更新商品主题成功！");
    }

    @DeleteMapping("/{id}")
    @RouteMeta(module = "主题", auth = "删除主题", mount = true)
    @GroupRequired
    public Result delete(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        themeService.deleteTheme(id);
        return ResultGenerator.genSuccessResult("删除商品主题成功！");
    }

    @GetMapping("/{id}")
    public Theme get(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        Theme theme = themeService.getById(id);
        if (theme == null) {
            throw new NotFound("未找到相关的主题");
        }
        return theme;
    }


    @GetMapping("/page")
    public PageResult<Theme> page(@RequestParam(name = "count", required = false, defaultValue = "10")
                                  @Min(value = 1, message = "count必须为正整数") Long count,
                                  @RequestParam(name = "page", required = false, defaultValue = "0")
                                  @Min(value = 0, message = "page必须为整数，且大于等于0") Long page) {
        PageResult<Theme> pageResult = themeService.getThemeByPage(count, page);
        return pageResult;
    }

    /**
     * 选择 theme/spus?id=1 作为 规则
     * 而没有选择 theme/1/spu 最为路由规则的主要原因是
     * theme下的spus以后可能会通过其它的属性进行筛选，例如 name
     */
    @GetMapping("/spus")
    public List<SimpleSpuDO> getSpus(@RequestParam(name = "id")
                                     @Min(value = 0, message = "id必须为整数，且大于等于0") Long id) {
        return themeService.getSpus(id);
    }

    @GetMapping("/spu/suggestion")
    public List<SuggestionDO> suggest(@RequestParam(name = "id")
                                      @Min(value = 0, message = "id必须为整数，且大于等于0") Long id) {
        return themeService.getSpuSuggestion(id);
    }

    @DeleteMapping("/spu/{id}")
    @RouteMeta(module = "主题", auth = "删除主题下的spu", mount = true)
    @GroupRequired
    public Result deleteSpu(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        themeService.deleteThemeSpu(id);
        return ResultGenerator.genSuccessResult("删除成功！");
    }

    @PostMapping("/spu")
    @RouteMeta(module = "主题", auth = "添加主题下的spu", mount = true)
    @GroupRequired
    public Result addThemeSpu(@RequestBody @Validated ThemeSpuCreateDTO dto) {
        themeService.addThemeSpu(dto);
        return ResultGenerator.genSuccessResult("添加成功！");
    }
}
