package com.madf.tank;

import java.io.IOException;
import java.util.Properties;

/**
 * 配置文件管理类
 */
public class PropertyMgr {

    static Properties props = new Properties();

    static {
        try {
            props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object get(String key) {
        if (props == null) return null;
        return props.get(key);
    }

    public static Integer getInt(String key) {
        return get(key) == null ? 0 : Integer.parseInt((String) get(key));
    }

    public static String getString(String key) {
        return get(key) == null ? "" : (String) get(key);
    }

    public static void main(String[] args) {
        System.out.println(PropertyMgr.get("initTankCount"));
    }

}
