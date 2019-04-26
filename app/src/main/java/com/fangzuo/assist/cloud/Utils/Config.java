package com.fangzuo.assist.cloud.Utils;

public class Config {
    //用于崩溃信息错误信息回执的服务器地址：
//    public static final String Error_Url = "http://192.168.0.115:8083/Assist/GetLogMessage";
    public static final String Error_Url = "http://148.70.108.65:8080/LogAssist/GetLogMessage";
    public static final String Apk_Url = "http://148.70.108.65:8080/AppFile/Cloud/app-debug.apk";
    public static final String Apk_Version = "http://148.70.108.65:8080/AppFile/Cloud/version.txt";
    public static String Company="通用Cloud版";
    public static String SaveTime="SaveTime";//用于保存使用截止日期
    public static String Key="01235679";//用于保存使用截止日期（需要web端的key与之相同,并且不能倒序，只能递增的数字）
    public static String PDA_IMIE="PDA_IMIE";//用于保存注册码
    public static String PDA_Language="PDA_Language";//用于保存语言
    public static String PDA_RegisterCode="PDA_RegisterCode";//用于保存注册码
    public static String PDA_RegisterMaxNum="PDA_RegisterMaxNum";//用于保存注册码


    public static final String Cloud_Url = "http://47.106.179.214/K3Cloud/";
    public static final String Cloud_ID = "DataBaseID";
    //回单的接口key
    public static final String C_Draft =        "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Draft.common.kdsvc";//暂存
    public static final String C_Save =         "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Save.common.kdsvc";//保存
    public static final String C_BatcnSave =    "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.BatchSave.common.kdsvc";//批量保存
    public static final String C_View =         "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.View.common.kdsvc";
    public static final String C_Submit =       "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Submit.common.kdsvc";
    public static final String C_Audit =        "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Audit.common.kdsvc";
    public static final String C_UnAudit =      "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.UnAudit.common.kdsvc";
    public static final String C_StatusConvert ="Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.StatusConvert.common.kdsvc";
    public static final String C_Login =        "Kingdee.BOS.WebApi.ServicesStub.AuthService.ValidateUser.common.kdsvc";


    public static final String DATABASESETTING = "master";
    public static final String OBJ_BLUETOOTH="key_Bluetooth_object";
    public static final String PDA = "PDA";
    public static final String PrintNum = "PrintNum";
    public static final String[] PDA_Type = {"请选择设备型号","G02A设备", "8000设备", "5000设备","M60","手机端"};

    //用于接口回调的判断------------------------------------------
    public static final String IO_type_Test="IO_type_Test";
    public static final String IO_type_TestDataBase="IO_type_TestDataBase";
    public static final String IO_type_SetProp="IO_type_SetProp";
    public static final String IO_type_connectToSQL="IO_type_connectToSQL";


    //用于Presenter下载表的type区分
    public static final int Data_Bibie           =1;    //币别表
    public static final int Data_Department      =2;    //部门表
    public static final int Data_Employee        =3;    //职员表
    public static final int Data_WaveHouse       =4;    //仓位表
    public static final int Data_InstorageNums   =5;    //库存表
    public static final int Data_Storage         =6;    //仓库表
    public static final int Data_Unit            =7;    //单位表
    public static final int Data_BarCodes        =8;    //条码表":
    public static final int Data_Suppliers       =9;    //供应商表"
    public static final int Data_PayTypes        =10;   //结算方式表
    public static final int Data_Product         =11;   //商品资料表
    public static final int Data_User            =12;   //用户信息表
    public static final int Data_Client          =13;   //客户信息表
    public static final int Data_GoodsDepartments=14;   //交货单位"
    public static final int Data_PurchaseMethod  =15;   //销售/采购方式表
    public static final int Data_yuandanType     =16;   //源单类型"
    public static final int Data_Wanglaikemu     =17;   //往来科目"
    public static final int Data_PriceMethods    =18;   //价格政策"
    public static final int Data_InStoreType     =19;   //入库类型"
    public static final int Data_Company         =20;   //公司信息表
    public static final int Data_Product_Type    =21;   //物料类别


    public static final String Text_Log    ="Text_Log1";   //物料类别

    public static final int PushDownMTActivity             =10001;
    public static final int PushDownPOActivity             =10002;
    public static final int PushDownSNActivity             =10003;
    public static final int ShouLiaoTongZhiActivity        =10004;
    public static final int OutsourcingOrdersISActivity    =10005;
    public static final int OutsourcingOrdersOSActivity    =10006;
    public static final int ProducePushInStoreActivity     =10007;
    public static final int ShengchanrenwudanxiatuilingliaoActivity =10008;
    public static final int CGDDPDSLTZDActivity                     =10009;
    public static final int XSDDPDFLTZDActivity                     =10010;
    public static final int SCRWDPDSCHBDActivity                    =10011;
    public static final int HBDPDCPRKActivity                       =10012;
    public static final int OutCheckGoodsActivity                   =10013;
    public static final int FHTZDDBActivity                         =10014;
    public static final int PurchaseInStorageActivity               =10016;
    public static final int ProductInStorageActivity                =10017;
//    public static final int OtherInStoreActivity                    =10018;
    public static final int SoldOutActivity                         =10019;
    public static final int ProduceAndGetActivity                   =10020;
//    public static final int OtherOutStoreActivity                   =10021;
//    public static final int PurchaseOrderActivity                   =10022;
//    public static final int SaleOrderActivity                       =10023;
//    public static final int PDActivity                              =10024;


    public static final String Lock                              ="Lock";//用于判断是否锁定表头的string
    public static final String OrderNo                              ="OrderNo";//用于锁定表头业务单号的key
    public static final String Note                              ="Note";//用于锁定表头备注的key
    public static final int PurchaseInStoreActivity                 =10025;//采购入库
    public static final int ProductInStoreActivity                  =10026;//产品入库
    public static final int ProductGetActivity                      =10027;//生产领料
    public static final int SaleOutActivity                         =10028;//销售出库
    public static final int OtherInStoreActivity                    =10029;//其他入库
    public static final int OtherOutStoreActivity                   =10030;//其他出库
    public static final int SaleOrderActivity                       =10031;//销售订单
    public static final int PurchaseOrderActivity                   =10032;//采购订单
    public static final int PdCgOrder2WgrkActivity                  =10033;//采购订单下推外购入库单
    public static final int PdSaleOrder2SaleOutActivity             =10034;//销售订单下推销售出库单
    public static final int PdSaleOrder2SaleBackActivity            =10035;//销售订单下推销售退货单
    public static final int PdSaleOut2SaleBackActivity              =10036;//销售出库单下推销售退货单
    public static final int PdSendMsg2SaleOutActivity               =10037;//发货通知单下推销售出库单
    public static final int PdBackMsg2SaleBackActivity              =10038;//退货通知单下推销售退货单
    public static final int Db2FDinActivity                         =10039;//调拨申请单下推分布式调入单
    public static final int Db2FDoutActivity                        =10040;//调拨申请单下推分布式调出单
    public static final int PDActivity                              =10041;//盘点单
    public static final int DBActivity                              =10042;//调拨单
    public static final int DgInActivity                            =10044;//到柜入库
    public static final int SimpleInActivity                        =10045;//简单生产入库
    public static final int TbGetActivity                           =10046;//挑板领料
    public static final int TbInActivity                            =10047;//挑板入库
    public static final int GbGetActivity                           =10048;//改板领料
    public static final int GbInActivity                            =10049;//改板入库
    public static final int DcOutActivity                           =10050;//代存出库
    public static final int DcInActivity                            =10051;//代存入库
    public static final int DhInActivity                            =10052;//到货入库
    public static final int YbOutActivity                           =10053;//样板出库
    public static final int HwIn3Activity                           =10054;//第三方货物入库
    public static final int HwOut3Activity                          =10055;//第三方货物出库

}
