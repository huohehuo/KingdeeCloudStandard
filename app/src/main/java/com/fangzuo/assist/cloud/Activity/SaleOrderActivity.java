package com.fangzuo.assist.cloud.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.fangzuo.assist.cloud.ABase.BaseActivity;
import com.fangzuo.assist.cloud.Beans.BackData;
import com.fangzuo.assist.cloud.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.cloud.Dao.Client;
import com.fangzuo.assist.cloud.Dao.Org;
import com.fangzuo.assist.cloud.Dao.Product;
import com.fangzuo.assist.cloud.Dao.Storage;
import com.fangzuo.assist.cloud.Dao.T_Detail;
import com.fangzuo.assist.cloud.Dao.T_main;
import com.fangzuo.assist.cloud.Dao.Unit;
import com.fangzuo.assist.cloud.Dao.WaveHouse;
import com.fangzuo.assist.cloud.R;
import com.fangzuo.assist.cloud.Service.DataService;
import com.fangzuo.assist.cloud.Utils.CommonUtil;
import com.fangzuo.assist.cloud.Utils.Config;
import com.fangzuo.assist.cloud.Utils.DataModel;
import com.fangzuo.assist.cloud.Utils.EventBusInfoCode;
import com.fangzuo.assist.cloud.Utils.GreedDaoUtil.GreenDaoManager;
import com.fangzuo.assist.cloud.Utils.Info;
import com.fangzuo.assist.cloud.Utils.Lg;
import com.fangzuo.assist.cloud.Utils.MathUtil;
import com.fangzuo.assist.cloud.Utils.MediaPlayer;
import com.fangzuo.assist.cloud.Utils.Toast;
import com.fangzuo.assist.cloud.Utils.UpLoadModel;
import com.fangzuo.assist.cloud.databinding.ActivitySaleOrderBinding;
import com.fangzuo.assist.cloud.widget.LoadingUtil;
import com.fangzuo.assist.cloud.widget.SpinnerUnit;
import com.fangzuo.assist.cloud.zxing.ScanManager;
import com.fangzuo.greendao.gen.ClientDao;
import com.fangzuo.greendao.gen.T_DetailDao;
import com.fangzuo.greendao.gen.T_mainDao;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.orhanobut.hawk.Hawk;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SaleOrderActivity extends BaseActivity {
    private int activity = Config.SaleOrderActivity;
    ActivitySaleOrderBinding binding;
    private long ordercode;
    private Product product;
    private Client client;
    private Storage storage;
    private WaveHouse waveHouse;
    private Unit unit;
    protected boolean isOpenBatch=false;
    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }
    @Override
    protected boolean isScan() {
        return true;
    }
    @Override
    protected void receiveEvent(ClassEvent event) {
        switch (event.Msg) {
            case EventBusInfoCode.ScanResult:
                BarcodeResult res = (BarcodeResult) event.postEvent;
                if (binding.cbScaning.isChecked()){
                }else{
                    mCaptureManager.onPause();
                    binding.zxingBarcodeScanner.setVisibility(View.GONE);
                }
                OnReceive(res.getResult().getText());
//                Toast.showText(mContext, "扫描结果：" + res.getResult().getText());
                break;
            case EventBusInfoCode.Product:
                product = (Product) event.postEvent;
                Lg.e("获得物料信息：" + product.toString());
                binding.setProduct(product);
                dealProduct();
//                setDATA("", true);
                break;
            case EventBusInfoCode.Client:
                client = (Client) event.postEvent;
                Lg.e("获得供应商：" + client.toString());
                binding.edClient.setText(client.FName);
//                setDATA("", true);
                break;
            case EventBusInfoCode.Upload_OK://回单成功
                BackData backData = (BackData)event.postEvent;
                if (backData.getResult().getResponseStatus().getIsSuccess()){
                    t_detailDao.deleteInTx(t_detailDao.queryBuilder().where(
                            T_DetailDao.Properties.Activity.eq(activity)
                    ).build().list());
                    t_mainDao.deleteInTx(t_mainDao.queryBuilder().where(
                            T_mainDao.Properties.Activity.eq(activity)
                    ).build().list());
                    Toast.showText(mContext, "回单成功");
//                btnBackorder.setClickable(true);
                    LoadingUtil.dismiss();
                    MediaPlayer.getInstance(mContext).ok();
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
                String error = (String)event.postEvent;
                Toast.showText(mContext, error);
//                btnBackorder.setClickable(true);
                LoadingUtil.dismiss();
                MediaPlayer.getInstance(mContext).error();
                break;
        }
    }
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sale_order);
        ButterKnife.bind(this);
        //摄像头初始化
        mCaptureManager = new ScanManager(this, binding.zxingBarcodeScanner);
        mCaptureManager.initializeFromIntent(getIntent(), savedInstanceState);
    }

    @Override
    protected void initData() {
        ordercode = CommonUtil.createOrderCode(this);
        binding.tvDate.setText(getTime(true));
        //第一个参数用于保存上一个值，第二个为自动跳转到该默认值
        binding.spOrgSend.setAutoSelection(getString(R.string.spOrgSale_sor), Hawk.get(Info.user_org,""));
//        binding.spOrgSend.setEnable(false);
        binding.spDepartmentSale.setAuto(getString(R.string.spDepartmentGet_pris), Hawk.get(getString(R.string.spDepartmentGet_pris),""), org,activity);
        binding.spSaleman.setAuto(getString(R.string.spSaleman_sor), "",org);
        binding.spUnit.setAuto(mContext, "", SpinnerUnit.Id);
    }

    Org org;
    @Override
    protected void initListener() {
        binding.spOrgSend.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                org =(Org) binding.spOrgSend.getAdapter().getItem(i);
                binding.spDepartmentSale.setAuto(getString(R.string.spDepartmentGet_pris), Hawk.get(getString(R.string.spDepartmentGet_pris),""), org, activity);
                binding.spSaleman.setAuto(getString(R.string.spSaleman_sor), "",org);

            }
        });

//        binding.spWhichStorage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
////                isSpStorageDefault = false;
//                storage = (Storage) binding.spWhichStorage.getAdapter().getItem(i);
//                Lg.e("选中仓库：" + storage.toString());
//                waveHouse = null;
//                binding.spWavehouse.setAuto(mContext, storage, "");
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        binding.spWavehouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                waveHouse = (WaveHouse) binding.spWavehouse.getAdapter().getItem(i);
//                Lg.e("选中仓位：" + waveHouse.toString());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
        binding.spUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unit = (Unit) binding.spUnit.getAdapter().getItem(i);
                Lg.e("选中单位："+gson.toJson(unit));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void OnReceive(String code) {
        DataModel.getProductForScan(code,null);
    }

    //处理物料数据
    private void dealProduct(){
        if (product==null){
            return;
        }
        //带出物料的默认值
        binding.spUnit.setAuto(mContext, product.FPurchaseUnitID, SpinnerUnit.Id);
//        binding.spWhichStorage.setAutoSelection("",product.FStockID);
        if (CommonUtil.isOpen(product.FIsBatchManage)){
            isOpenBatch=true;
        }else{
            binding.edPihao.setText("");
            isOpenBatch=false;
        }
        if (binding.isAutoAdd.isChecked()){
            Addorder();
        }
    }


    @OnClick({R.id.btn_finishorder, R.id.search_client, R.id.scanbyCamera, R.id.search, R.id.ll_show, R.id.ll_content, R.id.btn_add, R.id.btn_backorder, R.id.btn_checkorder, R.id.tv_date, R.id.drawer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_client:
                Bundle b = new Bundle();
                b.putString("search", binding.edClient.getText().toString());
                b.putInt("where", Info.SEARCHCLIENT);
                startNewActivityForResult(ProductSearchActivity.class, R.anim.activity_open, 0, Info.SEARCHFORRESULTPRODUCT, b);
                break;
            case R.id.scanbyCamera:
                mCaptureManager.onResume();
                binding.zxingBarcodeScanner.setVisibility(View.VISIBLE);
                mCaptureManager.decode();
                break;
            case R.id.search:
                Bundle bundle1 = new Bundle();
                bundle1.putString("search", binding.edCode.getText().toString());
                bundle1.putInt("where", Info.SEARCHPRODUCT);
                bundle1.putInt("activity", activity);
                startNewActivityForResult(ProductSearchActivity.class, R.anim.activity_open, 0, Info.SEARCHFORRESULT, bundle1);
                break;
            case R.id.ll_show:
                if (binding.llContent.getVisibility() == View.GONE) {
                    binding.llShow.setText("^^^选填项");
                    binding.llContent.setVisibility(View.VISIBLE);
                } else {
                    binding.llShow.setText(">>>选填项");
                    binding.llContent.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_content:
                break;
            case R.id.btn_add:
                Addorder();
                break;
            case R.id.btn_backorder:
                UpLoadModel.action(mContext,activity);
                break;
            case R.id.btn_finishorder:
                finishOrder();
                break;
            case R.id.btn_checkorder:
                Bundle bundle = new Bundle();
                bundle.putInt("activity", activity);
                startNewActivity(ReViewActivity.class,
                        R.anim.activity_fade_in, R.anim.activity_fade_out, false, bundle);
                break;
            case R.id.tv_date:
                datePicker(binding.tvDate);
                break;
            case R.id.drawer:
                break;
        }
    }

    //添加前检测
    private boolean checkBeforeAdd(){
        if (product==null){
            Toast.showText(mContext,"请选择物料");
            MediaPlayer.getInstance(mContext).error();
            return false;
        }
//        if (isOpenBatch && binding.edPihao.getText().toString().trim().equals("")){
//            Toast.showText(mContext,"请输入批号信息");
//            MediaPlayer.getInstance(mContext).error();
//            return false;
//        }//--------------------------------------------------
        if (binding.edNum.getText().toString().trim().equals("") || "0".equals(binding.edNum.getText().toString())){
            Toast.showText(mContext,"请输入数量");
            MediaPlayer.getInstance(mContext).error();
            return false;
        }//--------------------------------------------------
        if ("".equals(binding.edClient.getText().toString())){
            Toast.showText(mContext,"请选择客户");
            MediaPlayer.getInstance(mContext).error();
            return false;
        }else if (null==client && !"".equals(binding.edClient.getText().toString())){//当未选中客户，但输入框有数据时
            List<Client> list = GreenDaoManager.getmInstance(mContext).getDaoSession().getClientDao().queryBuilder().where(
                    ClientDao.Properties.FName.eq(binding.edClient.getText().toString())
            ).build().list();
            if (list.size()>0){
                client = list.get(0);
                binding.edClient.setText(client.FName);
            }else{
                Toast.showText(mContext,"无法找到相对应的客户信息，请重试");
                MediaPlayer.getInstance(mContext).error();
                return false;
            }
        }//--------------------------------------------------


        return true;
    }

    //添加数据
    private void Addorder() {
        try {
            if (!checkBeforeAdd()){
                return;
            }
            String num = binding.edNum.getText().toString();
            if (binding.ishebing.isChecked()) {
                Lg.e("合并");
                List<T_Detail> detailhebing = t_detailDao.queryBuilder().where(
                        T_DetailDao.Properties.Activity.eq(activity),
                        T_DetailDao.Properties.FOrderId.eq(ordercode),
                        T_DetailDao.Properties.FMaterialId.eq(product.FNumber),
                        T_DetailDao.Properties.FUnitID.eq(unit.FNumber)
//                        T_DetailDao.Properties.FStorageId.eq(storage.FNumber),
//                        T_DetailDao.Properties.FWaveHouseId.eq(binding.spWavehouse.getwaveHouseNumber()),
//                        T_DetailDao.Properties.FBatch.eq(binding.edPihao.getText().toString())
                ).build().list();
                if (detailhebing.size() > 0) {
                    Lg.e("合并："+detailhebing.size()+"--"+detailhebing.get(0).toString());
                    for (int i = 0; i < detailhebing.size(); i++) {
                        num = (MathUtil.toD(num) + MathUtil.toD(detailhebing.get(i).FRemainInStockQty)) + "";
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
            main.FIndex = timesecond;
            main.setData(Info.getType(activity),org.FNumber,org.FNumber);
            main.FDepartmentNumber = binding.spDepartmentSale.getDataNumber();
//            main.FPurchaseDeptId = binding.spDepartmentSend.getDataNumber();
            main.FPurchaserId = binding.spSaleman.getDataNumber();
            main.FDate = binding.tvDate.getText().toString();
            main.setClient(client);
            long insert1 = t_mainDao.insert(main);


            T_Detail detail = new T_Detail();//--------------------------------明细-----------------
            detail.activity = activity;
            detail.FOrderId = ordercode;
            detail.FIndex = timesecond;
            detail.FRemainInStockQty = num;
            detail.FRealQty = num;
            detail.FIsFree = binding.cbIsFree.isChecked();
            detail.FBatch = binding.edPihao.getText().toString();
            detail.setProduct(product);
            detail.setStorage(storage);
            detail.setWaveHouse(waveHouse);
            detail.setUnit(unit);
            long insert2 = t_detailDao.insert(detail);

            if (insert1 > 0 && insert2 > 0){
                Lg.e("成功添加：" + main.toString());
                Lg.e("成功添加：" + detail.toString());
                MediaPlayer.getInstance(mContext).ok();
                Toast.showText(mContext,"添加成功");
                resetAll();
            }else{
                MediaPlayer.getInstance(mContext).error();
                Toast.showText(mContext,"添加失败，请重试");
            }

        } catch (Exception e) {
            DataService.pushError(mContext, this.getClass().getSimpleName(), e);
        }

    }



    private void resetAll(){
        binding.edPihao.setText("");
        binding.edNum.setText("");
        client=null;
        product = null;
        binding.setProduct(new Product());

    }
    //执行完单，PDA单据编号+1
    public void finishOrder() {
        AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
        ab.setTitle("确认使用完单");
        ab.setMessage("确认？");
        ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ordercode++;
                Log.e("ordercode", ordercode + "");
                share.setOrderCode(SaleOrderActivity.this,ordercode);
            }
        });
        ab.setNegativeButton("取消", null);
        ab.create().show();

    }
    /**
     * 权限处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mCaptureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        if (binding.zxingBarcodeScanner.getVisibility()==View.VISIBLE) {
            binding.zxingBarcodeScanner.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }
}
