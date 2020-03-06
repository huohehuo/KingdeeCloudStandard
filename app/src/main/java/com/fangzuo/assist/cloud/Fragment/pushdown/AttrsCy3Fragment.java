package com.fangzuo.assist.cloud.Fragment.pushdown;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.device.ScanDevice;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fangzuo.assist.cloud.ABase.BaseFragment;
import com.fangzuo.assist.cloud.Activity.CheckCy2Activity;
import com.fangzuo.assist.cloud.Activity.Crash.App;
import com.fangzuo.assist.cloud.Activity.PagerForActivity;
import com.fangzuo.assist.cloud.Activity.ProductCheck4P2Activity;
import com.fangzuo.assist.cloud.Activity.ReViewPDAP2_33_Cy2Activity;
import com.fangzuo.assist.cloud.Adapter.NumRvAdapter;
import com.fangzuo.assist.cloud.Adapter.NumRyAdapter;
import com.fangzuo.assist.cloud.Adapter.PushDownListAdapter;
import com.fangzuo.assist.cloud.Beans.BackData;
import com.fangzuo.assist.cloud.Beans.CodeCheckBackDataBean;
import com.fangzuo.assist.cloud.Beans.CodeCheckBean;
import com.fangzuo.assist.cloud.Beans.CommonResponse;
import com.fangzuo.assist.cloud.Beans.DownloadReturnBean;
import com.fangzuo.assist.cloud.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.cloud.Beans.PrintHistory;
import com.fangzuo.assist.cloud.Beans.PushDownListReturnBean;
import com.fangzuo.assist.cloud.Dao.Product;
import com.fangzuo.assist.cloud.Dao.PushDownMain;
import com.fangzuo.assist.cloud.Dao.Storage;
import com.fangzuo.assist.cloud.Dao.T_Detail;
import com.fangzuo.assist.cloud.Dao.T_main;
import com.fangzuo.assist.cloud.Dao.TempDetil;
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
import com.fangzuo.assist.cloud.Utils.EventBusUtil;
import com.fangzuo.assist.cloud.Utils.GreedDaoUtil.GreenDaoManager;
import com.fangzuo.assist.cloud.Utils.Info;
import com.fangzuo.assist.cloud.Utils.Lg;
import com.fangzuo.assist.cloud.Utils.LocDataUtil;
import com.fangzuo.assist.cloud.Utils.MathUtil;
import com.fangzuo.assist.cloud.Utils.MediaPlayer;
import com.fangzuo.assist.cloud.Utils.ShareUtil;
import com.fangzuo.assist.cloud.Utils.Toast;
import com.fangzuo.assist.cloud.Utils.UpLoadModel;
import com.fangzuo.assist.cloud.Utils.VibratorUtil;
import com.fangzuo.assist.cloud.Utils.WebApi;
import com.fangzuo.assist.cloud.widget.LoadingUtil;
import com.fangzuo.assist.cloud.widget.RecyclerViewDivider;
import com.fangzuo.greendao.gen.DaoSession;
import com.fangzuo.greendao.gen.T_DetailDao;
import com.fangzuo.greendao.gen.T_mainDao;
import com.fangzuo.greendao.gen.TempDetilDao;
import com.google.gson.Gson;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.orhanobut.hawk.Hawk;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * <p>
 * //下载单据信息Fragment（所属：PushDownPagerActivity);
 */
public class AttrsCy3Fragment extends BaseFragment {


    @BindView(R.id.rv_numChoose)
    EasyRecyclerView rvNumChoose;
    @BindView(R.id.sp_lenght)
    Spinner spLenght;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.ed_pihao)
    TextView edPihao;
    @BindView(R.id.ed_allnum)
    TextView tvNum;
    @BindView(R.id.ed_num)
    EditText edNum;

    //    @BindView(R.id.tv_add_num)
//    TextView tvAddNum;
    Unbinder unbinder;
    @BindView(R.id.btn_choose)
    Button btnChoose;
    @BindView(R.id.btn_detail)
    Button btnDetail;
    //    private int tag;
    private PagerForActivity activityPager;
    private int activity;
    //    private int activityWg;
    private String billNo;
    private String interId;
    private String enterId;
    //    private AttrsPagerActivity attrsPagerActivity;
    private FragmentActivity mContext;
    private ArrayList<Boolean> isCheck;
    private PushDownListAdapter pushDownListAdapter;
    //    private ArrayList<PushDownMain> downloadIDs;            //用于listview选择时，添加临时对象
    private PushDownListReturnBean puBean;
    private DaoSession daosession;
    private ArrayList<PushDownMain> container;
    private ScanDevice sm;
    private Intent intent;
    private String TAG = "DownLoadPushFragment";
    private T_DetailDao t_detailDao;
    private DaoSession daoSession;
    private T_mainDao t_mainDao;
    private ArrayAdapter<String> lenghtAdapter;
    private String lenght;
    private String wide;
    private String numing;
    private ArrayList<String> container_Width;
    private NumRyAdapter numRvAdapter;
    private GridLayoutManager gridLayoutManager;
    private TempDetilDao tempDetilDao;
    private CodeCheckBackDataBean codeCheckBackDataBean;
    private Product product;
    private List<String> listOrder;
    private long ordercode;
    private ShareUtil share;
    private Gson gson;
    private Unit unit;
    private String batch = "";
    private String baseNum = "";
    private String storeNum = "";
    private String barcode = "";
    private String auxNum = "";

    private Storage storage;
    private WaveHouse waveHouse;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveEvent(ClassEvent event) {
        switch (event.Msg) {
            case EventBusInfoCode.Product:
                product = (Product) event.postEvent;
                Lg.e("获得物料信息：", product);
                Hawk.put("WgDryingInStoreCy2Activity"+activity,product);//用于下次进入时，获取上一次物料信息
//                binding.setProduct(product);
                dealProduct();
                break;
            case EventBusInfoCode.AutoAdd433:
                List<TempDetil> list = tempDetilDao.queryBuilder().where(
                        TempDetilDao.Properties.FAccountID.eq(CommonUtil.getAccountID()),
                        TempDetilDao.Properties.Activity.eq(activity)
                ).build().list();
                Double res = 0.0;
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        res += MathUtil.toD(list.get(i).FRealQty);
                    }
                }
                checkBeforeAdd(res + "");
                break;
            case EventBusInfoCode.AutoAdd433Ok:
                tempDetilDao.deleteInTx(tempDetilDao.queryBuilder().where(
                        TempDetilDao.Properties.FAccountID.eq(CommonUtil.getAccountID()),
                        TempDetilDao.Properties.Activity.eq(activity)
                ).build().list());
                Hawk.put("WgDryingInStoreCy2Activity"+activity,null);

                break;
            case EventBusInfoCode.Upload_OK://回单成功
                BackData backData = (BackData) event.postEvent;
                if (backData.getResult().getResponseStatus().getIsSuccess()) {
                    //获取生成的单号数据
                    for (int i = 0; i < backData.getResult().getResponseStatus().getSuccessEntitys().size(); i++) {
                        listOrder.add(backData.getResult().getResponseStatus().getSuccessEntitys().get(i).getNumber());
                    }
                    final List<T_main> mains = t_mainDao.queryBuilder().where(T_mainDao.Properties.Activity.eq(activity)).build().list();
                    for (int i = 0; i < mains.size(); i++) {
                        final int pos = i;
                        String reString = mains.get(i).FBillerID + "|" + listOrder.get(i) + "|" + mains.get(i).FOrderId + "|" + mains.get(i).IMIE;
                        App.getRService().doIOAction(WebApi.PrISUpload, reString, new MySubscribe<CommonResponse>() {
                            @Override
                            public void onNext(CommonResponse commonResponse) {
                                super.onNext(commonResponse);
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
                    ordercode++;
                    share.setOrderCode(activityPager.getActivity(), ordercode);
                    MediaPlayer.getInstance(mContext).ok();
                    EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Lock_Main, Config.Lock + "NO"));//上传成功，解锁表头
                    Toast.showText(mContext, "上传成功");
//                btnBackorder.setClickable(true);
                    LoadingUtil.dismiss();
                    DataModel.submitAndAudit(mContext, Config.ProductInStoreActivity, listOrder.get(0));
                } else {
                    LoadingUtil.dismiss();
                    List<BackData.ResultBean.ResponseStatusBean.ErrorsBean> errorsBeans = backData.getResult().getResponseStatus().getErrors();
                    StringBuilder builder = new StringBuilder();
                    for (BackData.ResultBean.ResponseStatusBean.ErrorsBean error : errorsBeans) {
                        builder.append(error.getFieldName() + "\n");
                        builder.append(error.getMessage() + "\n");
                    }
                    AlertDialog.Builder delete = new AlertDialog.Builder(activityPager);
                    delete.setTitle("上传错误");
                    delete.setMessage(builder.toString());
                    delete.setPositiveButton("确定", null);
//                    delete.setNegativeButton("反馈信息", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            DataService.pushBackJson(mContext, FragmentPris4P2Detail.this.getClass().getSimpleName(), Hawk.get(Config.Company,""));
//                        }
//                    });
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
//            case EventBusInfoCode.Code_Check://条码检测
//                LoadingUtil.dismiss();
//                codeCheckBackDataBean = (CodeCheckBackDataBean)event.postEvent;
//                if (codeCheckBackDataBean.FTip.equals("OK")){
//                    binding.edPihao.setText(codeCheckBackDataBean.FBatchNo);
//                    binding.edNum.setText(codeCheckBackDataBean.FQty);
//                    LoadingUtil.showDialog(mContext,"正在查找物料信息");
//                    DataModel.getProductForNumber(codeCheckBackDataBean.FItemID,org);
//                }else{
//                    ReSetScan(binding.cbScaning);
//                    Toast.showText(mContext,codeCheckBackDataBean.FTip);
//                }
//                break;
            case EventBusInfoCode.Code_Only_Insert://写入条码唯一临时表
                codeCheckBackDataBean = (CodeCheckBackDataBean) event.postEvent;
                if (codeCheckBackDataBean.FTip.equals("OK")) {
                    Addorder();
                } else {
                    LoadingUtil.dismiss();
                    Toast.showText(mContext, codeCheckBackDataBean.FTip);
                }
                break;
            case EventBusInfoCode.UpdataView://由表头的数据决定是否更新明细数据
                if (null != activityPager) {
//                    spWhichStorage.setAuto("", activityPager.getOrgOut());
//                    spDepartmentGet.setAuto(getString(R.string.spDepartmentGet_pis), "", activityPager.getOrgOut(), activityPager.getActivity());
//                    spUnit.setAuto("", activityPager.getOrgOut(), SpinnerUnit.Id);
//                    spUnitJiben.setAuto("", SpinnerUnit.Id);
//                    spUnitStore.setAuto("", SpinnerUnit.Id);
//                    spUnitAux.setAuto("", activityPager.getOrgOut(), SpinnerUnit.Id);
                }
                break;


        }
    }

    @Override
    protected void initView() {
        isCheck = new ArrayList<>();
        activity = activityPager.getActivity();
//        downloadIDs = new ArrayList<>();
        daosession = GreenDaoManager.getmInstance(mContext).getDaoSession();

        t_mainDao = daosession.getT_mainDao();
        t_detailDao = daosession.getT_DetailDao();
        tempDetilDao = daosession.getTempDetilDao();
        daoSession = activityPager.getDaoSession();

        share = activityPager.getShare();
        gson = activityPager.getGson();
        ordercode = CommonUtil.createOrderCode(activityPager.getActivity());//单据编号


//        List<PushDownSub> list = pushDownSubDao.queryBuilder().where(
//                PushDownSubDao.Properties.FAccountID.eq(CommonUtil.getAccountID()),
//                PushDownSubDao.Properties.FBillNo.eq(billNo),
//                PushDownSubDao.Properties.FID.eq(interId),
//                PushDownSubDao.Properties.FEntryID.eq(enterId)
//        ).build().list();
//        Lg.e("定位数据", list);
//        if (list.size() > 0) {
//            pushDownSub = list.get(0);
//            numing = MathUtil.toD(pushDownSub.FQtying)+"";
//            tvName.setText("物料名称："+pushDownSub.FName);
//            tvNum.setText("数量："+MathUtil.D2saveInt(MathUtil.toD(pushDownSub.FQty)));
//            tvNuming.setText("已验数量："+MathUtil.D2saveInt(MathUtil.toD(pushDownSub.FQtying)));
//            tvPihao.setText("批号："+pushDownSub.FBatchNo);
//        } else {
//            Toast.showText(mContext, "查验数据有误，请重新选择");
//        }
    }

    @Override
    protected void OnReceive(String barCode) {
    }

    @Override
    protected void initData() {
        gridLayoutManager = new GridLayoutManager(mContext, 5);
        container_Width = new ArrayList<>();
        listOrder = new ArrayList<>();
        ;
        rvNumChoose.setAdapter(numRvAdapter = new NumRyAdapter(mContext));
        rvNumChoose.setLayoutManager(new GridLayoutManager(mContext,5));
//        rvNumChoose.addItemDecoration(new RecyclerViewDivider(3));
//        rvNumChoose.setLayoutManager(gridLayoutManager);
//        int max = Hawk.get(Config.Jingji_Max,58);
        int num = 2;
        for (int i = 0; i < 24; i++) {
            container_Width.add(num + "");
            num += 1;
//            if (i % 2 == 0) container_Width.add(i + "");
        }
        numRvAdapter.addAll(container_Width);
        numRvAdapter.notifyDataSetChanged();
//        gridLayoutManager.scrollToPosition(24);

        //处理长度Spinner---------------------------------------------------
        String[] lenghts = new String[17];
        int numl = 4;
        for (int i = 0; i < lenghts.length; i++) {
            lenghts[i] = numl + "";
            numl++;
        }
        lenghtAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lenghts);
        spLenght.setAdapter(lenghtAdapter);
        //处理长度Spinner---------------------------------------------------END

        uploadProgress();

    }

    @Override
    protected void initListener() {
        spLenght.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                lenght = lenghtAdapter.getItem(i);
            }
        });
        numRvAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //                edDiameter.setText(numRvAdapter.getItem(position));
                VibratorUtil.Vibrate(mContext, Info.VibratorTime);
                wide = numRvAdapter.getItem(position);
                Lg.e("点击"+numRvAdapter.getAllData().get(position));
                numRvAdapter.setColor(numRvAdapter.getAllData().get(position));
            }
        });
//        numRvAdapter.setOnItemClickListener(new NumRvAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, final int position) {
//
//
//
//            }
//        });
    }

    //处理物料数据
    private void dealProduct() {
        if (product == null) {
            return;
        }
        tvName.setText(product.FName);
        tvCode.setText(product.FNumber);
        //带出物料的默认值
//        spUnitJiben.setAuto(product.FBaseUnitID, SpinnerUnit.Id);
//        spUnitStore.setAuto(product.FStoreUnitID, SpinnerUnit.Id);
        unit = LocDataUtil.getUnit(product.FProduceUnitID);
//        Lg.e("得到单位：", unit);
//        spUnit.setAuto(product.FPurchaseUnitID, activityPager.getOrgOut(), SpinnerUnit.Id);
//        spUnitAux.setAuto(product.FAuxUnitID, activityPager.getOrgOut(), SpinnerUnit.Id);
//        if (activityPager.isStorage()) {
        Lg.e("更新仓库");
//            spWhichStorage.setAutoSelection("", product.FStockID);
//            spWhichStorage.setAuto(product.FStockID, activityPager.getOrgOut());
//        EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.UpdataStorage, product.FStockID));

//        }
//        if (CommonUtil.isOpen(product.FIsBatchManage)) {
//            isOpenBatch = true;
//            edPihao.setEnabled(true);
//        } else {
//            edPihao.setEnabled(false);
//            edPihao.setText("");
//            isOpenBatch = false;
//        }


        //自动添加
        if (activityPager.getIsAuto().isChecked()) {
//            checkMainDlg();
//            if (!checkBeforeAdd()) {
//                ReSetScan(cbScaning);
//            }
        } else {
//            ReSetScan(cbScaning);
        }
    }


    //点击事件
    @OnClick({R.id.btn_download, R.id.btn_search, R.id.btn_add, R.id.btn_choose, R.id.btn_upload, R.id.btn_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_choose:
                ProductCheck4P2Activity.start(activityPager, activity, "", activityPager.getOrgOut(1));
                break;
            case R.id.btn_download:
                EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.AutoAdd433, ""));
//                getActivity().finish();
                break;
            case R.id.btn_add:
                if (MathUtil.toD(wide) <= 0 || MathUtil.toD(lenght) <= 0 || MathUtil.toD(edNum.getText().toString()) <= 0) {
                    Toast.showText(mContext, "长、宽、数量必须大于 0 ");
                } else {
                    AddorderTemp();
                }
                break;
            case R.id.btn_upload:
                new AlertDialog.Builder(mContext)
                        .setTitle("确认上传？")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LoadingUtil.showDialog(mContext, "正在上传...");
                                UpLoadModel.action(mContext, activity);
                            }
                        })
                        .create().show();
                break;
            case R.id.btn_detail:
                CheckCy2Activity.start(activityPager, activity);
                break;
            case R.id.btn_search:
//                attrsPagerActivity.clickToCheckView();
                Bundle bundle = new Bundle();
//                bundle.putString("fid", pushDownMain.FID);
                bundle.putInt("activity", activity);
                startNewActivity(activityPager, ReViewPDAP2_33_Cy2Activity.class,
                        R.anim.activity_fade_in, R.anim.activity_fade_out, false, bundle);
                break;
        }
    }

    private void AddorderTemp() {
        TempDetil tDetailTemp = new TempDetil();
        tDetailTemp.FAccountID = CommonUtil.getAccountID();
        tDetailTemp.FProductName = product.FName;
//        tDetailTemp.FID = pushDownSub.FID;
//        tDetailTemp.FEntryID = pushDownSub.FEntryID;
        tDetailTemp.FBatch = edPihao.getText().toString();
        tDetailTemp.activity = activity;
        tDetailTemp.FLenght = lenght;
        tDetailTemp.FWide = wide;
        tDetailTemp.FNum = edNum.getText().toString();
//        tDetailTemp.FAllNum = pushDownSub.FQty;
        tDetailTemp.FRealQty = MathUtil.getWgBf(lenght, wide, edNum.getText().toString());
        Lg.e("添加数据", tDetailTemp);
        tempDetilDao.insert(tDetailTemp);
        MediaPlayer.getInstance(mContext).ok();
        //表面进度条，实际本地Sub数据没变化

        uploadProgress();
    }

    private void uploadProgress() {
        Double res = 0.0;
        Double resAdd = 0.0;
        List<TempDetil> detailTemps = tempDetilDao.queryBuilder().where(
                TempDetilDao.Properties.FAccountID.eq(CommonUtil.getAccountID()),
//                TempDetilDao.Properties.FID.eq(pushDownSub.FID),
//                TempDetilDao.Properties.FEntryID.eq(pushDownSub.FEntryID),
                TempDetilDao.Properties.Activity.eq(activity)
        ).build().list();
        Lg.e("更新进度条", detailTemps);
        if (detailTemps.size() > 0) {
            for (int i = 0; i < detailTemps.size(); i++) {
                res += MathUtil.toD(detailTemps.get(i).FRealQty);
                resAdd += MathUtil.toD(detailTemps.get(i).FNum);
            }
            btnChoose.setClickable(false);
            btnChoose.setText("请先完成所选物料");
            btnDetail.setText("明细"+"-"+detailTemps.size());
            product = Hawk.get("WgDryingInStoreCy2Activity"+activity,null);
            dealProduct();
        } else {
            btnChoose.setClickable(true);
            btnChoose.setText("选择物料");
            btnDetail.setText("明细");
        }
        tvNum.setText(MathUtil.D2saveInt(res));
//        tvAddNum.setText("添加支数:"+MathUtil.D2saveInt(resAdd));

    }

    private String volem = "0";
    private String volem4Print = "0";//打印时，需要 *0.00236

    //添加前检测
    private boolean checkBeforeAdd(String bcNum) {
        if (product == null) {
            Toast.showText(mContext, "请选择物料");
            MediaPlayer.getInstance(mContext).error();
            return false;
        }
        if (activityPager.getStorage().FName.equals("")) {
            Toast.showText(mContext, "请选择仓库");
            MediaPlayer.getInstance(mContext).error();
            return false;
        }
        if (MathUtil.toD(bcNum) <= 0) {
            Toast.showText(mContext, "数量不能小于0");
            MediaPlayer.getInstance(mContext).error();
            return false;
        }
        volem = bcNum;
        volem4Print = MathUtil.D2save5(MathUtil.toD(bcNum) * 0.00236) + "";
//        if ("".equals(edLenght.getText().toString()) || MathUtil.toD(edLenght.getText().toString()) <= 0) {
//            Toast.showText(mContext, "长度不能小于等于 0 ");
//            MediaPlayer.getInstance(mContext).error();
//            return false;
//        }

//        if (mainStoreOrg.FNumber.equals("")) {
//            Toast.showText(mContext, "采购组织不能为空");
//            MediaPlayer.getInstance(mContext).error();
//            return false;
//        }
        unit = LocDataUtil.getUnit(product.FAuxUnitID);
        if (unit == null || unit.FMeasureUnitID.equals("")) {
            Toast.showText(mContext, "物料单位未带出，请重试...");
            MediaPlayer.getInstance(mContext).error();
            return false;
        }
        Lg.e("单位1111：", unit);
//        if (activityPager.getClient().FName.equals("")) {
//            Toast.showText(mContext, "客户不能为空");
//            MediaPlayer.getInstance(mContext).error();
//            return false;
//        }

//        if (activityPager.getHuozhuOut(0).equals("")) {
//            Toast.showText(mContext, "货主不能为空");
//            MediaPlayer.getInstance(mContext).error();
//            return false;
//        }
//        if (isOpenBatch && edPihao.getText().toString().trim().equals("")) {
//            Toast.showText(mContext, "请输入批号信息");
//            MediaPlayer.getInstance(mContext).error();
//            return false;
//        }
        //--------------------------------------------------
//        Lg.e("明细数据:",pushDownSub);
//        if (MathUtil.toD(pushDownSub.FQty) < ((MathUtil.toD(edNum.getText().toString())) + MathUtil.toD(pushDownSub.FQtying))) {
//            MediaPlayer.getInstance(mContext).error();
//            Toast.showText(mContext, "大兄弟,您的数量超过我的想象");
//            return false;
//        }
//        if (edNum.getText().toString().trim().equals("") || "0".equals(edNum.getText().toString())) {
//            Toast.showText(mContext, "请输入数量");
//            MediaPlayer.getInstance(mContext).error();
//            return false;
//        }//--------------------------------------------------
        //插入条码唯一临时表
//        CodeCheckBean bean = new CodeCheckBean(barcode, ordercode + "", edNum.getText().toString(), BasicShareUtil.getInstance(mContext).getIMIE());
//        DataModel.codeOnlyInsert(WebApi.CodeCheckInsertForOut, gson.toJson(bean));
//        Addorder();

//        volem = MathUtil.getVoleum2(edLenght.getText().toString() + "", edDiameter.getText().toString())+"";
        Lg.e("得到体积" + volem);
        LoadingUtil.showDialog(mContext, "正在获取条码数据...");
        String pdata = "";
        pdata = product.FMaterialid + "|" + unit.FMeasureUnitID + "|" + volem4Print + "|" + "" + "|" + "" + "|" + ""
                + "|" + BasicShareUtil.getInstance(mContext).getIMIE() + "|" + activityPager.getStorage().FNumber + "|" + activityPager.getOrgIn(0) + "|" + edPihao.getText().toString();
        App.getRService().doIOAction(WebApi.PrintData4P2_33_Cy2, pdata, new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                super.onNext(commonResponse);
                if (!commonResponse.state) return;
                DownloadReturnBean dBean = new Gson().fromJson(commonResponse.returnJson, DownloadReturnBean.class);
                if (null != dBean && dBean.printDataBeans.size() > 0) {
                    Lg.e("返回打印数据：", dBean.printDataBeans);
                    barcode = dBean.printDataBeans.get(0).FBarCode;
                    auxNum = dBean.printDataBeans.get(0).FAuxNum;
                    batch = dBean.printDataBeans.get(0).FBatch;
                    baseNum = dBean.printDataBeans.get(0).FBaseNum;
                    storeNum = dBean.printDataBeans.get(0).FStoreNum;
//                    edBasenum.setText(baseNum);
//                    edStorenum.setText(storeNum);
                    //把需要打印的数据保存到本地
                    PrintHistory printHistory = new PrintHistory();
                    printHistory.setProject("2");
                    printHistory.setData33(product, unit, volem4Print, volem4Print,
                            activityPager.getOrgIn().FNote, barcode, batch, CommonUtil.getTime(true), "", "", "");
                    daoSession.getPrintHistoryDao().insert(printHistory);
                    try {
                        CommonUtil.doPrint433(mContext, printHistory, activityPager.getPrintNum());
                    } catch (Exception e) {
                        Lg.e("打印错误", e.getMessage());
                        Toast.showText(mContext, "打印错误" + e.getMessage());
                        App.getInstance().connectPrint();
                    }
                    //-----END
                    CodeCheckBean bean = new CodeCheckBean(barcode, ordercode + "", activityPager.getStorage().FItemID, waveHouse == null ? "" : waveHouse.FSPID, BasicShareUtil.getInstance(mContext).getIMIE());
                    DataModel.codeOnlyInsert(WebApi.CodeCheckInsertForIn, gson.toJson(bean));
                } else {
                    Toast.showText(mContext, "生成条码失败，请重试");
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                LoadingUtil.dismiss();
//                EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Code_Only_Insert,new CodeCheckBackDataBean("找不到条码信息")));
            }
        });

        return true;
    }

    //添加数据
    private void Addorder() {
        try {

//            String num = edNum.getText().toString();
//            if ("".equals(num)||"0".equals(num))return;//避免多次点击，以上请求多次，导致第一次清空之后，再去添加一个空的数据
//            if (true) {
//                Lg.e("合并");
//                List<T_Detail> detailhebing = t_detailDao.queryBuilder().where(
//                        T_DetailDao.Properties.Activity.eq(activity),
//                        T_DetailDao.Properties.FOrderId.eq(ordercode),
//                        T_DetailDao.Properties.FMaterialId.eq(product.FMaterialid),
//                        T_DetailDao.Properties.FUnitID.eq(unit.FNumber),
//                        T_DetailDao.Properties.FBarcode.eq(barcode),
//                        T_DetailDao.Properties.FStorageId.eq(storage.FNumber),
//                        T_DetailDao.Properties.FWaveHouseId.eq(spWavehouse.getwaveHouseNumber()),
//                        T_DetailDao.Properties.FBatch.eq(edPihao.getText().toString())
//                ).build().list();
//                if (detailhebing.size() > 0) {
//                    Lg.e("合并：" + detailhebing.size() + "--" + detailhebing.get(0).toString());
//                    for (int i = 0; i < detailhebing.size(); i++) {
//                        num = (MathUtil.toD(num) + MathUtil.toD(detailhebing.get(i).FRemainInStockQty)) + "";
//                        t_detailDao.delete(detailhebing.get(i));
//                    }
//                }
//            }

            t_mainDao.deleteInTx(t_mainDao.queryBuilder().where(
                    T_mainDao.Properties.FOrderId.eq(ordercode)
            ).build().list());
            String timesecond = CommonUtil.getTimesecond();
            T_main main = new T_main();//--------------------------------------表头-----------------
            main.activity = activity;
            main.FAccountID = CommonUtil.getAccountID();
            main.FBillerID = Hawk.get(Info.user_id, "");
            main.FBarcode = barcode;
            main.IMIE = BasicShareUtil.getInstance(mContext).getIMIE();
            main.FOrderId = ordercode;
//            main.FSoorDerno = pushDownMain.FBillNo;
//            main.FID = pushDownSub.FID;
            main.FIndex = timesecond;
//            main.FBillNo = pushDownMain.FBillNo;
            main.setData(Info.getType(activity), activityPager.getOrgOut(0), activityPager.getOrgIn(0), activityPager.getOrgIn(0));
            main.FDepartmentNumber = activityPager.getDepartMent();
//            main.FPurchaseDeptId = activityPager.getDepartMentBuy();
//            main.FPurchaserId = activityPager.getManSale();
//            main.FSupplierId = pushDownMain.FSupplyID;
//            main.FPurchaseDeptId = mainBuyDept;
//            main.FPurchaserId = mainSaleMan;
            main.FStockerNumber = activityPager.getManStore();
            main.FDate = activityPager.getDate();
            main.FNot = activityPager.getNote();
//            main.FCustomerID = pushDownMain.FSupplyID;
            main.F_FFF_Text = activityPager.getFOrderNo();
//            main.setClient(activityPager.getClient());
            long insert1 = t_mainDao.insert(main);


            T_Detail detail = new T_Detail();//--------------------------------明细-----------------
            detail.activity = activity;
            detail.FAccountID = CommonUtil.getAccountID();
            detail.FBillerID = Hawk.get(Info.user_id, "");
            detail.FBarcode = barcode;
            detail.IMIE = BasicShareUtil.getInstance(mContext).getIMIE();
            detail.FOrderId = ordercode;
            detail.FIndex = timesecond;
//            detail.FEntryID = pushDownSub.FEntryID;
//            detail.FID = pushDownSub.FID;
//            detail.FHuoZhuNumber = scanOfHuozhuNumber;
            detail.FHuoZhuNumber = activityPager.getHuozhuOut(0);
//            detail.FSOEntryId = pushDownSub.FEntryID;
//            detail.FRemainInStockQty = pushDownSub.FQty;
//            detail.FTaxPrice = pushDownSub.FTaxPrice;
            detail.FRealQty = volem;
//            detail.FQuantity = pushDownSub.FQty;
            detail.FWorkShopId1 = activityPager.getDepartMent();
//            detail.FStoreNum = edStorenum.getText().toString();
//            detail.FBaseNum = edBasenum.getText().toString();
//            detail.FBackType = "THLX01_SYS";
            detail.FBackDate = CommonUtil.getTime(true);
//            detail.FIsFree = false;
//            detail.FProductNo = edPurchaseNo.getText().toString();
            detail.FBatch = batch;
//            detail.AuxSign = pushDownSub.AuxSign;
//            detail.ActualModel = pushDownSub.ActualModel;
            detail.setProduct(product);
            detail.setStorage(activityPager.getStorage());
            detail.setWaveHouse(waveHouse);
            detail.setUnit(unit);
//            detail.FLevel = pushDownSub.FLevel;
//            detail.FYmLenght = edLenght.getText().toString();
//            detail.FYmDiameter = edDiameter.getText().toString();
//            detail.FBLenght = pushDownSub.FBLenght;
//            detail.FBWide = pushDownSub.FBWide;
//            detail.FBThick = pushDownSub.FBThick;
            detail.FVolume = volem;
//            detail.setBaseUnit(spUnitJiben.getDataObject());
//            detail.setStoreUnit(spUnitStore.getDataObject());
            long insert2 = t_detailDao.insert(detail);

            if (insert1 > 0 && insert2 > 0) {
                //添加完成，删除查验时的表数据
                EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.AutoAdd433Ok, ""));
                Lg.e("成功添加表头：", main);
                Lg.e("成功添加明细：", detail);
                MediaPlayer.getInstance(mContext).ok();
                Toast.showText(mContext, "添加成功");
                LoadingUtil.dismiss();
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
//        activityPager.ReSetScan(cbScaning);
//        edPurchaseNo.setText("");
        listOrder.clear();
        barcode = "";
        batch = "";
        volem = "0";
        tvNum.setText("");
        tvName.setText("");
        tvCode.setText("");
        edNum.setText("");
        //清除临时表数据
        tempDetilDao.deleteInTx(tempDetilDao.queryBuilder().where(
                TempDetilDao.Properties.FAccountID.eq(CommonUtil.getAccountID()),
                TempDetilDao.Properties.Activity.eq(activity)
        ).build().list());
        Hawk.put("WgDryingInStoreCy2Activity"+activity,null);
//        if (!cbAutoPihao.isChecked()){
//            product = null;
//            pushDownSub = null;
//        }
//        //定位到没有打印的第一条
//        if (null != pushDownSubListAdapter) {
//            for (int i = 0; i < pushDownSubListAdapter.getCount(); i++) {
//                Lg.e("遍历" + i);
//                if (!"1".equals(((PushDownSub) pushDownSubListAdapter.getItem(i)).FIsPrint)) {
//                    lvPushsub.setSelection(i);
//                    break;
//                }
//            }
//        }
        uploadProgress();
        EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Lock_Main, Config.Lock));

    }


    //在oncreateView之前使用 不要使用控件
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (tempDetilDao != null) uploadProgress();
            //相当于Fragment的onResume Lg.e("fragment显示");;
//            }
        } else {
            //相当于Fragment的onPause Lg.e("fragment隐藏");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (tempDetilDao != null) uploadProgress();
    }

    public AttrsCy3Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        activityPager = ((PagerForActivity) activity);
//        tag = ((AttrsPagerActivity) activity).getTitles();
//        activityWg = ((AttrsPagerActivity) activity).getActivity();
//        billNo = ((AttrsPagerActivity) activity).getBillNo();
//        interId = ((AttrsPagerActivity) activity).getInterId();
//        enterId = ((AttrsPagerActivity) activity).getEnterId();
//        attrsPagerActivity = ((AttrsPagerActivity) activity).getAttrsPagerActivity();
//        Log.e("获取到--tag--", tag + "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attrs_cy3, container, false);
        unbinder = ButterKnife.bind(this, view);
        mContext = getActivity();
        EventBusUtil.register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            EventBusUtil.unregister(this);
        } catch (Exception e) {
        }
        unbinder.unbind();
    }


}
