package cn.edu.zut.lams.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author 86131
 * @Date 2020/12/25
 * @TIME 10:24
 */
public class GetDate {
    /**
     * 获取昨天时间
     * 返回格式：年-月-日
     *
     * @return
     */
    public static String getYesterdayDate() {
        return getYesterdayDate(1);
    }

    /**
     * 参数为前n的日期
     * 返回格式：年-月-日
     *
     * @param n
     * @return
     */
    public static String getYesterdayDate(int n) {
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.add(Calendar.DAY_OF_MONTH, -n);//设置为前n天
        Date dBefore = calendar.getTime();//得到前n天的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        String defaultStartDate = sdf.format(dBefore); //格式化前n天
        return defaultStartDate;
    }

    /**
     * 返回格式：月/日
     * @param defaultStartDate
     * @return
     */
    public static String getMDFormat(String defaultStartDate) {
        String[] strs = defaultStartDate.trim().split("-");
        if (strs.length == 3) return strs[1] + "/" + strs[2]; //格式化为 月/日 格式
        return "";
    }
}
