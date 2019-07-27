package com.lin.cms.demo.sleeve.controller;


import com.lin.cms.core.result.PageResult;
import com.lin.cms.core.result.Result;
import com.lin.cms.demo.sleeve.dto.SkuCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.Sku;
import com.lin.cms.demo.sleeve.service.ISkuService;
import com.lin.cms.exception.NotFound;
import com.lin.cms.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

/**
 * @author pedro
 * @since 2019-07-23
 */
@RestController
@RequestMapping("/sleeve/sku")
@Validated
public class SkuController {

    @Autowired
    private ISkuService skuService;

    @PostMapping("/")
    public Result create(@RequestBody @Valid SkuCreateOrUpdateDTO dto) {
        skuService.createSku(dto);
        return ResultGenerator.genSuccessResult("创建sku成功！");
    }

    @PutMapping("/{id}")
    public Result update(@RequestBody @Valid SkuCreateOrUpdateDTO dto, @PathVariable @Positive(message = "id必须为正整数") Long id) {
        skuService.updateSku(dto, id);
        return ResultGenerator.genSuccessResult("更新sku成功！");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        skuService.deleteSku(id);
        return ResultGenerator.genSuccessResult("删除sku成功！");
    }

    @GetMapping("/{id}")
    public Sku get(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        Sku sku = skuService.getDetailById(id);
        if (sku == null) {
            throw new NotFound("未找到相关的sku");
        }
        return sku;
    }


    @GetMapping("/page")
    public PageResult<Sku> page(@RequestParam(name = "count", required = false, defaultValue = "10")
                                @Min(value = 1, message = "count必须为正整数") Long count,
                                @RequestParam(name = "page", required = false, defaultValue = "0")
                                @Min(value = 0, message = "page必须为整数，且大于等于0") Long page) {
        PageResult<Sku> pageResult = skuService.getSkuByPage(count, page);
        if (pageResult.getTotalNums() == 0) {
            throw new NotFound("未找到相关的sku");
        }
        return pageResult;
    }

}
