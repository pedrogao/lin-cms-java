package com.lin.cms.demo.api.cms;

import com.lin.cms.core.annotation.GroupRequired;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/cms/log")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/")
    @RouteMeta(auth = "查询所有日志", module = "日志", mount = true)
    @GroupRequired
    public PageResult getLogs(
            @RequestParam(required = false) @DateTimeFormat(pattern = "YYYY-MM-DD HH:mm:ss") Date start,
            @RequestParam(required = false) @DateTimeFormat(pattern = "YYYY-MM-DD HH:mm:ss") Date end,
            @RequestParam(required = false) String name,
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "count必须为正整数") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "page必须为整数，且大于等于0") Long page) {
        PageResult result = logService.getLogs(page, count, name, start, end);
        return result;
    }

    @GetMapping("/search")
    @RouteMeta(auth = "搜索日志", module = "日志", mount = true)
    @GroupRequired
    public PageResult searchLogs(
            @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "YYYY-MM-DD HH:mm:ss") Date start,
            @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "YYYY-MM-DD HH:mm:ss") Date end,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "count必须为正整数") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "page必须为整数，且大于等于0") Long page) {
        PageResult result = logService.searchLogs(page, count, name, keyword, start, end);
        return result;
    }

    @GetMapping("/users")
    @RouteMeta(auth = "查询日志记录的用户", module = "日志", mount = true)
    @GroupRequired
    public List<String> getUsers(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "count必须为正整数") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "page必须为整数，且大于等于0") Long page) {
        List<String> result = logService.getUserNames(page, count);
        return result;
    }
}
