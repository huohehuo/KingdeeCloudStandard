package com.fangzuo.assist.cloud.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.fangzuo.assist.cloud.ABase.BaseActivity;
import com.fangzuo.assist.cloud.Activity.Crash.App;
import com.fangzuo.assist.cloud.Beans.BackData;
import com.fangzuo.assist.cloud.Beans.CodeCheckBackDataBean;
import com.fangzuo.assist.cloud.Beans.CodeCheckBean;
import com.fangzuo.assist.cloud.Beans.CommonResponse;
import com.fangzuo.assist.cloud.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.cloud.Dao.Org;
import com.fangzuo.assist.cloud.Dao.Product;
import com.fangzuo.assist.cloud.Dao.Storage;
import com.fangzuo.assist.cloud.Dao.Suppliers;
import com.fangzuo.assist.cloud.Dao.T_Detail;
import com.fangzuo.assist.cloud.Dao.T_main;
import com.fangzuo.assist.cloud.Dao.Unit;
import com.fangzuo.assist.cloud.Dao.WaveHouse;
import com.fangzuo.assist.cloud.R;
import com.fangzuo.assist.cloud.RxSerivce.MySubscribe;
import com.fangzuo.assist.cloud.Service.DataService;
import com.fangzuo.assist.cloud.Utils.BasicShareUtil;
import com.fangzuo.assist.cloud.Utils.CommonUtil;
import com.fangzuo.assist.cloud.Utils.Config;
import com.fangzuo.assist.cloud.Utils.DataModel;
import com.fangzuo.assist.cloud.Utils.EventBusInfoCode;
import com.fangzuo.assist.cloud.Utils.Info;
import com.fangzuo.assist.cloud.Utils.Lg;
import com.fangzuo.assist.cloud.Utils.MathUtil;
import com.fangzuo.assist.cloud.Utils.MediaPlayer;
import com.fangzuo.assist.cloud.Utils.Toast;
import com.fangzuo.assist.cloud.Utils.UpLoadModel;
import com.fangzuo.assist.cloud.Utils.WebApi;
import com.fangzuo.assist.cloud.databinding.ActivityProductGetBinding;
import com.fangzuo.assist.cloud.widget.LoadingUtil;
import com.fangzuo.assist.cloud.widget.SpinnerUnit;
import com.fangzuo.assist.cloud.zxing.ScanManager;
import com.fangzuo.greendao.gen.T_DetailDao;
import com.fangzuo.greendao.gen.T_mainDao;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

//import com.fangzuo.assist.zxing.activity.CaptureActivity;

public class ProductGetActivity extends BaseActivity {
    private int activity = Config.ProductGetActivity;
    ActivityProductGetBinding binding;
    private long ordercode;
    private Product product;
    private Suppliers supplier;
    private Storage storage;
    private WaveHouse waveHouse;
    private Unit unit;
    private Org org;
    private CodeCheckBackDataBean codeCheckBackDataBean;
    protected boolean isOpenBatch=false;
    private List<String> listOrder;
    private String autoAuxSing="";
    private String autoActualModel="";
    private String autoStorage="";
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
                Lg.e("获得物料信息：" , product);
                binding.setProduct(product);
                dealProduct();
//                setDATA("", true);
                break;
            case EventBusInfoCode.Supplier:
                supplier = (Suppliers) event.postEvent;
                Lg.e("获得供应商：" + supplier.toString());
                binding.edSupplier.setText(supplier.FName);
//                setDATA("", true);
                break;
            case EventBusInfoCode.Upload_OK://回单成功
                BackData backData = (BackData)event.postEvent;
                if (backData.getResult().getResponseStatus().getIsSuccess()){
                    //获取生成的单号数据
                    for (int i = 0; i < backData.getResult().getResponseStatus().getSuccessEntitys().size(); i++) {
                        listOrder.add(backData.getResult().getResponseStatus().getSuccessEntitys().get(i).getNumber());
                    }
                    final List<T_main> mains = t_mainDao.queryBuilder().where(T_mainDao.Properties.Activity.eq(activity)).build().list();
                    for (int i = 0; i < mains.size(); i++) {
                        final int pos = i;
                        String reString = mains.get(i).FBillerID+"|"+listOrder.get(i)+"|"+mains.get(i).FOrderId+"|"+mains.get(i).IMIE;
                        App.getRService().doIOAction(WebApi.PGUpload, reString, new MySubscribe<CommonResponse>() {
                            @Override
                            public void onNext(CommonResponse commonResponse) {
                                super.onNext(commonResponse);
                                if (!commonResponse.state)return;
                                t_detailDao.deleteInTx(t_detailDao.queryBuilder().where(
                                        T_DetailDao.Properties.Activity.eq(activity),
                                        T_DetailDao.Properties.FOrderId.eq(mains.get(pos).FOrderId)
                                ).build().list());
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                            }
                        });
                    }

//                    t_detailDao.deleteInTx(t_detailDao.queryBuilder().where(
//                            T_DetailDao.Properties.Activity.eq(activity)
//                    ).build().list());
//                    t_mainDao.deleteInTx(t_mainDao.queryBuilder().where(
//                            T_mainDao.Properties.Activity.eq(activity)
//                    ).build().list());
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
            case EventBusInfoCode.Code_Check://条码检测
                LoadingUtil.dismiss();
                codeCheckBackDataBean = (CodeCheckBackDataBean)event.postEvent;
                if (codeCheckBackDataBean.FTip.equals("OK")){
                    binding.edPihao.setText(codeCheckBackDataBean.FBatchNo);
                    binding.edPurchaseNo.setText(codeCheckBackDataBean.FPurchaseNo);
                    binding.edNum.setText(codeCheckBackDataBean.FQty);
                    autoActualModel = codeCheckBackDataBean.FActualmodel;
                    autoAuxSing = codeCheckBackDataBean.FAuxsign;
                    autoStorage = codeCheckBackDataBean.FStockID;
                    LoadingUtil.showDialog(mContext,"正在查找物料信息");
                    DataModel.getProductForNumber(codeCheckBackDataBean.FItemID,org);
                }else{
                    ReSetScan(binding.cbScaning);
                    Toast.showText(mContext,codeCheckBackDataBean.FTip);
                }
                break;
            case EventBusInfoCode.Code_Only_Insert://写入条码唯一临时表
                codeCheckBackDataBean = (CodeCheckBackDataBean)event.postEvent;
                if (codeCheckBackDataBean.FTip.equals("OK")){
                    Addorder();
                }else{
                    ReSetScan(binding.cbScaning);
                    Toast.showText(mContext,codeCheckBackDataBean.FTip);
                }
                break;
        }
    }

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_get);
        ButterKnife.bind(this);
        //摄像头初始化
        mCaptureManager = new ScanManager(this, binding.zxingBarcodeScanner);
        mCaptureManager.initializeFromIntent(getIntent(), savedInstanceState);
    }

    @Override
    protected void initData() {
        listOrder = new ArrayList<>();
        ordercode = CommonUtil.createOrderCode(this);
        binding.tvDate.setText(getTime(true));
        //第一个参数用于保存上一个值，第二个为自动跳转到该默认值
        binding.spOrgCreate.setAutoSelection(getString(R.string.spOrgCreate_pg), Hawk.get(Info.user_org,""));
        binding.spOrgSend.setAutoSelection(getString(R.string.spOrgSend_pg), Hawk.get(Info.user_org,""));
        binding.spOrgHuozhu.setAutoSelection(getString(R.string.spOrgHuozhu_pg), Hawk.get(Info.user_org,""));
        binding.spBuyer.setAuto(getString(R.string.spBuyer_pg), "",org);
        binding.spDepartmentCreate.setAuto(getString(R.string.spDepartmentCreate_pg), "",org,activity);
        binding.spStoreman.setAuto(getString(R.string.spStoreman_pg), "",org);
        binding.spWhichStorage.setAuto("",org);

//        binding.spBuyer.setAutoSelection(getString(R.string.spBuyer_pg), "");
//        binding.spDepartmentCreate.setAutoSelection(getString(R.string.spDepartmentCreate_pg), "");
//        binding.spStoreman.setAutoSelection(getString(R.string.spStoreman_pg), "");
//        binding.spSaleman.setAutoSelection(getString(R.string.spSaleMan_pis), "");
//        binding.spOrgCreate.setEnable(false);
//        binding.spOrgSend.setEnable(false);
        binding.spUnit.setAuto(mContext, "", SpinnerUnit.Id);
        binding.cbIsStorage.setChecked(Hawk.get(Info.Storage+activity,false));
        binding.spAuxsign.setEnabled(false);
        binding.spActualmodel.setEnabled(false);
    }

    @Override
    protected void initListener() {
        binding.edPihao.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == 0 && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    DataModel.getStoreNum(product,storage,binding.edPihao.getText().toString().trim(),mContext,binding.tvStorenum);
                }
                return true;
            }
        });
        binding.spOrgSend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                org = (Org) binding.spOrgCreate.getAdapter().getItem(i);
                Lg.e("组织：",org);
                storage=null;
                binding.spStoreman.setAuto(getString(R.string.spStoreMan_pis),"",org);
                binding.spBuyer.setAuto(getString(R.string.spBuyer_pg), "",org);
                binding.spWhichStorage.setAuto("",org);
                binding.spDepartmentCreate.setAuto(getString(R.string.spDepartmentGet_pis),"",org,activity);
                binding.spOrgSend.setAutoSelection(getString(R.string.spOrgSend_pg), org.FName);
                binding.spOrgHuozhu.setAutoSelection(getString(R.string.spOrgHuozhu_pg), org.FName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spWhichStorage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                isSpStorageDefault = false;
                storage = (Storage) binding.spWhichStorage.getAdapter().getItem(i);
                Lg.e("选中仓库：",storage);
                waveHouse = null;
                binding.spWavehouse.setAuto(mContext, storage, "");
                DataModel.getStoreNum(product,storage,binding.edPihao.getText().toString().trim(),mContext,binding.tvStorenum);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spWavehouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                waveHouse = (WaveHouse) binding.spWavehouse.getAdapter().getItem(i);
                Lg.e("选中仓位：" ,waveHouse);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unit = (Unit) binding.spUnit.getAdapter().getItem(i);
                Lg.e("单位：",unit);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    String barcode="";
    @Override
    protected void OnReceive(String code) {
        barcode = code;
        LoadingUtil.showDialog(mContext,"正在检测条码...");
        //查询条码唯一表
        CodeCheckBean bean = new CodeCheckBean(code);
        DataModel.codeCheck(WebApi.CodeCheckForOut,gson.toJson(bean));
//        DataModel.getProductForScan(code,null);
    }
    //处理物料数据
    private void dealProduct(){
        if (product==null){
            return;
        }
        //带出物料的默认值
        binding.spUnit.setAuto(mContext, product.FPurchaseUnitID, SpinnerUnit.Id);
        if (binding.cbIsStorage.isChecked()){
            binding.spWhichStorage.setAuto(autoStorage,org);
        }
        if (CommonUtil.isOpen(product.FIsBatchManage)){
            isOpenBatch=true;
        }else{
            binding.edPihao.setText("");
            isOpenBatch=false;
        }
        DataModel.getStoreNum(product,storage,binding.edPihao.getText().toString().trim(),mContext,binding.tvStorenum);

        binding.spAuxsign.getData(product.FMASTERID,autoAuxSing);
        binding.spActualmodel.getData(product.FMASTERID,autoActualModel);


        if (binding.isAutoAdd.isChecked()){
            if (!checkBeforeAdd()){
                ReSetScan(binding.cbScaning);
            }
        }else{
            ReSetScan(binding.cbScaning);
        }
    }


    @OnClick({R.id.btn_finishorder, R.id.search_supplier, R.id.scanbyCamera, R.id.search, R.id.ll_show, R.id.ll_content, R.id.btn_add, R.id.btn_backorder, R.id.btn_checkorder, R.id.tv_date, R.id.drawer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_supplier:
                Bundle b = new Bundle();
                b.putString("search", binding.edSupplier.getText().toString());
                b.putInt("where", Info.SEARCHSUPPLIER);
                startNewActivityForResult(ProductSearchActivity.class, R.anim.activity_open, 0, Info.SEARCHFORRESULTPRODUCT, b);
                break;
            case R.id.scanbyCamera:
                mCaptureManager.onResume();
                binding.zxingBarcodeScanner.setVisibility(View.VISIBLE);
                mCaptureManager.decode();
//                Intent in = new Intent(mContext, CaptureActivity.class);
//                startActivityForResult(in, 0);
//                IntentIntegrator intentIntegrator = new IntentIntegrator(this);
                // 设置自定义扫描Activity
//                intentIntegrator.setCaptureActivity(CustomCaptureActivity.class);
//                intentIntegrator.initiateScan();
                break;
            case R.id.search:
                Bundle bundle1 = new Bundle();
                bundle1.putString("search", binding.edCode.getText().toString());
                bundle1.putInt("where", Info.SEARCHPRODUCT);
                bundle1.putString("org", org==null?"":org.FOrgID);
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
                checkBeforeAdd();
                break;
            case R.id.btn_backorder:
                LoadingUtil.showDialog(mContext,"正在回单...");
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
        try{
            if (product==null){
                Toast.showText(mContext,"请选择物料");
                MediaPlayer.getInstance(mContext).error();
                return false;
            }
            if (storage ==null){
                Toast.showText(mContext, "请选择仓库");
                MediaPlayer.getInstance(mContext).error();
                return false;
            }
            Lg.e("仓库check:",storage);
            if (!CommonUtil.isAllowFStore(storage.FallowFStore) && MathUtil.toD(binding.tvStorenum.getText().toString())<MathUtil.toD(binding.edNum.getText().toString())){
                Toast.showText(mContext,"库存不足");
                MediaPlayer.getInstance(mContext).error();
                return false;
            }

            if (isOpenBatch && binding.edPihao.getText().toString().trim().equals("")){
                Toast.showText(mContext,"请输入批号信息");
                MediaPlayer.getInstance(mContext).error();
                return false;
            }//--------------------------------------------------
            if (binding.edNum.getText().toString().trim().equals("") || "0".equals(binding.edNum.getText().toString())){
                Toast.showText(mContext,"请输入数量");
                MediaPlayer.getInstance(mContext).error();
                return false;
            }//--------------------------------------------------
//        if ("".equals(binding.edSupplier.getText().toString())){
//            Toast.showText(mContext,"请选择供应商");
//            MediaPlayer.getInstance(mContext).error();
//            return false;
//        }else if (null==supplier && !"".equals(binding.edSupplier.getText().toString())){//当未选中供应商，但输入框有数据时
//            List<Suppliers> list = GreenDaoManager.getmInstance(mContext).getDaoSession().getSuppliersDao().queryBuilder().where(
//                    SuppliersDao.Properties.FName.eq(binding.edSupplier.getText().toString())
//            ).build().list();
//            if (list.size()>0){
//                supplier = list.get(0);
//            }else{
//                Toast.showText(mContext,"无法找到相对应的供应商信息，请重试");
//                MediaPlayer.getInstance(mContext).error();
//                return false;
//            }
//        }//--------------------------------------------------

            //插入条码唯一临时表
            CodeCheckBean bean = new CodeCheckBean(barcode,ordercode + "", BasicShareUtil.getInstance(mContext).getIMIE());
            DataModel.codeOnlyInsert(WebApi.CodeCheckInsertForOut,gson.toJson(bean));
            return true;
//            Addorder();
        }catch (Exception e){
            return false;
        }
    }

    //添加数据
    private void Addorder() {
        try {
            String num = binding.edNum.getText().toString();
            if (true) {
                Lg.e("合并");
                List<T_Detail> detailhebing = t_detailDao.queryBuilder().where(
                        T_DetailDao.Properties.Activity.eq(activity),
                        T_DetailDao.Properties.FOrderId.eq(ordercode),
                        T_DetailDao.Properties.FMaterialId.eq(product.FMaterialid),
                        T_DetailDao.Properties.FUnitID.eq(unit.FNumber),
                        T_DetailDao.Properties.FBarcode.eq(barcode),
                        T_DetailDao.Properties.FStorageId.eq(storage.FNumber),
                        T_DetailDao.Properties.FWaveHouseId.eq(binding.spWavehouse.getwaveHouseNumber()),
                        T_DetailDao.Properties.FBatch.eq(binding.edPihao.getText().toString())
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
            main.FBillerID = Hawk.get(Info.user_id,"");
            main.FBarcode = barcode;
            main.IMIE = BasicShareUtil.getInstance(mContext).getIMIE();
            main.FOrderId = ordercode;
            main.FIndex = timesecond;
            main.setData(Info.getType(activity),org==null?"":org.FNumber,binding.spOrgCreate.getDataNumber(),binding.spOrgHuozhu.getDataNumber());
            main.FDepartmentNumber = binding.spDepartmentCreate.getDataNumber();
//            main.FPurchaseDeptId = binding.spDepartmentBuy.getDataNumber();
            main.FPurchaserId = binding.spBuyer.getDataNumber();
            main.FStockerNumber = binding.spStoreman.getDataNumber();
            main.FDate = binding.tvDate.getText().toString();
            main.FNot = binding.edNot.getText().toString();
            main.F_FFF_Text = binding.edFfOrder.getText().toString();
            main.setSupplier(supplier);
            long insert1 = t_mainDao.insert(main);


            T_Detail detail = new T_Detail();//--------------------------------明细-----------------
            detail.activity = activity;
            detail.FBillerID = Hawk.get(Info.user_id,"");
            detail.FBarcode = barcode;
            detail.IMIE = BasicShareUtil.getInstance(mContext).getIMIE();
            detail.FOrderId = ordercode;
            detail.FIndex = timesecond;
            detail.FRemainInStockQty = num;
            detail.FRealQty = num;
            detail.FProductNo = binding.edPurchaseNo.getText().toString();
            detail.FBatch = binding.edPihao.getText().toString();
            detail.AuxSign = binding.spAuxsign.getDataNumber();
            detail.ActualModel = binding.spActualmodel.getDataNumber();
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

//            String sss = "{\"Model\":{\"FBillNo\":\"\",\"FBillTypeID\":{\"FNUMBER\":\"RKD01_SYS\"},\"FDate\":\"2018-11-29\",\"FInStockEntry\":[{\"FLot\":{\"FNumber\":\"101\"},\"FMaterialId\":{\"FNumber\":\"02.03.13.120.A.20.1200\"},\"FPriceUnitID\":{\"FNumber\":\"Pcs\"},\"FRealQty\":\"1\",\"FRemainInStockQty\":\"1\",\"FRemainInStockUnitId\":{\"FNumber\":\"Pcs\"},\"FStockId\":{\"FNumber\":\"CK004\"},\"FUnitID\":{\"FNumber\":\"Pcs\"}}],\"FInStockFin\":{\"FPriceTimePoint\":\"\",\"FSettleCurrId\":{\"FNumber\":\"PRE001\"},\"FSettleOrgId\":{\"FNumber\":\"100\"}},\"FOwnerIdHead\":{\"FNumber\":\"100\"},\"FOwnerTypeIdHead\":\"1\",\"FPurchaseOrgId\":{\"FNumber\":\"100\"},\"FStockDeptId\":{\"FNumber\":\"BM000008\"},\"FStockOrgId\":{\"FNumber\":\"100\"},\"FStockerId\":{\"FNumber\":\"00100\"},\"FSupplierId\":{\"FNumber\":\"VEN00002\"}}}";
//            PISBean bean = new PISBean();
//            List<T_Detail> detailList = new ArrayList<>();
//            detailList.add(detail);
//            detailList.add(detail);
//            PISBean.ModelBean modelBean = new PISBean.ModelBean();
//            modelBean.setModelBean(main, detailList);
//            bean.setModel(modelBean);
//            Lg.e("回单数据：" + gson.toJson(bean));
//
//
//            //组合多个单据回单
//            PISBatchBean batchBean = new PISBatchBean();
//            List<PISBean.ModelBean> list = new ArrayList<>();
//            list.add(modelBean);
//            list.add(modelBean);
//            batchBean.setModel(list);
//
//            //业务对象Id
//            String formid = "STK_InStock";
//            JSONArray jsonArray = new JSONArray();
//            jsonArray.put(formid);
//            jsonArray.put(gson.toJson(batchBean));
//            App.CloudService().doIOAction(Config.C_BatcnSave, jsonArray.toString(), new ToSubscribe<BackData>() {
//                @Override
//                public void onNext(BackData backData) {
//                    super.onNext(backData);
//                    if (backData.getResult().getResponseStatus().getIsSuccess()) {
//                        Lg.e("成功：" + gson.toJson(backData));
//                    } else {
//                        Lg.e("失败：" + gson.toJson(backData));
//                    }
//                }
//
//                @Override
//                public void onError(Throwable e) {
//                    super.onError(e);
//                    Lg.e("失败：" + e.toString());
//                }
//            });

        } catch (Exception e) {
            DataService.pushError(mContext, this.getClass().getSimpleName(), e);
        }

    }
    private void resetAll(){
        ReSetScan(binding.cbScaning);
        binding.edFfOrder.setText("");
        listOrder.clear();
        barcode="";
        binding.edPihao.setText("");
        binding.edNum.setText("");
        supplier=null;
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
                share.setOrderCode(ProductGetActivity.this,ordercode);
            }
        });
        ab.setNegativeButton("取消", null);
        ab.create().show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Hawk.put(Info.Storage+activity,binding.cbIsStorage.isChecked());
        mCaptureManager.onDestroy();
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
