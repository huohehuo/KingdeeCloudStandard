package com.fangzuo.assist.cloud.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fangzuo.assist.cloud.ABase.BaseFragment;
import com.fangzuo.assist.cloud.Activity.IpPortActivity;
import com.fangzuo.assist.cloud.Activity.LanguageActivity;
import com.fangzuo.assist.cloud.Activity.PrintOutTestActivity;
import com.fangzuo.assist.cloud.Activity.SettingActivity;
import com.fangzuo.assist.cloud.Activity.TestingActivity;
import com.fangzuo.assist.cloud.Adapter.SettingListAdapter;
import com.fangzuo.assist.cloud.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.cloud.R;
import com.fangzuo.assist.cloud.Utils.BasicShareUtil;
import com.fangzuo.assist.cloud.Utils.CommonUtil;
import com.fangzuo.assist.cloud.Utils.Config;
import com.fangzuo.assist.cloud.Utils.EventBusInfoCode;
import com.fangzuo.assist.cloud.Utils.EventBusUtil;
import com.fangzuo.assist.cloud.Utils.Lg;
import com.fangzuo.assist.cloud.Utils.Toast;
import com.fangzuo.assist.cloud.widget.LoadingUtil;
import com.fangzuo.assist.cloud.zxing.CustomCaptureActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.fangzuo.assist.cloud.Utils.GetSettingList.getList;


public class SettingFragment extends BaseFragment {
    @BindView(R.id.lv)
    ListView lv;
    private Unbinder bind;
    private FragmentActivity mContext;
    private ProgressDialog pDialog;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(ClassEvent event) {
        if (event != null) {
            switch (event.Msg) {
                case EventBusInfoCode.ScanResult://
                    BarcodeResult res = (BarcodeResult) event.postEvent;
                    OnReceive(res.getResult().getText());
                    break;
            }
        }
    }
    public SettingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        bind = ButterKnife.bind(this, v);
        return v;
    }


    @Override
    public void initView() {
        mContext = getActivity();
        EventBusUtil.register(this);

    }

    @Override
    protected void OnReceive(String barCode) {
        Lg.e("扫描下载"+barCode);
        //fangzuokeji^http://148.70.108.65:8080/AppFile/Cloud/app-debug.apk
        if (barCode.contains("fangzuokeji^")){
            String url = barCode.replace("fangzuokeji^","");
            DownLoad(url);
        }
    }

    @Override
    protected void initData() {
        SettingListAdapter ada = new SettingListAdapter(mContext,getList());
        lv.setAdapter(ada);
        ada.notifyDataSetChanged();
    }

    @Override
    protected void initListener() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Bundle b= new Bundle();
                        b.putInt("flag",100);
                        startNewActivity(SettingActivity.class,b);
                        break;
                    case 1:
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        break;
                    case 2:
                        startActivity(new Intent(Settings.ACTION_SOUND_SETTINGS));
                        break;
                    case 3:
                        IntentIntegrator intentIntegrator = new IntentIntegrator(mContext);
                        // 设置自定义扫描Activity
                        intentIntegrator.setCaptureActivity(CustomCaptureActivity.class);
                        intentIntegrator.initiateScan();
//                        checkNewVersion();
                        break;
                    case 4:
                        startActivity(new Intent(mContext, IpPortActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(mContext, PrintOutTestActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(mContext, TestingActivity.class));
                        break;
                }

            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                switch (i){
                    case 3:
                        new AlertDialog.Builder(mContext)
                                .setTitle("是否下载更新文件")
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DownLoad(Config.getApk_Url());
                                    }
                                })
                                .create().show();
                        break;

                }
                return true;
            }
        });
    }

    public String getBaseUrl(){
        return BasicShareUtil.getInstance(mContext).getBaseURL();
    }
    public int getVersionCode() {
        //androidmanifest中获取版本名称
        PackageManager packageManager = mContext.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    mContext.getPackageName(), 0);

            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;

            System.out.println("versionName=" + versionName + ";versionCode="
                    + versionCode);

            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
        try {
            EventBusUtil.unregister(this);
        } catch (Exception e) {

        }
    }

    private void checkNewVersion() {
        LoadingUtil.showDialog(mContext,"请扫描二维码进行下载");
//        DownLoad(Config.Apk_Url);

//        Asynchttp.post(mContext, getBaseUrl() + WebApi.GETNEWVERSION, "", new Asynchttp.Response() {
//            @Override
//            public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
//                final NewVersionBean nBean = new Gson().fromJson(cBean.returnJson,NewVersionBean.class);
//                if(nBean.Version>getVersionCode()){
//                    AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
//                    ab.setTitle("发现新版本");
//                    ab.setMessage(nBean.Rem);
//                    ab.setPositiveButton("更新", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            DownLoad(nBean.downLoadURL);
//                        }
//                    });
//                    ab.setNegativeButton("取消",null);
//                    ab.create().show();
//                }else{
//                    Toast.showText(mContext,"无新版本");
//                }
//            }
//
//            @Override
//            public void onFailed(String Msg, AsyncHttpClient client) {
//                Toast.showText(mContext,Msg);
//            }
//        });
    }

    private void DownLoad(String downLoadURL) {
        LoadingUtil.dismiss();
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            pDialog = new ProgressDialog(mContext);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setTitle("下载中");
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();
            String target = Environment.getExternalStorageDirectory()
                    + "/NewApp"+getTimeLong(false)+".apk";
            HttpUtils utils = new HttpUtils();
            utils.download(downLoadURL, target, new RequestCallBack<File>() {
                @Override
                public void onLoading(long total, long current,
                                      boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                    System.out.println("下载进度:" + current + "/" + total);
                    pDialog.setProgress((int) (current*100/total));
                }

                @Override
                public void onSuccess(ResponseInfo<File> arg0) {
                    pDialog.dismiss();
                    Lg.e("下载的文件数据："+arg0.toString());
                    Lg.e("下载的文件数据："+arg0.result);
                    System.out.println("下载完成"+arg0.result);
                    try{
                        CommonUtil.installApk(mContext,arg0.result+"");
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.addCategory(Intent.CATEGORY_DEFAULT);
//                        intent.setDataAndType(Uri.fromFile(arg0.result),
//                                "application/vnd.android.package-archive");
//                        startActivityForResult(intent, 0);
                    }catch (Exception e){
                        try{
                            StringBuilder builder = new StringBuilder();
                            builder.append("请先退出本软件\n");
                            builder.append("进入PDA软件主页\n");
                            builder.append("选择文件管理器\n");
                            builder.append("找到文件NewApp\n");
                            builder.append("长按变色点击右下角重命名\n删去后面的数字\n");
                            builder.append("变成文件名：NewApp.apk\n");
                            builder.append("点击安装\n");
                            AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                            ab.setTitle("下载成功!\n请按操作重新安装APK");
                            ab.setMessage(builder.toString());
                            ab.setPositiveButton("确定", null);
                            ab.create().show();
                        }catch (Exception e1){

                        }
                    }

                }

                @Override
                public void onFailure(HttpException arg0, String arg1) {
                    pDialog.dismiss();
                    Toast.showText(mContext, "下载失败");
                }
            });
        } else {
            pDialog.dismiss();
            Toast.showText(mContext, "正在安装");

        }
    }









    public String getTimeLong(boolean b){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(b?"yyyy-MM-dd-HH-mm-ss":"yyyyMMddHHmmss");
        Date curDate = new Date();
        Log.e("date",curDate.toString());
        String str = format.format(curDate);
        return str;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
