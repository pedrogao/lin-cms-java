package com.lin.cms.demo.sleeve.controller;


import com.lin.cms.core.result.PageResult;
import com.lin.cms.core.result.Result;
import com.lin.cms.demo.sleeve.dto.BannerCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.Banner;
import com.lin.cms.demo.sleeve.service.IBannerService;
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
@RequestMapping("/sleeve/banner")
@Validated
public class BannerController {

    @Autowired
    private IBannerService bannerService;

    @PostMapping("/")
    public Result create(@RequestBody @Valid BannerCreateOrUpdateDTO dto) {
        bannerService.createBanner(dto);
        return ResultGenerator.genSuccessResult("创建商品banner成功！");
    }

    @PutMapping("/{id}")
    public Result update(@RequestBody @Valid BannerCreateOrUpdateDTO dto, @PathVariable @Positive(message = "id必须为正整数") Long id) {
        bannerService.updateBanner(dto, id);
        return ResultGenerator.genSuccessResult("更新商品banner成功！");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        bannerService.deleteBanner(id);
        return ResultGenerator.genSuccessResult("删除商品banner成功！");
    }

    @GetMapping("/{id}")
    public Banner get(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        Banner banner = bannerService.getById(id);
        if (banner == null) {
            throw new NotFound("未找到相关的banner");
        }
        return banner;
    }


    @GetMapping("/page")
    public PageResult<Banner> page(@RequestParam(name = "count", required = false, defaultValue = "10")
                                   @Min(value = 1, message = "count必须为正整数") Long count,
                                   @RequestParam(name = "page", required = false, defaultValue = "0")
                                   @Min(value = 0, message = "page必须为整数，且大于等于0") Long page) {
        PageResult<Banner> pageResult = bannerService.getBannerByPage(count, page);
        if (pageResult.getTotalNums() == 0) {
            throw new NotFound("未找到相关的banner");
        }
        return pageResult;
    }
}
