package com.lin.cms.demo.controller.cms;

import com.lin.cms.core.annotation.AdminRequired;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.demo.bo.GroupPermissionsBO;
import com.lin.cms.demo.v2.service.AdminService;
import com.lin.cms.demo.vo.CommonResult;
import com.lin.cms.demo.vo.PageResult;
import com.lin.cms.demo.v2.model.GroupDO;
import com.lin.cms.beans.RouteMetaCollector;
import com.lin.cms.demo.common.utils.ResultUtil;
import com.lin.cms.demo.dto.admin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Map;

/**
 * Created by lin on 2019/06/12.
 * License MIT
 */

@RestController
@RequestMapping("/cms/admin")
@Validated
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RouteMetaCollector postBeanProcessor;

    @GetMapping("/permission")
    @AdminRequired
    @RouteMeta(permission = "查询所有可分配的权限", module = "管理员")
    public Map getAllPermissions() {
        return postBeanProcessor.getStructuralMeta();
    }


    @GetMapping("/users")
    @AdminRequired
    @RouteMeta(permission = "查询所有用户", module = "管理员")
    public PageResult getUsers(
            @RequestParam(name = "group_id", required = false)
            @Min(value = 1, message = "分组id必须为正整数") Long groupId,
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "count必须为正整数") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "page必须为整数，且大于等于0") Long page) {
        return adminService.getUserPageByGroupId(groupId, count, page);
    }

    @PutMapping("/{id}/password")
    @AdminRequired
    @RouteMeta(permission = "修改用户密码", module = "管理员")
    public CommonResult changeUserPassword(@PathVariable @PositiveOrZero(message = "id必须为正整数") Long id, @RequestBody @Validated ResetPasswordDTO validator) {
        adminService.changeUserPassword(id, validator);
        return ResultUtil.generateSuccessResult("密码修改成功");
    }

    @DeleteMapping("/{id}")
    @AdminRequired
    @RouteMeta(permission = "删除用户", module = "管理员")
    public CommonResult deleteUser(@PathVariable @PositiveOrZero(message = "id必须为正整数") Long id) {
        adminService.deleteUser(id);
        return ResultUtil.generateSuccessResult("删除用户成功");
    }


    @PutMapping("/{id}")
    @AdminRequired
    @RouteMeta(permission = "管理员更新用户信息", module = "管理员")
    public CommonResult updateUser(@PathVariable @PositiveOrZero(message = "id必须为正整数") Long id, @RequestBody @Validated UpdateUserInfoDTO validator) {
        adminService.updateUserInfo(id, validator);
        return ResultUtil.generateSuccessResult("更新用户成功");
    }

    @GetMapping("/groups")
    @AdminRequired
    @RouteMeta(permission = "查询所有权限组及其权限", module = "管理员")
    public PageResult getGroups(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "count必须为正整数") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "page必须为整数，且大于等于0") Long page) {
        PageResult pageResult = adminService.getGroupPage(page, count);
        return pageResult;
    }


    @GetMapping("/group/all")
    @AdminRequired
    @RouteMeta(permission = "查询所有权限组", module = "管理员")
    public List<GroupDO> getAllGroup() {
        List<GroupDO> groups = adminService.getAllGroups();
        return groups;
    }

    @GetMapping("/group/{id}")
    @AdminRequired
    @RouteMeta(permission = "查询一个权限组及其权限", module = "管理员")
    public GroupPermissionsBO getGroup(@PathVariable @PositiveOrZero(message = "id必须为正整数") Long id) {
        GroupPermissionsBO groupPermissions = adminService.getGroup(id);
        return groupPermissions;
    }


    @PostMapping("/group")
    @AdminRequired
    @RouteMeta(permission = "新建权限组", module = "管理员")
    public CommonResult createGroup(@RequestBody @Validated NewGroupDTO validator) {
        adminService.createGroup(validator);
        return ResultUtil.generateSuccessResult("新建分组成功！");
    }


    @PutMapping("/group/{id}")
    @AdminRequired
    @RouteMeta(permission = "更新一个权限组", module = "管理员")
    public CommonResult updateGroup(@PathVariable @PositiveOrZero Long id,
                                    @RequestBody @Validated UpdateGroupDTO validator) {
        adminService.updateGroup(id, validator);
        return ResultUtil.generateSuccessResult("更新分组成功!");
    }

    @DeleteMapping("/group/{id}")
    @AdminRequired
    @RouteMeta(permission = "删除一个权限组", module = "管理员")
    public CommonResult deleteGroup(@PathVariable @PositiveOrZero Long id) {
        adminService.deleteGroup(id);
        return ResultUtil.generateSuccessResult("删除分组成功!");
    }

    @PostMapping("/dispatch")
    @AdminRequired
    @RouteMeta(permission = "分配单个权限", module = "管理员")
    public CommonResult dispatchAuth(@RequestBody @Validated DispatchPermissionDTO validator) {
        adminService.dispatchPermission(validator);
        return ResultUtil.generateSuccessResult("添加权限成功!");
    }

    @PostMapping("/dispatch/batch")
    @AdminRequired
    @RouteMeta(permission = "分配多个权限", module = "管理员")
    public CommonResult dispatchAuths(@RequestBody @Validated DispatchPermissionsDTO validator) {
        adminService.dispatchPermissions(validator);
        return ResultUtil.generateSuccessResult("添加权限成功!");
    }

    @PostMapping("/remove")
    @AdminRequired
    @RouteMeta(permission = "删除多个权限", module = "管理员")
    public CommonResult removeAuths(@RequestBody @Validated RemovePermissionsDTO validator) {
        adminService.removePermissions(validator);
        return ResultUtil.generateSuccessResult("删除权限成功!");
    }

}
