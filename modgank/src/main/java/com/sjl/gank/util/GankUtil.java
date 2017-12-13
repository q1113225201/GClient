package com.sjl.gank.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * GankUtil
 *
 * @author SJL
 * @date 2017/12/13
 */

public class GankUtil {
    public static String parseDate(String dateStr){
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(dateStr);
            return new SimpleDateFormat("yyyy/MM/dd").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }
}
