package com.lin.cms.demo.utils;

import com.lin.cms.demo.model.UserPO;

public class LocalUser {

    private static ThreadLocal<UserPO> local = new ThreadLocal<>();

    public static UserPO getLocalUser() {
        return LocalUser.local.get();
    }

    public static void setLocalUser(UserPO user) {
        LocalUser.local.set(user);
    }

    public static <T> T getLocalUser(Class<T> clazz) {
        return (T) local.get();
    }
}
