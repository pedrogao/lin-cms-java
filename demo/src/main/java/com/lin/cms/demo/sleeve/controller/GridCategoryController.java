package com.lin.cms.demo.sleeve.controller;


import com.lin.cms.core.annotation.GroupRequired;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.core.result.Result;
import com.lin.cms.demo.sleeve.dto.GridCategoryCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.GridCategory;
import com.lin.cms.demo.sleeve.service.IGridCategoryService;
import com.lin.cms.exception.NotFound;
import com.lin.cms.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author pedro
 * @since 2019-08-09
 */
@RestController
@RequestMapping("/sleeve/grid_category")
@Validated
public class GridCategoryController {

    @Autowired
    private IGridCategoryService gridCategoryService;

    @GetMapping("/{id}")
    public GridCategory get(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        GridCategory gridCategory = gridCategoryService.getById(id);
        if (gridCategory == null) {
            throw new NotFound("未找到相关的六宫格");
        }
        return gridCategory;
    }

    @PostMapping("/")
    @RouteMeta(module = "六宫格", auth = "创建六宫格", mount = true)
    @GroupRequired
    public Result create(@RequestBody @Validated GridCategoryCreateOrUpdateDTO dto) {
        gridCategoryService.createGridCategory(dto);
        return ResultGenerator.genSuccessResult("创建成功！");
    }

    @PutMapping("/{id}")
    @RouteMeta(module = "六宫格", auth = "更新六宫格", mount = true)
    @GroupRequired
    public Result update(@RequestBody @Validated GridCategoryCreateOrUpdateDTO dto, @PathVariable @Positive(message = "id必须为正整数") Long id) {
        gridCategoryService.updateGridCategory(dto, id);
        return ResultGenerator.genSuccessResult("更新成功！");
    }

    @DeleteMapping("/{id}")
    @RouteMeta(module = "六宫格", auth = "删除六宫格", mount = true)
    @GroupRequired
    public Result delete(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        gridCategoryService.deleteGridCategory(id);
        return ResultGenerator.genSuccessResult("删除成功！");
    }

    @GetMapping("/list")
    public List<GridCategory> getList() {
        List<GridCategory> list = gridCategoryService.getList();
        if (list == null || list.size() == 0) {
            throw new NotFound("未找到六宫格数据");
        }
        return list;
    }
}
