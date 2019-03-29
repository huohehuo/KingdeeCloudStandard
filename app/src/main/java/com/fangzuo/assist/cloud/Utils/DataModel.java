package com.fangzuo.assist.cloud.Utils;

import android.content.Context;
import android.database.Cursor;
import android.widget.TextView;

import com.fangzuo.assist.cloud.Activity.Crash.App;
import com.fangzuo.assist.cloud.Beans.BackData;
import com.fangzuo.assist.cloud.Beans.CodeCheckBackDataBean;
import com.fangzuo.assist.cloud.Beans.CommonResponse;
import com.fangzuo.assist.cloud.Beans.DownloadReturnBean;
import com.fangzuo.assist.cloud.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.cloud.Beans.InStoreNumBean;
import com.fangzuo.assist.cloud.Beans.SearchBean;
import com.fangzuo.assist.cloud.Dao.InStorageNum;
import com.fangzuo.assist.cloud.Dao.Org;
import com.fangzuo.assist.cloud.Dao.Product;
import com.fangzuo.assist.cloud.Dao.Storage;
import com.fangzuo.assist.cloud.Dao.T_Detail;
import com.fangzuo.assist.cloud.Dao.T_main;
import com.fangzuo.assist.cloud.RxSerivce.MySubscribe;
import com.fangzuo.assist.cloud.RxSerivce.ToSubscribe;
import com.fangzuo.assist.cloud.widget.LoadingUtil;
import com.fangzuo.greendao.gen.DaoSession;
import com.fangzuo.greendao.gen.T_DetailDao;
import com.fangzuo.greendao.gen.T_mainDao;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;
import java.util.List;

public class DataModel {
    //检测点击回单时是否存在单据
    public static boolean checkHasDetail(Context mContext,int activity){
        return GreenDaoManager.getmInstance(mContext).getDaoSession().getT_DetailDao().queryBuilder().where(
                T_DetailDao.Properties.Activity.eq(activity)).build().list().size()>0;
    }

    //统一回单数据请求
    public static void upload(Context context,String url,String json){
        Asynchttp.post(context, url, json, new Asynchttp.Response() {
            @Override
            public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
                EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Upload_OK,""));
            }

            @Override
            public void onFailed(String Msg, AsyncHttpClient client) {
                EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Upload_Error,Msg));
            }
        });
    }


    //统一回单数据请求
    public static void upload(String io,String json){
        Lg.e("回单数据："+json);
        App.CloudService().doIOAction(io, json, new ToSubscribe<BackData>() {
            @Override
            public void onNext(BackData backData) {
                super.onNext(backData);
                if (backData.getResult().getResponseStatus().getIsSuccess()) {
                    EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Upload_OK,backData));
                } else {
                    EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Upload_OK,backData));
                }
            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Upload_Error,e.toString()));
            }
        });
    }

    public static void codeCheck(String io,String json){
        Lg.e("回单数据："+json);
        App.getRService().doIOAction(io, json, new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                super.onNext(commonResponse);
                if (!commonResponse.state)return;
                DownloadReturnBean dBean = new Gson().fromJson(commonResponse.returnJson, DownloadReturnBean.class);
                if (null != dBean && dBean.codeCheckBackDataBeans.size() > 0) {
                    EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Code_Check,dBean.codeCheckBackDataBeans.get(0)));
                }else{
                    EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Code_Check,new CodeCheckBackDataBean("找不到条码信息")));
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Code_Check,new CodeCheckBackDataBean("找不到条码信息")));
            }
        });
    }
    //入库的临时表写入
    public static void codeOnlyInsert(String io,String json){
        Lg.e("回单数据："+json);
        App.getRService().doIOAction(io, json, new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                super.onNext(commonResponse);
                if (!commonResponse.state)return;
                DownloadReturnBean dBean = new Gson().fromJson(commonResponse.returnJson, DownloadReturnBean.class);
                if (null != dBean && dBean.codeCheckBackDataBeans.size() > 0) {
                    EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Code_Only_Insert,dBean.codeCheckBackDataBeans.get(0)));
                }else{
                    EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Code_Only_Insert,new CodeCheckBackDataBean("找不到条码信息")));
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Code_Only_Insert,new CodeCheckBackDataBean("找不到条码信息")));
            }
        });
    }
    //生成打印信息并获取条码和批号等信息
    public static void getPrintData(String io,String json){
        Lg.e("回单数据："+json);
        App.getRService().doIOAction(io, json, new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                super.onNext(commonResponse);
                if (!commonResponse.state)return;
                DownloadReturnBean dBean = new Gson().fromJson(commonResponse.returnJson, DownloadReturnBean.class);
                if (null != dBean && dBean.printDataBeans.size() > 0) {
                    EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Code_Only_Insert,dBean.codeCheckBackDataBeans.get(0)));
                }else{
                    EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Code_Only_Insert,new CodeCheckBackDataBean("找不到条码信息")));
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Code_Only_Insert,new CodeCheckBackDataBean("找不到条码信息")));
            }
        });
    }




    //下推时，统一回单数据请求
    public static long findOrderCode(Context context, int activity, ArrayList<String> fidcontainer){
        String con="";
        for (String str:fidcontainer) {
            con= con+str+",";
        }
        if (con.length() > 0) {
            con = con.subSequence(0, con.length() - 1).toString();
        }
        ArrayList<T_main> mainTips = new ArrayList<>();
        String SQL = "SELECT ORDER_ID,FINDEX,FDELIVERY_TYPE FROM T_MAIN WHERE ACTIVITY=? AND FDELIVERY_TYPE IN ("+con+")";
        Lg.e("SQL:"+SQL);
        Cursor cursor = GreenDaoManager.getmInstance(context).getDaoSession().getDatabase().rawQuery(SQL, new String[]{activity + ""});
        while (cursor.moveToNext()) {
            T_main f = new T_main();
            f.FIndex = cursor.getString(cursor.getColumnIndex("FINDEX"));
//            f.orderId = cursor.getLong(cursor.getColumnIndex("ORDER_ID"));
//            f.FDeliveryType = cursor.getString(cursor.getColumnIndex("FDELIVERY_TYPE"));
            mainTips.add(f);
        }
        if (mainTips.size()>0){
            if (mainTips.size()==1){
                return mainTips.get(0).FOrderId;
            }else{
                long ordercode = System.currentTimeMillis();
                for (int i = 0; i < mainTips.size(); i++) {
                    //重新查找并更新，不适用上面的查找数据，不然会被清空，无法更新
                    List<T_main> mains = GreenDaoManager.getmInstance(context).getDaoSession().getT_mainDao().queryBuilder().where(
                            T_mainDao.Properties.Activity.eq(activity),
                            T_mainDao.Properties.FOrderId.eq(mainTips.get(i).FOrderId)
                    ).build().list();
                    List<T_Detail> t_details = GreenDaoManager.getmInstance(context).getDaoSession().getT_DetailDao().queryBuilder().where(
                            T_DetailDao.Properties.FOrderId.eq(mainTips.get(i).FOrderId)
                    ).build().list();
                    for (T_Detail bean:t_details) {
                        bean.FOrderId = ordercode;
                        GreenDaoManager.getmInstance(context).getDaoSession().getT_DetailDao().update(bean);
                    }
                    for (T_main bean:mains) {
                        bean.FOrderId = ordercode;
                        GreenDaoManager.getmInstance(context).getDaoSession().getT_mainDao().update(bean);
                        Lg.e("更新main："+bean.toString());
                    }

                }
                return ordercode;
            }
        }else{
            return System.currentTimeMillis();
        }

    }

    //回单时，合并相同条件的物料
    public static List<T_Detail> mergeDetail(Context context,String orderID,int Activity){
        List<T_Detail> list = new ArrayList<>();
        DaoSession daoSession = GreenDaoManager.getmInstance(context).getDaoSession();
        Cursor cursor = daoSession.getDatabase().rawQuery("SELECT " +
                "ACTIVITY," +
                "FORDER_ID," +
                "FSTORAGE_ID," +
                "FID," +
                "FENTRY_ID," +
                "FPRODUCT_NO," +
                "FSTORAGE_OUT_ID," +
                "FSTORAGE_OUT," +
                "FSTORAGE_IN_ID," +
                "FSTORAGE_IN," +
                "FWAVE_HOUSE_OUT_ID," +
                "FWAVE_HOUSE_OUT," +
                "FWAVE_HOUSE_IN_ID," +
                "FWAVE_HOUSE_IN," +
                "AUX_SIGN," +
                "ACTUAL_MODEL," +
                "FWORK_SHOP_ID1," +
                "FSTORAGE_PDID," +
                "FWAVE_HOUSE_ID," +
                "FWAVE_HOUSE_PDID," +
                "FSOENTRY_ID," +
                "FBATCH," +
                "FIS_FREE," +
                "FREMAIN_IN_STOCK_UNIT_ID," +
                "FPRICE_UNIT_ID," +
                "FMATERIAL_ID," +
                "FMATERIAL_ID_FOR_PD," +
                "FUNIT_ID," +
                "FUNIT_IDFOR_PD," +
                "FBILL_NO," +
                "FBILL_TYPE_ID," +
                "FSTOCK_ORG_ID," +
                "SUM(FREMAIN_IN_STOCK_QTY) AS FREMAIN_IN_STOCK_QTYALL," +
                "SUM(FREAL_QTY) AS FREAL_QTYALL " +
                "FROM T__DETAIL " +
                "WHERE " +
                "FORDER_ID = ? AND ACTIVITY = ? " +
                "GROUP BY " +
                "FORDER_ID," +
                "FWORK_SHOP_ID1," +
                "FSTORAGE_ID," +
                "FWAVE_HOUSE_ID," +
                "FBATCH," +
                "FMATERIAL_ID," +
                "FUNIT_ID ORDER BY FENTRY_ID", new String[]{orderID+"", Activity+""});
        while (cursor.moveToNext()){
            T_Detail t_detail = new T_Detail();
            t_detail.activity = cursor.getInt(cursor.getColumnIndex("ACTIVITY"));
            t_detail.FOrderId = cursor.getLong(cursor.getColumnIndex("FORDER_ID"));
            t_detail.FStorageId = cursor.getString(cursor.getColumnIndex("FSTORAGE_ID"));
            t_detail.FWorkShopId1 = cursor.getString(cursor.getColumnIndex("FWORK_SHOP_ID1"));
            t_detail.FStoragePDId = cursor.getString(cursor.getColumnIndex("FSTORAGE_PDID"));
            t_detail.FWaveHouseId = cursor.getString(cursor.getColumnIndex("FWAVE_HOUSE_ID"));
            t_detail.FWaveHousePDId = cursor.getString(cursor.getColumnIndex("FWAVE_HOUSE_PDID"));
            t_detail.FSOEntryId = cursor.getString(cursor.getColumnIndex("FSOENTRY_ID"));
            t_detail.FBatch = cursor.getString(cursor.getColumnIndex("FBATCH"));
            t_detail.FIsFree = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("FIS_FREE")));
            t_detail.FRemainInStockQty = cursor.getString(cursor.getColumnIndex("FREMAIN_IN_STOCK_QTYALL"));
            t_detail.FRealQty = cursor.getString(cursor.getColumnIndex("FREAL_QTYALL"));
            t_detail.FRemainInStockUnitId = cursor.getString(cursor.getColumnIndex("FREMAIN_IN_STOCK_UNIT_ID"));
            t_detail.FPriceUnitID = cursor.getString(cursor.getColumnIndex("FPRICE_UNIT_ID"));
            t_detail.FMaterialId = cursor.getString(cursor.getColumnIndex("FMATERIAL_ID"));
            t_detail.FMaterialIdForPD = cursor.getString(cursor.getColumnIndex("FMATERIAL_ID_FOR_PD"));
            t_detail.FUnitID = cursor.getString(cursor.getColumnIndex("FUNIT_ID"));
            t_detail.FUnitIDForPD = cursor.getString(cursor.getColumnIndex("FUNIT_IDFOR_PD"));
            t_detail.FBillNo = cursor.getString(cursor.getColumnIndex("FBILL_NO"));
            t_detail.FBillTypeID = cursor.getString(cursor.getColumnIndex("FBILL_TYPE_ID"));
            t_detail.FStockOrgId = cursor.getString(cursor.getColumnIndex("FSTOCK_ORG_ID"));
            t_detail.AuxSign = cursor.getString(cursor.getColumnIndex("AUX_SIGN"));
            t_detail.ActualModel = cursor.getString(cursor.getColumnIndex("ACTUAL_MODEL"));
            t_detail.FProductNo = cursor.getString(cursor.getColumnIndex("FPRODUCT_NO"));

            t_detail.FStorageOutId = cursor.getString(cursor.getColumnIndex("FSTORAGE_OUT_ID"));
            t_detail.FStorageOut = cursor.getString(cursor.getColumnIndex("FSTORAGE_OUT"));
            t_detail.FStorageInId = cursor.getString(cursor.getColumnIndex("FSTORAGE_IN_ID"));
            t_detail.FStorageIn = cursor.getString(cursor.getColumnIndex("FSTORAGE_IN"));
            t_detail.FWaveHouseOutId = cursor.getString(cursor.getColumnIndex("FWAVE_HOUSE_OUT_ID"));
            t_detail.FWaveHouseOut = cursor.getString(cursor.getColumnIndex("FWAVE_HOUSE_OUT"));
            t_detail.FWaveHouseInId = cursor.getString(cursor.getColumnIndex("FWAVE_HOUSE_IN_ID"));
            t_detail.FWaveHouseIn = cursor.getString(cursor.getColumnIndex("FWAVE_HOUSE_IN"));
            t_detail.FEntryID = cursor.getString(cursor.getColumnIndex("FENTRY_ID"));
            t_detail.FID = cursor.getString(cursor.getColumnIndex("FID"));


            list.add(t_detail);
        }

        return list;
    }

    //遍历所有的货主信息
    public static List<String> getHuoZhuForPushDown(Context context,String orderID,int Activity,String fid){
        List<String> list = new ArrayList<>();
        DaoSession daoSession = GreenDaoManager.getmInstance(context).getDaoSession();
        Cursor cursor = daoSession.getDatabase().rawQuery("SELECT " +
                "ACTIVITY," +
                "FORDER_ID," +
                "FID," +
                "FHUO_ZHU_NUMBER " +
                "FROM T__DETAIL " +
                "WHERE " +
                "FORDER_ID = ? AND ACTIVITY = ? AND FID = ? " +
                "GROUP BY " +
                "FHUO_ZHU_NUMBER ORDER BY FHUO_ZHU_NUMBER", new String[]{orderID+"", Activity+"",fid});
        while (cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex("FHUO_ZHU_NUMBER")));
        }
        return list;
    }


    //回单时，合并相同条件的物料：：：下推单专用
    public static List<T_Detail> mergeDetailForPushDown(Context context,String orderID,int Activity,String fid){
        List<T_Detail> list = new ArrayList<>();
        DaoSession daoSession = GreenDaoManager.getmInstance(context).getDaoSession();
        Cursor cursor = daoSession.getDatabase().rawQuery("SELECT " +
                "ACTIVITY," +
                "FORDER_ID," +
                "FSTORAGE_ID," +
                "FID," +
                "FENTRY_ID," +
                "FTAX_PRICE," +
                "FHUO_ZHU_NUMBER," +
                "FPRODUCT_NO," +
                "FSTORAGE_OUT_ID," +
                "FSTORAGE_OUT," +
                "FSTORAGE_IN_ID," +
                "FSTORAGE_IN," +
                "FWAVE_HOUSE_OUT_ID," +
                "FWAVE_HOUSE_OUT," +
                "FWAVE_HOUSE_IN_ID," +
                "FWAVE_HOUSE_IN," +
                "AUX_SIGN," +
                "ACTUAL_MODEL," +
                "FWORK_SHOP_ID1," +
                "FSTORAGE_PDID," +
                "FWAVE_HOUSE_ID," +
                "FWAVE_HOUSE_PDID," +
                "FSOENTRY_ID," +
                "FBATCH," +
                "FIS_FREE," +
                "FREMAIN_IN_STOCK_UNIT_ID," +
                "FPRICE_UNIT_ID," +
                "FMATERIAL_ID," +
                "FMATERIAL_ID_FOR_PD," +
                "FUNIT_ID," +
                "FUNIT_IDFOR_PD," +
                "FBILL_NO," +
                "FBILL_TYPE_ID," +
                "FSTOCK_ORG_ID," +
                "SUM(FREMAIN_IN_STOCK_QTY) AS FREMAIN_IN_STOCK_QTYALL," +
                "SUM(FREAL_QTY) AS FREAL_QTYALL " +
                "FROM T__DETAIL " +
                "WHERE " +
                "FORDER_ID = ? AND ACTIVITY = ? AND FID = ?" +
                "GROUP BY " +
                "FORDER_ID," +
                "FWORK_SHOP_ID1," +
                "FSTORAGE_ID," +
                "FWAVE_HOUSE_ID," +
                "FBATCH," +
                "FMATERIAL_ID," +
                "FUNIT_ID ORDER BY FENTRY_ID", new String[]{orderID+"", Activity+"",fid});
        while (cursor.moveToNext()){
            T_Detail t_detail = new T_Detail();
            t_detail.activity = cursor.getInt(cursor.getColumnIndex("ACTIVITY"));
            t_detail.FOrderId = cursor.getLong(cursor.getColumnIndex("FORDER_ID"));
            t_detail.FStorageId = cursor.getString(cursor.getColumnIndex("FSTORAGE_ID"));
            t_detail.FWorkShopId1 = cursor.getString(cursor.getColumnIndex("FWORK_SHOP_ID1"));
            t_detail.FStoragePDId = cursor.getString(cursor.getColumnIndex("FSTORAGE_PDID"));
            t_detail.FWaveHouseId = cursor.getString(cursor.getColumnIndex("FWAVE_HOUSE_ID"));
            t_detail.FWaveHousePDId = cursor.getString(cursor.getColumnIndex("FWAVE_HOUSE_PDID"));
            t_detail.FSOEntryId = cursor.getString(cursor.getColumnIndex("FSOENTRY_ID"));
            t_detail.FBatch = cursor.getString(cursor.getColumnIndex("FBATCH"));
            t_detail.FIsFree = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("FIS_FREE")));
            t_detail.FRemainInStockQty = cursor.getString(cursor.getColumnIndex("FREMAIN_IN_STOCK_QTYALL"));
            t_detail.FRealQty = cursor.getString(cursor.getColumnIndex("FREAL_QTYALL"));
            t_detail.FRemainInStockUnitId = cursor.getString(cursor.getColumnIndex("FREMAIN_IN_STOCK_UNIT_ID"));
            t_detail.FPriceUnitID = cursor.getString(cursor.getColumnIndex("FPRICE_UNIT_ID"));
            t_detail.FMaterialId = cursor.getString(cursor.getColumnIndex("FMATERIAL_ID"));
            t_detail.FMaterialIdForPD = cursor.getString(cursor.getColumnIndex("FMATERIAL_ID_FOR_PD"));
            t_detail.FUnitID = cursor.getString(cursor.getColumnIndex("FUNIT_ID"));
            t_detail.FUnitIDForPD = cursor.getString(cursor.getColumnIndex("FUNIT_IDFOR_PD"));
            t_detail.FBillNo = cursor.getString(cursor.getColumnIndex("FBILL_NO"));
            t_detail.FBillTypeID = cursor.getString(cursor.getColumnIndex("FBILL_TYPE_ID"));
            t_detail.FStockOrgId = cursor.getString(cursor.getColumnIndex("FSTOCK_ORG_ID"));
            t_detail.AuxSign = cursor.getString(cursor.getColumnIndex("AUX_SIGN"));
            t_detail.ActualModel = cursor.getString(cursor.getColumnIndex("ACTUAL_MODEL"));
            t_detail.FProductNo = cursor.getString(cursor.getColumnIndex("FPRODUCT_NO"));
            t_detail.FTaxPrice = cursor.getString(cursor.getColumnIndex("FTAX_PRICE"));
            t_detail.FHuoZhuNumber = cursor.getString(cursor.getColumnIndex("FHUO_ZHU_NUMBER"));

            t_detail.FStorageOutId = cursor.getString(cursor.getColumnIndex("FSTORAGE_OUT_ID"));
            t_detail.FStorageOut = cursor.getString(cursor.getColumnIndex("FSTORAGE_OUT"));
            t_detail.FStorageInId = cursor.getString(cursor.getColumnIndex("FSTORAGE_IN_ID"));
            t_detail.FStorageIn = cursor.getString(cursor.getColumnIndex("FSTORAGE_IN"));
            t_detail.FWaveHouseOutId = cursor.getString(cursor.getColumnIndex("FWAVE_HOUSE_OUT_ID"));
            t_detail.FWaveHouseOut = cursor.getString(cursor.getColumnIndex("FWAVE_HOUSE_OUT"));
            t_detail.FWaveHouseInId = cursor.getString(cursor.getColumnIndex("FWAVE_HOUSE_IN_ID"));
            t_detail.FWaveHouseIn = cursor.getString(cursor.getColumnIndex("FWAVE_HOUSE_IN"));
            t_detail.FEntryID = cursor.getString(cursor.getColumnIndex("FENTRY_ID"));
            t_detail.FID = cursor.getString(cursor.getColumnIndex("FID"));


            list.add(t_detail);
        }

        return list;
    }

    //获取库存
    public static void getStoreNum(Product product, Storage storage, String batch, Context mContext, final TextView textView,Org org){
        if (product == null || storage == null){
            return;
        }
        Lg.e("库存查找条件：",product.FMASTERID+"-"+storage.FItemID+"-"+batch);
        if (BasicShareUtil.getInstance(mContext).getIsOL()) {
            InStoreNumBean storageNum = new InStoreNumBean(product.FMASTERID,storage.FItemID,"",batch,org==null?"":org.FOrgID);
            App.getRService().doIOAction(WebApi.GETINSTORENUM, new Gson().toJson(storageNum), new MySubscribe<CommonResponse>() {
                @Override
                public void onNext(CommonResponse commonResponse) {
                    super.onNext(commonResponse);
                    if (!commonResponse.state)return;
                    if (!MathUtil.isNumeric(commonResponse.returnJson)){
                        textView.setText("0");
                    }else{
                        textView.setText(DoubleUtil.Cut0(commonResponse.returnJson)+"");
                    }
                }

                @Override
                public void onError(Throwable e) {
//                    super.onError(e);
                    textView.setText("0");
                }
            });
        }else{
            List<InStorageNum> container = new ArrayList<>();
            String con="";
            if (!"".equals(storage.FItemID)){
                con+=" and FSTOCK_ID='"+storage.FItemID+"'";
            }
            if (!"".equals(product.FMaterialid)){
                con+=" and FITEM_ID='"+product.FMaterialid+"'";
            }
            if (!"".equals(batch)){
                con+=" and FBATCH_NO='"+batch+"'";
            }
            String SQL = "SELECT * FROM IN_STORAGE_NUM WHERE 1=1 "+con;
            Lg.e("库存查询SQL:"+SQL);
            Cursor cursor = GreenDaoManager.getmInstance(mContext).getDaoSession().getDatabase().rawQuery(SQL, null);
            while (cursor.moveToNext()) {
                InStorageNum f = new InStorageNum();
                f.FQty = cursor.getString(cursor.getColumnIndex("FQTY"));
                Lg.e("库存查询存在FQty："+f.FQty);
                container.add(f);
            }
            if (container.size() > 0) {
                textView.setText(container.get(0).FQty);
            } else {
                textView.setText("0");
            }
        }
    }

    //获取库存()舍弃
    public static void getStoreNum(Product product, Storage storage, String batch, Context mContext, final TextView textView){
        if (product == null || storage == null){
            return;
        }
        Lg.e("库存查找条件：",product.FMASTERID+"-"+storage.FItemID+"-"+batch);
        if (BasicShareUtil.getInstance(mContext).getIsOL()) {
            InStoreNumBean storageNum = new InStoreNumBean(product.FMASTERID,storage.FItemID,"",batch);
            App.getRService().doIOAction(WebApi.GETINSTORENUM, new Gson().toJson(storageNum), new MySubscribe<CommonResponse>() {
                @Override
                public void onNext(CommonResponse commonResponse) {
                    super.onNext(commonResponse);
                    if (!commonResponse.state)return;
                    if (!MathUtil.isNumeric(commonResponse.returnJson)){
                        textView.setText("0");
                    }else{
                        textView.setText(commonResponse.returnJson);
                    }
                }

                @Override
                public void onError(Throwable e) {
//                    super.onError(e);
                    textView.setText("0");
                }
            });
        }else{
            List<InStorageNum> container = new ArrayList<>();
            String con="";
            if (!"".equals(storage.FItemID)){
                con+=" and FSTOCK_ID='"+storage.FItemID+"'";
            }
            if (!"".equals(product.FMaterialid)){
                con+=" and FITEM_ID='"+product.FMaterialid+"'";
            }
            if (!"".equals(batch)){
                con+=" and FBATCH_NO='"+batch+"'";
            }
            String SQL = "SELECT * FROM IN_STORAGE_NUM WHERE 1=1 "+con;
            Lg.e("库存查询SQL:"+SQL);
            Cursor cursor = GreenDaoManager.getmInstance(mContext).getDaoSession().getDatabase().rawQuery(SQL, null);
            while (cursor.moveToNext()) {
                InStorageNum f = new InStorageNum();
                f.FQty = cursor.getString(cursor.getColumnIndex("FQTY"));
                Lg.e("库存查询存在FQty："+f.FQty);
                container.add(f);
            }
            if (container.size() > 0) {
                textView.setText(container.get(0).FQty);
            } else {
                textView.setText("0");
            }
        }
    }

    //条码检测后，通过ID查找物料数据
    public static void getProductForNumber(String barCode, Org org){
        App.getRService().doIOAction(WebApi.S2Product, new Gson().toJson(new SearchBean(SearchBean.product_for_number, new Gson().toJson(new SearchBean.S2Product(barCode,org==null?"":org.FOrgID)))), new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                super.onNext(commonResponse);
                if (!commonResponse.state)return;
                final DownloadReturnBean dBean = new Gson().fromJson(commonResponse.returnJson, DownloadReturnBean.class);
                LoadingUtil.dismiss();
                if (dBean.products.size()>0){
                    EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Product,dBean.products.get(0)));
                }else{
                    Toast.showText(App.getContext(),"查无此物料信息");
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                LoadingUtil.dismiss();
            }
        });
    }

    //通过扫码时，通过条码查找物料
    public static void getProductForScan(String barCode, Org org){
        App.getRService().doIOAction(WebApi.S2Product, new Gson().toJson(new SearchBean(SearchBean.product_for_barcode, new Gson().toJson(new SearchBean.S2Product(barCode,org==null?"":org.FOrgID)))), new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                super.onNext(commonResponse);
                if (!commonResponse.state)return;
                final DownloadReturnBean dBean = new Gson().fromJson(commonResponse.returnJson, DownloadReturnBean.class);
                if (dBean.products.size()>0){
                    EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Product,dBean.products.get(0)));
                }else{
                    Toast.showText(App.getContext(),"查无此物料信息");
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    //下载配置的连接
    public static void SetConnectSQL(String json){
        App.getRService().connectToSQL(json, new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                super.onNext(commonResponse);
                if (!commonResponse.state)return;
                EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Connect_OK,commonResponse));
            }

            @Override
            public void onError(Throwable e) {
                EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Connect_Error,e.toString()));
            }
        });
    }
    //下载配置的配置
    public static void SetProp(String json){
        App.getRService().SetProp(json, new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Prop_OK,commonResponse));
            }

            @Override
            public void onError(Throwable e) {
                EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Prop_Error,e.toString()));
            }
        });
    }

}
