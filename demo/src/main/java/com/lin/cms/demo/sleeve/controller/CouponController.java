package com.lin.cms.demo.sleeve.controller;


import com.lin.cms.core.result.PageResult;
import com.lin.cms.core.result.Result;
import com.lin.cms.demo.sleeve.dto.CouponCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.CategorySuggestionDO;
import com.lin.cms.demo.sleeve.model.Coupon;
import com.lin.cms.demo.sleeve.model.CouponTemplate;
import com.lin.cms.demo.sleeve.model.SuggestionDO;
import com.lin.cms.demo.sleeve.service.ICouponService;
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
 * @since 2019-08-09
 */
@RestController
@RequestMapping("/sleeve/coupon")
@Validated
public class CouponController {

    @Autowired
    private ICouponService couponService;

    @PostMapping("/")
    public Result create(@RequestBody @Valid CouponCreateOrUpdateDTO dto) {
        couponService.createCoupon(dto);
        return ResultGenerator.genSuccessResult("创建优惠卷成功！");
    }

    @PutMapping("/{id}")
    public Result update(@RequestBody @Valid CouponCreateOrUpdateDTO dto, @PathVariable @Positive(message = "id必须为正整数") Long id) {
        couponService.updateCoupon(dto, id);
        return ResultGenerator.genSuccessResult("更新优惠卷成功！");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        couponService.deleteCoupon(id);
        return ResultGenerator.genSuccessResult("删除优惠卷成功！");
    }

    @GetMapping("/{id}")
    public Coupon get(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        Coupon coupon = couponService.getById(id);
        if (coupon == null) {
            throw new NotFound("未找到相关的优惠卷");
        }
        return coupon;
    }

    @GetMapping("/page")
    public PageResult<Coupon> page(@RequestParam(name = "count", required = false, defaultValue = "10")
                                   @Min(value = 1, message = "count必须为正整数") Long count,
                                   @RequestParam(name = "page", required = false, defaultValue = "0")
                                   @Min(value = 0, message = "page必须为整数，且大于等于0") Long page) {
        PageResult<Coupon> pageResult = couponService.getCouponByPage(count, page);
        if (pageResult.getTotalNums() == 0) {
            throw new NotFound("未找到相关的优惠卷");
        }
        return pageResult;
    }

    @GetMapping("/templates")
    public List<CouponTemplate> templates() {
        List<CouponTemplate> templates = couponService.getTemplates();
        if (templates == null || templates.size() == 0) {
            throw new NotFound("未找到优惠卷模板");
        }
        return templates;
    }

    @GetMapping("/suggestion")
    public List<SuggestionDO> suggest(@RequestParam(name = "id", required = false)
                                      @Min(value = 1, message = "id必须为正整数") Long id) {
        return couponService.getSuggestions(id);
    }

}
