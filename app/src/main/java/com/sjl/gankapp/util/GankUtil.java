package com.sjl.gankapp.util;

import com.sjl.platform.util.DateUtil;

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
    public static String parseDate(String dateStr) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(dateStr);
            return new SimpleDateFormat("yyyy/MM/dd").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * 超过7天直接返回日期，否则显示计算天数
     */
    public static String caluateDate(String fromDate, String toDate) {
        long fromTime = DateUtil.formatTime(fromDate, "yyyy/MM/dd");
        long toTime = DateUtil.formatTime(toDate, "yyyy/MM/dd");
        long result = (toTime - fromTime) / 1000 / 60 / 60 / 24;
        if (result > 7) {
            return fromDate;
        } else if (result < 1) {
            return "今天";
        } else {
            return String.format("%d天前", result);
        }
    }
}
