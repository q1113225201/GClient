package com.sjl.platform.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * DateUtil
 *
 * @author 林zero
 * @date 2018/4/25
 */

public class DateUtil {
    public static String format(long time, String format) {
        return new SimpleDateFormat(format, Locale.US).format(new Date(time));
    }

    public static long formatTime(String time, String format) {
        try {
            return new SimpleDateFormat(format, Locale.CHINA).parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
