package com.fangzuo.assist.cloud.Activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.CheckBox;

import com.fangzuo.assist.cloud.ABase.BaseActivity;
import com.fangzuo.assist.cloud.Adapter.StripAdapter;
import com.fangzuo.assist.cloud.Dao.Org;
import com.fangzuo.assist.cloud.Fragment.TabForActivity.FragmentDBDetail;
import com.fangzuo.assist.cloud.Fragment.TabForActivity.FragmentDBMain;
import com.fangzuo.assist.cloud.Fragment.TabForActivity.FragmentPrGetDetail;
import com.fangzuo.assist.cloud.Fragment.TabForActivity.FragmentPrGetMain;
import com.fangzuo.assist.cloud.Fragment.TabForActivity.FragmentPrisDetail;
import com.fangzuo.assist.cloud.Fragment.TabForActivity.FragmentPrisMain;
import com.fangzuo.assist.cloud.R;
import com.fangzuo.assist.cloud.Utils.Config;
import com.fangzuo.assist.cloud.Utils.Lg;
import com.fangzuo.assist.cloud.Utils.ShareUtil;
import com.fangzuo.assist.cloud.databinding.ActivityPagerForBinding;
import com.fangzuo.assist.cloud.zxing.ScanManager;
import com.fangzuo.greendao.gen.DaoSession;
import com.fangzuo.greendao.gen.T_DetailDao;
import com.fangzuo.greendao.gen.T_mainDao;
import com.google.gson.Gson;

import java.util.ArrayList;

public class PagerForActivity extends BaseActivity {
    ActivityPagerForBinding binding;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;
    private int activity;
    private Org orgIn;
    private Org orgOut;
    private Org huozhuIn;
    private Org huozhuOut;
    private String DBType;//调拨类型
    private String DBDirection;//调拨方向
    private String note;
    private String FOrderNo;//业务单号
    private String ManStore;//仓管员
    private String ManSale;//销售员
    private String ManGet;//领料人
    private String DepartMent;//生产车间
    private String date;//日期
    private boolean isStorage=false;//是否带出默认仓库
    StripAdapter stripAdapter;
    @Override
    protected boolean isScan() {
        return true;
    }
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pager_for);
        Intent in = getIntent();
        Bundle b = in.getExtras();
//        String searchString = b.getString("search", "");
        activity = b.getInt("activity");
        Lg.e("获得数据：",""+activity);
    }

    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        titles.add("基本信息");
        titles.add("明细信息");
        setFragment(activity);

    }

    private void setFragment(int activity){
        switch (activity){
            case Config.ProductInStoreActivity:
                binding.topActivity.tvTitle.setText("简单生产入库");
                fragments.add(new FragmentPrisMain());
                fragments.add(new FragmentPrisDetail());
                break;
            case Config.ProductGetActivity:
                binding.topActivity.tvTitle.setText("简单生产领料");
                fragments.add(new FragmentPrGetMain());
                fragments.add(new FragmentPrGetDetail());
                break;
            case Config.DBActivity:
                binding.topActivity.tvTitle.setText("调拨单");
                fragments.add(new FragmentDBMain());
                fragments.add(new FragmentDBDetail());
                break;

        }
        //设置pager
        if (null==stripAdapter){
            stripAdapter = new StripAdapter(getSupportFragmentManager(), fragments, titles);
            binding.viewpager.setAdapter(stripAdapter);
            binding.tabstrip.setShouldExpand(true);
            binding.tabstrip.setViewPager(binding.viewpager);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                binding.tabstrip.setDividerColor(getColor(R.color.cpb_blue));
                binding.tabstrip.setIndicatorColor(getColor(R.color.cpb_blue));
            }else{
                binding.tabstrip.setDividerColor(Color.BLUE);
                binding.tabstrip.setIndicatorColor(Color.BLUE);
            }
            binding.tabstrip.setUnderlineHeight(3);
            binding.tabstrip.setTextSize(45);
            binding.tabstrip.setIndicatorHeight(6);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void OnReceive(String code) {

    }
    public int getActivity(){
        return activity;
    }

    public void setOrgIn(Org orgIn) {
        this.orgIn = orgIn;
    }

    public void setOrgOut(Org orgOut) {
        this.orgOut = orgOut;
    }

    public void setHuozhuIn(Org huozhuIn) {
        this.huozhuIn = huozhuIn;
    }

    public void setHuozhuOut(Org huozhuOut) {
        this.huozhuOut = huozhuOut;
    }

    public void setDBType(String DBType) {
        this.DBType = DBType;
    }

    public void setDBDirection(String DBDirection) {
        this.DBDirection = DBDirection;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFOrderNo() {
        return FOrderNo;
    }

    public void setFOrderNo(String fOrderNo) {
        this.FOrderNo = fOrderNo;
    }

    public void setManStore(String manStore) {
        ManStore = manStore;
    }

    public void setManSale(String manSale) {
        ManSale = manSale;
    }

    public void setManGet(String manGet) {
        ManGet = manGet;
    }

    public void setDepartMent(String departMent) {
        DepartMent = departMent;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStorage(boolean storage) {
        isStorage = storage;
    }

    public String getDBType() {
        return DBType==null?"":DBType;
    }
    public boolean isStorage() {
        return isStorage;
    }
    public String getDBDirection() {
        return DBDirection==null?"":DBDirection;
    }

    public String getNote() {
        return note==null?"":note;
    }

    public String getManStore() {
        return ManStore==null?"":ManStore;
    }

    public String getManSale() {
        return ManSale==null?"":ManSale;
    }

    public String getManGet() {
        return ManGet==null?"":ManGet;
    }

    public String getDepartMent() {
        return DepartMent==null?"":DepartMent;
    }

    public String getDate() {
        return date==null?"":date;
    }

    public CheckBox getIsHebing() {
        return binding.topActivity.ishebing;
    }

    public CheckBox getIsAuto() {
        return binding.topActivity.isAutoAdd;
    }

    public Org getOrgIn() {
        return orgIn;
    }
    public String getOrgIn(int type) {
        if (type==0){
            return orgIn==null?"":orgIn.FNumber;
        }else{
            return orgIn==null?"":orgIn.FOrgID;
        }
    }

    public Org getOrgOut() {
        return orgOut;
    }
    public String getOrgOut(int type) {
        if (type==0){
            return orgOut==null?"":orgOut.FNumber;
        }else{
            return orgOut==null?"":orgOut.FOrgID;
        }
    }

    public Org getHuozhuIn() {
        return huozhuIn;
    }
    public String getHuozhuIn(int type) {
        if (type==0){
            return huozhuIn==null?"":huozhuIn.FNumber;
        }else{
            return huozhuIn==null?"":huozhuIn.FOrgID;
        }
    }

    public Org getHuozhuOut() {
        return huozhuOut;
    }
    public String getHuozhuOut(int type) {
        if (type==0){
            return huozhuOut==null?"":huozhuOut.FNumber;
        }else{
            return huozhuOut==null?"":huozhuOut.FOrgID;
        }
    }

    public T_mainDao getT_mainDao() {
        return t_mainDao;
    }

    public T_DetailDao getT_detailDao() {
        return t_detailDao;
    }
    public DaoSession getDaoSession() {
        return daoSession;
    }
    public Gson getGson() {
        return gson;
    }
    public ShareUtil getShare() {
        return share;
    }
    public ScanManager getScanManager() {
        return mCaptureManager;
    }
    public void setScanManager(ScanManager scanManager) {
        mCaptureManager = scanManager;
    }
    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

    public static void start(Context context, int activity){
        Intent intent = new Intent(context,PagerForActivity.class);
        intent.putExtra("activity",activity);
        context.startActivity(intent);
    }
    /**
     * 权限处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (null!=mCaptureManager)mCaptureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
