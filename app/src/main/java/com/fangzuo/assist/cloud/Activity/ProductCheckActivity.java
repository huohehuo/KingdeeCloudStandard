package com.fangzuo.assist.cloud.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;
import android.widget.AdapterView;

import com.fangzuo.assist.cloud.ABase.BaseActivity;
import com.fangzuo.assist.cloud.Activity.Crash.App;
import com.fangzuo.assist.cloud.Adapter.ProductRyAdapter;
import com.fangzuo.assist.cloud.Beans.CommonResponse;
import com.fangzuo.assist.cloud.Beans.DownloadReturnBean;
import com.fangzuo.assist.cloud.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.cloud.Beans.ProductTreeBeanList;
import com.fangzuo.assist.cloud.Beans.SearchBean;
import com.fangzuo.assist.cloud.Dao.Product;
import com.fangzuo.assist.cloud.R;
import com.fangzuo.assist.cloud.RxSerivce.MySubscribe;
import com.fangzuo.assist.cloud.Utils.Config;
import com.fangzuo.assist.cloud.Utils.EventBusInfoCode;
import com.fangzuo.assist.cloud.Utils.EventBusUtil;
import com.fangzuo.assist.cloud.Utils.Lg;
import com.fangzuo.assist.cloud.Utils.WebApi;
import com.fangzuo.assist.cloud.databinding.ActivityProductCheckBinding;
import com.fangzuo.assist.cloud.widget.LoadingUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ProductCheckActivity extends BaseActivity implements ProductRyAdapter.OnItemClickListener {
    ActivityProductCheckBinding binding;
    private String searchOrg;
    private int activity;
    private String searchString;
    private SearchBean.S2Product s2Product;//用于数据查找...
    private ProductRyAdapter productRyAdapter;
    private ArrayList<Product> products;
    private ArrayList<Product> productsForSearch;
    private ProductTreeBeanList.ProductTreeBean type1,type2,treeType,lv,ply,ht,wt;
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_check);
        binding.toolbar.tvTitle.setText("物料选择");
        Intent in = getIntent();
        Bundle b = in.getExtras();
        searchString = b.getString("search", "");
        searchOrg = b.getString("org", "");
        activity = b.getInt("activity");
        s2Product = new SearchBean.S2Product();
        s2Product.FOrg = searchOrg;
        toFilter(activity);
    }

    @Override
    protected void initData() {
        products = new ArrayList<>();
        productsForSearch = new ArrayList<>();
        productRyAdapter = new ProductRyAdapter(this,products);
        binding.ryProductSearchList.setAdapter(productRyAdapter);
        binding.ryProductSearchList.setItemAnimator(new DefaultItemAnimator());
        binding.ryProductSearchList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        productRyAdapter.setOnItemClickListener(this);
        binding.spType1.setAutoSelection("0");
    }

    @Override
    protected void initListener() {
        binding.spType1.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                type1 = (ProductTreeBeanList.ProductTreeBean) binding.spType1.getAdapter().getItem(i);
                clearOther();
                binding.spType2.setAutoSelection(type1.FID);
            }
        });
        binding.spType2.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                type2 = (ProductTreeBeanList.ProductTreeBean) binding.spType2.getAdapter().getItem(i);
                binding.spTreeType.clear();treeType=null;
                binding.spTreeType.setAutoSelection(type2.FID);
            }
        });
        binding.spTreeType.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                treeType = (ProductTreeBeanList.ProductTreeBean) binding.spTreeType.getAdapter().getItem(i);
                binding.spLv.clear();lv=null;
                binding.spLv.setAutoSelection(treeType.FID);
            }
        });
        binding.spLv.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                lv = (ProductTreeBeanList.ProductTreeBean) binding.spLv.getAdapter().getItem(i);
                binding.spPly.clear();ply=null;
                binding.spPly.setAutoSelection(lv.FID);
            }
        });
        binding.spPly.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                ply = (ProductTreeBeanList.ProductTreeBean) binding.spPly.getAdapter().getItem(i);
                binding.spHt.clear();ht=null;
                binding.spHt.setAutoSelection(ply.FID);
            }
        });
        binding.spHt.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                ht = (ProductTreeBeanList.ProductTreeBean) binding.spHt.getAdapter().getItem(i);
                binding.spWt.clear();wt=null;
                binding.spWt.setAutoSelection(ht.FID);
            }
        });
        binding.spWt.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                wt = (ProductTreeBeanList.ProductTreeBean) binding.spWt.getAdapter().getItem(i);
                Lg.e("选中wt",wt);
                dealProduct(wt.FName);
            }
        });

        binding.btnCheck.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                productRyAdapter.clear();
                products.clear();
                productsForSearch.clear();
                LoadingUtil.showDialog(mContext,"正在查找...");
                getProduct(checkProduct());
            }
        });
    }

    //查找物料
    private void getProduct(String number){
        s2Product.likeOr = number;
        App.getRService().doIOAction(WebApi.ProductSearchForTree, gson.toJson(new SearchBean(SearchBean.product_for_like,gson.toJson(s2Product))), new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                super.onNext(commonResponse);
                binding.pg.setVisibility(View.GONE);
                LoadingUtil.dismiss();
                if (!commonResponse.state)return;
                DownloadReturnBean dBean = new Gson().fromJson(commonResponse.returnJson, DownloadReturnBean.class);
                if (dBean.products.size()>0){
                    if (dBean.products.size()==1){
                        EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Product,dBean.products.get(0)));
                        finish();
                    }else{
                        getWtData(dBean.products);
                        products.addAll(dBean.products);
                        productsForSearch.addAll(dBean.products);
                    }

                }else{
                    binding.spWt.clear();
                    productRyAdapter.clear();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                binding.pg.setVisibility(View.GONE);
            }
        });
    }
    private void dealProduct(String string){
        List<Product> productList = new ArrayList<>();
//        productList.addAll(products);
        Lg.e("333333:",productsForSearch);
//        Lg.e("1111:",productList);
        if (!"".equals(string)){
            for (int i = 0; i < productsForSearch.size(); i++) {
                    Lg.e("1111",productsForSearch.get(i));
                if (string.equals(productsForSearch.get(i).FModel)){
                    Lg.e("22222",productsForSearch.get(i));
                    productList.add(productsForSearch.get(i));
                }
            }
            binding.tvDataNum.setText(productList.size()+"");
            productRyAdapter.addAll(productList);
        }else{
            binding.tvDataNum.setText(productsForSearch.size()+"");
            productRyAdapter.addAll(productsForSearch);
        }
        productRyAdapter.notifyDataSetChanged();
    }

    //添加搜索到的物料规格型号到长度栏
    private void getWtData(List<Product> products){
        binding.spWt.addItems(products);
    }
    //获取FNumber用于物料查询
    private String checkProduct(){
//            if (wt!=null)return wt.FNumber;
            if (ht!=null)return ht.FNumber;
            if (ply!=null)return ply.FNumber;
            if (lv!=null)return lv.FNumber;
            if (treeType!=null)return treeType.FNumber;
            if (type2!=null)return type2.FNumber;
            if (type1!=null)return type1.FNumber;
            return "";
    }
    //清楚
    private void clearOther(){
        binding.spType2.clear();type2=null;
        binding.spTreeType.clear();treeType=null;
        binding.spLv.clear();lv=null;
        binding.spPly.clear();ply=null;
        binding.spHt.clear();ht=null;
        binding.spWt.clear();wt=null;
    }

    //物料点击
    @Override
    public void onProductItemClick(View view, int position) {
        EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Product,products.get(position)));
        finish();
    }

    //根据activity过滤是否物料（是否允许生产，是否允许采购等)
    private void toFilter(int activity){
        switch (activity){
            case Config.PurchaseInStoreActivity://采购入库
                s2Product.FIsPurchase="1";
//                FIsPurchase="1";
                break;
            case Config.ProductInStoreActivity://产品入库
                s2Product.FIsProduce="1";
//                FIsProduce="1";
                break;
            case Config.ProductGetActivity://生产领料
                s2Product.FIsInventory="1";
//                FIsInventory="1";
                break;
            case Config.SaleOutActivity://销售出库
                s2Product.FIsSale="1";
//                FIsSale="1";
                break;
            case Config.OtherInStoreActivity://其他入库
                s2Product.FIsInventory="1";
//                FIsInventory="1";
                break;
            case Config.OtherOutStoreActivity://其他出库
                s2Product.FIsInventory="1";
//                FIsInventory="1";
                break;
            case Config.SaleOrderActivity://销售订单
                s2Product.FIsSale="1";
//                FIsSale="1";
                break;
            case Config.PurchaseOrderActivity://采购订单
                s2Product.FIsPurchase="1";
//                FIsPurchase="1";
                break;
        }
    }

    @Override
    protected void OnReceive(String code) {

    }


}
