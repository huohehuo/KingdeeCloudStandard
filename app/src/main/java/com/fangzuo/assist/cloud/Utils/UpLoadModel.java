package com.fangzuo.assist.cloud.Utils;

import android.content.Context;

import com.fangzuo.assist.cloud.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.cloud.Dao.T_Detail;
import com.fangzuo.assist.cloud.Dao.T_main;
import com.fangzuo.assist.cloud.Service.DataService;
import com.fangzuo.assist.cloud.widget.LoadingUtil;
import com.fangzuo.greendao.gen.T_DetailDao;
import com.fangzuo.greendao.gen.T_mainDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//回单模块
public class UpLoadModel {



    //执行统一回单（查询出物料）
    public static void action(Context mContext, int activity){
        try{
                T_mainDao t_mainDao=GreenDaoManager.getmInstance(mContext).getDaoSession().getT_mainDao();
                T_DetailDao t_detailDao = GreenDaoManager.getmInstance(mContext).getDaoSession().getT_DetailDao();
                Map<String,List<T_Detail>> listMap = new HashMap<>();
            if (activity!=Config.PDActivity){
                LoadingUtil.showDialog(mContext,"正在上传...");

                List<T_main> mains = t_mainDao.queryBuilder().where(T_mainDao.Properties.Activity.eq(activity)).build().list();
                if (mains.size()<=0){
                    Toast.showText(mContext,"本地不存在单据数据");
                    EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Lock_Main, Config.Lock+"NO"));//本地不存在单据，解锁该单据的表头
                    LoadingUtil.dismiss();
                    return;
                }
                List<T_main> t_mainList =new ArrayList<>();
                t_mainList.addAll(mains);
                for (T_main main:t_mainList) {
                    //合并回单
                    List<T_Detail> details = DataModel.mergeDetail(mContext,main.FOrderId+"",activity);
//                    List<T_Detail> details = t_detailDao.queryBuilder().where(
//                            T_DetailDao.Properties.Activity.eq(activity),
//                            T_DetailDao.Properties.FOrderId.eq(main.FOrderId)
//                    ).build().list();
                    Lg.e("detail:"+details.size());
                    Lg.e("detail:",details);
                    if (details.size()>0){
                        listMap.put(main.FOrderId+"",details);
//                    dealResult(activity,main,details);
                    } else{
                        //当表头找不到相应的明细时，删除表头
//                    Lg.e("???删除"+main.toString());
                        mains.remove(main);
                        t_mainDao.delete(main);
//                    Lg.e("单据 "+main.FOrderId+" 不存在明细数据");
//                    Toast.showText(mContext,"单据 "+main.FOrderId+" 不存在明细数据");
                    }
                }
                //执行回单拼接
                dealResult(activity,mains,listMap);
            }else{
                List<T_Detail> details = t_detailDao.queryBuilder().where(
                        T_DetailDao.Properties.Activity.eq(activity)
                ).build().list();
                //执行回单拼接
                listMap.put("PD",details);
                dealResult(activity,null,listMap);
            }

        }catch (Exception e){
            Toast.showText(mContext,"回单错误");
            LoadingUtil.dismiss();
            DataService.pushError(mContext, mContext.getClass().getSimpleName(), e);
        }
    }
    //执行统一回单（查询出物料）
    public static void actionPushDown(Context mContext, int activity,String fid){
        try{
            T_mainDao t_mainDao=GreenDaoManager.getmInstance(mContext).getDaoSession().getT_mainDao();
            T_DetailDao t_detailDao = GreenDaoManager.getmInstance(mContext).getDaoSession().getT_DetailDao();
            Map<String,List<T_Detail>> listMap = new HashMap<>();
                LoadingUtil.showDialog(mContext,"正在上传...");
                List<T_main> mains = t_mainDao.queryBuilder().where(
                        T_mainDao.Properties.Activity.eq(activity),
                        T_mainDao.Properties.FID.eq(fid)
                        ).build().list();//一个FID只会对应着一个表头
                if (mains.size()<=0){
                    Toast.showText(mContext,"本地不存在单据数据:"+fid);
                    EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Lock_Main, Config.Lock+"NO"));//本地不存在单据，解锁该单据的表头
                    LoadingUtil.dismiss();
                    return;
                }
                Lg.e("得到表头："+mains.size(),mains);
                List<T_main> t_mainList =new ArrayList<>();
                t_mainList.addAll(mains);
//                List<String> hzList=DataModel.getHuoZhuForPushDown(mContext,mains.get(0).FOrderId+"",activity,fid);
//                Lg.e("得到货主："+hzList.size(),hzList);
//            for (String str:hzList) {
//                t_mainList.add(mains.get(0));
//            }
                for (T_main main:t_mainList) {
//                Lg.e("遍历货主:"+huozhu);
                    //遍历出不同的货主信息
                    //合并回单
                    List<T_Detail> details = DataModel.mergeDetailForPushDown(mContext,main.FOrderId+"",activity,fid);
//                    List<T_Detail> details = t_detailDao.queryBuilder().where(
//                            T_DetailDao.Properties.Activity.eq(activity),
//                            T_DetailDao.Properties.FOrderId.eq(main.FOrderId)
//                    ).build().list();
                    Lg.e("detail:"+details.size());
                    Lg.e("detail:",details);
                    if (details.size()>0){
                        listMap.put(main.FOrderId+"",details);
//                    dealResult(activity,main,details);
                    } else{
                        //当表头找不到相应的明细时，删除表头
//                    Lg.e("???删除"+main.toString());
                        mains.remove(main);
                        t_mainDao.delete(main);
//                    Lg.e("单据 "+main.FOrderId+" 不存在明细数据");
//                    Toast.showText(mContext,"单据 "+main.FOrderId+" 不存在明细数据");
                    }
                }
                //执行回单拼接
                dealResult(activity,t_mainList,listMap);
        }catch (Exception e){
            Toast.showText(mContext,"回单错误");
            LoadingUtil.dismiss();
            DataService.pushError(mContext, mContext.getClass().getSimpleName(), e);
        }
    }

    //处理回单数据并组合成json回单
    public static void dealResult(int activity,List<T_main> mains,Map<String,List<T_Detail>> details){
        switch (activity){
            case Config.PurchaseInStoreActivity://采购入库
                DataModel.upload(Config.C_BatcnSave,Info.getJson(activity,JsonDealUtils.JSonPIS(mains,details)));
                break;
            case Config.PdCgOrder2WgrkActivity://采购订单下推外购入库单
                DataModel.upload(Config.C_BatcnSave,Info.getJson(activity,JsonDealUtils.JSonCgOrder2Wgrk(mains,details)));
                break;
            case Config.PurchaseOrderActivity://采购订单
                DataModel.upload(Config.C_BatcnSave,Info.getJson(activity,JsonDealUtils.JSonPuO(mains,details)));
                break;
            case Config.ProductInStoreActivity://产品入库
            case Config.TbInActivity://挑板入库
            case Config.GbInActivity://改板入库
            case Config.DhInActivity://到货入库
            case Config.SimpleInActivity://产品入库
                DataModel.upload(Config.C_BatcnSave,Info.getJson(activity,JsonDealUtils.JSonPrIS(mains,details)));
                break;
            case Config.ProductGetActivity://生产领料
            case Config.TbGetActivity://挑板领料
            case Config.GbGetActivity://改板领料
                DataModel.upload(Config.C_BatcnSave,Info.getJson(activity,JsonDealUtils.JSonPrG(mains,details)));
                break;
            case Config.SaleOutActivity://销售出库
                DataModel.upload(Config.C_BatcnSave,Info.getJson(activity,JsonDealUtils.JSonSaleOut(mains,details)));
                break;
            case Config.PdSaleOrder2SaleOutActivity://销售订单下推销售出库
                DataModel.upload(Config.C_BatcnSave,Info.getJson(activity,JsonDealUtils.JSonSaleOrder2SaleOut(mains,details)));
                break;
            case Config.PdSendMsg2SaleOutActivity://发货通知单下推销售出库单
                DataModel.upload(Config.C_BatcnSave,Info.getJson(activity,JsonDealUtils.JSonSendMsg2SaleOut(mains,details)));
                break;
            case Config.OtherInStoreActivity://其他入库
            case Config.HwIn3Activity://第三方货物入库
                DataModel.upload(Config.C_BatcnSave, Info.getJson(activity,JsonDealUtils.JSonOIS(mains,details)));
                break;
            case Config.OtherOutStoreActivity://其他出库
            case Config.YbOutActivity://样板出库
            case Config.HwOut3Activity://第三方货物出库
                DataModel.upload(Config.C_BatcnSave, Info.getJson(activity,JsonDealUtils.JSonOOS(mains,details)));
                break;
            case Config.SaleOrderActivity://销售订单
                DataModel.upload(Config.C_BatcnSave, Info.getJson(activity,JsonDealUtils.JSonSaleOrder(mains,details)));
                break;
            case Config.PdSaleOrder2SaleBackActivity://销售订单下推销售退货单
                DataModel.upload(Config.C_BatcnSave, Info.getJson(activity,JsonDealUtils.JSonSaleOrder2SaleOutBack(mains,details)));
                break;
            case Config.PdSaleOut2SaleBackActivity://销售出库单下推销售退货单
                DataModel.upload(Config.C_BatcnSave, Info.getJson(activity,JsonDealUtils.JSonSaleOut2SaleOutBack(mains,details)));
                break;
            case Config.PdBackMsg2SaleBackActivity://退货通知单下推销售退货单
                DataModel.upload(Config.C_BatcnSave, Info.getJson(activity,JsonDealUtils.JSonBackMsg2SaleOutBack(mains,details)));
                break;
            case Config.Db2FDinActivity:
                DataModel.upload(Config.C_BatcnSave, Info.getJson(activity,JsonDealUtils.JSonFDin(mains,details)));
                break;
            case Config.Db2FDoutActivity:
                DataModel.upload(Config.C_BatcnSave, Info.getJson(activity,JsonDealUtils.JSonFDout(mains,details)));
                break;
            case Config.PDActivity:
                DataModel.upload(Config.C_BatcnSave, Info.getJson(activity,JsonDealUtils.JSonPD(mains,details)));
                break;
            case Config.DBActivity:
                DataModel.upload(Config.C_BatcnSave, Info.getJson(activity,JsonDealUtils.JSonDB(mains,details)));
                break;


        }
    }
}
