package online.githuboy.retwis.common;

import java.util.Date;

/**
 * DateUtils
 *
 * @author suchu
 * @since 2019/4/4 10:17
 */
public class DateUtils {
    /**
     * Calculate how long the given date has elapsed
     *
     * @param date The given date
     * @return string
     */
    public static String strElapsed(Date date) {
        Date now = new Date();
        long diff = (now.getTime() - date.getTime()) / 1000; // convert to unix timestamp
        if (diff < 60) return diff + " seconds";
        if (diff < 3600) {
            int m = (int) (diff / 60);
            return m + " minute" + (m > 1 ? "s" : "");
        }
        if (diff < 3600 * 24) {
            int h = (int) (diff / 3600);
            return h + " hour" + ((h > 1) ? "s" : "");
        }
        int day = (int) (diff / 3600 * 24);
        return day + " day" + (day > 1 ? "s" : "");
    }
}
