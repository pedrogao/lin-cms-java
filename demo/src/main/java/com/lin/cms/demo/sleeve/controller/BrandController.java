package com.lin.cms.demo.sleeve.controller;


import com.lin.cms.core.result.PageResult;
import com.lin.cms.core.result.Result;
import com.lin.cms.demo.sleeve.dto.BrandCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.Brand;
import com.lin.cms.demo.sleeve.service.IBrandService;
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
@RequestMapping("/sleeve/brand")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    @PostMapping("/")
    public Result create(@RequestBody @Valid BrandCreateOrUpdateDTO dto) {
        brandService.createBrand(dto);
        return ResultGenerator.genSuccessResult("创建商品品牌成功！");
    }

    @PutMapping("/{id}")
    public Result update(@RequestBody @Valid BrandCreateOrUpdateDTO dto, @PathVariable @Positive(message = "id必须为正整数") Integer id) {
        brandService.updateBrand(dto, id);
        return ResultGenerator.genSuccessResult("更新商品品牌成功！");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable @Positive(message = "id必须为正整数") Integer id) {
        brandService.deleteBrand(id);
        return ResultGenerator.genSuccessResult("删除商品品牌成功！");
    }

    @GetMapping("/{id}")
    public Brand get(@PathVariable @Positive(message = "id必须为正整数") Integer id) {
        Brand brand = brandService.getById(id);
        if (brand == null) {
            throw new NotFound("未找到相关的品牌");
        }
        return brand;
    }


    @GetMapping("/page")
    public PageResult<Brand> page(@RequestParam(name = "count", required = false, defaultValue = "10")
                                  @Min(value = 1, message = "count必须为正整数") Integer count,
                                  @RequestParam(name = "page", required = false, defaultValue = "0")
                                  @Min(value = 0, message = "page必须为整数，且大于等于0") Integer page) {
        PageResult<Brand> pageResult = brandService.getBrandByPage(count, page);
        if (pageResult.getTotalNums() == 0) {
            throw new NotFound("未找到相关的品牌");
        }
        return pageResult;
    }
}
