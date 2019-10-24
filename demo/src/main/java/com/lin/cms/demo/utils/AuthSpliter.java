package com.lin.cms.demo.utils;

import com.lin.cms.demo.model.SimpleAuthDO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthSpliter {
    public static List<Map<String, List<Map<String, String>>>> splitAuths(List<SimpleAuthDO> auths) {
        Map<String, List<Map<String, String>>> tmp = new HashMap();
        auths.forEach(auth -> {
            if (!tmp.containsKey(auth.getModule())) {
                Map<String, String> tiny = new HashMap();
                tiny.put("module", auth.getModule());
                tiny.put("auth", auth.getAuth());
                List<Map<String, String>> mini = new ArrayList();
                mini.add(tiny);
                tmp.put(auth.getModule(), mini);
            } else {
                Map<String, String> tiny = new HashMap();
                tiny.put("module", auth.getModule());
                tiny.put("auth", auth.getAuth());
                tmp.get(auth.getModule()).add(tiny);
            }
            auth.getAuth();
        });
        List<Map<String, List<Map<String, String>>>> structual = new ArrayList();
        tmp.forEach((k, v) -> {
            Map<String, List<Map<String, String>>> ttmp = new HashMap();
            ttmp.put(k, v);
            structual.add(ttmp);
        });
        return structual;
    }
}
