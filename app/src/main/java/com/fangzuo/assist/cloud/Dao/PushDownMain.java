package com.fangzuo.assist.cloud.Dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by NB on 2017/8/24.
 *
 *              单据信息
 */
@Entity
public class PushDownMain {
    @Id(autoincrement = true)
    public Long id;
    public String FID;
    public String FBillNo;
    public String FDate;
    public String FSupplyID;
    public String FSupply;
    public int tag;
    @Generated(hash = 89079877)
    public PushDownMain(Long id, String FID, String FBillNo, String FDate,
            String FSupplyID, String FSupply, int tag) {
        this.id = id;
        this.FID = FID;
        this.FBillNo = FBillNo;
        this.FDate = FDate;
        this.FSupplyID = FSupplyID;
        this.FSupply = FSupply;
        this.tag = tag;
    }
    @Generated(hash = 92092905)
    public PushDownMain() {
    }

    public PushDownMain(String FID, String FBillNo, String FDate, String FSupplyID, String FSupply, int tag) {
        this.FID = FID;
        this.FBillNo = FBillNo;
        this.FDate = FDate;
        this.FSupplyID = FSupplyID;
        this.FSupply = FSupply;
        this.tag = tag;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFID() {
        return this.FID;
    }
    public void setFID(String FID) {
        this.FID = FID;
    }
    public String getFBillNo() {
        return this.FBillNo;
    }
    public void setFBillNo(String FBillNo) {
        this.FBillNo = FBillNo;
    }
    public String getFDate() {
        return this.FDate;
    }
    public void setFDate(String FDate) {
        this.FDate = FDate;
    }
    public String getFSupplyID() {
        return this.FSupplyID;
    }
    public void setFSupplyID(String FSupplyID) {
        this.FSupplyID = FSupplyID;
    }
    public String getFSupply() {
        return this.FSupply;
    }
    public void setFSupply(String FSupply) {
        this.FSupply = FSupply;
    }
    public int getTag() {
        return this.tag;
    }
    public void setTag(int tag) {
        this.tag = tag;
    }

//    public String FName;
//    public String FDeptID;
//    public String FManagerID;
//    public String FEmpID;
//    public String FInterID;

}
