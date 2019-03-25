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
import com.fangzuo.assist.cloud.Adapter.ReViewPDAdapter;
import com.fangzuo.assist.cloud.Beans.BackData;
import com.fangzuo.assist.cloud.Beans.CommonResponse;
import com.fangzuo.assist.cloud.Beans.PurchaseInStoreUploadBean;
import com.fangzuo.assist.cloud.Dao.PushDownSub;
import com.fangzuo.assist.cloud.Dao.T_Detail;
import com.fangzuo.assist.cloud.Dao.T_main;
import com.fangzuo.assist.cloud.R;
import com.fangzuo.assist.cloud.RxSerivce.MySubscribe;
import com.fangzuo.assist.cloud.Utils.Lg;
import com.fangzuo.assist.cloud.Utils.MathUtil;
import com.fangzuo.assist.cloud.Utils.Toast;
import com.fangzuo.assist.cloud.Utils.WebApi;
import com.fangzuo.assist.cloud.databinding.ActivityReViewPdBinding;
import com.fangzuo.assist.cloud.widget.LoadingUtil;
import com.fangzuo.greendao.gen.PushDownSubDao;
import com.fangzuo.greendao.gen.T_DetailDao;
import com.fangzuo.greendao.gen.T_mainDao;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReViewPDActivity extends BaseActivity {
    ActivityReViewPdBinding binding;
    private int activity;
//    private T_main main;
    private List<T_Detail> list;
    private List<T_main> list_main;
    private ReViewPDAdapter adapter;
    private List<Boolean> isCheck;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_re_view_pd);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        Intent in = getIntent();
        Bundle extras = in.getExtras();
        activity = extras.getInt("activity");
        initList();
    }

    private void initList() {
        double num = 0;
        isCheck = new ArrayList<>();
        list = new ArrayList<>();
        list_main = new ArrayList<>();
        list = t_detailDao.queryBuilder().where(
                T_DetailDao.Properties.Activity.eq(activity)
        ).build().list();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                isCheck.add(false);
            }
        }else{//若列表为空，删除所有该activity的表头信息
            t_mainDao.deleteInTx(t_mainDao.queryBuilder().where(T_mainDao.Properties.Activity.eq(activity)).build().list());
        }
        Lg.e("列表数据：" + gson.toJson(list));
        adapter = new ReViewPDAdapter(mContext, list, isCheck);
        binding.lvResult.setAdapter(adapter);
//        adapter.setInnerListener(this);
        adapter.notifyDataSetChanged();
        List<String> products = new ArrayList<>();
        products.clear();
        if (list.size() > 0) {
            binding.tvCheckMain.setVisibility(View.GONE);
            list_main = t_mainDao.queryBuilder().where(T_mainDao.Properties.Activity.eq(activity)).build().list();
            if (products.size() == 0) {
                products.add(list.get(0).FBarcode);
            }
            for (int i = 0; i < list.size(); i++) {
                if (!products.contains(list.get(i).FBarcode)) {
                    products.add(list.get(i).FBarcode);
                }
                num += MathUtil.toD(list.get(i).FRealQty);
            }
            binding.productcategory.setText("已添加数量:" + products.size() + "个");
            binding.productnum.setText("物料总数为:" + num + "");
        } else {
            binding.tvCheckMain.setVisibility(View.GONE);
            binding.productcategory.setText("已添加数量:" + 0 + "个");
            binding.productnum.setText("物料总数为:" + 0 + "");
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
        binding.tvCheckMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder builder = new StringBuilder();
                    builder.append("客户名称:"+list_main.get(0).FCustomer + "\n");
                    builder.append("客户ID:"+list_main.get(0).FCustomerID + "\n");
                    builder.append("销售员ID:"+list_main.get(0).FPurchaserId + "\n");
                    builder.append("销售组织ID:"+list_main.get(0).FStockOrgId + "\n");
                    builder.append("发货组织ID:"+list_main.get(0).FPurchaseOrgId + "\n");
                    builder.append("备注:"+list_main.get(0).FNot + "\n");
                    builder.append("入库时间:"+list_main.get(0).FDate + "\n");
                    LoadingUtil.showAlter(mContext,"表头数据:",builder.toString(),false);
            }
        });
    }

    @Override
    protected void OnReceive(String code) {

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
//            case R.id.delete_all:
//                AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
//                ab.setTitle("确认删除");
//                ab.setMessage("确认删除所有么？");
//                ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        t_detailDao.deleteInTx(t_detailDao.queryBuilder().where(
//                                T_DetailDao.Properties.Activity.eq(activity)).build().list());
//                        initList();
//                        t_mainDao.deleteInTx(t_mainDao.queryBuilder().where(
//                                T_mainDao.Properties.Activity.eq(activity)).build().list());
//                    }
//                });
//                ab.setNegativeButton("取消", null);
//                ab.create().show();
//                break;
            case R.id.btn_delete:
                AlertDialog.Builder delete = new AlertDialog.Builder(mContext);
                delete.setTitle("确认删除");
                delete.setMessage("确认删除选中单据么？");
                delete.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoadingUtil.showDialog(mContext, "正在删除...");
                        t_detailList = new ArrayList<>();
                        t_mainsList = new ArrayList<>();
                        pBean = new PurchaseInStoreUploadBean();
                        listBean = pBean.new purchaseInStore();
                        data = new ArrayList<>();
                        for (int j = 0; j < isCheck.size(); j++) {
                            if (isCheck.get(j)) {
//                                Log.e(i + "", isCheck.get(j) + "");
                                final T_Detail t_detail = t_detailDao.queryBuilder().where(
                                        T_DetailDao.Properties.FIndex.eq(list.get(j).FIndex)
                                ).build().unique();
                                t_detailList.add(t_detail);
                                final T_main t_main = t_mainDao.queryBuilder().where(
                                        T_mainDao.Properties.FIndex.eq(list.get(j).FIndex)
                                ).build().unique();
                                t_mainsList.add(t_main);
                                Log.e(TAG, "获取到T_Detail:" + t_detail.toString());
                            }
                        }
                        String detail = "";
                        listBean.main = "";
                        ArrayList<String> detailContainer = new ArrayList<>();
                        for (int j = 0; j < t_detailList.size(); j++) {
                            if (j != 0 && j % 49 == 0) {
                                Log.e("j%49", j % 49 + "");
                                T_Detail t_detail = t_detailList.get(j);
                                detail = detail +
                                        t_detail.FBarcode + "|" +
                                        t_detail.FRealQty + "|" +
                                        t_detail.IMIE + "|" +
                                        t_detail.FOrderId + "|";
                                detail = detail.subSequence(0, detail.length() - 1).toString();
                                detailContainer.add(detail);
                                detail = "";
                            } else {
                                Log.e("j", j + "");
                                T_Detail t_detail = t_detailList.get(j);
                                detail = detail +
                                        t_detail.FBarcode + "|" +
                                        t_detail.FRealQty + "|" +
                                        t_detail.IMIE + "|" +
                                        t_detail.FOrderId + "|";
                                Log.e("detail1", detail);
                            }
                        }
                        if (detail.length() > 0) {
                            detail = detail.subSequence(0, detail.length() - 1).toString();
                        }
                        Log.e("detail", detail);
                        detailContainer.add(detail);
                        listBean.detail = detailContainer;
                        data.add(listBean);
                        pBean.list = data;
                        Gson gson = new Gson();
                        App.getRService().doIOAction(WebApi.CodeOnlyDelete, gson.toJson(pBean), new MySubscribe<CommonResponse>() {
                            @Override
                            public void onNext(CommonResponse commonResponse) {
                                super.onNext(commonResponse);
                                if (!commonResponse.state) return;
                                Lg.e("删除请求成功");
                                deleteMain(t_detailList);
//                                t_detailDao.deleteInTx(t_detailList);
//                                t_mainDao.deleteInTx(t_mainsList);
//                                Toast.showText(mContext, "删除成功");
//                                initList();
//                                tableAdapter.notifyDataSetChanged();
                                LoadingUtil.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                LoadingUtil.dismiss();
                                Toast.showText(mContext, "删除失败：" + e.toString());
                            }
                        });

//                        List<T_Detail> details = new ArrayList<>();
//                        for (int j = 0; j < list.size(); j++) {
//                            if (isCheck.get(j)) {
//                                details.add(t_detailDao.queryBuilder().where(
//                                        T_DetailDao.Properties.FIndex.eq(list.get(j).FIndex)
//                                ).build().unique());
//                            }
//                        }
//                        deleteMain(details);
//                        initList();

//                        List<T_Detail> details = new ArrayList<>();
//                        for (int j = 0; j < list.size(); j++) {
//                            if (isCheck.get(j)) {
//                                details.add(t_detailDao.queryBuilder().where(
//                                        T_DetailDao.Properties.FIndex.eq(list.get(j).FIndex)
//                                ).build().unique());
//                                PushDownSubDao pushDownSubDao = daoSession.getPushDownSubDao();
//                                List<PushDownSub> pushDownSubs = pushDownSubDao.queryBuilder().where(
//                                        PushDownSubDao.Properties.FEntryID.eq(list.get(j).FEntryID)
//                                ).build().list();
//                                Lg.e(pushDownSubs.size() + "多少个");
//                                if (pushDownSubs.size() > 0) {
//                                    //删除后，更新数据里面的已验收数
//                                    double result = MathUtil.toD(list.get(j).FRealQty);
//                                    pushDownSubs.get(0).FQtying = MathUtil.doubleSub(MathUtil.toD(pushDownSubs.get(0).FQtying), result) + "";
//                                    pushDownSubDao.update(pushDownSubs.get(0));
//                                }
//                            }
//                        }
//                        deleteMain(details);
//                        initList();
                    }
                });
                delete.setNegativeButton("取消", null);
                delete.create().show();
                break;
        }
    }
    //删除相应的表头信息
    private void deleteMain(List<T_Detail> listsss){
        for (int j = 0; j < list.size(); j++) {//这里要以列表数据为基础，不能以过滤出来的数据
            if (isCheck.get(j)) {
                PushDownSubDao pushDownSubDao = daoSession.getPushDownSubDao();
                List<PushDownSub> pushDownSubs = pushDownSubDao.queryBuilder().where(
                        PushDownSubDao.Properties.FEntryID.eq(list.get(j).FEntryID)
                ).build().list();
                Lg.e(pushDownSubs.size() + "多少个");
                if (pushDownSubs.size() > 0) {
                    Lg.e("存在明细：-数量："+list.get(j).FRealQty);
                    //删除后，更新数据里面的已验收数
                    double result = MathUtil.toD(list.get(j).FRealQty);
                    pushDownSubs.get(0).FQtying = MathUtil.doubleSub(MathUtil.toD(pushDownSubs.get(0).FQtying), result) + "";
                    Lg.e("减掉得到：", pushDownSubs.get(0).FQtying);
                    pushDownSubDao.update(pushDownSubs.get(0));
                }
            }
        }


        TreeSet<String> treeSet = new TreeSet<>();
        for (int i = 0; i < listsss.size(); i++) {
            treeSet.add(listsss.get(i).FOrderId+"");
        }
        for (String string:treeSet) {
            List<T_Detail> list1=t_detailDao.queryBuilder().where(
                    T_DetailDao.Properties.FOrderId.eq(string)
            ).build().list();
            //当明细小于等于1时，删除该单据的表头数据
            if (list1.size()<=1){
                t_mainDao.deleteInTx(t_mainDao.queryBuilder().where(
                        T_mainDao.Properties.FOrderId.eq(string)).build().list());
            }
        }
        t_detailDao.deleteInTx(listsss);
        initList();

    }

//    private double getUnitrateSub(PushDownSub pushDownSub) {
//        UnitDao unitDao = daoSession.getUnitDao();
//        List<Unit> units = unitDao.queryBuilder().where(
//                UnitDao.Properties.FMeasureUnitID.eq(pushDownSub.FUnitID)
//        ).build().list();
//        if (units.size() > 0) {
//            return Double.valueOf(units.get(0).FCoefficient);
////            Lg.e("获得明细换算率：" + unitrateSub);
//        } else {
//            return  1;
////            Lg.e("获得明细换算率失败：" + unitrateSub);
//        }
//    }
}
