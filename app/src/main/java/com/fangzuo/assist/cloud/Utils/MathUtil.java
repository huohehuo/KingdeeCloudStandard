package com.fangzuo.assist.cloud.Utils;

import com.fangzuo.assist.cloud.Activity.Crash.App;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathUtil {

    //防止强转时崩溃操作
    public static double toD(String string) {
        if (null == string) {
            return 0;
        } else if (string.equals("")) {
            return 0;
        } else {
            return Double.parseDouble(string);
        }
    }

    //解决 1.1-1.0=0.10000009的情况
    //double相减
    public static double doubleSub(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.subtract(b2).doubleValue();
    }
    //是否为数字
    public static boolean isNumeric(String str) {
        //Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");//这个有问题，一位的整数不能通过
        Pattern pattern = Pattern.compile("^(\\-|\\+)?\\d+(\\.\\d+)?$");//这个是对的
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
    //保留两位小数（四舍五入
    public static double D2save2(Double d){
        return Double.parseDouble(String.format("%.2f", d));
    }

    //去掉末尾为.0的数
    public static String cutZero(String num){
        String string;
        if (num.endsWith(".0")){
            Lg.e("有0");
//            Lg.e("去掉0",string.substring(0,string.length()-2));
            string = num.substring(0,num.length()-2);
        }else{
            string=num;
        }
        return string;
    }
    //超过时间
    public static boolean MoreTime(double min){
        Lg.e("最初时间",App.PDA_Time);
        Lg.e("当前时间", CommonUtil.getTime2Fen());
        if (doubleSub(Double.parseDouble(CommonUtil.getTime2Fen()),Double.parseDouble(App.PDA_Time))>min){
            App.PDA_Time = CommonUtil.getTime2Fen();
            return true;
        }else{
            return false;
        }
    }


}
