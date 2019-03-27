package com.fangzuo.assist.cloud.Dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by NB on 2017/8/24.
 *      订单详情
 */
@Entity
public class PushDownSub {
    @Id(autoincrement = true)
    public Long id;
    public String FSEQ;
    public String FID;
    public String FMaterialID;
    public String FEntryID;
    public String FUnitID;
    public String FNumber;
    public String FName;
    public String FModel;
    public String FBillNo;
    public String FQty;
    public String FQtying;
    public String FTaxPrice;
    public String FStockID;
    public String FBatchNo;
    public String FBaseCanreturnQty;
    public String FHuoZhuNumber;
    @Generated(hash = 947756660)
    public PushDownSub(Long id, String FSEQ, String FID, String FMaterialID,
            String FEntryID, String FUnitID, String FNumber, String FName,
            String FModel, String FBillNo, String FQty, String FQtying,
            String FTaxPrice, String FStockID, String FBatchNo,
            String FBaseCanreturnQty, String FHuoZhuNumber) {
        this.id = id;
        this.FSEQ = FSEQ;
        this.FID = FID;
        this.FMaterialID = FMaterialID;
        this.FEntryID = FEntryID;
        this.FUnitID = FUnitID;
        this.FNumber = FNumber;
        this.FName = FName;
        this.FModel = FModel;
        this.FBillNo = FBillNo;
        this.FQty = FQty;
        this.FQtying = FQtying;
        this.FTaxPrice = FTaxPrice;
        this.FStockID = FStockID;
        this.FBatchNo = FBatchNo;
        this.FBaseCanreturnQty = FBaseCanreturnQty;
        this.FHuoZhuNumber = FHuoZhuNumber;
    }
    @Generated(hash = 2008125598)
    public PushDownSub() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFSEQ() {
        return this.FSEQ;
    }
    public void setFSEQ(String FSEQ) {
        this.FSEQ = FSEQ;
    }
    public String getFID() {
        return this.FID;
    }
    public void setFID(String FID) {
        this.FID = FID;
    }
    public String getFMaterialID() {
        return this.FMaterialID;
    }
    public void setFMaterialID(String FMaterialID) {
        this.FMaterialID = FMaterialID;
    }
    public String getFEntryID() {
        return this.FEntryID;
    }
    public void setFEntryID(String FEntryID) {
        this.FEntryID = FEntryID;
    }
    public String getFUnitID() {
        return this.FUnitID;
    }
    public void setFUnitID(String FUnitID) {
        this.FUnitID = FUnitID;
    }
    public String getFNumber() {
        return this.FNumber;
    }
    public void setFNumber(String FNumber) {
        this.FNumber = FNumber;
    }
    public String getFName() {
        return this.FName;
    }
    public void setFName(String FName) {
        this.FName = FName;
    }
    public String getFModel() {
        return this.FModel;
    }
    public void setFModel(String FModel) {
        this.FModel = FModel;
    }
    public String getFBillNo() {
        return this.FBillNo;
    }
    public void setFBillNo(String FBillNo) {
        this.FBillNo = FBillNo;
    }
    public String getFQty() {
        return this.FQty;
    }
    public void setFQty(String FQty) {
        this.FQty = FQty;
    }
    public String getFQtying() {
        return this.FQtying;
    }
    public void setFQtying(String FQtying) {
        this.FQtying = FQtying;
    }
    public String getFTaxPrice() {
        return this.FTaxPrice;
    }
    public void setFTaxPrice(String FTaxPrice) {
        this.FTaxPrice = FTaxPrice;
    }
    public String getFStockID() {
        return this.FStockID;
    }
    public void setFStockID(String FStockID) {
        this.FStockID = FStockID;
    }
    public String getFBatchNo() {
        return this.FBatchNo;
    }
    public void setFBatchNo(String FBatchNo) {
        this.FBatchNo = FBatchNo;
    }
    public String getFBaseCanreturnQty() {
        return this.FBaseCanreturnQty;
    }
    public void setFBaseCanreturnQty(String FBaseCanreturnQty) {
        this.FBaseCanreturnQty = FBaseCanreturnQty;
    }
    public String getFHuoZhuNumber() {
        return this.FHuoZhuNumber;
    }
    public void setFHuoZhuNumber(String FHuoZhuNumber) {
        this.FHuoZhuNumber = FHuoZhuNumber;
    }

}
