package com.lin.cms.demo.sleeve.controller;

import com.lin.cms.core.result.Result;
import com.lin.cms.demo.sleeve.dto.BannerItemCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.BannerItem;
import com.lin.cms.demo.sleeve.service.IBannerItemService;
import com.lin.cms.exception.NotFound;
import com.lin.cms.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

/**
 * @author pedro
 * @since 2019-07-23
 */
@RestController
@RequestMapping("/sleeve/banner_item")
public class BannerItemController {

    @Autowired
    private IBannerItemService bannerItemService;

    @PostMapping("/")
    public Result create(@RequestBody @Valid BannerItemCreateOrUpdateDTO dto) {
        bannerItemService.createBannerItem(dto);
        return ResultGenerator.genSuccessResult("创建商品banner-item成功！");
    }

    @PutMapping("/{id}")
    public Result update(@RequestBody @Valid BannerItemCreateOrUpdateDTO dto, @PathVariable @Positive(message = "id必须为正整数") Integer id) {
        bannerItemService.updateBannerItem(dto, id);
        return ResultGenerator.genSuccessResult("更新商品banner-item成功！");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable @Positive(message = "id必须为正整数") Integer id) {
        bannerItemService.deleteBannerItem(id);
        return ResultGenerator.genSuccessResult("删除商品banner-item成功！");
    }

    @GetMapping("/{id}")
    public BannerItem get(@PathVariable @Positive(message = "id必须为正整数") Integer id) {
        BannerItem bannerItem = bannerItemService.getById(id);
        if (bannerItem == null) {
            throw new NotFound("未找到相关的banner-item");
        }
        return bannerItem;
    }
}
