package com.lin.cms.base.api.cms;

import com.lin.cms.base.validators.admin.*;
import com.lin.cms.core.annotation.AdminRequired;
import com.lin.cms.core.annotation.Logger;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.core.exception.Forbidden;
import com.lin.cms.core.exception.NotFound;
import com.lin.cms.core.exception.Parameter;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.core.result.Result;
import com.lin.cms.base.mapper.GroupMapper;
import com.lin.cms.base.model.GroupDO;
import com.lin.cms.base.view.GroupWithAuthsVO;
import com.lin.cms.beans.CollectMetaPostBeanProcessor;
import com.lin.cms.core.result.ResultGenerator;
import com.lin.cms.base.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private CollectMetaPostBeanProcessor postBeanProcessor;

    @GetMapping("/authority")
    @AdminRequired
    @RouteMeta(auth = "查询所有可分配的权限", module = "管理员", mount = false)
    public Map getAuthority() {
        return postBeanProcessor.getStructuralMeta();
    }


    @GetMapping("/users")
    @AdminRequired
    @RouteMeta(auth = "查询所有用户", module = "管理员", mount = false)
    public PageResult getAdminUsers(
            @RequestParam(name = "group_id", required = false)
            @Min(value = 1, message = "分组id必须为正整数") Integer groupId,
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "count必须为正整数") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "page必须为整数，且大于等于0") Integer page
    ) {
        return adminService.getUsers(groupId, count, page);
    }

    @PutMapping("/password/{id}")
    @AdminRequired
    @RouteMeta(auth = "修改用户密码", module = "管理员")
    public Result changeUserPassword(@PathVariable @PositiveOrZero Integer id, @RequestBody @Valid ResetPasswordValidator validator) throws NotFound {
        adminService.changeUserPassword(id, validator);
        return ResultGenerator.genSuccessResult("密码修改成功");
    }

    @DeleteMapping("/{id}")
    @AdminRequired
    @RouteMeta(auth = "删除用户", module = "管理员")
    public Result deleteUser(@PathVariable @PositiveOrZero Integer id) throws NotFound {
        adminService.deleteUser(id);
        return ResultGenerator.genSuccessResult("删除用户成功");
    }


    @PutMapping("/{id}")
    @AdminRequired
    @RouteMeta(auth = "管理员更新用户信息", module = "管理员")
    public Result updateUser(@PathVariable @PositiveOrZero Integer id, @RequestBody @Valid UpdateUserInfoValidator validator) throws NotFound, Parameter {
        adminService.updateUserInfo(id, validator);
        return ResultGenerator.genSuccessResult("删除用户成功");
    }

    @GetMapping("/groups")
    @AdminRequired
    @RouteMeta(auth = "查询所有权限组及其权限", module = "管理员")
    public PageResult getAdminGroups(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "count必须为正整数") Integer count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "page必须为整数，且大于等于0") Integer page) {

        PageResult pageResult = adminService.getGroups(page, count);
        return pageResult;
    }


    @GetMapping("/group/all")
    @AdminRequired
    @Logger(template = "pedro正在调试呢！")
    @RouteMeta(auth = "查询所有权限组", module = "管理员")
    public List<GroupDO> getAllGroup() {
        List<GroupDO> groups = groupMapper.selectAll();
        return groups;
    }

    @GetMapping("/group/{id}")
    @AdminRequired
    @RouteMeta(auth = "查询一个权限组及其权限", module = "管理员")
    public GroupWithAuthsVO getGroup(@PathVariable @PositiveOrZero Integer id) {
        GroupWithAuthsVO groupWithAuths = adminService.getGroup(id);
        return groupWithAuths;
    }


    @PostMapping("/group")
    @AdminRequired
    @RouteMeta(auth = "新建权限组", module = "管理员")
    public Result createGroup(@RequestBody @Valid NewGroupValidator validator) throws Forbidden {
        adminService.createGroup(validator);
        return ResultGenerator.genSuccessResult("ok!");
    }


    @PutMapping("/group/{id}")
    @AdminRequired
    @RouteMeta(auth = "更新一个权限组", module = "管理员")
    public Result updateGroup(@PathVariable @PositiveOrZero Integer id,
                              @RequestBody @Valid UpdateGroupValidator validator) throws NotFound {
        adminService.updateGroup(id, validator);
        return ResultGenerator.genSuccessResult("更新分组成功!");
    }

    @DeleteMapping("/group/{id}")
    @AdminRequired
    @RouteMeta(auth = "删除一个权限组", module = "管理员")
    public Result deleteGroup(@PathVariable @PositiveOrZero Integer id) throws NotFound, Forbidden {
        adminService.deleteGroup(id);
        return ResultGenerator.genSuccessResult("删除分组成功!");
    }

    @PostMapping("/dispatch")
    @AdminRequired
    @RouteMeta(auth = "分配单个权限", module = "管理员")
    public Result dispatchAuth(@RequestBody @Valid DispatchAuthValidator validator) throws NotFound, Forbidden {
        adminService.dispatchAuth(validator);
        return ResultGenerator.genSuccessResult("添加权限成功!");
    }

    @PostMapping("/dispatch/patch")
    @AdminRequired
    @RouteMeta(auth = "分配多个权限", module = "管理员")
    public Result dispatchAuths(@RequestBody @Valid DispatchAuthsValidator validator) throws NotFound, Forbidden {
        adminService.dispatchAuths(validator);
        return ResultGenerator.genSuccessResult("添加权限成功!");
    }

    @PostMapping("/remove")
    @AdminRequired
    @RouteMeta(auth = "删除多个权限", module = "管理员")
    public Result removeAuths(@RequestBody @Valid RemoveAuthsValidator validator) throws NotFound, Forbidden {
        adminService.removeAuths(validator);
        return ResultGenerator.genSuccessResult("添加权限成功!");
    }

}
