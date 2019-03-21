package com.fangzuo.assist.cloud.Beans;

public class CodeCheckBackDataBean {
    public String FTip;
    public String FBillNo;
    public String FItemID;
    public String FUnitID;
    public String FQty;
    public String FBaseQty;
    public String FStoreQty;
    public String FStockID;
    public String FStockPlaceID;
    public String FBatchNo;
    public String FKFPeriod;
    public String FKFDate;
    public String FActualmodel;
    public String FAuxsign;
    public String FPurchaseNo;

    public String FBarCode;



    public CodeCheckBackDataBean(String FTip){
        this.FTip=FTip;
    }

    @Override
    public String toString() {
        return "CodeCheckBackDataBean{" +
                "FTip='" + FTip + '\'' +
                "FBillNo='" + FBillNo + '\'' +
                ", FItemID='" + FItemID + '\'' +
                ", FUnitID='" + FUnitID + '\'' +
                ", FQty='" + FQty + '\'' +
                ", FStockID='" + FStockID + '\'' +
                ", FStockPlaceID='" + FStockPlaceID + '\'' +
                ", FBatchNo='" + FBatchNo + '\'' +
                ", FKFPeriod='" + FKFPeriod + '\'' +
                ", FKFDate='" + FKFDate + '\'' +
                '}';
    }
}
