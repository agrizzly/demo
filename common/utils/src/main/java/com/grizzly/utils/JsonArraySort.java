package com.grizzly.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author:vic
 * @Date:14:59 2018/11/30
 * @Description: is_desc-false升序列  is_desc-true降序 (排序字段为数字类型)
 */
public class JsonArraySort {

    public static JSONArray jsonArraySort(JSONArray jsonArr, String sortKey, boolean is_desc) {
        JSONArray sortedJsonArray = new JSONArray();
        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
        for (int i = 0; i < jsonArr.size(); i++) {
            jsonValues.add(jsonArr.getJSONObject(i));
        }
        Collections.sort(jsonValues, new Comparator<JSONObject>() {
            private final String KEY_NAME = sortKey;

            @Override
            public int compare(JSONObject a, JSONObject b) {
                double valA = 0;
                double valB = 0;
                try {
                    valA = a.getDoubleValue(KEY_NAME);
                    valB = b.getDoubleValue(KEY_NAME);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (is_desc) {
                    if (valA < valB) {
                        return 1;
                    }
                    if (valA == valB) {
                        return 0;
                    }
                    return -1;
                } else {
                    if (valA > valB) {
                        return 1;
                    }
                    if (valA == valB) {
                        return 0;
                    }
                    return -1;
                }

            }
        });
        for (int i = 0; i < jsonArr.size(); i++) {
            sortedJsonArray.add(jsonValues.get(i));
        }
        return sortedJsonArray;
    }
}
