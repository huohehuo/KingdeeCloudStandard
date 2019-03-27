package com.fangzuo.assist.cloud.Beans;


import com.fangzuo.assist.cloud.Dao.Product;
import com.fangzuo.assist.cloud.Dao.Unit;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class PrintHistory {
    public String FTitle;
    public String FHuoquan;
    public String FBarCode;
    public String FBatch;
    public String FName;
    public String FModel;
    public String FNum;
    public String FNum2;
    public String FUnit;
    public String FBaseUnit;
    public String FBaseUnitID;
    public String FStoreUnit;
    public String FUnitAux;
    public String FNot;
    public String FWaveHouse;
    public String FSaveIn;
    public String FCheck;
    public String FDate;
    public String FMaterialid;
    public String FAuxSign;
    public String FActualModel;

    public void setProduct(Product product){
        this.FName = product.FName;
        this.FModel = product.FModel;
    }
    public void setData(Product product, Unit unit, Unit unitAux, String num, String num2, String wave,
                        String not, String huoquan, String barcode, String batch, String date, String title,String auxSign){
        this.FTitle = title;
        this.FName = product.FName;
        this.FModel = product.FModel;
        this.FUnit=unit.FName;
        this.FUnitAux=unitAux.FName;
        this.FNum = num;
        this.FNum2 = num2;
        this.FWaveHouse = wave;
        this.FNot = not;
        this.FHuoquan = huoquan;
        this.FBarCode=barcode;
        this.FBatch = batch;
        this.FDate = date;
        this.FAuxSign = auxSign;
    }














    @Generated(hash = 1210314488)
    public PrintHistory(String FTitle, String FHuoquan, String FBarCode, String FBatch, String FName,
            String FModel, String FNum, String FNum2, String FUnit, String FBaseUnit, String FBaseUnitID,
            String FStoreUnit, String FUnitAux, String FNot, String FWaveHouse, String FSaveIn, String FCheck,
            String FDate, String FMaterialid, String FAuxSign, String FActualModel) {
        this.FTitle = FTitle;
        this.FHuoquan = FHuoquan;
        this.FBarCode = FBarCode;
        this.FBatch = FBatch;
        this.FName = FName;
        this.FModel = FModel;
        this.FNum = FNum;
        this.FNum2 = FNum2;
        this.FUnit = FUnit;
        this.FBaseUnit = FBaseUnit;
        this.FBaseUnitID = FBaseUnitID;
        this.FStoreUnit = FStoreUnit;
        this.FUnitAux = FUnitAux;
        this.FNot = FNot;
        this.FWaveHouse = FWaveHouse;
        this.FSaveIn = FSaveIn;
        this.FCheck = FCheck;
        this.FDate = FDate;
        this.FMaterialid = FMaterialid;
        this.FAuxSign = FAuxSign;
        this.FActualModel = FActualModel;
    }
    @Generated(hash = 915761306)
    public PrintHistory() {
    }
    public String getFTitle() {
        return this.FTitle;
    }
    public void setFTitle(String FTitle) {
        this.FTitle = FTitle;
    }
    public String getFHuoquan() {
        return this.FHuoquan;
    }
    public void setFHuoquan(String FHuoquan) {
        this.FHuoquan = FHuoquan;
    }
    public String getFBarCode() {
        return this.FBarCode;
    }
    public void setFBarCode(String FBarCode) {
        this.FBarCode = FBarCode;
    }
    public String getFBatch() {
        return this.FBatch;
    }
    public void setFBatch(String FBatch) {
        this.FBatch = FBatch;
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
    public String getFNum() {
        return this.FNum;
    }
    public void setFNum(String FNum) {
        this.FNum = FNum;
    }
    public String getFNum2() {
        return this.FNum2;
    }
    public void setFNum2(String FNum2) {
        this.FNum2 = FNum2;
    }
    public String getFUnit() {
        return this.FUnit;
    }
    public void setFUnit(String FUnit) {
        this.FUnit = FUnit;
    }
    public String getFUnitAux() {
        return this.FUnitAux;
    }
    public void setFUnitAux(String FUnitAux) {
        this.FUnitAux = FUnitAux;
    }
    public String getFNot() {
        return this.FNot;
    }
    public void setFNot(String FNot) {
        this.FNot = FNot;
    }
    public String getFWaveHouse() {
        return this.FWaveHouse;
    }
    public void setFWaveHouse(String FWaveHouse) {
        this.FWaveHouse = FWaveHouse;
    }
    public String getFSaveIn() {
        return this.FSaveIn;
    }
    public void setFSaveIn(String FSaveIn) {
        this.FSaveIn = FSaveIn;
    }
    public String getFCheck() {
        return this.FCheck;
    }
    public void setFCheck(String FCheck) {
        this.FCheck = FCheck;
    }
    public String getFDate() {
        return this.FDate;
    }
    public void setFDate(String FDate) {
        this.FDate = FDate;
    }
    public String getFMaterialid() {
        return this.FMaterialid;
    }
    public void setFMaterialid(String FMaterialid) {
        this.FMaterialid = FMaterialid;
    }
    public String getFBaseUnit() {
        return this.FBaseUnit;
    }
    public void setFBaseUnit(String FBaseUnit) {
        this.FBaseUnit = FBaseUnit;
    }
    public String getFAuxSign() {
        return this.FAuxSign;
    }
    public void setFAuxSign(String FAuxSign) {
        this.FAuxSign = FAuxSign;
    }
    public String getFActualModel() {
        return this.FActualModel;
    }
    public void setFActualModel(String FActualModel) {
        this.FActualModel = FActualModel;
    }
    public String getFBaseUnitID() {
        return this.FBaseUnitID;
    }
    public void setFBaseUnitID(String FBaseUnitID) {
        this.FBaseUnitID = FBaseUnitID;
    }
    public String getFStoreUnit() {
        return this.FStoreUnit;
    }
    public void setFStoreUnit(String FStoreUnit) {
        this.FStoreUnit = FStoreUnit;
    }
}
