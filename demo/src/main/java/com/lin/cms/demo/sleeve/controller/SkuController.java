package com.lin.cms.demo.sleeve.controller;


import com.lin.cms.core.annotation.GroupRequired;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.core.result.Result;
import com.lin.cms.demo.sleeve.dto.SkuCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.Sku;
import com.lin.cms.demo.sleeve.model.SkuWithNameDO;
import com.lin.cms.demo.sleeve.model.SuggestionDO;
import com.lin.cms.demo.sleeve.service.ISkuService;
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
@RequestMapping("/sleeve/sku")
@Validated
public class SkuController {

    @Autowired
    private ISkuService skuService;

    @PostMapping("")
    @RouteMeta(module = "sku", auth = "创建sku", mount = true)
    @GroupRequired
    public Result create(@RequestBody @Validated SkuCreateOrUpdateDTO dto) {
        skuService.createSku(dto);
        return ResultGenerator.genSuccessResult("创建sku成功！");
    }

    @PutMapping("/{id}")
    @RouteMeta(module = "sku", auth = "更新sku", mount = true)
    @GroupRequired
    public Result update(@RequestBody @Validated SkuCreateOrUpdateDTO dto, @PathVariable @Positive(message = "id必须为正整数") Long id) {
        skuService.updateSku(dto, id);
        return ResultGenerator.genSuccessResult("更新sku成功！");
    }

    @DeleteMapping("/{id}")
    @RouteMeta(module = "sku", auth = "删除sku", mount = true)
    @GroupRequired
    public Result delete(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        skuService.deleteSku(id);
        return ResultGenerator.genSuccessResult("删除sku成功！");
    }

    @GetMapping("/{id}")
    public Sku get(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        Sku sku = skuService.getById(id);
        if (sku == null) {
            throw new NotFound("未找到相关的sku");
        }
        return sku;
    }

    @GetMapping("/{id}/name")
    public SkuWithNameDO getWithName(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        SkuWithNameDO sku = skuService.getWithName(id);
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
        return pageResult;
    }


    @GetMapping("/suggestion")
    public List<SuggestionDO> suggest(@RequestParam(name = "id", required = false)
                                      @Min(value = 1, message = "id必须为正整数") Long id,
                                      @RequestParam(name = "spu_id", required = false)
                                      @Min(value = 1, message = "spu_id必须为正整数") Long spuId) {
        return skuService.getSuggestions(id, spuId);
    }

    // 在 spu 下选择 spec_key 而后， 与 spu 相关的 sku 在 spec_key 下选择 spec_value
    @GetMapping("/spec_value_id")
    public Long getSpecValueId(@RequestParam(name = "key_id", required = false)
                               @Min(value = 1, message = "key_id必须为正整数") Long keyId,
                               @RequestParam(name = "sku_id", required = false)
                               @Min(value = 1, message = "sku_id必须为正整数") Long skuId) {
        return skuService.getSpecValueId(keyId, skuId);
    }
}
