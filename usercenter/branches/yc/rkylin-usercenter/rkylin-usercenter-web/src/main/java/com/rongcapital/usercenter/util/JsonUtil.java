package com.rongcapital.usercenter.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by shuimiao on 2016-3-14.
 */
public class JsonUtil {

    /**
     * @Description:POJO转Json字符串
     * @param obj
     * @return String
     */
    public static String bean2JsonStr(Object obj) {
        if(obj==null){
            return "";
        }else if (obj instanceof List) {
            JSONArray jsonArray = JSONArray.fromObject(obj);
            return JsonUtil.toString(jsonArray);
        } else if (obj instanceof String) {

            return obj.toString();
        } else {
            JSONObject jsonObj = JSONObject.fromObject(obj);
            return JsonUtil.toString(jsonObj);
        }
    }

    /**
     * @Description:Json字符串转POJO对象
     * @param jsonStr
     * @param
     * @return Object
     */
    public static Object jsonStr2Bean(String jsonStr, Class<?> clazz) {
        Object obj;

        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        obj = JSONObject.toBean(jsonObject, clazz);

        return obj;
    }

    /**
     * @Description:Json字符串转Map<String, Object>
     * @param jsonStr
     * @return Map<String,Object>
     */
    public static Map<String, Object> jsonStr2Map(String jsonStr) {

        Map<String, Object> result = new HashMap<String, Object>();

        JSONObject jsonObj = JSONObject.fromObject(jsonStr);
        Iterator<?> keys = jsonObj.keys();
        String key;
        Object val;

        while (keys.hasNext()) {
            key = (String) keys.next();
            val = jsonObj.get(key);
            result.put(key, val);
        }

        return result;
    }

    /**
     * @Description:Json字符串转List,内部对象为Object，需要手动转换为制定的对象类型
     * @param jsonStr
     * @param
     * @return List
     */
    public static List jsonStr2List(String jsonStr, Class clazz) {
        List result = new ArrayList();

        JSONArray jsonArray = JSONArray.fromObject(jsonStr);
        JSONObject jsonObj;
        Object pojoVal;

        for (int i = 0; i < jsonArray.size(); i++) {
            jsonObj = jsonArray.getJSONObject(i);
            pojoVal = JSONObject.toBean(jsonObj, clazz);
            result.add(pojoVal);
        }

        return result;
    }

    public static Object[] jsonStr2ObjectArray(String jsonStr) {

        JSONArray jsonArray = JSONArray.fromObject(jsonStr);
        Object[] result = new Object[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            result[i] = jsonArray.get(i);

        }
        return result;
    }

    /**
     * @Description:将Object转化为String
     * @param obj 指定对象，默认值为""
     * @return String
     */
    public static String toString(Object obj) {
        return toString(obj, "");
    }

    /**
     * @Description:将Object转化为String
     * @param obj 指定对象
     * @param defaultStr 为空的默认字符串
     * @return String
     */
    public static String toString(Object obj, String defaultStr) {
        String result = defaultStr;
        if (obj != null) {
            result = obj.toString();
        }
        return result;
    }

}
