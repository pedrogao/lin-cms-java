package com.lin.cms.demo.sleeve.controller;


import com.lin.cms.core.annotation.GroupRequired;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.core.result.Result;
import com.lin.cms.demo.sleeve.bo.SpecKeyAndItems;
import com.lin.cms.demo.sleeve.dto.SpecKeyCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.Banner;
import com.lin.cms.demo.sleeve.model.BrandSuggestionDO;
import com.lin.cms.demo.sleeve.model.SpecKey;
import com.lin.cms.demo.sleeve.model.SpecKeySuggestionDO;
import com.lin.cms.demo.sleeve.service.ISpecKeyService;
import com.lin.cms.exception.Forbidden;
import com.lin.cms.exception.NotFound;
import com.lin.cms.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author pedro
 * @since 2019-07-23
 */
@RestController
@RequestMapping("/sleeve/spec_key")
@Validated
public class SpecKeyController {

    @Autowired
    private ISpecKeyService specKeyService;

    @PostMapping("/")
    @RouteMeta(module = "规格名", auth = "创建规格名", mount = true)
    @GroupRequired
    public Result create(@RequestBody @Validated SpecKeyCreateOrUpdateDTO dto) {
        specKeyService.createSpecKey(dto);
        return ResultGenerator.genSuccessResult("创建规格键成功！");
    }

    @PutMapping("/{id}")
    @RouteMeta(module = "规格名", auth = "更新规格名", mount = true)
    @GroupRequired
    public Result update(@RequestBody @Validated SpecKeyCreateOrUpdateDTO dto, @PathVariable @Positive(message = "id必须为正整数") Long id) {
        // 更新的时候检查，然后更新 sku 里面的specs
        // specKeyService.updateSpecKey(dto, id);
        // return ResultGenerator.genSuccessResult("更新规格键成功！");
        return ResultGenerator.genSuccessResult("不允许修改！");
    }

    @DeleteMapping("/{id}")
    @RouteMeta(module = "规格名", auth = "删除规格名", mount = true)
    @GroupRequired
    public Result delete(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        // specKeyService.deleteSpecKey(id);
        // return ResultGenerator.genSuccessResult("删除规格键成功！");
        return ResultGenerator.genSuccessResult("不允许删除！");
    }

    @GetMapping("/{id}")
    public SpecKey get(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        SpecKey specKey = specKeyService.getById(id);
        if (specKey == null) {
            throw new NotFound("未找到相关的规格键");
        }
        return specKey;
    }

    @GetMapping("/{id}/detail")
    public SpecKeyAndItems getDetail(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        SpecKeyAndItems specKeyAndItems = specKeyService.getKeyAndValuesById(id);
        return specKeyAndItems;
    }


    @GetMapping("/page")
    public PageResult<SpecKey> page(@RequestParam(name = "count", required = false, defaultValue = "10")
                                    @Min(value = 1, message = "count必须为正整数") Long count,
                                    @RequestParam(name = "page", required = false, defaultValue = "0")
                                    @Min(value = 0, message = "page必须为整数，且大于等于0") Long page) {
        PageResult<SpecKey> pageResult = specKeyService.getSpecKeyByPage(count, page);
        if (pageResult.getTotalNums() == 0) {
            throw new NotFound("未找到相关的规格键");
        }
        return pageResult;
    }

    @GetMapping("/suggestion")
    public List<SpecKeySuggestionDO> suggest(@RequestParam(name = "id", required = false)
                                             @Min(value = 1, message = "id必须为正整数") Long id,
                                             @RequestParam(name = "spu_id", required = false)
                                             @Min(value = 1, message = "id必须为正整数") Long spuId) {
        return specKeyService.getSuggestions(id, spuId);
    }
}
