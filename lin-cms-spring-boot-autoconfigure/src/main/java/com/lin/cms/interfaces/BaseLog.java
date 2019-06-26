package com.lin.cms.interfaces;

import java.util.Date;

public interface BaseLog {

    /**
     * @return id
     */
    Integer getId();

    /**
     * @param id
     */
    void setId(Integer id);

    /**
     * @return message
     */
    String getMessage();

    /**
     * @param message
     */
    void setMessage(String message);

    /**
     * @return user_id
     */
    Integer getUserId();

    /**
     * @param userId
     */
    void setUserId(Integer userId);

    /**
     * @return user_name
     */
    String getUserName();

    /**
     * @param userName
     */
    void setUserName(String userName);

    /**
     * @return status_code
     */
    Integer getStatusCode();

    /**
     * @param statusCode
     */
    void setStatusCode(Integer statusCode);

    /**
     * @return method
     */
    String getMethod();

    /**
     * @param method
     */
    void setMethod(String method);

    /**
     * @return path
     */
    String getPath();

    /**
     * @param path
     */
    void setPath(String path);

    /**
     * @return authority
     */
    String getAuthority();

    /**
     * @param authority
     */
    void setAuthority(String authority);

    /**
     * @return time
     */
    Date getTime();

    /**
     * @param time
     */
    void setTime(Date time);
}
