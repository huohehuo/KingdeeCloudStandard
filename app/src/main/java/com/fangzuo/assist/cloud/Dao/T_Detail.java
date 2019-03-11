package com.fangzuo.assist.cloud.Dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by NB on 2017/8/3.
 */
@Entity
public class T_Detail {


    @Id
    public String FIndex;
    public String FBarcode;
    public String IMIE;
    public String FBillerID;
    public long FOrderId;
    public int activity;

    public String FEntryID;     //下推的明细唯一值
    public String FID;     //下推的明细内码

    public String FProductName;
    public String model;
    public String FStorageId;           //实为number
    public String FStoragePDId;         //盘点时的仓库id
    public String FStorage;             //仓库名
    public String FWaveHouse;             //仓库名
    public String FWaveHouseId;             //仓库名
    public String FWaveHousePDId;             //仓库名
    public String FUnit;
    public String FSupplier;
    public String FQuantity;            //

    public String FBackDate;            //退货日期
    public String FBackType;            //退货类型

    public String FSOEntryId;//销售订单EntryId
    public String FB2CORDERDETAILID;//B2C订单明细Id

    public String FStorageOutId;            //
    public String FStorageOut;            //
    public String FStorageInId;            //
    public String FStorageIn;            //
    public String FWaveHouseOutId;            //
    public String FWaveHouseOut;            //
    public String FWaveHouseInId;            //
    public String FWaveHouseIn;            //


    public String FBatch;
    public boolean FIsFree;
    public String FWorkShopId1;         //生产车间
    public String FRemainInStockQty;            ////采购数量
    public String FRealQty;            //实收数量
    public String FRemainInStockUnitId;//采购单位
    public String FPriceUnitID;//计价单位
    public String FMaterialId;//物料编码
    public String FMaterialIdForPD;//物料编码
    public String FUnitID;//库存单位
    public String FUnitIDForPD;//库存单位
    public String AuxSign;//辅助标识
    public String ActualModel;//实际规格
    public String FProductNo;//生产编号
    //-----------------------------------------

    //盘点
    public String FBillNo;//盘点方案编码
    public String FAllowAddMaterial;//物料盘点作业允许增加物料
    public String FZeroStockInCount;//零库存参与盘点
    public String FBillTypeID;//单据类型
    public String FCheckQtyDefault;//实盘数默认值
    public String FNotIncludeForbidMat;//禁用物料不参与盘点
    public String FStockOrgId;//库存组织
    public String FDocumentStatus;//状态
    public String FCloseStatus;//关闭状态
    public String FNote;//备注
    public String FOWnerTypeID;//货主类型
    public String FOwnerId;//货主
    public String FKeeperTypeId;//保管者类型
    public String FKeeperId;//保管者




    @Generated(hash = 1273864512)
    public T_Detail(String FIndex, String FBarcode, String IMIE, String FBillerID,
            long FOrderId, int activity, String FEntryID, String FID,
            String FProductName, String model, String FStorageId,
            String FStoragePDId, String FStorage, String FWaveHouse,
            String FWaveHouseId, String FWaveHousePDId, String FUnit,
            String FSupplier, String FQuantity, String FBackDate, String FBackType,
            String FSOEntryId, String FB2CORDERDETAILID, String FStorageOutId,
            String FStorageOut, String FStorageInId, String FStorageIn,
            String FWaveHouseOutId, String FWaveHouseOut, String FWaveHouseInId,
            String FWaveHouseIn, String FBatch, boolean FIsFree,
            String FWorkShopId1, String FRemainInStockQty, String FRealQty,
            String FRemainInStockUnitId, String FPriceUnitID, String FMaterialId,
            String FMaterialIdForPD, String FUnitID, String FUnitIDForPD,
            String AuxSign, String ActualModel, String FProductNo, String FBillNo,
            String FAllowAddMaterial, String FZeroStockInCount, String FBillTypeID,
            String FCheckQtyDefault, String FNotIncludeForbidMat,
            String FStockOrgId, String FDocumentStatus, String FCloseStatus,
            String FNote, String FOWnerTypeID, String FOwnerId,
            String FKeeperTypeId, String FKeeperId) {
        this.FIndex = FIndex;
        this.FBarcode = FBarcode;
        this.IMIE = IMIE;
        this.FBillerID = FBillerID;
        this.FOrderId = FOrderId;
        this.activity = activity;
        this.FEntryID = FEntryID;
        this.FID = FID;
        this.FProductName = FProductName;
        this.model = model;
        this.FStorageId = FStorageId;
        this.FStoragePDId = FStoragePDId;
        this.FStorage = FStorage;
        this.FWaveHouse = FWaveHouse;
        this.FWaveHouseId = FWaveHouseId;
        this.FWaveHousePDId = FWaveHousePDId;
        this.FUnit = FUnit;
        this.FSupplier = FSupplier;
        this.FQuantity = FQuantity;
        this.FBackDate = FBackDate;
        this.FBackType = FBackType;
        this.FSOEntryId = FSOEntryId;
        this.FB2CORDERDETAILID = FB2CORDERDETAILID;
        this.FStorageOutId = FStorageOutId;
        this.FStorageOut = FStorageOut;
        this.FStorageInId = FStorageInId;
        this.FStorageIn = FStorageIn;
        this.FWaveHouseOutId = FWaveHouseOutId;
        this.FWaveHouseOut = FWaveHouseOut;
        this.FWaveHouseInId = FWaveHouseInId;
        this.FWaveHouseIn = FWaveHouseIn;
        this.FBatch = FBatch;
        this.FIsFree = FIsFree;
        this.FWorkShopId1 = FWorkShopId1;
        this.FRemainInStockQty = FRemainInStockQty;
        this.FRealQty = FRealQty;
        this.FRemainInStockUnitId = FRemainInStockUnitId;
        this.FPriceUnitID = FPriceUnitID;
        this.FMaterialId = FMaterialId;
        this.FMaterialIdForPD = FMaterialIdForPD;
        this.FUnitID = FUnitID;
        this.FUnitIDForPD = FUnitIDForPD;
        this.AuxSign = AuxSign;
        this.ActualModel = ActualModel;
        this.FProductNo = FProductNo;
        this.FBillNo = FBillNo;
        this.FAllowAddMaterial = FAllowAddMaterial;
        this.FZeroStockInCount = FZeroStockInCount;
        this.FBillTypeID = FBillTypeID;
        this.FCheckQtyDefault = FCheckQtyDefault;
        this.FNotIncludeForbidMat = FNotIncludeForbidMat;
        this.FStockOrgId = FStockOrgId;
        this.FDocumentStatus = FDocumentStatus;
        this.FCloseStatus = FCloseStatus;
        this.FNote = FNote;
        this.FOWnerTypeID = FOWnerTypeID;
        this.FOwnerId = FOwnerId;
        this.FKeeperTypeId = FKeeperTypeId;
        this.FKeeperId = FKeeperId;
    }
    @Generated(hash = 594111564)
    public T_Detail() {
    }

//设置物料基础信息
    public void setProduct(Product product){
        if (null==product){
            return;
        }
        this.FProductName = product.FName;
        this.model= product.FModel;
        this.FMaterialId= product.FNumber;
        this.FMaterialIdForPD= product.FMaterialid;
//        this.FMaterialId= "CH4446";
    }
    //设置仓库基础信息
    public void setStorage(Storage storage){
        if (null==storage){
            return;
        }
        this.FStorageId = storage.FNumber;
        this.FStoragePDId = storage.FItemID;
        this.FStorage= storage.FName;
    }
    public void setStorageOut(Storage storage){
        if (null==storage){
            return;
        }
        this.FStorageOutId = storage.FNumber;
        this.FStorageOut= storage.FName;
    }
    public void setStorageIn(Storage storage){
        if (null==storage){
            return;
        }
        this.FStorageInId = storage.FNumber;
        this.FStorageIn= storage.FName;
    }


    //设置仓位基础信息
    public void setWaveHouse(WaveHouse waveHouse){
        if (null==waveHouse){
            this.FWaveHouseId = "";
            this.FWaveHousePDId= "0";
            this.FWaveHouse= "";
            return;
        }
        this.FWaveHouseId = waveHouse.FNumber;
        this.FWaveHousePDId = waveHouse.FSPID;
        this.FWaveHouse= waveHouse.FName;
    }
    //设置仓位基础信息
    public void setWaveHouseOut(WaveHouse waveHouse){
        if (null==waveHouse){
            this.FWaveHouseOutId = "";
            this.FWaveHouseOut= "";
            return;
        }
        this.FWaveHouseOutId = waveHouse.FNumber;
        this.FWaveHouseOut= waveHouse.FName;
    }
    //设置仓位基础信息
    public void setWaveHouseIn(WaveHouse waveHouse){
        if (null==waveHouse){
            this.FWaveHouseInId = "";
            this.FWaveHouseIn= "";
            return;
        }
        this.FWaveHouseInId = waveHouse.FNumber;
        this.FWaveHouseIn= waveHouse.FName;
    }
    //设置单位基础信息
    public void setUnit(Unit unit){
        if (null==unit){
            return;
        }
        this.FUnitID = unit.FNumber;
        this.FUnitIDForPD = unit.FMeasureUnitID;
        this.FUnit= unit.FName;
        this.FPriceUnitID = unit.FNumber;//计价单位
        this.FRemainInStockUnitId = unit.FNumber;////采购单位
    }

    public String getFIndex() {
        return this.FIndex;
    }
    public void setFIndex(String FIndex) {
        this.FIndex = FIndex;
    }
    public long getFOrderId() {
        return this.FOrderId;
    }
    public void setFOrderId(long FOrderId) {
        this.FOrderId = FOrderId;
    }
    public int getActivity() {
        return this.activity;
    }
    public void setActivity(int activity) {
        this.activity = activity;
    }
    public String getFEntryID() {
        return this.FEntryID;
    }
    public void setFEntryID(String FEntryID) {
        this.FEntryID = FEntryID;
    }
    public String getFProductName() {
        return this.FProductName;
    }
    public void setFProductName(String FProductName) {
        this.FProductName = FProductName;
    }
    public String getModel() {
        return this.model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getFStorageId() {
        return this.FStorageId;
    }
    public void setFStorageId(String FStorageId) {
        this.FStorageId = FStorageId;
    }
    public String getFStorage() {
        return this.FStorage;
    }
    public void setFStorage(String FStorage) {
        this.FStorage = FStorage;
    }
    public String getFWaveHouse() {
        return this.FWaveHouse;
    }
    public void setFWaveHouse(String FWaveHouse) {
        this.FWaveHouse = FWaveHouse;
    }
    public String getFWaveHouseId() {
        return this.FWaveHouseId;
    }
    public void setFWaveHouseId(String FWaveHouseId) {
        this.FWaveHouseId = FWaveHouseId;
    }
    public String getFUnit() {
        return this.FUnit;
    }
    public void setFUnit(String FUnit) {
        this.FUnit = FUnit;
    }
    public String getFSupplier() {
        return this.FSupplier;
    }
    public void setFSupplier(String FSupplier) {
        this.FSupplier = FSupplier;
    }
    public String getFQuantity() {
        return this.FQuantity;
    }
    public void setFQuantity(String FQuantity) {
        this.FQuantity = FQuantity;
    }
    public String getFBackDate() {
        return this.FBackDate;
    }
    public void setFBackDate(String FBackDate) {
        this.FBackDate = FBackDate;
    }
    public String getFBackType() {
        return this.FBackType;
    }
    public void setFBackType(String FBackType) {
        this.FBackType = FBackType;
    }
    public String getFStorageOutId() {
        return this.FStorageOutId;
    }
    public void setFStorageOutId(String FStorageOutId) {
        this.FStorageOutId = FStorageOutId;
    }
    public String getFStorageOut() {
        return this.FStorageOut;
    }
    public void setFStorageOut(String FStorageOut) {
        this.FStorageOut = FStorageOut;
    }
    public String getFStorageInId() {
        return this.FStorageInId;
    }
    public void setFStorageInId(String FStorageInId) {
        this.FStorageInId = FStorageInId;
    }
    public String getFStorageIn() {
        return this.FStorageIn;
    }
    public void setFStorageIn(String FStorageIn) {
        this.FStorageIn = FStorageIn;
    }
    public String getFWaveHouseOutId() {
        return this.FWaveHouseOutId;
    }
    public void setFWaveHouseOutId(String FWaveHouseOutId) {
        this.FWaveHouseOutId = FWaveHouseOutId;
    }
    public String getFWaveHouseOut() {
        return this.FWaveHouseOut;
    }
    public void setFWaveHouseOut(String FWaveHouseOut) {
        this.FWaveHouseOut = FWaveHouseOut;
    }
    public String getFWaveHouseInId() {
        return this.FWaveHouseInId;
    }
    public void setFWaveHouseInId(String FWaveHouseInId) {
        this.FWaveHouseInId = FWaveHouseInId;
    }
    public String getFWaveHouseIn() {
        return this.FWaveHouseIn;
    }
    public void setFWaveHouseIn(String FWaveHouseIn) {
        this.FWaveHouseIn = FWaveHouseIn;
    }
    public String getFBatch() {
        return this.FBatch;
    }
    public void setFBatch(String FBatch) {
        this.FBatch = FBatch;
    }
    public boolean getFIsFree() {
        return this.FIsFree;
    }
    public void setFIsFree(boolean FIsFree) {
        this.FIsFree = FIsFree;
    }
    public String getFWorkShopId1() {
        return this.FWorkShopId1;
    }
    public void setFWorkShopId1(String FWorkShopId1) {
        this.FWorkShopId1 = FWorkShopId1;
    }
    public String getFRemainInStockQty() {
        return this.FRemainInStockQty;
    }
    public void setFRemainInStockQty(String FRemainInStockQty) {
        this.FRemainInStockQty = FRemainInStockQty;
    }
    public String getFRealQty() {
        return this.FRealQty;
    }
    public void setFRealQty(String FRealQty) {
        this.FRealQty = FRealQty;
    }
    public String getFRemainInStockUnitId() {
        return this.FRemainInStockUnitId;
    }
    public void setFRemainInStockUnitId(String FRemainInStockUnitId) {
        this.FRemainInStockUnitId = FRemainInStockUnitId;
    }
    public String getFPriceUnitID() {
        return this.FPriceUnitID;
    }
    public void setFPriceUnitID(String FPriceUnitID) {
        this.FPriceUnitID = FPriceUnitID;
    }
    public String getFMaterialId() {
        return this.FMaterialId;
    }
    public void setFMaterialId(String FMaterialId) {
        this.FMaterialId = FMaterialId;
    }
    public String getFUnitID() {
        return this.FUnitID;
    }
    public void setFUnitID(String FUnitID) {
        this.FUnitID = FUnitID;
    }
    public String getFBillNo() {
        return this.FBillNo;
    }
    public void setFBillNo(String FBillNo) {
        this.FBillNo = FBillNo;
    }
    public String getFAllowAddMaterial() {
        return this.FAllowAddMaterial;
    }
    public void setFAllowAddMaterial(String FAllowAddMaterial) {
        this.FAllowAddMaterial = FAllowAddMaterial;
    }
    public String getFZeroStockInCount() {
        return this.FZeroStockInCount;
    }
    public void setFZeroStockInCount(String FZeroStockInCount) {
        this.FZeroStockInCount = FZeroStockInCount;
    }
    public String getFBillTypeID() {
        return this.FBillTypeID;
    }
    public void setFBillTypeID(String FBillTypeID) {
        this.FBillTypeID = FBillTypeID;
    }
    public String getFCheckQtyDefault() {
        return this.FCheckQtyDefault;
    }
    public void setFCheckQtyDefault(String FCheckQtyDefault) {
        this.FCheckQtyDefault = FCheckQtyDefault;
    }
    public String getFNotIncludeForbidMat() {
        return this.FNotIncludeForbidMat;
    }
    public void setFNotIncludeForbidMat(String FNotIncludeForbidMat) {
        this.FNotIncludeForbidMat = FNotIncludeForbidMat;
    }
    public String getFStockOrgId() {
        return this.FStockOrgId;
    }
    public void setFStockOrgId(String FStockOrgId) {
        this.FStockOrgId = FStockOrgId;
    }
    public String getFDocumentStatus() {
        return this.FDocumentStatus;
    }
    public void setFDocumentStatus(String FDocumentStatus) {
        this.FDocumentStatus = FDocumentStatus;
    }
    public String getFCloseStatus() {
        return this.FCloseStatus;
    }
    public void setFCloseStatus(String FCloseStatus) {
        this.FCloseStatus = FCloseStatus;
    }
    public String getFNote() {
        return this.FNote;
    }
    public void setFNote(String FNote) {
        this.FNote = FNote;
    }
    public String getFOWnerTypeID() {
        return this.FOWnerTypeID;
    }
    public void setFOWnerTypeID(String FOWnerTypeID) {
        this.FOWnerTypeID = FOWnerTypeID;
    }
    public String getFOwnerId() {
        return this.FOwnerId;
    }
    public void setFOwnerId(String FOwnerId) {
        this.FOwnerId = FOwnerId;
    }
    public String getFKeeperTypeId() {
        return this.FKeeperTypeId;
    }
    public void setFKeeperTypeId(String FKeeperTypeId) {
        this.FKeeperTypeId = FKeeperTypeId;
    }
    public String getFKeeperId() {
        return this.FKeeperId;
    }
    public void setFKeeperId(String FKeeperId) {
        this.FKeeperId = FKeeperId;
    }
    public String getFStoragePDId() {
        return this.FStoragePDId;
    }
    public void setFStoragePDId(String FStoragePDId) {
        this.FStoragePDId = FStoragePDId;
    }
    public String getFMaterialIdForPD() {
        return this.FMaterialIdForPD;
    }
    public void setFMaterialIdForPD(String FMaterialIdForPD) {
        this.FMaterialIdForPD = FMaterialIdForPD;
    }
    public String getFWaveHousePDId() {
        return this.FWaveHousePDId;
    }
    public void setFWaveHousePDId(String FWaveHousePDId) {
        this.FWaveHousePDId = FWaveHousePDId;
    }
    public String getFUnitIDForPD() {
        return this.FUnitIDForPD;
    }
    public void setFUnitIDForPD(String FUnitIDForPD) {
        this.FUnitIDForPD = FUnitIDForPD;
    }
    public String getFSOEntryId() {
        return this.FSOEntryId;
    }
    public void setFSOEntryId(String FSOEntryId) {
        this.FSOEntryId = FSOEntryId;
    }
    public String getFB2CORDERDETAILID() {
        return this.FB2CORDERDETAILID;
    }
    public void setFB2CORDERDETAILID(String FB2CORDERDETAILID) {
        this.FB2CORDERDETAILID = FB2CORDERDETAILID;
    }
    public String getFID() {
        return this.FID;
    }
    public void setFID(String FID) {
        this.FID = FID;
    }
    public String getFBarcode() {
        return this.FBarcode;
    }
    public void setFBarcode(String FBarcode) {
        this.FBarcode = FBarcode;
    }
    public String getIMIE() {
        return this.IMIE;
    }
    public void setIMIE(String IMIE) {
        this.IMIE = IMIE;
    }
    public String getFBillerID() {
        return this.FBillerID;
    }
    public void setFBillerID(String FBillerID) {
        this.FBillerID = FBillerID;
    }
    public String getAuxSign() {
        return this.AuxSign;
    }
    public void setAuxSign(String AuxSign) {
        this.AuxSign = AuxSign;
    }
    public String getActualModel() {
        return this.ActualModel;
    }
    public void setActualModel(String ActualModel) {
        this.ActualModel = ActualModel;
    }
    public String getFProductNo() {
        return this.FProductNo;
    }
    public void setFProductNo(String FProductNo) {
        this.FProductNo = FProductNo;
    }


  
}
