package com.elasticcloudservice.predict;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Time_Oper {
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");//用于转换请求时间的dateformat
    public int calcu_day_diff(String begin_time, String end_time){//format传入之前定义的format即可，两个时间字符串格式必须为"yyyy-MM-dd HH:mm:ss"
        int diff=0;
        try
        {
            diff=(int)((format.parse(end_time).getTime()-format.parse(begin_time).getTime())/(24*60*60*1000));//获取相差天数
            //System.out.println("相隔的天数="+day);
        } catch (ParseException e)
        {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
        return diff;
    }
}
