package com.lin.cms.demo.service;

import com.lin.cms.demo.vo.PageResult;

import java.util.Date;
import java.util.List;


/**
 * Created by lin on 2019/06/14.
 * License MIT
 */
public interface LogService {

    PageResult getLogs(Long page, Long count, String name, Date start, Date end);

    PageResult searchLogs(Long page, Long count, String name, String keyword, Date start, Date end);

    List<String> getUserNames(Long page, Long count);

    void createOneLog(String message, String authority, Long userId,
                      String userNickname, String method, String path,
                      Integer status);
}
