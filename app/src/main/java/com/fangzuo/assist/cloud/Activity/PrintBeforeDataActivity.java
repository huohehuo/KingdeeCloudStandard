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
import com.fangzuo.assist.cloud.R;
import com.fangzuo.assist.cloud.RxSerivce.MySubscribe;
import com.fangzuo.assist.cloud.Utils.BasicShareUtil;
import com.fangzuo.assist.cloud.Utils.CommonUtil;
import com.fangzuo.assist.cloud.Utils.Config;
import com.fangzuo.assist.cloud.Utils.EventBusInfoCode;
import com.fangzuo.assist.cloud.Utils.EventBusUtil;
import com.fangzuo.assist.cloud.Utils.Lg;
import com.fangzuo.assist.cloud.Utils.Toast;
import com.fangzuo.assist.cloud.Utils.WebApi;
import com.fangzuo.assist.cloud.databinding.ActivityPrintBeforeDataBinding;
import com.fangzuo.assist.cloud.widget.LoadingUtil;
import com.google.gson.Gson;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.orhanobut.hawk.Hawk;

import zpSDK.zpSDK.zpBluetoothPrinter;

public class PrintBeforeDataActivity extends BaseActivity {
    ActivityPrintBeforeDataBinding binding;
    zpBluetoothPrinter zpSDK;
    private PrintHistoryAdapter adapter;
    BlueToothBean bean;

    @Override
    protected void receiveEvent(ClassEvent event) {
        switch (event.Msg) {
            case EventBusInfoCode.Print_Check://回单失败
                String msg = (String) event.postEvent;
                LoadingUtil.dismiss();
                if ("OK".equals(msg)){
                    binding.toolbar.tvRight.setText("打印机就绪");
                    binding.toolbar.tvRight.setTextColor(Color.BLACK);
                }else{
                    binding.toolbar.tvRight.setText("连接打印机错误");
                    binding.toolbar.tvRight.setTextColor(Color.RED);
                }
                break;

        }
    }

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_print_before_data);
        zpSDK=new zpBluetoothPrinter(this);
        binding.toolbar.tvTitle.setText("条码补打");
        binding.ryPrintHistory.setAdapter(adapter = new PrintHistoryAdapter(this));
        binding.ryPrintHistory.setLayoutManager(new LinearLayoutManager(this));
        bean = Hawk.get(Config.OBJ_BLUETOOTH, new BlueToothBean("", ""));
        linkBluePrint();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                PrintHistory bean =adapter.getAllData().get(position);
                Lg.e("点击历史：",bean);
                String stringdata = bean.FMaterialid+"|"+bean.FBaseUnit+"|"+bean.FNum+"|"+
                        bean.FBatch+"|"+ BasicShareUtil.getInstance(mContext).getIMIE()+"|"+"";
//                bean.FBatch = binding.edPihao.getText().toString();
                pushAndCreateCode(bean,stringdata);
            }
        });
        binding.ivFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.edPihao.getText().toString().equals("")){
                    getPrintHistory(binding.edPihao.getText().toString());
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
                        getPrintHistory(binding.edPihao.getText().toString());
                    }else{
                        Toast.showText(mContext,"请输入需要查询的批号");
                    }
                }
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
     *              通过批号查找打印数据
     * @param string        需要查找的模糊批号
     */
    private void getPrintHistory(String string){
        LoadingUtil.showDialog(mContext,"正在查找打印数据...");
        App.getRService().doIOAction(WebApi.PrintBeforeData, string, new MySubscribe<CommonResponse>() {
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

    //生成条码并执行打印
    private void pushAndCreateCode(final PrintHistory data, String string){
        Lg.e("Data:pushAndCreateCode",data);
        App.getRService().doIOAction(WebApi.PrintBeforeDataForCreateCode, string, new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                super.onNext(commonResponse);
                if (!commonResponse.state)return;
                DownloadReturnBean dBean = new Gson().fromJson(commonResponse.returnJson, DownloadReturnBean.class);
                if (null != dBean && dBean.codeCheckBackDataBeans.size() > 0) {
                    data.FNum2=dBean.codeCheckBackDataBeans.get(0).FQty;
                    data.FBarCode = dBean.codeCheckBackDataBeans.get(0).FBarCode;
                    data.FDate = getTime(true);
                    try {
                        CommonUtil.doPrint(zpSDK,data);
                    } catch (Exception e) {
//                    e.printStackTrace();
                        LoadingUtil.showAlter(mContext,"打印错误","请检查打印机是否已连接");
                    }
                }else{
                    Toast.showText(mContext,"生成条码失败,请重试");
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    //展示打印数据
    private void showMsg(final PrintHistory data){
        if (null==data.FDate || "".equals(data.FDate)){
            data.FDate = getTime(true);
        }
        Lg.e("Data:打印信息：",data);
        AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
        ab.setTitle("打印信息：");
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
        num.setText(data.getFNum());
        num2.setText(data.getFNum2());
        note.setText(data.getFNot());
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
                    CommonUtil.doPrint(zpSDK,data);
                } catch (Exception e) {
//                    e.printStackTrace();
                    LoadingUtil.showAlter(mContext,"打印错误","请检查打印机是否已连接");
                }
                alertDialog.dismiss();
            }
        });
    }

    //连接打印机
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
    protected void OnReceive(String code) {

    }
    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }
}
