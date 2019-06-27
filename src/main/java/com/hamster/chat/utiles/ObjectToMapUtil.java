package com.hamster.chat.utiles;

import com.hamster.chat.model.SysUser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对象转MAP
 * 将对象中需要的数据转成map封装
 *
 * @author Administrator
 */
public class ObjectToMapUtil {
    /***
     * 将对象转换为需要的字段Map类型
     * @param list 要转换的对象集合
     * @param discts 要转换的字段
     * @return List
     */
    public static <T> List<Map<String, Object>> getListMapToObject(List<T> list, String[] discts) {
        List<Map<String, Object>> listMap = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (T t : list) {
                listMap.add(getMapToObject(t, discts));
            }
        }
        return listMap;
    }

    /***
     * 将对象转换为需要的字段Map类型
     * @param t 对象
     * @param discts 需要转换的字段
     * @return map
     */
    public static <T> Map<String, Object> getMapToObject(T t, String[] discts) {
        Map<String, Object> map = new HashMap<>();
        if (t == null) {
            return null;
        }
        Class<?> cl = t.getClass();
        for (String string : discts) {
            try {
                if (string.contains("|")) {
                    String[] vals = string.split("\\|");
                    map.put(vals[0], cl.getMethod(vals[1]).invoke(t));
                } else {
                    map.put(string, cl.getMethod(getObjectMethodName(string)).invoke(t));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /***
     * 将对象转换为需要的字段Map类型
     * @param t 对象
     * @param discts 不需要转换的字段集合
     * @return map
     */
    public static <T> Map<String, Object> getMapToObjectByNoDis(T t, List discts) {
        Map<String, Object> map = new HashMap<>();
        Class<?> cl = t.getClass();
        for (Field field : cl.getDeclaredFields()) {
            try {
                if (!discts.contains(field.getName())) {
                    map.put(field.getName(), cl.getMethod(getObjectMethodName(field.getName())).invoke(t));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 获取方法
     * 将属性的首字符大写，方便构造get，set方法
     *
     * @param disname
     * @return
     */
    private static String getObjectMethodName(String disname) {
        disname = disname.substring(0, 1).toUpperCase() + disname.substring(1);
        return "get" + disname;
    }

    public static void main(String[] args) {
        SysUser user = new SysUser();
        user.setUsername("abc");
        user.setPassword("123456");
        System.out.println("=========" + getMapToObject(user, "username,password,status|isStatus".split(",")));
    }
}
