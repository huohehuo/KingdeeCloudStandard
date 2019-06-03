package com.fangzuo.assist.cloud.Activity;

import android.app.AlertDialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fangzuo.assist.cloud.ABase.BaseActivity;
import com.fangzuo.assist.cloud.Activity.Crash.App;
import com.fangzuo.assist.cloud.Adapter.PrintHistoryAdapter;
import com.fangzuo.assist.cloud.Beans.BlueToothBean;
import com.fangzuo.assist.cloud.Beans.CommonResponse;
import com.fangzuo.assist.cloud.Beans.DownloadReturnBean;
import com.fangzuo.assist.cloud.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.cloud.Beans.PrintHistory;
import com.fangzuo.assist.cloud.Beans.SearchBean;
import com.fangzuo.assist.cloud.R;
import com.fangzuo.assist.cloud.RxSerivce.MySubscribe;
import com.fangzuo.assist.cloud.Utils.CommonUtil;
import com.fangzuo.assist.cloud.Utils.Config;
import com.fangzuo.assist.cloud.Utils.EventBusInfoCode;
import com.fangzuo.assist.cloud.Utils.EventBusUtil;
import com.fangzuo.assist.cloud.Utils.Lg;
import com.fangzuo.assist.cloud.Utils.LocDataUtil;
import com.fangzuo.assist.cloud.Utils.Toast;
import com.fangzuo.assist.cloud.Utils.WebApi;
import com.fangzuo.assist.cloud.databinding.ActivityPrintHistoryBinding;
import com.fangzuo.assist.cloud.widget.LoadingUtil;
import com.fangzuo.assist.cloud.zxing.ScanManager;
import com.google.gson.Gson;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.orhanobut.hawk.Hawk;

import zpSDK.zpSDK.zpBluetoothPrinter;

/**
 * 查找并打印已打印过的数据
 */
public class PrintHistoryActivity extends BaseActivity {

    private ActivityPrintHistoryBinding binding;
    zpBluetoothPrinter zpSDK;
    private PrintHistoryAdapter adapter;
    BlueToothBean bean;
    private ScanManager mCaptureManager;

    @Override
    protected void receiveEvent(ClassEvent event) {
        switch (event.Msg) {
            case EventBusInfoCode.ScanResult:
                BarcodeResult res = (BarcodeResult) event.postEvent;
                mCaptureManager.onPause();
                binding.zxingBarcodeScanner.setVisibility(View.GONE);
                OnReceive(res.getResult().getText());
//                Toast.showText(mContext, "扫描结果：" + res.getResult().getText());
                break;
            case EventBusInfoCode.Print_Check://回单失败
                String msg = (String) event.postEvent;
                if ("OK".equals(msg)){
                    binding.toolbar.tvRight.setText("打印机就绪");
                    binding.toolbar.tvRight.setTextColor(Color.BLACK);
                }else{
                    binding.toolbar.tvRight.setText("连接打印机错误");
                    binding.toolbar.tvRight.setTextColor(Color.RED);
                }
                LoadingUtil.dismiss();
                break;

        }
    }


    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_print_history);
        zpSDK=new zpBluetoothPrinter(this);
        //摄像头初始化
        mCaptureManager = new ScanManager(PrintHistoryActivity.this, binding.zxingBarcodeScanner);
        mCaptureManager.initializeFromIntent(getIntent(), savedInstanceState);
        binding.toolbar.tvTitle.setText("条码补打");
        binding.ryPrintHistory.setAdapter(adapter = new PrintHistoryAdapter(this));
        binding.ryPrintHistory.setLayoutManager(new LinearLayoutManager(this));
        bean = Hawk.get(Config.OBJ_BLUETOOTH, new BlueToothBean("", ""));
        linkBluePrint();
    }

    @Override
    protected void initData() {
//        PrintHistoryDao printHistoryDao = daoSession.getPrintHistoryDao();
//        List<PrintHistory> list =printHistoryDao.queryBuilder().orderDesc(PrintHistoryDao.Properties.FDate).build().list();
//        adapter.addAll(list);
        setfocus(binding.ivFind);
    }

    @Override
    protected void initListener() {
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Lg.e("点击历史：",adapter.getAllData().get(position));
                showMsg(adapter.getAllData().get(position));
            }
        });
        binding.ivScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.zxingBarcodeScanner.getVisibility()==View.GONE){
                    mCaptureManager.onResume();
                    binding.zxingBarcodeScanner.setVisibility(View.VISIBLE);
                    mCaptureManager.decode();
                }else{
                    binding.zxingBarcodeScanner.setVisibility(View.GONE);
//                    mCaptureManager.onPause();
                }
            }
        });
        binding.ivFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.edPihao.getText().toString().equals("")){
                    getPrintHistory("1",binding.edPihao.getText().toString());
                }else{
                    Toast.showText(mContext,"请输入需要查询的批号");
                }
            }
        });
        binding.edPihao.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == 0 && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (!binding.edPihao.getText().toString().equals("")){
                        getPrintHistory("1",binding.edPihao.getText().toString());
                    }else{
                        Toast.showText(mContext,"请输入需要查询的批号");
                    }                }
                return true;
            }
        });
        binding.toolbar.tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"打印机就绪".equals(binding.toolbar.tvRight.getText().toString())){
                    linkBluePrint();
                }
            }
        });
    }

    /**
     *              通过批号或者条码查找打印数据
     * @param type      1 ：通过批号查找，0：通过条码查找
     * @param string
     */
    private void getPrintHistory(String type,String string){
        LoadingUtil.showDialog(mContext,"正在查找打印数据...");
        SearchBean searchBean = new SearchBean(type,string);
        App.getRService().doIOAction(WebApi.PrintOutCheck, gson.toJson(searchBean), new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                super.onNext(commonResponse);
                LoadingUtil.dismiss();
                if (!commonResponse.state)return;
//                Lg.e("获得打印数据：",commonResponse.returnJson);
                DownloadReturnBean dBean = new Gson().fromJson(commonResponse.returnJson, DownloadReturnBean.class);
                if (dBean.printHistories.size()>0){
                    adapter.clear();
                    adapter.addAll(dBean.printHistories);
//                    showMsg(dBean.printHistories.get(0));
                }else{
                    Toast.showText(mContext,"无数据");
                    adapter.clear();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                adapter.clear();
                LoadingUtil.dismiss();
            }
        });
    }

    //展示打印数据
    private void showMsg(final PrintHistory data){
        if (null==data.FDate || "".equals(data.FDate)){
            data.FDate = getTime(true);
        }

        String huozhuNote= LocDataUtil.getRemark(data.FHuoquan,"number").FNote;
        data.FHuoquan=huozhuNote;
        Lg.e("打印信息：",data);
        AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
        ab.setTitle(R.string.msg_print);
        View v = LayoutInflater.from(mContext).inflate(R.layout.show_print_history, null);
        TextView huoquan     = v.findViewById(R.id.tv_huoquan);
        TextView batch       = v.findViewById(R.id.tv_batch);
        TextView name        = v.findViewById(R.id.tv_name);
        TextView model       = v.findViewById(R.id.tv_model);
        TextView num         = v.findViewById(R.id.tv_num);
        TextView num2        = v.findViewById(R.id.tv_num2);
        TextView note        = v.findViewById(R.id.tv_note);
        TextView wavehouse   = v.findViewById(R.id.tv_wavehouse);
        TextView date        = v.findViewById(R.id.tv_date);
        TextView btn        = v.findViewById(R.id.btn_print);
        huoquan.setText(data.getFHuoquan());
        batch.setText(data.getFBatch());
        name.setText(data.getFName());
        model.setText(data.getFModel());
        num.setText(data.getFNum()+"  "+data.getFUnit());
        num2.setText(data.getFNum2()+"  "+data.getFUnitAux());
        note.setText(data.getFAuxSign());
        wavehouse.setText(data.getFWaveHouse());
        date.setText(data.getFDate());
        ab.setView(v);
        final AlertDialog alertDialog = ab.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             try {
                    CommonUtil.doPrint(zpSDK,data,"1");
                } catch (Exception e) {
//                    e.printStackTrace();
                    LoadingUtil.showAlter(mContext,getString(R.string.error_print),getString(R.string.check_print));
                }
                alertDialog.dismiss();
            }
        });
//                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        getProductOL(dBean, i);
//                        default_unitID = dBean.products.get(i).FUnitID;
//                        chooseUnit(default_unitID);
//                        alertDialog.dismiss();
//                        }
//                        });
    }


    private void linkBluePrint(){
        LoadingUtil.showDialog(mContext,"正在连接打印机...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (bean.address.equals("")){
                    EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Print_Check, "NOOK"));
                }else{
                    if(!zpSDK.connect(bean.address))
                    {
                        EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Print_Check, "NOOK"));
                    }else{
                        EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Print_Check, "OK"));
                    }
                }
            }
        }).start();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            zpSDK.disconnect();
        }catch (Exception e){}
    }
    @Override
    public void onBackPressed() {
        if (binding.zxingBarcodeScanner.getVisibility() == View.VISIBLE) {
            binding.zxingBarcodeScanner.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void OnReceive(String code) {
        getPrintHistory("0",code);
    }
    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected boolean isScan() {
        return true;
    }

}
