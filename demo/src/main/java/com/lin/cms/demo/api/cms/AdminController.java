package com.lin.cms.demo.api.cms;

import com.lin.cms.core.annotation.AdminRequired;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.demo.service.AdminService;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.core.result.Result;
import com.lin.cms.demo.model.GroupDO;
import com.lin.cms.demo.bo.GroupWithAuthsBO;
import com.lin.cms.beans.CollectMetaPostBeanProcessor;
import com.lin.cms.utils.ResultGenerator;
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
    private CollectMetaPostBeanProcessor postBeanProcessor;

    @GetMapping("/authority")
    @AdminRequired
    @RouteMeta(auth = "查询所有可分配的权限", module = "管理员")
    public Map getAuthority() {
        return postBeanProcessor.getStructuralMeta();
    }


    @GetMapping("/users")
    @AdminRequired
    @RouteMeta(auth = "查询所有用户", module = "管理员")
    public PageResult getAdminUsers(
            @RequestParam(name = "group_id", required = false)
            @Min(value = 1, message = "分组id必须为正整数") Long groupId,
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "count必须为正整数") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "page必须为整数，且大于等于0") Long page
    ) {
        return adminService.getUsers(groupId, count, page);
    }

    @PutMapping("/password/{id}")
    @AdminRequired
    @RouteMeta(auth = "修改用户密码", module = "管理员")
    public Result changeUserPassword(@PathVariable @PositiveOrZero(message = "id必须为正整数") Long id, @RequestBody @Validated ResetPasswordDTO validator) {
        adminService.changeUserPassword(id, validator);
        return ResultGenerator.genSuccessResult("密码修改成功");
    }

    @DeleteMapping("/{id}")
    @AdminRequired
    @RouteMeta(auth = "删除用户", module = "管理员")
    public Result deleteUser(@PathVariable @PositiveOrZero(message = "id必须为正整数") Long id) {
        adminService.deleteUser(id);
        return ResultGenerator.genSuccessResult("删除用户成功");
    }


    @PutMapping("/{id}")
    @AdminRequired
    @RouteMeta(auth = "管理员更新用户信息", module = "管理员")
    public Result updateUser(@PathVariable @PositiveOrZero(message = "id必须为正整数") Long id, @RequestBody @Validated UpdateUserInfoDTO validator) {
        adminService.updateUserInfo(id, validator);
        return ResultGenerator.genSuccessResult("更新用户成功");
    }

    @GetMapping("/groups")
    @AdminRequired
    @RouteMeta(auth = "查询所有权限组及其权限", module = "管理员")
    public PageResult getAdminGroups(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "count必须为正整数") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "page必须为整数，且大于等于0") Long page) {

        PageResult pageResult = adminService.getGroups(page, count);
        return pageResult;
    }


    @GetMapping("/group/all")
    @AdminRequired
    @RouteMeta(auth = "查询所有权限组", module = "管理员")
    public List<GroupDO> getAllGroup() {
        List<GroupDO> groups = adminService.getAllGroups();
        return groups;
    }

    @GetMapping("/group/{id}")
    @AdminRequired
    @RouteMeta(auth = "查询一个权限组及其权限", module = "管理员")
    public GroupWithAuthsBO getGroup(@PathVariable @PositiveOrZero(message = "id必须为正整数") Long id) {
        GroupWithAuthsBO groupWithAuths = adminService.getGroup(id);
        return groupWithAuths;
    }


    @PostMapping("/group")
    @AdminRequired
    @RouteMeta(auth = "新建权限组", module = "管理员")
    public Result createGroup(@RequestBody @Validated NewGroupDTO validator) {
        adminService.createGroup(validator);
        return ResultGenerator.genSuccessResult("新建分组成功！");
    }


    @PutMapping("/group/{id}")
    @AdminRequired
    @RouteMeta(auth = "更新一个权限组", module = "管理员")
    public Result updateGroup(@PathVariable @PositiveOrZero Long id,
                              @RequestBody @Validated UpdateGroupDTO validator) {
        adminService.updateGroup(id, validator);
        return ResultGenerator.genSuccessResult("更新分组成功!");
    }

    @DeleteMapping("/group/{id}")
    @AdminRequired
    @RouteMeta(auth = "删除一个权限组", module = "管理员")
    public Result deleteGroup(@PathVariable @PositiveOrZero Long id) {
        adminService.deleteGroup(id);
        return ResultGenerator.genSuccessResult("删除分组成功!");
    }

    @PostMapping("/dispatch")
    @AdminRequired
    @RouteMeta(auth = "分配单个权限", module = "管理员")
    public Result dispatchAuth(@RequestBody @Validated DispatchAuthDTO validator) {
        adminService.dispatchAuth(validator);
        return ResultGenerator.genSuccessResult("添加权限成功!");
    }

    @PostMapping("/dispatch/patch")
    @AdminRequired
    @RouteMeta(auth = "分配多个权限", module = "管理员")
    public Result dispatchAuths(@RequestBody @Validated DispatchAuthsDTO validator) {
        adminService.dispatchAuths(validator);
        return ResultGenerator.genSuccessResult("添加权限成功!");
    }

    @PostMapping("/remove")
    @AdminRequired
    @RouteMeta(auth = "删除多个权限", module = "管理员")
    public Result removeAuths(@RequestBody @Validated RemoveAuthsDTO validator) {
        adminService.removeAuths(validator);
        return ResultGenerator.genSuccessResult("删除权限成功!");
    }

}
