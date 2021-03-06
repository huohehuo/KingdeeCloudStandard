package com.fangzuo.assist.cloud.Activity;

import android.app.AlertDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;

import com.fangzuo.assist.cloud.ABase.BaseActivity;
import com.fangzuo.assist.cloud.Adapter.PushDownSubListAdapter;
import com.fangzuo.assist.cloud.Beans.BackData;
import com.fangzuo.assist.cloud.Beans.CommonResponse;
import com.fangzuo.assist.cloud.Beans.DownloadReturnBean;
import com.fangzuo.assist.cloud.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.cloud.Beans.SearchBean;
import com.fangzuo.assist.cloud.Dao.BarCode;
import com.fangzuo.assist.cloud.Dao.Client;
import com.fangzuo.assist.cloud.Dao.Product;
import com.fangzuo.assist.cloud.Dao.PushDownMain;
import com.fangzuo.assist.cloud.Dao.PushDownSub;
import com.fangzuo.assist.cloud.Dao.Storage;
import com.fangzuo.assist.cloud.Dao.Suppliers;
import com.fangzuo.assist.cloud.Dao.T_Detail;
import com.fangzuo.assist.cloud.Dao.T_main;
import com.fangzuo.assist.cloud.Dao.Unit;
import com.fangzuo.assist.cloud.R;
import com.fangzuo.assist.cloud.Service.DataService;
import com.fangzuo.assist.cloud.Utils.Asynchttp;
import com.fangzuo.assist.cloud.Utils.BasicShareUtil;
import com.fangzuo.assist.cloud.Utils.CommonUtil;
import com.fangzuo.assist.cloud.Utils.Config;
import com.fangzuo.assist.cloud.Utils.DoubleUtil;
import com.fangzuo.assist.cloud.Utils.EventBusInfoCode;
import com.fangzuo.assist.cloud.Utils.GreedDaoUtil.GreenDaoManager;
import com.fangzuo.assist.cloud.Utils.Info;
import com.fangzuo.assist.cloud.Utils.Lg;
import com.fangzuo.assist.cloud.Utils.MathUtil;
import com.fangzuo.assist.cloud.Utils.MediaPlayer;
import com.fangzuo.assist.cloud.Utils.Toast;
import com.fangzuo.assist.cloud.Utils.UpLoadModel;
import com.fangzuo.assist.cloud.Utils.WebApi;
import com.fangzuo.assist.cloud.databinding.ActivityPdSaleOut2SaleBackBinding;
import com.fangzuo.assist.cloud.widget.LoadingUtil;
import com.fangzuo.assist.cloud.widget.SpinnerUnit;
import com.fangzuo.assist.cloud.widget.SpinnerWaveHouse;
import com.fangzuo.greendao.gen.BarCodeDao;
import com.fangzuo.greendao.gen.ClientDao;
import com.fangzuo.greendao.gen.ProductDao;
import com.fangzuo.greendao.gen.PushDownMainDao;
import com.fangzuo.greendao.gen.PushDownSubDao;
import com.fangzuo.greendao.gen.T_DetailDao;
import com.fangzuo.greendao.gen.T_mainDao;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PdSaleOut2SaleBackActivity extends BaseActivity {
    ActivityPdSaleOut2SaleBackBinding binding;
    private int activity = Config.PdSaleOut2SaleBackActivity;
    private int tag = 4;
    private long ordercode;
    private Product product;
    private Suppliers supplier;
    private Storage storage;
//    private WaveHouse waveHouse;
    private Unit unit;
    private PushDownSub pushDownSub;
    private List<PushDownSub> container;
    private PushDownMainDao pushDownMainDao;
    private PushDownSubDao pushDownSubDao;
    private ArrayList<String> fidcontainer;
    private PushDownSubListAdapter pushDownSubListAdapter;
    private String default_unitID;
    private ArrayList<String> fidc;
    private boolean hebing = false;
    protected boolean isOpenBatch = false;
    protected PushDownMain pushDownMain;
    private Client client;

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(ClassEvent event) {
        switch (event.Msg) {
            case EventBusInfoCode.Client:
                client = (Client) event.postEvent;
                Lg.e("获得供应商：" + client.toString());
                binding.edClient.setText(client.FName);
                break;
            case EventBusInfoCode.Upload_OK://回单成功
                BackData backData = (BackData)event.postEvent;
                if (backData.getResult().getResponseStatus().getIsSuccess()){
                    t_detailDao.deleteInTx(t_detailDao.queryBuilder().where(
                            T_DetailDao.Properties.Activity.eq(activity)
                    ).build().list());
                    List<T_main> list = t_mainDao.queryBuilder().where(
                            T_mainDao.Properties.Activity.eq(activity)
                    ).build().list();
                    for (int i = 0; i < list.size(); i++) {
                        List<PushDownSub> pushDownSubs = pushDownSubDao.queryBuilder().where(
                                PushDownSubDao.Properties.FBillNo.eq(list.get(i).FBillNo)).build().list();
                        pushDownSubDao.deleteInTx(pushDownSubs);
                        List<PushDownMain> pushDownMains = pushDownMainDao.queryBuilder().where(
                                PushDownMainDao.Properties.FBillNo.eq(list.get(i).FBillNo)).build().list();
                        pushDownMainDao.deleteInTx(pushDownMains);
                    }
                    t_mainDao.deleteInTx(list);
//            btnBackorder.setClickable(true);
                    LoadingUtil.dismiss();
                    Toast.showText(mContext, "上传成功");
                    MediaPlayer.getInstance(mContext).ok();
                    Bundle b = new Bundle();
                    b.putInt("123", tag);
                    startNewActivity(PushDownPagerActivity.class, 0, 0, true, b);
                }else{
                    LoadingUtil.dismiss();
                    List<BackData.ResultBean.ResponseStatusBean.ErrorsBean> errorsBeans = backData.getResult().getResponseStatus().getErrors();
                    StringBuilder builder =new StringBuilder();
                    for (BackData.ResultBean.ResponseStatusBean.ErrorsBean error :errorsBeans) {
                        builder.append(error.getFieldName()+"\n");
                        builder.append(error.getMessage() + "\n");
                    }
                    AlertDialog.Builder delete = new AlertDialog.Builder(mContext);
                    delete.setTitle("回单错误");
                    delete.setMessage(builder.toString());
                    delete.setPositiveButton("确定", null);
                    delete.create().show();
                }
                break;
            case EventBusInfoCode.Upload_Error://回单失败
                String error = (String) event.postEvent;
                Toast.showText(mContext, error);
//                btnBackorder.setClickable(true);
                LoadingUtil.dismiss();
                MediaPlayer.getInstance(mContext).error();
                break;
        }
    }

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pd_sale_out2_sale_back);
        ButterKnife.bind(this);
        setDrawerLeftEdgeSize(this, binding.drawerLayout, 0.2f);//设置抽屉滑动响应范围
    }

    @Override
    protected void initData() {
        ordercode = CommonUtil.createOrderCode(this);
        binding.tvDate.setText(getTime(true));
        binding.tvDateBack.setText(getTime(true));
        //第一个参数用于保存上一个值，第二个为自动跳转到该默认值
        binding.spDepartmentStore.setAutoSelection(getString(R.string.spDepartmentStore_so_bk_pd), "");
        binding.spDepartmentSale.setAutoSelection(getString(R.string.spDepartmentSale_so_bk_pd), "");
        binding.spStoreman.setAutoSelection(getString(R.string.spStoreman_so_bk_pd), "");
        binding.spSaleman.setAutoSelection(getString(R.string.spSaleman_so_bk_pd), "");
        binding.spOrgSale.setAutoSelection(getString(R.string.spOrgSale_so_bk_pd), Hawk.get(Info.user_org, ""));
        binding.spOrgStore.setAutoSelection(getString(R.string.spOrgStore_so_bk_pd), Hawk.get(Info.user_org, ""));
        binding.spOrgSale.setEnable(false);
        binding.spOrgStore.setEnable(false);
        binding.spUnit.setAuto(mContext, "", SpinnerUnit.Id);
        container = new ArrayList<>();
        fidcontainer = getIntent().getExtras().getStringArrayList("fid");
        getList();
        List<PushDownMain> pushDownMains = pushDownMainDao.queryBuilder().where(
                PushDownMainDao.Properties.FBillNo.eq(fidcontainer.get(0))).build().list();
        if (pushDownMains.size() > 0) {
            Lg.e("表头：",pushDownMains.get(0));
            pushDownMain = pushDownMains.get(0);
//            fwanglaiUnit = list1.get(0).FSupplyID;
//            employeeId = list1.get(0).FEmpID;
//            departmentId = list1.get(0).FDeptID;
//            Log.e("employeeId", employeeId == null ? "" : employeeId);
//            Log.e("departmentId", departmentId == null ? "" : departmentId);
//            billNo = list1.get(0).FBillNo;
        }else{
            Toast.showText(mContext,"表头数据获取失败");
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        getList();
    }
    private void getList() {
        container.clear();
        pushDownSubDao = daoSession.getPushDownSubDao();
        pushDownMainDao = daoSession.getPushDownMainDao();
        for (int i = 0; i < fidcontainer.size(); i++) {
            List<PushDownSub> list = pushDownSubDao.queryBuilder().where(
                    PushDownSubDao.Properties.FBillNo.eq(fidcontainer.get(i))).build().list();
            container.addAll(list);
        }
        if (container.size() > 0) {
            pushDownSubListAdapter = new PushDownSubListAdapter(mContext, container);
            binding.lvPushsub.setAdapter(pushDownSubListAdapter);
            pushDownSubListAdapter.notifyDataSetChanged();
        } else {
            Toast.showText(mContext, getString(R.string.find_nothing));
        }
    }

    @Override
    protected void initListener() {
        binding.spStorage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                storage = (Storage) binding.spStorage.getAdapter().getItem(i);
                Lg.e("选中仓库：" + storage.toString());
//                waveHouse = null;
                binding.spWavehouse.setAuto(mContext, storage, "", SpinnerWaveHouse.ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spWavehouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                waveHouse = (WaveHouse) binding.spWavehouse.getAdapter().getItem(i);
//                Lg.e("选中仓位：" + waveHouse.toString());

                Lg.e("仓位变化");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unit = (Unit) binding.spUnit.getAdapter().getItem(i);
                Lg.e("选中单位：" + gson.toJson(unit));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.lvPushsub.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pushDownSub = (PushDownSub) pushDownSubListAdapter.getItem(i);
                Lg.e("点击明细:",pushDownSub);
                if (BasicShareUtil.getInstance(mContext).getIsOL()) {
                    Asynchttp.post(mContext, getBaseUrl() + WebApi.S2Product, gson.toJson(new SearchBean(SearchBean.product_for_id,pushDownSub.FMaterialID)), new Asynchttp.Response() {
                        @Override
                        public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
                            final DownloadReturnBean dBean = new Gson().fromJson(cBean.returnJson, DownloadReturnBean.class);
                            Log.e("product.size", dBean.products.size() + "");
                            if (dBean.products.size() > 0) {
                                product = dBean.products.get(0);
                                Log.e("product.size", product + "");
                                dealProduct();
                            }else{
                                Toast.showText(mContext,"找不到物料数据");
                            }
                        }

                        @Override
                        public void onFailed(String Msg, AsyncHttpClient client) {
                            Toast.showText(mContext, "列表物料:" + Msg);
                        }
                    });
                } else {
                    ProductDao productDao = daoSession.getProductDao();
                    List<Product> products = productDao.queryBuilder().where(ProductDao.Properties.FMaterialid.eq(pushDownSub.FMaterialID)).build().list();
                    if (products.size() > 0) {
                        product = products.get(0);
                        dealProduct();
                    }else{
                        Toast.showText(mContext,"找不到物料数据");
                    }
                }

            }
        });
    }

    @Override
    protected void OnReceive(String code) {
        ScanBarCode(code);
    }

    private void ScanBarCode(String barcode) {
        product = null;
        if (BasicShareUtil.getInstance(mContext).getIsOL()) {
            Asynchttp.post(mContext, getBaseUrl() + WebApi.S2Product, gson.toJson(new SearchBean(SearchBean.product_for_barcode,barcode)), new Asynchttp.Response() {
                @Override
                public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
                    final DownloadReturnBean dBean = new Gson().fromJson(cBean.returnJson, DownloadReturnBean.class);
                    Log.e("product.size", dBean.products.size() + "");
                    if (dBean.products.size() > 0) {
                        product = dBean.products.get(0);
                        Log.e("product.size", product + "");
                        default_unitID = dBean.products.get(0).FProduceUnitID;
                        setProduct(product);
                    }
                }

                @Override
                public void onFailed(String Msg, AsyncHttpClient client) {
                    Toast.showText(mContext, "物料：" + Msg);
                }
            });
        } else {
            ProductDao productDao = daoSession.getProductDao();
            BarCodeDao barCodeDao = daoSession.getBarCodeDao();
            final List<BarCode> barCodes = barCodeDao.queryBuilder().where(BarCodeDao.Properties.FBarCode.eq(barcode)).build().list();
            if (barCodes.size() > 0) {
                List<Product> products = productDao.queryBuilder().where(ProductDao.Properties.FProduceUnitID.eq(barCodes.get(0).FItemID)).build().list();
                if (products != null && products.size() > 0) {
                    product = products.get(0);
                    default_unitID = barCodes.get(0).FUnitID;
                    setProduct(product);
                }
            } else {
                Toast.showText(mContext, "条码不存在");
                MediaPlayer.getInstance(mContext).error();
            }
        }

    }

    private void setProduct(Product product) {
        if (product != null) {
            boolean flag = true;
            for (int j = 0; j < pushDownSubListAdapter.getCount(); j++) {
                PushDownSub pushDownSub1 = (PushDownSub) pushDownSubListAdapter.getItem(j);
                if (product.FMaterialid.equals(pushDownSub1.FMaterialID)) {
                    if (MathUtil.toD(pushDownSub1.FQty) == MathUtil.toD(pushDownSub1.FQtying)) {
                        flag = true;
                        continue;
                    } else {
                        if (!"".equals(default_unitID)) {
                            if (default_unitID.equals(pushDownSub1.FUnitID)) {
                                flag = false;
                                binding.lvPushsub.setSelection(j);
                                binding.lvPushsub.performItemClick(binding.lvPushsub.getChildAt(j), j, binding.lvPushsub.getItemIdAtPosition(j));
                                break;
                            }
                        } else {
                            flag = false;
                            binding.lvPushsub.setSelection(j);
                            binding.lvPushsub.performItemClick(binding.lvPushsub.getChildAt(j), j, binding.lvPushsub.getItemIdAtPosition(j));
                            break;
                        }
                    }

                }
            }

            if (flag) {
                Toast.showText(mContext, getString(R.string.product_nothing));
                MediaPlayer.getInstance(mContext).error();

            }
        } else {
            Toast.showText(mContext, "列表中不存在商品");
        }
    }

    //处理物料数据
    private void dealProduct() {
        if (product == null) {
            return;
        }
        binding.setProduct(product);
        Lg.e("物料数据：",product);
        //带出物料的默认值
        binding.spUnit.setAuto(mContext, product.FPurchaseUnitID, SpinnerUnit.Id);
        binding.spStorage.setAutoSelection("", product.FStockID);
        if (CommonUtil.isOpen(product.FIsBatchManage)) {
            isOpenBatch = true;
        } else {
            binding.edBatchNo.setText("");
            isOpenBatch = false;
        }
    }

    //添加前检测
    private boolean checkBeforeAdd() {
        if (product == null){
            Toast.showText(mContext, "物料数据为空");
            MediaPlayer.getInstance(mContext).error();
            return false;
        }
        if (isOpenBatch && binding.edBatchNo.getText().toString().trim().equals("")) {
            Toast.showText(mContext, "请输入批号信息");
            MediaPlayer.getInstance(mContext).error();
            return false;
        }//--------------------------------------------------
        if (binding.edNum.getText().toString().trim().equals("") || "0".equals(binding.edNum.getText().toString())) {
            Toast.showText(mContext, "请输入数量");
            MediaPlayer.getInstance(mContext).error();
            return false;
        }
        if (MathUtil.toD(pushDownSub.FQty) < ((MathUtil.toD(binding.edNum.getText().toString())) + MathUtil.toD(pushDownSub.FQtying))) {
            MediaPlayer.getInstance(mContext).error();
            Toast.showText(mContext, "大兄弟,您的数量超过我的想象");
            return false;
        }
        //--------------------------------------------------
        if ("".equals(binding.edClient.getText().toString())){
            binding.drawerLayout.openDrawer(Gravity.RIGHT);
            Toast.showText(mContext,"请选择客户");
            MediaPlayer.getInstance(mContext).error();
            return false;
        }else if (null==client && !"".equals(binding.edClient.getText().toString())){//当未选中供应商，但输入框有数据时
            List<Client> list = GreenDaoManager.getmInstance(mContext).getDaoSession().getClientDao().queryBuilder().where(
                    ClientDao.Properties.FName.eq(binding.edClient.getText().toString())
            ).build().list();
            if (list.size()>0){
                client = list.get(0);
            }else{
                Toast.showText(mContext,"无法找到相对应的供应商信息，请重试");
                MediaPlayer.getInstance(mContext).error();
                return false;
            }
        }//--------------------------------------------------

        return true;
    }

    //添加数据
    private void Addorder() {
        try {
            if (!checkBeforeAdd()) {
                return;
            }
            String num = binding.edNum.getText().toString();
            if (binding.ishebing.isChecked()) {
                Lg.e("合并");
                List<T_Detail> detailhebing = t_detailDao.queryBuilder().where(
                        T_DetailDao.Properties.Activity.eq(activity),
                        T_DetailDao.Properties.FOrderId.eq(ordercode),
                        T_DetailDao.Properties.FMaterialId.eq(product.FNumber),
                        T_DetailDao.Properties.FUnitID.eq(unit.FNumber),
                        T_DetailDao.Properties.FStorageId.eq(storage.FNumber),
                        T_DetailDao.Properties.FWaveHouseId.eq(binding.spWavehouse.getWaveHouseNumber()),
                        T_DetailDao.Properties.FBatch.eq(binding.edBatchNo.getText().toString())
                ).build().list();
                if (detailhebing.size() > 0) {
                    Lg.e("合并：" + detailhebing.size() + "--" + detailhebing.get(0).toString());
                    for (int i = 0; i < detailhebing.size(); i++) {
                        num = (MathUtil.toD(num) + MathUtil.toD(detailhebing.get(i).FRealQty)) + "";
                        t_detailDao.delete(detailhebing.get(i));
                    }
                }
            }
            t_mainDao.deleteInTx(t_mainDao.queryBuilder().where(
                    T_mainDao.Properties.FOrderId.eq(ordercode)
            ).build().list());
            String timesecond = getTimesecond();
            T_main main = new T_main();//--------------------------------------表头-----------------
            main.activity = activity;
            main.FOrderId = ordercode;
            main.FSoorDerno = pushDownMain.FBillNo;
            main.FIndex = timesecond;
            main.FBillNo = pushDownMain.FBillNo;
            main.setData(Info.getType(activity), binding.spOrgSale.getDataNumber(), binding.spOrgStore.getDataNumber());
            main.FPurchaseDeptId = binding.spDepartmentSale.getDataNumber();
            main.FDepartmentNumber = binding.spDepartmentStore.getDataNumber();
            main.FPurchaserId = binding.spSaleman.getDataNumber();
            main.FStockerNumber = binding.spStoreman.getDataNumber();
            main.FDate = binding.tvDate.getText().toString();
            main.setClient(client);
            long insert1 = t_mainDao.insert(main);

            T_Detail detail = new T_Detail();//--------------------------------明细-----------------
            detail.activity = activity;
            detail.FOrderId = ordercode;
            detail.FIndex = timesecond;
            detail.FEntryID = pushDownSub.FEntryID;
            detail.FID = pushDownSub.FID;
            detail.FSOEntryId = pushDownSub.FEntryID;
            detail.FRemainInStockQty = pushDownSub.FQty;
            detail.FRealQty = num;
            detail.FIsFree = true;
            detail.FBatch = binding.edBatchNo.getText().toString();
            detail.FBackType = "";
            detail.FBackDate = binding.tvDateBack.getText().toString();
            detail.setProduct(product);
            detail.setStorage(storage);
            detail.setWaveHouse(binding.spWavehouse.getwaveHouseObject());
            detail.setUnit(unit);
            long insert2 = t_detailDao.insert(detail);

            if (insert1 > 0 && insert2 > 0) {
                pushDownSub.FQtying = DoubleUtil.sum(MathUtil.toD(pushDownSub.FQtying),
                        (MathUtil.toD(binding.edNum.getText().toString()) )) + "";
                pushDownSubDao.update(pushDownSub);
                pushDownSubListAdapter.notifyDataSetChanged();
                Lg.e("成功添加：" + main.toString());
                Lg.e("成功添加：" + detail.toString());
                MediaPlayer.getInstance(mContext).ok();
                Toast.showText(mContext, "添加成功");
                resetAll();
            } else {
                MediaPlayer.getInstance(mContext).error();
                Toast.showText(mContext, "添加失败，请重试");
            }

        } catch (Exception e) {
            DataService.pushError(mContext, this.getClass().getSimpleName(), e);
        }

    }


    private void resetAll() {
        binding.edBatchNo.setText("");
        binding.edNum.setText("");
        supplier = null;
        product = null;
        pushDownSub = null;
        binding.setProduct(new Product());

    }

    @OnClick({R.id.btn_add, R.id.tv_date, R.id.btn_backorder, R.id.btn_checkorder,R.id.tv_date_back,R.id.search_client})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                Addorder();
                break;
            case R.id.btn_backorder:
                UpLoadModel.action(mContext,activity);
                break;
            case R.id.btn_checkorder:
                Bundle bundle = new Bundle();
                bundle.putInt("activity", activity);
                startNewActivity(ReViewPDActivity.class,
                        R.anim.activity_fade_in, R.anim.activity_fade_out, false, bundle);
                break;
            case R.id.search_client:
                Bundle b = new Bundle();
                b.putString("search", binding.edClient.getText().toString());
                b.putInt("where", Info.SEARCHCLIENT);
                startNewActivityForResult(ProductSearchActivity.class, R.anim.activity_open, 0, Info.SEARCHFORRESULTPRODUCT, b);
                break;
            case R.id.tv_date:
                datePicker(binding.tvDate);
                break;
            case R.id.tv_date_back:
                datePicker(binding.tvDateBack);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Bundle b = new Bundle();
            b.putInt("123", tag);
            startNewActivity(PushDownPagerActivity.class, 0, 0, true, b);
            super.onBackPressed();
        }
    }
}
