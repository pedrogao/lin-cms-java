package com.lin.cms.demo.sleeve.controller;


import com.lin.cms.core.annotation.GroupRequired;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.core.result.Result;
import com.lin.cms.demo.sleeve.dto.SpecValueCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.SpecValue;
import com.lin.cms.demo.sleeve.model.SuggestionDO;
import com.lin.cms.demo.sleeve.service.ISpecValueService;
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
@RequestMapping("/sleeve/spec_value")
@Validated
public class SpecValueController {
    @Autowired
    private ISpecValueService specValueService;

    @PostMapping("/")
    @RouteMeta(module = "规格值", auth = "创建规格值", mount = true)
    @GroupRequired
    public Result create(@RequestBody @Valid SpecValueCreateOrUpdateDTO dto) {
        specValueService.createSpecValue(dto);
        return ResultGenerator.genSuccessResult("创建规格键成功！");
    }

    @PutMapping("/{id}")
    @RouteMeta(module = "规格值", auth = "更新规格值", mount = true)
    @GroupRequired
    public Result update(@RequestBody @Valid SpecValueCreateOrUpdateDTO dto, @PathVariable @Positive(message = "id必须为正整数") Long id) {
        // specValueService.updateSpecValue(dto, id);
        // return ResultGenerator.genSuccessResult("更新规格键成功！");
        return ResultGenerator.genSuccessResult("不允许更新！");
    }

    @DeleteMapping("/{id}")
    @RouteMeta(module = "规格值", auth = "删除规格值", mount = true)
    @GroupRequired
    public Result delete(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        // specValueService.deleteSpecValue(id);
        // return ResultGenerator.genSuccessResult("删除规格键成功！");
        return ResultGenerator.genSuccessResult("不允许删除！");
    }

    @GetMapping("/{id}")
    public SpecValue get(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        SpecValue specValue = specValueService.getById(id);
        if (specValue == null) {
            throw new NotFound("未找到相关的规格键");
        }
        return specValue;
    }


    @GetMapping("/page")
    public PageResult<SpecValue> page(@RequestParam(name = "count", required = false, defaultValue = "10")
                                      @Min(value = 1, message = "count必须为正整数") Long count,
                                      @RequestParam(name = "page", required = false, defaultValue = "0")
                                      @Min(value = 0, message = "page必须为整数，且大于等于0") Long page) {
        PageResult<SpecValue> pageResult = specValueService.getSpecValueByPage(count, page);
        if (pageResult.getTotalNums() == 0) {
            throw new NotFound("未找到相关的规格键");
        }
        return pageResult;
    }

    @GetMapping("/suggestion")
    public List<SuggestionDO> suggest(@RequestParam(name = "key_id", required = true)
                                      @Min(value = 1, message = "id必须为正整数") Long keyId) {
        return specValueService.getSuggestions(keyId);
    }
}
