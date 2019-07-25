package com.lin.cms.demo.sleeve.controller;


import com.lin.cms.core.result.PageResult;
import com.lin.cms.core.result.Result;
import com.lin.cms.demo.sleeve.dto.CategoryCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.Category;
import com.lin.cms.demo.sleeve.service.ICategoryService;
import com.lin.cms.exception.NotFound;
import com.lin.cms.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

/**
 * 自顶向下进行开发
 *
 * @author pedro
 * @since 2019-07-23
 */
@RestController
@RequestMapping("/sleeve/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @PostMapping("/")
    public Result create(@RequestBody @Valid CategoryCreateOrUpdateDTO dto) {
        categoryService.createCategory(dto);
        return ResultGenerator.genSuccessResult("创建商品种类成功！");
    }

    @PutMapping("/{id}")
    public Result update(@RequestBody @Valid CategoryCreateOrUpdateDTO dto, @PathVariable @Positive(message = "id必须为正整数") Integer id) {
        categoryService.updateCategory(dto, id);
        return ResultGenerator.genSuccessResult("更新商品种类成功！");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable @Positive(message = "id必须为正整数") Integer id) {
        categoryService.deleteCategory(id);
        return ResultGenerator.genSuccessResult("删除商品种类成功！");
    }

    @GetMapping("/{id}")
    public Category get(@PathVariable @Positive(message = "id必须为正整数") Integer id) {
        Category category = categoryService.getById(id);
        if (category == null) {
            throw new NotFound("未找到相关的分类");
        }
        return category;
    }


    @GetMapping("/page")
    public PageResult<Category> page(@RequestParam(name = "count", required = false, defaultValue = "10")
                                     @Min(value = 1, message = "count必须为正整数") Integer count,
                                     @RequestParam(name = "page", required = false, defaultValue = "0")
                                     @Min(value = 0, message = "page必须为整数，且大于等于0") Integer page) {
        PageResult<Category> pageResult = categoryService.getCategoryByPage(count, page);
        if (pageResult.getTotalNums() == 0) {
            throw new NotFound("未找到相关的分类");
        }
        return pageResult;
    }
}
