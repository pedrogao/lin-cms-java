package com.lin.cms.utils;

import com.lin.cms.interfaces.BaseUser;

public class LocalUser {

    private static ThreadLocal<BaseUser> local = new ThreadLocal<>();

    public static BaseUser getLocalUser() {
        return LocalUser.local.get();
    }

    public static void setLocalUser(BaseUser user) {
        LocalUser.local.set(user);
    }

    public static <T> T getLocalUser(Class<T> clazz) {
        return (T) local.get();
    }
}
