package com.lin.cms.demo.sleeve.controller;


import com.lin.cms.core.annotation.GroupRequired;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.core.result.Result;
import com.lin.cms.demo.sleeve.dto.ActivityCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.Activity;
import com.lin.cms.demo.sleeve.model.ActivityDetailDO;
import com.lin.cms.demo.sleeve.service.IActivityService;
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
@RequestMapping("/sleeve/activity")
@Validated
public class ActivityController {

    @Autowired
    private IActivityService activityService;

    @PostMapping("")
    @RouteMeta(module = "活动", auth = "创建活动", mount = true)
    @GroupRequired
    public Result create(@RequestBody @Validated ActivityCreateOrUpdateDTO dto) {
        activityService.createActivity(dto);
        return ResultGenerator.genSuccessResult("创建活动成功！");
    }

    @PutMapping("/{id}")
    @RouteMeta(module = "活动", auth = "更新活动", mount = true)
    @GroupRequired
    public Result update(@RequestBody @Validated ActivityCreateOrUpdateDTO dto, @PathVariable @Positive(message = "id必须为正整数") Long id) {
        activityService.updateActivity(dto, id);
        return ResultGenerator.genSuccessResult("更新活动成功！");
    }

    @DeleteMapping("/{id}")
    @RouteMeta(module = "活动", auth = "删除活动", mount = true)
    @GroupRequired
    public Result delete(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        activityService.deleteActivity(id);
        return ResultGenerator.genSuccessResult("删除活动成功！");
    }

    @GetMapping("/{id}")
    public Activity get(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        Activity activity = activityService.getById(id);
        if (activity == null) {
            throw new NotFound("未找到相关的活动");
        }
        return activity;
    }

    @GetMapping("/{id}/detail")
    public ActivityDetailDO getDetail(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        ActivityDetailDO activityDetail = activityService.getDetailById(id);
        return activityDetail;
    }

    @GetMapping("/page")
    public PageResult<Activity> page(@RequestParam(name = "count", required = false, defaultValue = "10")
                                     @Min(value = 1, message = "count必须为正整数") Long count,
                                     @RequestParam(name = "page", required = false, defaultValue = "0")
                                     @Min(value = 0, message = "page必须为整数，且大于等于0") Long page) {
        PageResult<Activity> pageResult = activityService.getActivityByPage(count, page);
        if (pageResult.getTotalNums() == 0) {
            throw new NotFound("未找到相关的活动");
        }
        return pageResult;
    }
}
