package com.fangzuo.assist.cloud.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fangzuo.assist.cloud.ABase.BaseActivity;
import com.fangzuo.assist.cloud.Activity.Crash.App;
import com.fangzuo.assist.cloud.Adapter.LoginSpAdapter;
import com.fangzuo.assist.cloud.Beans.BackData;
import com.fangzuo.assist.cloud.Beans.BackDataLogin;
import com.fangzuo.assist.cloud.Beans.CommonResponse;
import com.fangzuo.assist.cloud.Beans.DownloadReturnBean;
import com.fangzuo.assist.cloud.Beans.UseTimeBean;
import com.fangzuo.assist.cloud.Dao.User;
import com.fangzuo.assist.cloud.R;
import com.fangzuo.assist.cloud.RxSerivce.MySubscribe;
import com.fangzuo.assist.cloud.RxSerivce.RService;
import com.fangzuo.assist.cloud.RxSerivce.ToSubscribe;
import com.fangzuo.assist.cloud.Service.DataService;
import com.fangzuo.assist.cloud.Utils.Asynchttp;
import com.fangzuo.assist.cloud.Utils.BasicShareUtil;
import com.fangzuo.assist.cloud.Utils.CommonUtil;
import com.fangzuo.assist.cloud.Utils.Config;
import com.fangzuo.assist.cloud.Utils.DoubleUtil;
import com.fangzuo.assist.cloud.Utils.Info;
import com.fangzuo.assist.cloud.Utils.JsonCreater;
import com.fangzuo.assist.cloud.Utils.JsonDealUtils;
import com.fangzuo.assist.cloud.Utils.Lg;
import com.fangzuo.assist.cloud.Utils.ShareUtil;
import com.fangzuo.assist.cloud.Utils.Toast;
import com.fangzuo.assist.cloud.Utils.WebApi;
import com.fangzuo.assist.cloud.widget.LoadingUtil;
import com.fangzuo.assist.cloud.widget.SpinnerUser;
import com.fangzuo.greendao.gen.SaleManDao;
import com.fangzuo.greendao.gen.UserDao;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.orhanobut.hawk.Hawk;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

import static com.fangzuo.assist.cloud.Utils.CommonUtil.dealTime;


public class LoginActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {


    @BindView(R.id.isRemPass)
    CheckBox isRemPass;
    @BindView(R.id.ver)
    TextView mTvVersion;
    @BindView(R.id.ed_pass)
    EditText mEtPassword;
//    @BindView(R.id.isOL)
//    CheckBox mCbisOL;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_setting)
    Button btnSetting;
    private Button mBtnLogin;
    private Button mBtnSetting;
    private LoginActivity mContext;
    @BindView(R.id.sp_login)
    SpinnerUser spinner;
    private String userName = "";
    private String userID = "";
//    private List<User> users;
    private BasicShareUtil share;
    private boolean isOL;
    private String userPass;
    private UserDao userDao;

    RService rService;

    @Override
    public void initView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContext = this;
        initBar();
        share = BasicShareUtil.getInstance(mContext);
        userDao = daoSession.getUserDao();

        getPermisssion();
        Log.e("123", ShareUtil.getInstance(mContext).getPISpayMethod() + "");
        mTvVersion.setText("Cloud Ver:" + CommonUtil.getVersionName());
        Lg.e("PDA：" + App.PDA_Choose);
        isRemPass.setChecked(Hawk.get(Info.IsRemanber, false));
        rService = App.getRService();
        TelephonyManager tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint({"HardwareIds", "MissingPermission"}) String deviceId = tm.getDeviceId();
        BasicShareUtil.getInstance(mContext).setIsOL(true);
        share.setIMIE(deviceId);


    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取用户数据，并且设置默认值
        spinner.setAutoSelection(Info.AutoLogin,Hawk.get(Info.AutoLogin, ""));
        DataService.updateTime(mContext);
        DownLoadUseTime();
    }
    //获取配置文件中的时间数据
    private void DownLoadUseTime(){
        App.getRService().doIOAction(WebApi.GetUseTime, "获取时间", new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
                super.onNext(commonResponse);
                LoadingUtil.dismiss();
                if (!commonResponse.state)return;
                UseTimeBean bean = gson.fromJson(commonResponse.returnJson, UseTimeBean.class);
                if (Integer.parseInt(getTime(false))<Integer.parseInt(bean.nowTime)){
                    Toast.showText(mContext,"PDA本地时间与服务器时间有误，请调整好时间");
                    Hawk.put(Config.SaveTime,bean);
                    return;
                }else{
                    if (Integer.parseInt(getTime(false))>Integer.parseInt(dealTime(bean.endTime))){
                        Toast.showText(mContext,"软件已过期，请联系供应商提供服务");
                        Hawk.put(Config.SaveTime,bean);
                        return;
                    }else{
                        Lg.e("获取起止时间："+commonResponse.returnJson);
                        Hawk.put(Config.SaveTime,bean);
                    }
                }
            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                LoadingUtil.dismiss();
//                Hawk.put(Config.SaveTime,null);
                Toast.showText(mContext,e.toString());
                Lg.e("错误："+e.toString());
            }
        });


//        Lg.e("本地配置数据；",Hawk.get(Config.SettingData,new DownloadReturnBean().new SetFile()));
//
//        Asynchttp.post(mContext,Config.Setting_Url, "sdfa", new Asynchttp.Response() {
//            @Override
//            public void onSucceed(CommonResponse cBean, AsyncHttpClient client) {
//                Lg.e("配置数据：",cBean);
//                Lg.e("配置数据：",cBean.returnJson);
//                DownloadReturnBean dBean = JsonCreater.gson.fromJson(cBean.returnJson, DownloadReturnBean.class);
//                Lg.e("配置解析：",dBean);
//                Lg.e("配置解析：",dBean.serverTime);
//                for (int i = 0; i < dBean.setFiles.size(); i++) {
//                    if (getApplication().getPackageName().equals(dBean.setFiles.get(i).AppID)){
//                        Lg.e("存在App：",dBean.setFiles.get(i));
//                        Hawk.put(Config.SettingData,dBean.setFiles.get(i));
//                    }
//                }
//            }
//
//            @Override
//            public void onFailed(String Msg, AsyncHttpClient client) {
//                Lg.e("配置解析错误："+Msg);
//            }
//        });
    }
    //检测是否符合时间要求
    private boolean checkTime (){
        if (null== Hawk.get(Config.SaveTime,null)){
            LoadingUtil.showDialog(mContext,"正在获取配置信息...");
            DownLoadUseTime();
            return false;
        }else{
            UseTimeBean bean= Hawk.get(Config.SaveTime);
            if (Integer.parseInt(getTime(false))<Integer.parseInt(bean.nowTime)){
                Toast.showText(mContext,"PDA本地时间与服务器时间有误，请调整好时间");
                return false;
            }else{
                if (Integer.parseInt(getTime(false))>Integer.parseInt(dealTime(bean.endTime))){
                    Toast.showText(mContext,"软件已过期，请联系供应商提供服务");
                    return false;
                }else{
                    return true;
                }
            }
        }
    }


    @Override
    public void initData() {
    }

    @Override
    public void initListener() {
        isRemPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Hawk.put(Info.IsRemanber, b);
            }
        });
//        mCbisOL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                isOL = b;
//                BasicShareUtil.getInstance(mContext).setIsOL(b);
//                users = userDao.loadAll();
//                LoginSpAdapter ada = new LoginSpAdapter(mContext, users);
//                spinner.setAdapter(ada);
//                ada.notifyDataSetChanged();
//            }
//        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                User user = (User) spinner.getAdapter().getItem(i);
                userName = user.FName;
                userID = user.FUserID;
                userPass = user.FPassWord;
                ShareUtil.getInstance(mContext).setUserName(userName);
                ShareUtil.getInstance(mContext).setUserID(userID);
                //设置下次默认选择的用户
                Hawk.put(Info.AutoLogin, userName);
                //设置该用户密码
                mEtPassword.setText(Hawk.get(userName, ""));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void OnReceive(String code) {
        Toast.showText(mContext, "扫码:" + code);
        Log.e("CODE", code + ":获得的code");
    }

    @OnClick({R.id.btn_login, R.id.btn_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Login();
                break;
            case R.id.btn_setting:
                Bundle b = new Bundle();
                b.putInt("flag", 0);
                startNewActivity(SettingMenuActivity.class, R.anim.activity_slide_left_in, R.anim.activity_slide_left_out, false, null);
                break;
        }
    }


    //    String JsonStr="{"acctID":"5beb8db650b4cf","username":"Administrator","lcid":"888888"}";
    private void Login() {
        if (!checkTime()){
            DownLoadUseTime();
            Toast.showText(mContext,"验证信息失败");
            return;
        }

        if (Hawk.get(Config.Cloud_ID,"").equals("")){
            Toast.showText(mContext,"未确定账套，请于下载配置里面选择账套");
            return;
        }
        if ("".equals(userName)){
            Toast.showText(mContext,"请选择用户");
            return;
        }
//        startNewActivity(HomeActivity.class, R.anim.activity_slide_left_in, R.anim.activity_slide_left_out, false, null);

        LoadingUtil.showDialog(mContext,"正在验证...");
        //组装登录数据
        JSONArray jParas = new JSONArray();
        jParas.put(Hawk.get(Config.Cloud_ID,""));// 帐套Id
        jParas.put(userName);// 用户名
        jParas.put(mEtPassword.getText().toString());// 密码
        jParas.put(2052);// 语言T
        App.CloudService().doIOActionLogin(Config.C_Login, jParas.toString(), new ToSubscribe<BackDataLogin>() {
            @Override
            public void onNext(BackDataLogin bean) {
                try{
//                    BackDataLogin bean = JsonCreater.gson.fromJson(string, BackDataLogin.class);
                    if (bean.getLoginResultType() == 1 || bean.getLoginResultType() == -5) {
                    ShareUtil.getInstance(mContext).setUserName(userName);
                    Hawk.put(Info.user_org,bean.getContext().getCurrentOrganizationInfo().getName());
                    Hawk.put(Info.user_id,bean.getContext().getUserId()+"");
                    Hawk.put(Info.user_data,bean.getContext().getDataCenterName()+"");
//                    ShareUtil.getInstance(mContext).setUserID(userID);
                    if (isRemPass.isChecked()){
//                        保存该用户的密码
                        Hawk.put(userName,mEtPassword.getText().toString());
                    }else {
                        Hawk.put(userName, "");
                    }
                        startNewActivity(HomeActivity.class, R.anim.activity_slide_left_in, R.anim.activity_slide_left_out, false, null);
                        LoadingUtil.dismiss();
                    } else {
                        LoadingUtil.dismiss();
                        LoadingUtil.showAlter(mContext,"登陆失败",bean.getMessage(),false);
//                        Toast.showText(App.getContext(), bean.getMessage());
                        Lg.e("登录错误2：" + bean.toString());
                    }
                }catch (Exception e){
                        LoadingUtil.dismiss();
                    Lg.e("json转换错误："+e.toString());
                }
            }

            @Override
            public void onError(Throwable e) {
                        LoadingUtil.dismiss();
                Lg.e("登录错误："+e.toString());
                super.onError(e);
            }
        });
    }


    //权限获取-------------------------------------------------------------
    private void getPermisssion() {
        String[] perm = {
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(mContext, perm)) {
            EasyPermissions.requestPermissions(this, "必要的权限", 0, perm);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //把申请权限的回调交由EasyPermissions处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.i("permisssion", "获取成功的权限" + perms);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.i("permisssion", "获取失败的权限" + perms);
    }
}
