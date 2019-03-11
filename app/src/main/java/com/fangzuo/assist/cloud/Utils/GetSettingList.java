package com.fangzuo.assist.cloud.Utils;


import com.fangzuo.assist.cloud.Beans.SettingList;
import com.fangzuo.assist.cloud.R;

import java.util.ArrayList;

/**
 * Created by NB on 2017/7/28.
 */

public class GetSettingList {
    public static ArrayList<SettingList> getList() {
        ArrayList<SettingList> items = new ArrayList<>();
        items.add(new SettingList("下载配置", R.mipmap.download));
        items.add(new SettingList("wifi连接",R.mipmap.wifi));
        items.add(new SettingList("声音设置",R.mipmap.sound));
        items.add(new SettingList("更新版本",R.mipmap.getnewversion));
        items.add(new SettingList("服务器设置",R.mipmap.tomcat));
        items.add(new SettingList("打印配置与测试",R.mipmap.test));
        items.add(new SettingList("网络测试",R.mipmap.test));
        return items;
    }
    public static ArrayList<SettingList> getPurchaseList() {
        ArrayList<SettingList> items = new ArrayList<>();
        items.add(new SettingList("简单产品入库",R.mipmap.purchaseinstorage));
        items.add(new SettingList("简单生产领料",R.mipmap.chuku));
        items.add(new SettingList("调拨单",R.mipmap.diaobo));
        items.add(new SettingList("标签补打",R.mipmap.printmain));
        items.add(new SettingList("期初物料补打",R.mipmap.printmain));
        items.add(new SettingList("销售出库",R.mipmap.sellinout));
        items.add(new SettingList("其他入库",R.mipmap.ruku));
        items.add(new SettingList("其他出库",R.mipmap.chuku));
        items.add(new SettingList("单据下推",R.mipmap.sellout));
        items.add(new SettingList("盘点",R.mipmap.pandian));
        //-------------
        items.add(new SettingList("采购订单",R.mipmap.purchaseorder));
        items.add(new SettingList("采购入库",R.mipmap.purchaseorder));
        items.add(new SettingList("销售订单",R.mipmap.saleorder));
//        items.add(new SettingList("盘点",R.mipmap.pandian));
//        for (int i=0; i<items.size();i++){
//            Log.e("test","定位ary:"+ary[i]);
//            Log.e("test","定位items:"+items.get(i).tag);
            //根据ary的值，遍历list符合的item并添加
//            for (int j=0;j<ary.length;j++){
//                if (TextUtils.equals(items.get(i).tag,ary[j])){
//                    Log.e("test","222加入："+items.get(i).toString());
//                    backItems.add(items.get(i));
//                }
//            }
//        }
        return items;
    }

    public static ArrayList<SettingList> getSaleList() {
        ArrayList<SettingList> items = new ArrayList<>();
//        items.add(new SettingList("销售订单",R.mipmap.saleorder));
//        items.add(new SettingList("销售出库",R.mipmap.sellinout));
//        items.add(new SettingList("单据下推",R.mipmap.sellout));
//        items.add(new SettingList("生产领料",R.mipmap.chuku));
        return items;
    }
    public static ArrayList<SettingList> getStorageList() {
        ArrayList<SettingList> items = new ArrayList<>();
//        items.add(new SettingList("盘点",R.mipmap.pandian));
//        items.add(new SettingList("调拨",R.mipmap.diaobo));
//        items.add(new SettingList("其他入库",R.mipmap.ruku));
//        items.add(new SettingList("其他出库",R.mipmap.chuku));
        return items;
    }

    public static ArrayList<SettingList> GetPushDownList() {
        ArrayList<SettingList> items = new ArrayList<>();
        items.add(new SettingList("采购订单下推外购入库单",R.mipmap.pandian));
        items.add(new SettingList("销售订单下推销售出库单",R.mipmap.pandian));
        items.add(new SettingList("销售订单下推销售退货单",R.mipmap.diaobo));
        items.add(new SettingList("销售出库单下推销售退货单",R.mipmap.ruku));
        items.add(new SettingList("发货通知单下推销售出库单",R.mipmap.chuku));
        items.add(new SettingList("退货通知单下推销售退货单",R.mipmap.pandian));
        items.add(new SettingList("调拨申请单下推分布式调入单",R.mipmap.diaobo));
        items.add(new SettingList("调拨申请单下推分布式调出单",R.mipmap.ruku));

//        items.add(new SettingList("生产任务单下推生产领料",R.mipmap.pandian));
//        items.add(new SettingList("采购订单下推收料通知单",R.mipmap.pandian));
//        items.add(new SettingList("销售订单下推发料通知单",R.mipmap.pandian));
//        items.add(new SettingList("生产任务单下推生产汇报单",R.mipmap.pandian));
//        items.add(new SettingList("汇报单下推产品入库",R.mipmap.pandian));
//        items.add(new SettingList("销售出库单验货",R.mipmap.pandian));
//        items.add(new SettingList("发货通知生成调拨单",R.mipmap.pandian));
//        items.add(new SettingList("产品入库验货",R.mipmap.pandian));
        return items;
    }
}
