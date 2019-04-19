package com.fangzuo.assist.cloud.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.fangzuo.assist.cloud.Activity.Crash.App;
import com.fangzuo.assist.cloud.Beans.PrintHistory;
import com.orhanobut.hawk.Hawk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zpSDK.zpSDK.zpBluetoothPrinter;

public class CommonUtil {

    public static void doPrint(zpBluetoothPrinter zpSDK, PrintHistory bean) throws Exception{
        Lg.e("打印数据:",bean);
        if (null== zpSDK)return;
        int size=4;
        int size2=3;
        int lineSize=3;
        int printNum = Integer.parseInt(Hawk.get(Config.PrintNum,"2"));
        for (int i = 0; i < printNum; i++) {
            zpSDK.pageSetup(668, 973);
            zpSDK.drawText(0, 5, "______________________________________________", 2, 0, 0, false, false);

            zpSDK.drawText(105, 50, "荣源木业物料标签", size, 0, 1, false, false);
            zpSDK.drawText(0, 90, "______________________________________________", 2, 0, 0, false, false);
            zpSDK.drawText(10, 120, "货主：", size, 0, 0, false, false);
            zpSDK.drawText(160, 124, bean.FHuoquan==null?"":bean.FHuoquan,size2, 0, 0, false, false);
            zpSDK.drawText(10, 170, "批号：", size, 0, 0, false, false);
            zpSDK.drawText(160, 174, bean.FBatch,size2, 0, 0, false, false);
            zpSDK.drawText(10, 220, "品名：", size, 0, 0, false, false);
            zpSDK.drawText(160, 224, bean.FName,size2, 0, 0, false, false);
            zpSDK.drawText(10, 280, "规格：", size, 0, 0, false, false);
            zpSDK.drawText(160, 284, bean.FModel,size2, 0, 0, false, false);
            zpSDK.drawText(10, 340, "数量1：", size, 0, 0, false, false);
            zpSDK.drawText(160, 344, bean.FNum==null?"":bean.FNum,size2, 0, 0, false, false);
            zpSDK.drawText(450, 344, bean.FUnit==null?"":bean.FUnit,size2, 0, 0, false, false);
            zpSDK.drawText(10, 400, "数量2：", size, 0, 0, false, false);
            zpSDK.drawText(160, 404, bean.FNum2==null?"":bean.FNum2,size2, 0, 0, false, false);
            zpSDK.drawText(450, 404, bean.FUnitAux==null?"":bean.FUnitAux,size2, 0, 0, false, false);
            zpSDK.drawText(10, 460, "辅助标识：", size, 0, 0, false, false);
            zpSDK.drawText(360, 464, bean.FAuxSign==null?"":bean.FAuxSign,size2, 0, 0, false, false);
            zpSDK.drawText(10, 500, "______________________________________________", 2, 0, 0, false, false);
            zpSDK.drawQrCode(10, 560, bean.FBarCode, 0, 11, 0);
            zpSDK.drawText(300, 560, "仓位：",size2, 0, 0, false, false);
            zpSDK.drawText(380, 560, bean.FWaveHouse==null?"":bean.FWaveHouse,size2, 0, 0, false, false);
            zpSDK.drawText(300, 640, "录入：",size2, 0, 0, false, false);
            zpSDK.drawText(380, 640, bean.FSaveIn==null?"":bean.FSaveIn,size2, 0, 0, false, false);
            zpSDK.drawText(300, 720, "审核：",size2, 0, 0, false, false);
            zpSDK.drawText(380, 720, bean.FCheck==null?"":bean.FCheck,size2, 0, 0, false, false);
            zpSDK.drawText(300, 790, "日期：",size2, 0, 0, false, false);
            zpSDK.drawText(380, 790, bean.FDate,size2, 0, 0, false, false);
            zpSDK.drawText(10, 850, "______________________________________________", 2, 0, 0, false, false);

            zpSDK.print(0, 0);
        }

//        }
//            zpSDK.drawBarCode(8, 540, "12345678901234567", 128, true, 3, 60);
//              zpSDK.drawGraphic(90, 70, 0, 0, bmp);
//            zpSDK.drawQrCode(350, 48, "111111111", 0, 6, 0);
//            zpSDK.drawText(90, 48+100, "400-8800-", 2
//                    , 0, 0, false, false);
//            zpSDK.drawText(100, 48+100+56, "株洲      张贺", 4, 0, 0, false, false);
//            zpSDK.drawText(250, 48+100+56+56, "经由  株洲", 2, 0, 0, false, false);

//            zpSDK.drawText(100, 48+100+56+56+80, "2015110101079-01-01   广州", 2, 0, 0, false, false);
//            zpSDK.drawText(100, 48+100+56+56+80+80, "2015-11-01  23:00    卡班", 2, 0, 0, false, false);

//            zpSDK.drawBarCode(124,48+100+56+56+80+80+80 , "12345678901234567", 128, false, 3, 60);
//            zpSDK.print(0, 0);
    }

    /*条码规则：
    物料条码.16位数

    批次条码:大于22位
                批次和备注  11位到空格是批次 后三位备注
    批次条码:大于16小于20位
               批次和备注  11位到空格是批次*/

    /*if (CommonUtil.ScanBack(code).size()>0){
            List<String> list = CommonUtil.ScanBack(code);
            edNum.setText(list.get(1));
            ScanBarCode(list.get(0));
        }*/
    public static List<String> ScanBack(String code) {
        List<String> list = new ArrayList<>();
        if (code.contains("/")) {
            String[] split = code.split("/", 3);
            Log.e("code:", split.length + "");
            if (split.length == 3) {
                String fcode = split[0];
                if (fcode.length() > 12) {
                    try {
                        String barcode = fcode.substring(0, 12);
                        list.add(barcode);
                        String num = fcode.substring(12, fcode.length());
                        list.add(num);
                        list.add(split[1]);
                        return list;
                    } catch (Exception e) {
                        Toast.showText(App.getContext(), "条码有误");
                        return new ArrayList<>();
                    }
                } else {
                    Toast.showText(App.getContext(), "条码有误");
                    return new ArrayList<>();
                }
            } else {
                Toast.showText(App.getContext(), "条码有误");
                return new ArrayList<>();
            }
        } else {
            Toast.showText(App.getContext(), "条码有误");
            return new ArrayList<>();
        }

//        List<String> list = new ArrayList<>();
//        if (code.length()>22){
//            String barcode = code.substring(0, 12);
//            list.add(barcode);
//            return list;
//        }else if (code.length()>16 && code.length()<20){
//            String barcode = code.substring(0, 12);
//            list.add(barcode);
//            return list;
//        }else{
//            return new ArrayList<>();
//        }

        // 大于12位的条码  前面12是条形码 后面为数量
        //角标以 0 未开始获取
//        if (code.length()>12){
//            try {
//                String barcode = code.substring(0, 12);
//                list.add(barcode);
//                String num = code.substring(12, code.length());
//                list.add(num);
//                return list;
//            } catch (Exception e) {
//                Toast.showText(App.getContext(), "条码有误");
//                return new ArrayList<>();
//            }
//        }else{
//            Toast.showText(App.getContext(), "条码有误");
//            return new ArrayList<>();
//        }
    }

    //生成单据编号（生成的单据编号只用作查找，不具备时间效应）
    public static long createOrderCode(Activity activity) {
        Long ordercode = 0l;
        ShareUtil share = ShareUtil.getInstance(activity.getApplicationContext());
        if (share.getOrderCode(activity) == 0) {
            ordercode = Long.parseLong(getTimeLong(false) + "001");
            share.setOrderCode(activity, ordercode);
        } else {
            //当不是当天时，生成新的单据，重新计算
//            if (String.valueOf(share.getOrderCode(activity)).contains(getTime(false))) {
                ordercode = share.getOrderCode(activity);
//            } else {
//                ordercode = Long.parseLong(getTimeLong(false) + "001");
//                share.setOrderCode(activity, ordercode);
//            }
        }
//        Log.e("生成新的单据:", ordercode + "");
        return ordercode;
    }
    //生成单据编号（生成的单据编号只用作查找，不具备时间效应）
    public static long createOrderCode(int activity) {
        Long ordercode = 0l;
        ShareUtil share = ShareUtil.getInstance(App.getContext());
        if (share.getOrderCode(activity) == 0) {
            ordercode = Long.parseLong(getTimeLong(false) + "001");
            share.setOrderCode(activity, ordercode);
        } else {
            //当不是当天时，生成新的单据，重新计算
//            if (String.valueOf(share.getOrderCode(activity)).contains(getTime(false))) {
            ordercode = share.getOrderCode(activity);
//            } else {
//                ordercode = Long.parseLong(getTimeLong(false) + "001");
//                share.setOrderCode(activity, ordercode);
//            }
        }
//        Log.e("生成新的单据:", ordercode + "");
        return ordercode;
    }
    //生成单据编号（生成的单据编号只用作查找，不具备时间效应）下推单时使用
    public static long createOrderCode(String activity) {
        Long ordercode = 0l;
        ShareUtil share = ShareUtil.getInstance(App.getContext());
        if (share.getOrderCode(activity) == 0) {
            ordercode = Long.parseLong(getTimeLong(false) + "001");
            share.setOrderCode(activity, ordercode);
        } else {
            //当不是当天时，生成新的单据，重新计算
//            if (String.valueOf(share.getOrderCode(activity)).contains(getTime(false))) {
            ordercode = share.getOrderCode(activity);
//            } else {
//                ordercode = Long.parseLong(getTimeLong(false) + "001");
//                share.setOrderCode(activity, ordercode);
//            }
        }
//        Log.e("生成新的单据:", ordercode + "");
        return ordercode;
    }

    public static String getTime(boolean b) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(b ? "yyyy-MM-dd" : "yyyyMMdd");
        Date curDate = new Date();
        Log.e("date", curDate.toString());
        String str = format.format(curDate);
        return str;
    }

    public static String getTimeLong(boolean b) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(b ? "yyyy-MM-dd-HH-mm-ss" : "yyyyMMddHHmmss");
        Date curDate = new Date();
        Log.e("date", curDate.toString());
        String str = format.format(curDate);
        return str;
    }

    //用于页面中DataBinding判断是否开启批次并且处理
    public static boolean isOpen(String string) {
        if (null != string && "1".equals(string)) {
            return true;
        } else {
            return false;
        }
    }

    //是否开启库存管理
    public static boolean isAllowFStore(String string) {
        if (null != string && "1".equals(string)) {
            return true;
        } else {
            return false;
        }
    }

    //读取本地的txt文件
    public static String getString(String txtName) {
        InputStreamReader inputStreamReader = null;
                String lineTxt = null;
        try {

            File file = new File(Environment.getExternalStorageDirectory()+"/"+txtName);
            if (file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                while ((lineTxt = br.readLine()) != null) {
                    Lg.e("读取txt:"+lineTxt);
                    System.out.println(lineTxt);
                }
                br.close();
            } else {
                System.out.println("文件不存在!");
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineTxt;
    }

    //解密加密的时间
    public static String dealTime(String timemd){
//        Lg.e("加密的日期："+timemd);
        StringBuffer buffer = new StringBuffer()
                .append(timemd.charAt(Integer.parseInt(Config.Key.charAt(0)+"")+1))
                .append(timemd.charAt(Integer.parseInt(Config.Key.charAt(1)+"")+2))
                .append(timemd.charAt(Integer.parseInt(Config.Key.charAt(2)+"")+3))
                .append(timemd.charAt(Integer.parseInt(Config.Key.charAt(3)+"")+4))
                .append(timemd.charAt(Integer.parseInt(Config.Key.charAt(4)+"")+5))
                .append(timemd.charAt(Integer.parseInt(Config.Key.charAt(5)+"")+6))
                .append(timemd.charAt(Integer.parseInt(Config.Key.charAt(6)+"")+7))
                .append(timemd.charAt(Integer.parseInt(Config.Key.charAt(7)+"")+8));
        Lg.e("解析日期："+buffer.toString());
        return buffer.toString();
    }

    //androidmanifest中获取版本名称
    public static String getVersionName() {
        PackageManager packageManager = App.getContext().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    App.getContext().getPackageName(), 0);

            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;

            System.out.println("versionName=" + versionName + ";versionCode="
                    + versionCode);

            return versionName;
        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }

        return "";
    }

    public static String getTimesecond() {
        Date curDate = new Date();
        Long time = curDate.getTime();
        return time + "";
    }

//标准销售订单  对应 销售出库单的单据类型 XSCKD01_SYS
//寄售销售订单  对应 销售出库单的单据类型 XSCKD02_SYS
//分销购销订单  对应 销售出库单的单据类型 XSCKD04_SYS
//VMI销售订单   对应 销售出库单的单据类型 XSCKD05_SYS
//现销订单      对应 销售出库单的单据类型 XSCKD06_SYS
    public static String getSaleOutBillType(String saleoder){
        String backData="";
//        if (null==saleoder||"".equals(saleoder)){
//            backData="XSCKD01_SYS";
//            return backData;
//        }
        switch (saleoder==null||"".equals(saleoder)?"":saleoder){
            case "标准销售订单":
                backData="XSCKD01_SYS";
                break;
            case "寄售销售订单":
                backData="XSCKD02_SYS";
                break;
            case "分销购销订单":
                backData="XSCKD04_SYS";
                break;
            case "VMI销售订单":
                backData="XSCKD05_SYS";
                break;
            case "现销订单":
                backData="XSCKD06_SYS";
                break;
            case "":
                backData="XSCKD01_SYS";
                break;
        }
        return backData;
    }
}




















/*
String str = "abc,12,3yy98,0";
String[]  strs=str.split(",");//以，截取   或者\\^
for(int i=0,len=strs.length;i<len;i++){
    System.out.println(strs[i].toString());



 sb.substring(2);//索引号 2 到结尾

String barcode = code.substring(0, 12);//第一位到第十一位

3.通过StringUtils提供的方法
StringUtils.substringBefore(“dskeabcee”, “e”);
/结果是：dsk/
这里是以第一个”e”，为标准。

StringUtils.substringBeforeLast(“dskeabcee”, “e”)
结果为：dskeabce
这里以最后一个“e”为准。
* */