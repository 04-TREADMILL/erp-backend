package com.nju.edu.erp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class IdGenerator {
    /**
     * 获取新单号
     * @param id 上一次的单号
     * @return 新的单号
     */
    public static String generateSheetId(String id, String prefix) { // "{prefix}-20220216-00000"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String today = dateFormat.format(new Date());
        if(id == null) {
            return prefix + "-" + today + "-" + String.format("%05d", 0);
        }
        String lastDate = id.split("-")[1];
        if(lastDate.equals(today)) {
            String prevNum = id.split("-")[2];
            return prefix + "-" + today + "-" + String.format("%05d", Integer.parseInt(prevNum) + 1);
        } else {
            return prefix + "-" + today + "-" + String.format("%05d", 0);
        }
    }
}
