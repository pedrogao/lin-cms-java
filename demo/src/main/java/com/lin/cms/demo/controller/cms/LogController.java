package com.lin.cms.demo.controller.cms;

import com.lin.cms.core.annotation.GroupRequired;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.demo.vo.PageResult;
import com.lin.cms.demo.v2.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/cms/log")
@Validated
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("")
    @RouteMeta(permission = "查询所有日志", module = "日志", mount = true)
    @GroupRequired
    public PageResult getLogs(
            @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date start,
            @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date end,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "count必须为正整数") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "page必须为整数，且大于等于0") Long page) {
        return logService.getLogs(page, count, name, start, end);
    }

    @GetMapping("/search")
    @RouteMeta(permission = "搜索日志", module = "日志", mount = true)
    @GroupRequired
    public PageResult searchLogs(
            @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date start,
            @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date end,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "count必须为正整数") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "page必须为整数，且大于等于0") Long page) {
        return logService.searchLogs(page, count, name, keyword, start, end);
    }

    @GetMapping("/users")
    @RouteMeta(permission = "查询日志记录的用户", module = "日志", mount = true)
    @GroupRequired
    public List<String> getUsers(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "count必须为正整数") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "page必须为整数，且大于等于0") Long page) {
        return logService.getUserNames(page, count);
    }
}
