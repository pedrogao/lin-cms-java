package com.lin.cms.demo.common;


import com.lin.cms.demo.model.UserDO;

public class LocalUserLegacy {

    private static ThreadLocal<UserDO> local = new ThreadLocal<>();

    public static UserDO getLocalUser() {
        return LocalUserLegacy.local.get();
    }

    public static void setLocalUser(UserDO user) {
        LocalUserLegacy.local.set(user);
    }

    public static <T> T getLocalUser(Class<T> clazz) {
        return (T) local.get();
    }
}
