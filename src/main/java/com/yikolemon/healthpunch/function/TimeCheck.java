package com.yikolemon.healthpunch.function;

import java.util.Calendar;

public class TimeCheck {
    public static boolean TimeCheck(){
        Calendar calendar= Calendar.getInstance();
        int h=calendar.get(Calendar.HOUR_OF_DAY);
        int mi=calendar.get(Calendar.MINUTE);
        int time=h*60+mi;
        if (time<=15*60){
            //在范围内
            return true;
        }else {
            //不在打卡的范围内，直接退出
            return false;
        }
    }
}
