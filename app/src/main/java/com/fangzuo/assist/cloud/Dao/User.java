package com.fangzuo.assist.cloud.Dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by NB on 2017/7/26.
 */
@Entity
public class User {
    public String FUserID;
    public String FName;
    public String FPassWord;
    public String FEmpID;

    @Generated(hash = 887260491)
    public User(String FUserID, String FName, String FPassWord, String FEmpID) {
        this.FUserID = FUserID;
        this.FName = FName;
        this.FPassWord = FPassWord;
        this.FEmpID = FEmpID;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "FUserID='" + FUserID + '\'' +
                ", FName='" + FName + '\'' +
                ", FPassWord='" + FPassWord + '\'' +
                ", FEmpID='" + FEmpID + '\'' +
                '}';
    }

    public String getFUserID() {
        return this.FUserID;
    }

    public void setFUserID(String FUserID) {
        this.FUserID = FUserID;
    }

    public String getFName() {
        return this.FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getFPassWord() {
        return this.FPassWord;
    }

    public void setFPassWord(String FPassWord) {
        this.FPassWord = FPassWord;
    }

    public String getFEmpID() {
        return this.FEmpID;
    }

    public void setFEmpID(String FEmpID) {
        this.FEmpID = FEmpID;
    }
}
