package com.fangzuo.assist.cloud.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.fangzuo.assist.cloud.ABase.BaseActivity;
import com.fangzuo.assist.cloud.Activity.Crash.App;
import com.fangzuo.assist.cloud.Adapter.ReViewAdapter;
import com.fangzuo.assist.cloud.Adapter.ReViewP2CFOAdapter;
import com.fangzuo.assist.cloud.Beans.BlueToothBean;
import com.fangzuo.assist.cloud.Beans.CommonResponse;
import com.fangzuo.assist.cloud.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.cloud.Beans.PrintHistory;
import com.fangzuo.assist.cloud.Beans.PurchaseInStoreUploadBean;
import com.fangzuo.assist.cloud.Dao.PDSub;
import com.fangzuo.assist.cloud.Dao.T_Detail;
import com.fangzuo.assist.cloud.Dao.T_main;
import com.fangzuo.assist.cloud.R;
import com.fangzuo.assist.cloud.RxSerivce.MySubscribe;
import com.fangzuo.assist.cloud.Utils.CommonUtil;
import com.fangzuo.assist.cloud.Utils.Config;
import com.fangzuo.assist.cloud.Utils.DoubleUtil;
import com.fangzuo.assist.cloud.Utils.EventBusInfoCode;
import com.fangzuo.assist.cloud.Utils.EventBusUtil;
import com.fangzuo.assist.cloud.Utils.Info;
import com.fangzuo.assist.cloud.Utils.Lg;
import com.fangzuo.assist.cloud.Utils.MathUtil;
import com.fangzuo.assist.cloud.Utils.Toast;
import com.fangzuo.assist.cloud.Utils.WebApi;
import com.fangzuo.assist.cloud.databinding.ActivityReViewBinding;
import com.fangzuo.assist.cloud.databinding.ActivityReViewP2cfoBinding;
import com.fangzuo.assist.cloud.widget.LoadingUtil;
import com.fangzuo.greendao.gen.PDSubDao;
import com.fangzuo.greendao.gen.PrintHistoryDao;
import com.fangzuo.greendao.gen.T_DetailDao;
import com.fangzuo.greendao.gen.T_mainDao;
import com.google.gson.Gson;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import butterknife.ButterKnife;
import butterknife.OnClick;
import zpSDK.zpSDK.zpBluetoothPrinter;

public class ReViewP2CFOActivity extends BaseActivity {
    ActivityReViewP2cfoBinding binding;
    private int activity;
    private List<T_Detail> list;
    private List<T_main> mainsList;
    private ReViewP2CFOAdapter adapter;
    private List<Boolean> isCheck;
    private PDSubDao pdSubDao;

    @Override
    protected void receiveEvent(ClassEvent event) {
        switch (event.Msg) {
//            case EventBusInfoCode.Print_Check://回单失败
//                String msg = (String) event.postEvent;
//                if ("OK".equals(msg)){
//                    isOkPrint = true;
//                    Toast.showText(mContext,"打印机就绪");
////                    binding.toolbar.tvRight.setText("打印机就绪");
////                    binding.toolbar.tvRight.setTextColor(Color.BLACK);
//                }else{
//                    isOkPrint = false;
//                    Toast.showText(mContext,"连接打印机错误");
////                    binding.toolbar.tvRight.setText("连接打印机错误");
////                    binding.toolbar.tvRight.setTextColor(Color.RED);
//                }
//                LoadingUtil.dismiss();
//                break;
//            case EventBusInfoCode.Print_Out://打印
//                PrintHistory data = (PrintHistory) event.postEvent;
//                try {
//                    CommonUtil.doPrint(zpSDK,data,"1");
//                } catch (Exception e) {
////                    e.printStackTrace();
//                    LoadingUtil.showAlter(mContext,getString(R.string.error_print),getString(R.string.check_print));
//                }
//                break;
        }
    }

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_re_view_p2cfo);
        ButterKnife.bind(this);
        pdSubDao = daoSession.getPDSubDao();
    }

    @Override
    protected void initData() {
        Intent in = getIntent();
        Bundle extras = in.getExtras();
        activity = extras.getInt("activity");
        initList();
    }

    private void initList() {
        LoadingUtil.dismiss();
//        double num = 0;
        double storenum = 0;
        isCheck = new ArrayList<>();
        list = new ArrayList<>();
        list = t_detailDao.queryBuilder().where(
                T_DetailDao.Properties.Activity.eq(activity),
                T_DetailDao.Properties.FAccountID.eq(CommonUtil.getAccountID())
        ).build().list();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                isCheck.add(false);
            }
        } else {//若列表为空，删除所有该activity的表头信息
            t_mainDao.deleteInTx(t_mainDao.queryBuilder().where(
                    T_mainDao.Properties.Activity.eq(activity),
                    T_mainDao.Properties.FAccountID.eq(CommonUtil.getAccountID())
            ).build().list());
            EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Lock_Main, Config.Lock+"NO"));
        }
        Lg.e("列表数据：" + gson.toJson(list));
        adapter = new ReViewP2CFOAdapter(mContext, list, isCheck);
        binding.lvResult.setAdapter(adapter);
//        adapter.setInnerListener(this);
        adapter.notifyDataSetChanged();
        List<String> products = new ArrayList<>();
        products.clear();
        if (list.size() > 0) {
            if (products.size() == 0) {
                products.add(list.get(0).FBarcode);
            }
            for (int i = 0; i < list.size(); i++) {
                if (!products.contains(list.get(i).FBarcode)) {
                    products.add(list.get(i).FBarcode);
                }
//                num += MathUtil.toD(list.get(i).FBaseNum);
                storenum += MathUtil.toD(list.get(i).FRealQty);
            }

            binding.productcategory.setText("已添加数量:" + list.size() + "个");
//            binding.productnum.setText("基本数量:" + num +list.get(0).FBaseUnit);
            binding.tvStorenum.setText("库存数量:" + DoubleUtil.Cut4(storenum+""));
        } else {
            binding.productcategory.setText("已添加数量:" + 0 + "个");
//            binding.productnum.setText("基本数量:" + 0 + "");
            binding.tvStorenum.setText("库存数量:" + 0 + "");
        }

        mainsList = new ArrayList<>();
        mainsList = t_mainDao.queryBuilder().where(
                T_mainDao.Properties.Activity.eq(activity),
                T_mainDao.Properties.FAccountID.eq(CommonUtil.getAccountID())
        ).build().list();
        Lg.e("表头信息",mainsList);
        if (mainsList.size() > 0) {
            for (int i = 0; i < mainsList.size(); i++) {
                List<T_Detail> details = t_detailDao.queryBuilder().where(
                        T_DetailDao.Properties.FOrderId.eq(mainsList.get(i).FOrderId),
                        T_DetailDao.Properties.FAccountID.eq(CommonUtil.getAccountID())
                ).build().list();
                if (details.size() == 0 || details == null) {
                    t_mainDao.deleteInTx(mainsList.get(i));
                    Hawk.delete(Info.MainData+mainsList.get(i).FOrderId);
                }
            }
        }
    }


    @Override
    protected void initListener() {
        binding.lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (isCheck.get(i)) {
                    isCheck.set(i, false);
                } else {
                    isCheck.set(i, true);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }




    private PurchaseInStoreUploadBean pBean;
    private PurchaseInStoreUploadBean.purchaseInStore listBean;
    private ArrayList<PurchaseInStoreUploadBean.purchaseInStore> data;
    private ArrayList<T_Detail> t_detailList;
    private ArrayList<T_main> t_mainsList;

    @OnClick({R.id.btn_back, R.id.delete_all, R.id.btn_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.delete_all:
                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                ab.setTitle("确认删除");
                ab.setMessage("确认删除所有么？");
                ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int pos) {
                        LoadingUtil.showDialog(mContext, "正在删除...");
                        t_detailDao.deleteInTx(t_detailDao.queryBuilder().where(
                                T_DetailDao.Properties.Activity.eq(activity),
                                T_DetailDao.Properties.FAccountID.eq(CommonUtil.getAccountID())
                        ).build().list());
                        initList();
                        t_mainDao.deleteInTx(t_mainDao.queryBuilder().where(
                                T_mainDao.Properties.Activity.eq(activity),
                                T_mainDao.Properties.FAccountID.eq(CommonUtil.getAccountID())
                        ).build().list());
                        Toast.showText(mContext, "删除成功");
                        initList();
                    }
                });
                ab.setNegativeButton("取消", null);
                ab.create().show();
                break;
            case R.id.btn_delete:
                AlertDialog.Builder delete = new AlertDialog.Builder(mContext);
                delete.setTitle("确认删除");
                delete.setMessage("确认删除选中单据么？");
                delete.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoadingUtil.showDialog(mContext, "正在删除...");
                        t_detailList = new ArrayList<>();
                        data = new ArrayList<>();
                        for (int j = 0; j < isCheck.size(); j++) {
                            if (isCheck.get(j)) {
//                                Log.e(i + "", isCheck.get(j) + "");
                                final T_Detail t_detail = t_detailDao.queryBuilder().where(
                                        T_DetailDao.Properties.FIndex.eq(list.get(j).FIndex)
                                ).build().unique();
                                t_detailList.add(t_detail);
                            }
                        }
                        t_detailDao.deleteInTx(t_detailList);
                        initList();
                    }
                });
                delete.setNegativeButton("取消", null);
                delete.create().show();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Lg.e("ReView：","OnPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void OnReceive(String code) {

    }
    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

}
