package com.fangzuo.assist.cloud.Dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by NB on 2017/7/26.
 */
@Entity
public class Org {
    public String FOrgID;
    public String FNumber;
    public String FName;
    @Generated(hash = 6131445)
    public Org(String FOrgID, String FNumber, String FName) {
        this.FOrgID = FOrgID;
        this.FNumber = FNumber;
        this.FName = FName;
    }
    @Generated(hash = 708329430)
    public Org() {
    }
    public String getFOrgID() {
        return this.FOrgID;
    }
    public void setFOrgID(String FOrgID) {
        this.FOrgID = FOrgID;
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

        @Override
    public String toString() {
        return "Org{" +
                "FOrgID='" + FOrgID + '\'' +
                ", FNumber='" + FNumber + '\'' +
                ", FName='" + FName + '\'' +
                '}';
    }
}
