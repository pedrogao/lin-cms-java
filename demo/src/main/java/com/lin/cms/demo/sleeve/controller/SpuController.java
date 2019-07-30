package com.lin.cms.demo.sleeve.controller;


import com.lin.cms.core.result.PageResult;
import com.lin.cms.core.result.Result;
import com.lin.cms.demo.sleeve.dto.SpuCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.Spu;
import com.lin.cms.demo.sleeve.model.SpuWithNamesDO;
import com.lin.cms.demo.sleeve.model.SuggestionDO;
import com.lin.cms.demo.sleeve.service.ISpuService;
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
@RequestMapping("/sleeve/spu")
@Validated
public class SpuController {

    @Autowired
    private ISpuService spuService;

    @PostMapping("/")
    public Result create(@RequestBody @Valid SpuCreateOrUpdateDTO dto) {
        spuService.createSpu(dto);
        return ResultGenerator.genSuccessResult("创建spu成功！");
    }

    @PutMapping("/{id}")
    public Result update(@RequestBody @Valid SpuCreateOrUpdateDTO dto, @PathVariable @Positive(message = "id必须为正整数") Long id) {
        spuService.updateSpu(dto, id);
        return ResultGenerator.genSuccessResult("更新spu成功！");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        spuService.deleteSpu(id);
        return ResultGenerator.genSuccessResult("删除spu成功！");
    }

    @GetMapping("/{id}")
    public Spu get(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        Spu spu = spuService.getById(id);
        if (spu == null) {
            throw new NotFound("未找到相关的spu");
        }
        return spu;
    }

    @GetMapping("/{id}/names")
    public SpuWithNamesDO getWithNames(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        SpuWithNamesDO spu = spuService.getWithNames(id);
        return spu;
    }


    @GetMapping("/page")
    public PageResult<Spu> page(@RequestParam(name = "count", required = false, defaultValue = "10")
                                @Min(value = 1, message = "count必须为正整数") Long count,
                                @RequestParam(name = "page", required = false, defaultValue = "0")
                                @Min(value = 0, message = "page必须为整数，且大于等于0") Long page) {
        PageResult<Spu> pageResult = spuService.getSpuByPage(count, page);
        if (pageResult.getTotalNums() == 0) {
            throw new NotFound("未找到相关的spu");
        }
        return pageResult;
    }

    @GetMapping("/suggestion")
    public List<SuggestionDO> suggest(@RequestParam(name = "id", required = false)
                                      @Min(value = 1, message = "id必须为正整数") Long id) {
        return spuService.getSuggestions(id);
    }
}
