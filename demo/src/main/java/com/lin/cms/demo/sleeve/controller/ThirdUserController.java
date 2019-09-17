package com.lin.cms.demo.sleeve.controller;


import com.lin.cms.core.result.PageResult;
import com.lin.cms.core.result.Result;
import com.lin.cms.demo.sleeve.model.ThirdUser;
import com.lin.cms.demo.sleeve.model.ThirdUserParsedDO;
import com.lin.cms.demo.sleeve.service.IThirdUserService;
import com.lin.cms.exception.NotFound;
import com.lin.cms.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

/**
 * @author pedro
 * @since 2019-07-23
 */
@RestController
@RequestMapping("/sleeve/user")
@Validated
public class ThirdUserController {

    @Autowired
    private IThirdUserService thirdUserService;

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        // 暂时不做删除
        return ResultGenerator.genSuccessResult("删除用户成功！");
    }

    @GetMapping("/{id}")
    public ThirdUserParsedDO get(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        ThirdUserParsedDO user = thirdUserService.getParsedUserById(id);
        return user;
    }

    @GetMapping("/page")
    public PageResult<ThirdUser> page(@RequestParam(name = "count", required = false, defaultValue = "10")
                                      @Min(value = 1, message = "count必须为正整数") Long count,
                                      @RequestParam(name = "page", required = false, defaultValue = "0")
                                      @Min(value = 0, message = "page必须为整数，且大于等于0") Long page) {
        PageResult<ThirdUser> pageResult = thirdUserService.getUserByPage(count, page);
        return pageResult;
    }
}
