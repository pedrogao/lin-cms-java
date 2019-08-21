package com.lin.cms.demo.sleeve.controller;


import com.lin.cms.core.annotation.GroupRequired;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.core.result.Result;
import com.lin.cms.demo.sleeve.dto.ActivityCoverCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.ActivityCover;
import com.lin.cms.demo.sleeve.service.IActivityCoverService;
import com.lin.cms.exception.NotFound;
import com.lin.cms.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

/**
 * @author pedro
 * @since 2019-08-09
 */
@RestController
@RequestMapping("/sleeve/activity_cover")
@Validated
public class ActivityCoverController {

    @Autowired
    private IActivityCoverService activityCoverService;

    @PostMapping("/")
    @RouteMeta(module = "活动页", auth = "创建活动页", mount = true)
    @GroupRequired
    public Result create(@RequestBody @Validated ActivityCoverCreateOrUpdateDTO dto) {
        activityCoverService.createActivityCover(dto);
        return ResultGenerator.genSuccessResult("创建活动页成功！");
    }

    @PutMapping("/{id}")
    @RouteMeta(module = "活动页", auth = "更新活动页", mount = true)
    @GroupRequired
    public Result update(@RequestBody @Validated ActivityCoverCreateOrUpdateDTO dto, @PathVariable @Positive(message = "id必须为正整数") Long id) {
        activityCoverService.updateActivityCover(dto, id);
        return ResultGenerator.genSuccessResult("更新活动页成功！");
    }

    @DeleteMapping("/{id}")
    @RouteMeta(module = "活动页", auth = "删除活动页", mount = true)
    @GroupRequired
    public Result delete(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        activityCoverService.deleteActivityCover(id);
        return ResultGenerator.genSuccessResult("删除活动页成功！");
    }

    @GetMapping("/{id}")
    public ActivityCover get(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        ActivityCover activityCover = activityCoverService.getById(id);
        if (activityCover == null) {
            throw new NotFound("未找到相关的活动页");
        }
        return activityCover;
    }

    @GetMapping("/page")
    public PageResult<ActivityCover> page(@RequestParam(name = "count", required = false, defaultValue = "10")
                                          @Min(value = 1, message = "count必须为正整数") Long count,
                                          @RequestParam(name = "page", required = false, defaultValue = "0")
                                          @Min(value = 0, message = "page必须为整数，且大于等于0") Long page) {
        PageResult<ActivityCover> pageResult = activityCoverService.getActivityCoverByPage(count, page);
        if (pageResult.getTotalNums() == 0) {
            throw new NotFound("未找到相关的活动页");
        }
        return pageResult;
    }
}

