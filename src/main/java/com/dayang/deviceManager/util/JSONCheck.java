package com.dayang.deviceManager.util;

import net.sf.json.JSONObject;

public class JSONCheck {

    public static String ifAllExist(JSONObject jsonObject, String... keys) {
        for (String key: keys) {
            if (!jsonObject.containsKey(key)) {
                return key;
            }
        }

        return null;
    }
}
