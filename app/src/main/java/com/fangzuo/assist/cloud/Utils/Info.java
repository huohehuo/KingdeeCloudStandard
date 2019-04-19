package com.fangzuo.assist.cloud.Utils;



import org.json.JSONArray;


/**
 * Created by NB on 2017/7/24.
 */

public class Info {
//    public static final String DATABASESETTING = "K3DBConfiger201811123395555";//数据库名称
    public static final String DATABASESETTING = "K3DBConfigerRY";
    public static final String TestNo = "5.2";
    public static final int RegisterNo = 155;
    public static final int SEARCHFORRESULT = 9998;
    public static final int SEARCHFORRESULTPRODUCT = 9997;
    public static final int SEARCHFORRESULTCLIRNT = 9999;
    public static final int SEARCHFORRESULTJH = 9996;
    public static final int SEARCHPRODUCT = 7777;
    public static final int SEARCHSUPPLIER = 7778;
    public static final int SEARCHCLIENT = 7779;
    public static final int SEARCHJH = 7770;

    public static final String AutoLogin="AutoLogin";
    public static final String IsRemanber="IsRemanber";
    public static final String Storage="storage";

    public static final String Type_DB_type="Type_DB_type";
    public static final String Type_DB_direction="Type_DB_direction";

    public static final int format=1;
    public static final String useragent="ApiClient";
    public static final String user_org="user_org";//登录时获取到的组织名称
    public static final String user_id="user_id";//登录时获取到的组织名称
    public static final String user_data="user_data";//登录时获取到的数据中心

    //表单ID
    public static final String FormID_PIS = "STK_InStock";//采购入库单/采购订单下推采购入库单/收料通知单下推采购入库单
    public static final String FormID_SaleOut = "SAL_OUTSTOCK";//销售出库单/销售订单下推销售出库单/发货通知单下推采购入库单
    public static final String FormID_OtherIn = "STK_MISCELLANEOUS";//其他入库单
    public static final String FormID_OtherOut = "STK_MisDelivery";//其他出库单
    public static final String FormID_PuO = "PUR_PurchaseOrder";//采购订单
    public static final String FormID_SaleOrder = "SAL_SaleOrder";//销售订单
    public static final String FormID_DB = "STK_TransferDirect";//调拨单
    public static final String FormID_ProductIS = "SP_InStock";//产品入库单
    public static final String FormID_productGet = "SP_PickMtrl";//生产领料单
    public static final String FormID_SaleBack = "SAL_RETURNSTOCK";//销售退货单
    public static final String FormID_FDi = "STK_TRANSFERIN";//分布式调入单
    public static final String FormID_FDo = "STK_TRANSFEROUT";//分布式调出单
    public static final String FormID_PD = "STK_StockCountScheme";//盘点
    //单据类型
    public static final String BT_PIS = "RKD01_SYS";//采购入库单/采购订单下推采购入库单/收料通知单下推采购入库单
    public static final String BT_SaleOut = "XSCKD01_SYS";//销售出库单/销售订单下推销售出库单/发货通知单下推采购入库单
    public static final String BT_OIS = "QTRKD01_SYS";//其他入库单
//    public static final String BT_OIS = "QTRKD01_BSB";//其他入库单
    public static final String BT_OOS = "QTCKD01_SYS";//其他出库单
    public static final String BT_PuO = "CGDD01_SYS";//采购订单
    public static final String BT_SaleOrder = "XSDD01_SYS";//销售订单
    public static final String BT_DB = "ZJDB01_SYS";//调拨单
    public static final String BT_ProductIS = "JDSCRK01_SYS";//产品入库单
    public static final String BT_ProductGet = "JDSCLL01_SYS";//生产领料单
    public static final String BT_SaleBack = "XSTHD01_SYS";//退货通知单下推销售退货单/销售出库单下推销售退货/销售订单下推销售退货单
    public static final String BT_FDi = "FBDR01_SYS";//调拨申请单下推分布式调入单
    public static final String BT_FDo = "FBDC01_SYS";//调拨申请单下推分布式调出单
    public static final String BT_PD = "PDFA01_SYS";//盘点单


    //拼接回单的数据（单据ID+json)
    public static String getJson(int activity,String json){
        Lg.e("回单json:"+json);
        //业务对象Id
        String formid = getFormID(activity);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(formid);
        jsonArray.put(json);
        return jsonArray.toString();
    }

    //根据activity获取相应的单据ID
    private static String getFormID(int activity){
        String backString="";
        switch (activity){
            case Config.PdCgOrder2WgrkActivity:
            case Config.PurchaseInStoreActivity://采购入库
                backString=FormID_PIS;
                break;
            case Config.ProductInStoreActivity://产品入库
            case Config.TbInActivity://挑板入库
            case Config.GbInActivity://改版入库
            case Config.DhInActivity://到货入库
            case Config.SimpleInActivity://产品入库
                backString=FormID_ProductIS;
                break;
            case Config.ProductGetActivity://生产领料
            case Config.TbGetActivity://挑板领料
            case Config.GbGetActivity://改板领料
                backString=FormID_productGet;
                break;
            case Config.SaleOutActivity://销售出库
            case Config.PdSaleOrder2SaleOutActivity:
            case Config.PdSendMsg2SaleOutActivity:
                backString=FormID_SaleOut;
                break;
            case Config.OtherInStoreActivity://其他入库
            case Config.HwIn3Activity://第三方货物入库
                backString=FormID_OtherIn;
                break;
            case Config.OtherOutStoreActivity://其他出库
            case Config.YbOutActivity://样板出库
            case Config.HwOut3Activity://第三方货物出库
                backString=FormID_OtherOut;
                break;
            case Config.SaleOrderActivity://销售订单
                backString=FormID_SaleOrder;
                break;
            case Config.PurchaseOrderActivity://采购订单
                backString=FormID_PuO;
                break;
            case Config.PdSaleOrder2SaleBackActivity://销售订单下推销售退货单
            case Config.PdSaleOut2SaleBackActivity://销售出库单下推销售退货单
            case Config.PdBackMsg2SaleBackActivity://退货通知单下推销售退货单
                backString=FormID_SaleBack;
                break;
            case Config.Db2FDinActivity://调拨申请单下推分布式调入单
                backString=FormID_FDi;
                break;
            case Config.Db2FDoutActivity://调拨申请单下推分布式调出单
                backString=FormID_FDo;
                break;
            case Config.PDActivity://调拨申请单下推分布式调出单
                backString=FormID_PD;
                break;
            case Config.DBActivity://调拨单
                backString=FormID_DB;
                break;

        }
        return backString;
    }

    //根据activity获取相应的单据类型
    public static String getType(int activity){
        String backString="";
        switch (activity){
            case Config.PdCgOrder2WgrkActivity://采购订单下推外购入库单
            case Config.PurchaseInStoreActivity:
                backString=BT_PIS;
                break;
            case Config.ProductInStoreActivity://产品入库
            case Config.TbInActivity://挑板入库
            case Config.GbInActivity://改版入库
            case Config.DhInActivity://到货入库
            case Config.SimpleInActivity://产品入库
                backString=BT_ProductIS;
                break;
            case Config.ProductGetActivity://生产领料
            case Config.TbGetActivity://挑板领料
            case Config.GbGetActivity://改板领料
                backString=BT_ProductGet;
                break;
            case Config.SaleOutActivity:
            case Config.PdSaleOrder2SaleOutActivity:
            case Config.PdSendMsg2SaleOutActivity:
                backString=BT_SaleOut;
                break;
            case Config.OtherInStoreActivity://其他入库
            case Config.HwIn3Activity://第三方货物入库
                backString=BT_OIS;
                break;
            case Config.OtherOutStoreActivity://其他出库
            case Config.YbOutActivity://样板出库
            case Config.HwOut3Activity://第三方货物出库
                backString=BT_OOS;
                break;
            case Config.SaleOrderActivity:
                backString=BT_SaleOrder;
                break;
            case Config.PurchaseOrderActivity:
                backString=BT_PuO;
                break;
            case Config.PdSaleOrder2SaleBackActivity://销售订单下推销售退货单
            case Config.PdSaleOut2SaleBackActivity://销售出库单下推销售退货单
            case Config.PdBackMsg2SaleBackActivity://退货通知单下推销售退货单
                backString=BT_SaleBack;
                break;
            case Config.Db2FDinActivity://调拨申请单下推分布式调入单
                backString=BT_FDi;
                break;
            case Config.Db2FDoutActivity://调拨申请单下推分布式调出单
                backString=BT_FDo;
                break;
            case Config.PDActivity://调拨申请单下推分布式调出单
                backString=BT_PD;
                break;
            case Config.DBActivity://调拨单
                backString=BT_DB;
                break;

        }
        return backString;
    }
}
