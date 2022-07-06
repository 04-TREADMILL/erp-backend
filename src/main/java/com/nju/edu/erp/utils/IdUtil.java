package com.nju.edu.erp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IdUtil {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    /**
     * 获取新单号
     *
     * @param id 上一次的单号
     * @return 新的单号
     */
    public static String generateSheetId(String id, String prefix) { // "{prefix}-20220216-00000"
        String today = dateFormat.format(new Date());
        if (id == null) {
            return prefix + "-" + today + "-" + String.format("%05d", 0);
        }
        String lastDate = id.split("-")[1];
        if (lastDate.equals(today)) {
            String prevNum = id.split("-")[2];
            return prefix + "-" + today + "-" + String.format("%05d", Integer.parseInt(prevNum) + 1);
        } else {
            return prefix + "-" + today + "-" + String.format("%05d", 0);
        }
    }

    public static Date parseDateFromSheetId(String id, String prefix) {
        try {
            return dateFormat.parse(id.substring(prefix.length() + 1, prefix.length() + 9));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
