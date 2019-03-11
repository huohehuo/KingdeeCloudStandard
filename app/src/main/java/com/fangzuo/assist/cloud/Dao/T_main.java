package com.fangzuo.assist.cloud.Dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by NB on 2017/8/3.
 */
@Entity
public class T_main {
    @Id
    public String FIndex;
    public long FOrderId;
    public String FBarcode;
    public String IMIE;   //用于记录多订单下推时的InterId，获取ordercode
    public String FBillNo;
    public String FBillerID;
    public int activity;

    //必填项
    public String FSoorDerno;//订单单号
    public String FPriceTimePoint;//定价时点
    public String FStockOrgId;//收料组织
    public String FPurchaseOrgId;//采购组织
    public String FBillTypeID;//单据类型

    public String FOwnerTypeIdHead;//(调出)货主类型
    public String FOwnerIdHead;//(调出)货主
    public String FOwnerTypeIdHeadIn;//调入货主类型
    public String FOwnerIdHeadIn;//调入货主
    public String FDBType;//调拨类型
    public String FDBDirection;//调拨方向




    public String FSettleOrgId;//结算组织
    public String FSettleCurrId;//结算币别
    public String FDate;//入库日期
    public String FSupplierId;//供应商
    public String FDepartmentNumber;    //收料部门
    public String FDepartment;          //收料部门
    public String FPurchaseDeptId;    //采购部门
    public String FPurchaseDept;          //采购部门
    public String FPurchaserId;    //采购员
    public String FPurchaser;          //采购员
    public String FStockerNumber;       //仓管员
    public String FStocker;            //仓管员
    public String FCustomerID;            //客户
    public String FCustomer;            //客户
    public String FNot;            //备注
    public String F_FFF_Text;            //业务单号
    @Generated(hash = 1152772511)
    public T_main(String FIndex, long FOrderId, String FBarcode, String IMIE, String FBillNo, String FBillerID,
            int activity, String FSoorDerno, String FPriceTimePoint, String FStockOrgId, String FPurchaseOrgId,
            String FBillTypeID, String FOwnerTypeIdHead, String FOwnerIdHead, String FOwnerTypeIdHeadIn,
            String FOwnerIdHeadIn, String FDBType, String FDBDirection, String FSettleOrgId, String FSettleCurrId,
            String FDate, String FSupplierId, String FDepartmentNumber, String FDepartment, String FPurchaseDeptId,
            String FPurchaseDept, String FPurchaserId, String FPurchaser, String FStockerNumber, String FStocker,
            String FCustomerID, String FCustomer, String FNot, String F_FFF_Text) {
        this.FIndex = FIndex;
        this.FOrderId = FOrderId;
        this.FBarcode = FBarcode;
        this.IMIE = IMIE;
        this.FBillNo = FBillNo;
        this.FBillerID = FBillerID;
        this.activity = activity;
        this.FSoorDerno = FSoorDerno;
        this.FPriceTimePoint = FPriceTimePoint;
        this.FStockOrgId = FStockOrgId;
        this.FPurchaseOrgId = FPurchaseOrgId;
        this.FBillTypeID = FBillTypeID;
        this.FOwnerTypeIdHead = FOwnerTypeIdHead;
        this.FOwnerIdHead = FOwnerIdHead;
        this.FOwnerTypeIdHeadIn = FOwnerTypeIdHeadIn;
        this.FOwnerIdHeadIn = FOwnerIdHeadIn;
        this.FDBType = FDBType;
        this.FDBDirection = FDBDirection;
        this.FSettleOrgId = FSettleOrgId;
        this.FSettleCurrId = FSettleCurrId;
        this.FDate = FDate;
        this.FSupplierId = FSupplierId;
        this.FDepartmentNumber = FDepartmentNumber;
        this.FDepartment = FDepartment;
        this.FPurchaseDeptId = FPurchaseDeptId;
        this.FPurchaseDept = FPurchaseDept;
        this.FPurchaserId = FPurchaserId;
        this.FPurchaser = FPurchaser;
        this.FStockerNumber = FStockerNumber;
        this.FStocker = FStocker;
        this.FCustomerID = FCustomerID;
        this.FCustomer = FCustomer;
        this.FNot = FNot;
        this.F_FFF_Text = F_FFF_Text;
    }
    public void setData(String FBillTypeID,String FStockOrgId,String FPurchaseOrgId){
        this.FPriceTimePoint = "";//定价时点
        this.FStockOrgId = FStockOrgId;//收料组织
        this.FPurchaseOrgId = FPurchaseOrgId;//采购组织
        this.FBillTypeID = FBillTypeID;//单据类型
        this.FOwnerTypeIdHead = "BD_OwnerOrg";//货主类型BD_SupplierBD_Customer
        this.FOwnerIdHead = "102";//货主
        this.FSettleOrgId = FPurchaseOrgId;//结算组织
        this.FSettleCurrId = "PRE001";//结算币别
    }
    public void setData(String FBillTypeID,String FStockOrgId,String FPurchaseOrgId,String huozhu){
        this.FPriceTimePoint = "";//定价时点
        this.FStockOrgId = FStockOrgId;//收料组织
        this.FPurchaseOrgId = FPurchaseOrgId;//采购组织
        this.FBillTypeID = FBillTypeID;//单据类型
        this.FOwnerTypeIdHead = "BD_OwnerOrg";//货主类型BD_SupplierBD_Customer
        this.FOwnerIdHead = huozhu;//货主
        this.FSettleOrgId = FPurchaseOrgId;//结算组织
        this.FSettleCurrId = "PRE001";//结算币别
    }
    //调拨单时
    public void setData(String FBillTypeID,String FStockOrgId,String FPurchaseOrgId,String huozhu,String huozhuIn){
        this.FPriceTimePoint = "";//定价时点
        this.FStockOrgId = FStockOrgId;//收料组织
        this.FPurchaseOrgId = FPurchaseOrgId;//采购组织
        this.FBillTypeID = FBillTypeID;//单据类型
        this.FOwnerTypeIdHead = "BD_OwnerOrg";//货主类型BD_SupplierBD_Customer
        this.FOwnerTypeIdHeadIn = "BD_OwnerOrg";//货主类型BD_SupplierBD_Customer
        this.FOwnerIdHead = huozhu;//调出货主
        this.FOwnerIdHeadIn = huozhuIn;//调入货主
        this.FSettleOrgId = FPurchaseOrgId;//结算组织
        this.FSettleCurrId = "PRE001";//结算币别
    }


    public void setSupplier(Suppliers suppliers){
        if (null==suppliers){
            return;
        }
        this.FSupplierId = suppliers.FNumber;
//        this.FSupplier= suppliers.FName;
    }
    public void setClient(Client client){
        if (null==client){
            return;
        }
        this.FCustomerID = client.FNumber;
//        this.FSupplier= suppliers.FName;
    }


    @Generated(hash = 884209494)
    public T_main() {
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
    public String getIMIE() {
        return this.IMIE;
    }
    public void setIMIE(String IMIE) {
        this.IMIE = IMIE;
    }
    public int getActivity() {
        return this.activity;
    }
    public void setActivity(int activity) {
        this.activity = activity;
    }
    public String getFPriceTimePoint() {
        return this.FPriceTimePoint;
    }
    public void setFPriceTimePoint(String FPriceTimePoint) {
        this.FPriceTimePoint = FPriceTimePoint;
    }
    public String getFStockOrgId() {
        return this.FStockOrgId;
    }
    public void setFStockOrgId(String FStockOrgId) {
        this.FStockOrgId = FStockOrgId;
    }
    public String getFPurchaseOrgId() {
        return this.FPurchaseOrgId;
    }
    public void setFPurchaseOrgId(String FPurchaseOrgId) {
        this.FPurchaseOrgId = FPurchaseOrgId;
    }
    public String getFBillTypeID() {
        return this.FBillTypeID;
    }
    public void setFBillTypeID(String FBillTypeID) {
        this.FBillTypeID = FBillTypeID;
    }
    public String getFOwnerTypeIdHead() {
        return this.FOwnerTypeIdHead;
    }
    public void setFOwnerTypeIdHead(String FOwnerTypeIdHead) {
        this.FOwnerTypeIdHead = FOwnerTypeIdHead;
    }
    public String getFOwnerIdHead() {
        return this.FOwnerIdHead;
    }
    public void setFOwnerIdHead(String FOwnerIdHead) {
        this.FOwnerIdHead = FOwnerIdHead;
    }
    public String getFSettleOrgId() {
        return this.FSettleOrgId;
    }
    public void setFSettleOrgId(String FSettleOrgId) {
        this.FSettleOrgId = FSettleOrgId;
    }
    public String getFSettleCurrId() {
        return this.FSettleCurrId;
    }
    public void setFSettleCurrId(String FSettleCurrId) {
        this.FSettleCurrId = FSettleCurrId;
    }
    public String getFDate() {
        return this.FDate;
    }
    public void setFDate(String FDate) {
        this.FDate = FDate;
    }
    public String getFSupplierId() {
        return this.FSupplierId;
    }
    public void setFSupplierId(String FSupplierId) {
        this.FSupplierId = FSupplierId;
    }
    public String getFDepartmentNumber() {
        return this.FDepartmentNumber;
    }
    public void setFDepartmentNumber(String FDepartmentNumber) {
        this.FDepartmentNumber = FDepartmentNumber;
    }
    public String getFDepartment() {
        return this.FDepartment;
    }
    public void setFDepartment(String FDepartment) {
        this.FDepartment = FDepartment;
    }
    public String getFStockerNumber() {
        return this.FStockerNumber;
    }
    public void setFStockerNumber(String FStockerNumber) {
        this.FStockerNumber = FStockerNumber;
    }
    public String getFStocker() {
        return this.FStocker;
    }
    public void setFStocker(String FStocker) {
        this.FStocker = FStocker;
    }
    public String getFPurchaseDeptId() {
        return this.FPurchaseDeptId;
    }
    public void setFPurchaseDeptId(String FPurchaseDeptId) {
        this.FPurchaseDeptId = FPurchaseDeptId;
    }
    public String getFPurchaseDept() {
        return this.FPurchaseDept;
    }
    public void setFPurchaseDept(String FPurchaseDept) {
        this.FPurchaseDept = FPurchaseDept;
    }
    public String getFPurchaserId() {
        return this.FPurchaserId;
    }
    public void setFPurchaserId(String FPurchaserId) {
        this.FPurchaserId = FPurchaserId;
    }
    public String getFPurchaser() {
        return this.FPurchaser;
    }
    public void setFPurchaser(String FPurchaser) {
        this.FPurchaser = FPurchaser;
    }
    public String getFCustomerID() {
        return this.FCustomerID;
    }
    public void setFCustomerID(String FCustomerID) {
        this.FCustomerID = FCustomerID;
    }
    public String getFCustomer() {
        return this.FCustomer;
    }
    public void setFCustomer(String FCustomer) {
        this.FCustomer = FCustomer;
    }
    public String getFNot() {
        return this.FNot;
    }
    public void setFNot(String FNot) {
        this.FNot = FNot;
    }
    public String getFBillNo() {
        return this.FBillNo;
    }
    public void setFBillNo(String FBillNo) {
        this.FBillNo = FBillNo;
    }
    public String getFSoorDerno() {
        return this.FSoorDerno;
    }
    public void setFSoorDerno(String FSoorDerno) {
        this.FSoorDerno = FSoorDerno;
    }
    public String getFBarcode() {
        return this.FBarcode;
    }
    public void setFBarcode(String FBarcode) {
        this.FBarcode = FBarcode;
    }
    public String getFBillerID() {
        return this.FBillerID;
    }
    public void setFBillerID(String FBillerID) {
        this.FBillerID = FBillerID;
    }
    public String getF_FFF_Text() {
        return this.F_FFF_Text;
    }
    public void setF_FFF_Text(String F_FFF_Text) {
        this.F_FFF_Text = F_FFF_Text;
    }
    public String getFOwnerTypeIdHeadIn() {
        return this.FOwnerTypeIdHeadIn;
    }
    public void setFOwnerTypeIdHeadIn(String FOwnerTypeIdHeadIn) {
        this.FOwnerTypeIdHeadIn = FOwnerTypeIdHeadIn;
    }
    public String getFOwnerIdHeadIn() {
        return this.FOwnerIdHeadIn;
    }
    public void setFOwnerIdHeadIn(String FOwnerIdHeadIn) {
        this.FOwnerIdHeadIn = FOwnerIdHeadIn;
    }
    public String getFDBType() {
        return this.FDBType;
    }
    public void setFDBType(String FDBType) {
        this.FDBType = FDBType;
    }
    public String getFDBDirection() {
        return this.FDBDirection;
    }
    public void setFDBDirection(String FDBDirection) {
        this.FDBDirection = FDBDirection;
    }
}
