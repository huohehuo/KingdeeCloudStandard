package com.fangzuo.assist.cloud.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.fangzuo.assist.cloud.Activity.Crash.App;
import com.fangzuo.assist.cloud.Beans.PrintHistory;
import com.fangzuo.assist.cloud.Dao.Client;
import com.fangzuo.assist.cloud.Dao.Department;
import com.fangzuo.assist.cloud.Dao.Org;
import com.fangzuo.assist.cloud.Dao.SaleMan;
import com.fangzuo.assist.cloud.Dao.T_Detail;
import com.fangzuo.assist.cloud.Dao.Unit;
import com.fangzuo.greendao.gen.ClientDao;
import com.fangzuo.greendao.gen.DepartmentDao;
import com.fangzuo.greendao.gen.OrgDao;
import com.fangzuo.greendao.gen.SaleManDao;
import com.fangzuo.greendao.gen.T_DetailDao;
import com.fangzuo.greendao.gen.UnitDao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zpSDK.zpSDK.zpBluetoothPrinter;

public class LocDataUtil {


    public static Unit getUnit(String unitid){
        Lg.e("获取单位位置：",unitid);
//        Lg.e("获取单位位置org：",org);
        if (null==unitid){
            return new Unit("","","","");
        }
//        if (null==org){
//            return new Unit("","","","");
//        }
        UnitDao unitDao = GreenDaoManager.getmInstance(App.getContext()).getDaoSession().getUnitDao();
        List<Unit> units = unitDao.queryBuilder().where(
//                UnitDao.Properties.FOrg.eq(org.FOrgID),
                UnitDao.Properties.FMeasureUnitID.eq(unitid)
        ).build().list();
        if (units.size()>0){
            return units.get(0);
        }else{
            return new Unit("","","","");
        }

    }

    //获取客户数据
    public static Client getClient(String name){
        Lg.e("查找本地客户",name);
        if ("".equals(name)){
            return new Client("","","","");
        }
        ClientDao unitDao = GreenDaoManager.getmInstance(App.getContext()).getDaoSession().getClientDao();
        List<Client> units = unitDao.queryBuilder().where(
                ClientDao.Properties.FName.eq(name)
        ).build().list();
        if (units.size()>0){
            return units.get(0);
        }else{
            return new Client("","","","");
        }
    }

    //判断是否存在明细数据
    public static boolean hasTDetail(int activity){
        T_DetailDao unitDao = GreenDaoManager.getmInstance(App.getContext()).getDaoSession().getT_DetailDao();
        List<T_Detail> units = unitDao.queryBuilder().where(
                T_DetailDao.Properties.Activity.eq(activity)
        ).build().list();
        if (units.size()>0){
            return true;
        }else{
            return false;
        }
    }

    //获取销售员数据
    public static SaleMan getSaleMan(String id){
        Lg.e("查找本地销售员",id);
        if ("".equals(id)){
            return new SaleMan("","","","","");
        }
//        if (null==org){
//            return new SaleMan("","","","","");
//        }
        SaleManDao employeeDao = GreenDaoManager.getmInstance(App.getContext()).getDaoSession().getSaleManDao();
        List<SaleMan> employees = employeeDao.queryBuilder().where(
//                SaleManDao.Properties.FOrg.eq(org.FOrgID),
                SaleManDao.Properties.FID.eq(id)
        ).build().list();
        if (employees.size()>0){
            return employees.get(0);
        }else{
            return new SaleMan("","","","","");
        }
    }

    //获取部门数据
    public static Department getDept(String id){
        Lg.e("查找本地部门",id);
        if ("".equals(id)){
            return new Department("","","","","");
        }
//        if (null==org){
//            return new SaleMan("","","","","");
//        }
        DepartmentDao employeeDao = GreenDaoManager.getmInstance(App.getContext()).getDaoSession().getDepartmentDao();
        List<Department> employees = employeeDao.queryBuilder().where(
//                DepartmentDao.Properties.FOrg.eq(org.FOrgID),
                DepartmentDao.Properties.FItemID.eq(id)
        ).build().list();
        if (employees.size()>0){
            return employees.get(0);
        }else{
            return new Department("","","","","");
        }
    }
    //获取组织数据
    public static Org getOrg(String id){
        Lg.e("查找本地组织",id);
        if ("".equals(id)){
            return new Org("","","");
        }
        OrgDao employeeDao = GreenDaoManager.getmInstance(App.getContext()).getDaoSession().getOrgDao();
        List<Org> employees = employeeDao.queryBuilder().where(
//                DepartmentDao.Properties.FOrg.eq(org.FOrgID),
                OrgDao.Properties.FOrgID.eq(id)
        ).build().list();
        if (employees.size()>0){
            return employees.get(0);
        }else{
            return new Org("","","");
        }
    }

}




















/*
String str = "abc,12,3yy98,0";
String[]  strs=str.split(",");//以，截取   或者\\^
for(int i=0,len=strs.length;i<len;i++){
    System.out.println(strs[i].toString());



 sb.substring(2);//索引号 2 到结尾

String barcode = code.substring(0, 12);//第一位到第十一位

3.通过StringUtils提供的方法
StringUtils.substringBefore(“dskeabcee”, “e”);
/结果是：dsk/
这里是以第一个”e”，为标准。

StringUtils.substringBeforeLast(“dskeabcee”, “e”)
结果为：dskeabce
这里以最后一个“e”为准。
* */