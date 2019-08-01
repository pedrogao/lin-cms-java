package com.lin.cms.demo.sleeve.controller;


import com.lin.cms.core.result.PageResult;
import com.lin.cms.core.result.Result;
import com.lin.cms.demo.sleeve.dto.CategoryCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.enums.CategoryRootOrNot;
import com.lin.cms.demo.sleeve.model.Category;
import com.lin.cms.demo.sleeve.model.CategorySuggestionDO;
import com.lin.cms.demo.sleeve.service.ICategoryService;
import com.lin.cms.exception.Forbidden;
import com.lin.cms.exception.NotFound;
import com.lin.cms.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * 自顶向下进行开发
 *
 * @author pedro
 * @since 2019-07-23
 */
@RestController
@RequestMapping("/sleeve/category")
@Validated
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @PostMapping("/")
    public Result create(@RequestBody @Valid CategoryCreateOrUpdateDTO dto) {
        // parent_id 与 is_root 不能同时有
        checkRootAndParent(dto);
        categoryService.createCategory(dto);
        return ResultGenerator.genSuccessResult("创建商品种类成功！");
    }

    @PutMapping("/{id}")
    public Result update(@RequestBody @Valid CategoryCreateOrUpdateDTO dto, @PathVariable @Positive(message = "id必须为正整数") Long id) {
        checkRootAndParent(dto);
        categoryService.updateCategory(dto, id);
        return ResultGenerator.genSuccessResult("更新商品种类成功！");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        // 检验是否为 root
        categoryService.deleteCategory(id);
        return ResultGenerator.genSuccessResult("删除商品种类成功！");
    }

    @GetMapping("/{id}")
    public Category get(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        Category category = categoryService.getById(id);
        if (category == null) {
            throw new NotFound("未找到相关的分类");
        }
        return category;
    }


    @GetMapping("/page")
    public PageResult<Category> page(@RequestParam(name = "count", required = false, defaultValue = "10")
                                     @Min(value = 1, message = "count必须为正整数") Long count,
                                     @RequestParam(name = "page", required = false, defaultValue = "0")
                                     @Min(value = 0, message = "page必须为整数，且大于等于0") Long page,
                                     @RequestParam(name = "root", required = false, defaultValue = "0")
                                     @Min(value = 0, message = "root必须为0或1")
                                     @Max(value = 1, message = "root必须为0或1") Integer root) {
        PageResult<Category> pageResult = categoryService.getCategoryByPage(count, page, root);
        if (pageResult.getTotalNums() == 0) {
            throw new NotFound("未找到相关的分类");
        }
        return pageResult;
    }

    @GetMapping("/sub_page")
    public PageResult<Category> getSubPage(@RequestParam(name = "count", required = false, defaultValue = "10")
                                           @Min(value = 1, message = "count必须为正整数") Long count,
                                           @RequestParam(name = "page", required = false, defaultValue = "0")
                                           @Min(value = 0, message = "page必须为整数，且大于等于0") Long page,
                                           @RequestParam(name = "id")
                                           @Min(value = 0, message = "id必须为整数，且大于等于0") Integer id) {
        PageResult<Category> pageResult = categoryService.getSubCategoryByPage(count, page, id);
        if (pageResult.getTotalNums() == 0) {
            throw new NotFound("未找到相关的分类");
        }
        return pageResult;
    }

    @GetMapping("/suggestion")
    public List<CategorySuggestionDO> suggest(@RequestParam(name = "id", required = false)
                                              @Min(value = 1, message = "count必须为正整数") Long id) {
        return categoryService.getSuggestions(id);
    }

    private void checkRootAndParent(CategoryCreateOrUpdateDTO dto) {
        if ((dto.getIsRoot() != null) &&
                (dto.getParentId() != null && dto.getIsRoot() == CategoryRootOrNot.ROOT.getValue())
        ) {
            throw new Forbidden("root分类不能有父分类");
        }
    }
}
