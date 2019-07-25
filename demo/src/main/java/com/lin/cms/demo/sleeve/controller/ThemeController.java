package com.lin.cms.demo.sleeve.controller;


import com.lin.cms.core.result.PageResult;
import com.lin.cms.core.result.Result;
import com.lin.cms.demo.sleeve.dto.ThemeCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.Theme;
import com.lin.cms.demo.sleeve.service.IThemeService;
import com.lin.cms.exception.NotFound;
import com.lin.cms.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

/**
 * @author pedro
 * @since 2019-07-23
 */
@RestController
@RequestMapping("/sleeve/theme")
public class ThemeController {

    @Autowired
    private IThemeService themeService;

    @PostMapping("/")
    public Result create(@RequestBody @Valid ThemeCreateOrUpdateDTO dto) {
        themeService.createTheme(dto);
        return ResultGenerator.genSuccessResult("创建商品主题成功！");
    }

    @PutMapping("/{id}")
    public Result update(@RequestBody @Valid ThemeCreateOrUpdateDTO dto, @PathVariable @Positive(message = "id必须为正整数") Integer id) {
        themeService.updateTheme(dto, id);
        return ResultGenerator.genSuccessResult("更新商品主题成功！");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable @Positive(message = "id必须为正整数") Integer id) {
        themeService.deleteTheme(id);
        return ResultGenerator.genSuccessResult("删除商品主题成功！");
    }

    @GetMapping("/{id}")
    public Theme get(@PathVariable @Positive(message = "id必须为正整数") Integer id) {
        Theme theme = themeService.getById(id);
        if (theme == null) {
            throw new NotFound("未找到相关的主题");
        }
        return theme;
    }


    @GetMapping("/page")
    public PageResult<Theme> page(@RequestParam(name = "count", required = false, defaultValue = "10")
                                  @Min(value = 1, message = "count必须为正整数") Integer count,
                                  @RequestParam(name = "page", required = false, defaultValue = "0")
                                  @Min(value = 0, message = "page必须为整数，且大于等于0") Integer page) {
        PageResult<Theme> pageResult = themeService.getThemeByPage(count, page);
        if (pageResult.getTotalNums() == 0) {
            throw new NotFound("未找到相关的主题");
        }
        return pageResult;
    }
}
