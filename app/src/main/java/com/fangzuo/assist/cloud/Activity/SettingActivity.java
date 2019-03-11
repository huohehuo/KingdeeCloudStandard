package com.fangzuo.assist.cloud.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangzuo.assist.cloud.ABase.BaseActivity;
import com.fangzuo.assist.cloud.Activity.Crash.App;
import com.fangzuo.assist.cloud.Adapter.DataSearchRyAdapter;
import com.fangzuo.assist.cloud.Beans.CommonResponse;
import com.fangzuo.assist.cloud.Beans.ConnectResponseBean;
import com.fangzuo.assist.cloud.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.cloud.R;
import com.fangzuo.assist.cloud.RxSerivce.MySubscribe;
import com.fangzuo.assist.cloud.Service.DataService;
import com.fangzuo.assist.cloud.Utils.BasicShareUtil;
import com.fangzuo.assist.cloud.Utils.Config;
import com.fangzuo.assist.cloud.Utils.DataBaseAdapter;
import com.fangzuo.assist.cloud.Utils.DataModel;
import com.fangzuo.assist.cloud.Utils.EventBusInfoCode;
import com.fangzuo.assist.cloud.Utils.Info;
import com.fangzuo.assist.cloud.Utils.JsonCreater;
import com.fangzuo.assist.cloud.Utils.Lg;
import com.fangzuo.assist.cloud.Utils.ShareUtil;
import com.fangzuo.assist.cloud.Utils.Toast;
import com.fangzuo.assist.cloud.Utils.UpdataLocData;
import com.fangzuo.assist.cloud.Utils.WebApi;
import com.fangzuo.assist.cloud.widget.LoadingUtil;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SettingActivity extends BaseActivity implements DataSearchRyAdapter.OnItemClickListener {

    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ry_data_search)
    RecyclerView ryDataSearch;
    @BindView(R.id.btn_connect)
    Button btnConnect;
    @BindView(R.id.btn_prop)
    Button btnProp;
    @BindView(R.id.btn_download)
    Button btnDownload;
    @BindView(R.id.ed_serverip)
    EditText edServerip;
    @BindView(R.id.ed_port)
    EditText edPort;
    @BindView(R.id.ed_username)
    EditText edUsername;
    @BindView(R.id.ed_pass)
    EditText edPass;
    @BindView(R.id.lv_database)
    ListView lvDatabase;
//    @BindView(R.id.container)
//    CoordinatorLayout containerView;
    private DataBaseAdapter adapter;
    private SettingActivity mContext;
    private ArrayList<ConnectResponseBean.DataBaseList> container;
    private BasicShareUtil share;
    private String chooseDatabase;
    private long nowTime;
    private DataSearchRyAdapter dataSearchRyAdapter;
    private ProgressDialog pDialog;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    long nowTime = (long) msg.obj;
                    int size = msg.arg1;
                    long endTime = System.currentTimeMillis();
                    AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                    ab.setTitle("下载完成");
                    ab.setMessage("耗时:" + (endTime - nowTime) + "ms" + ",共插入" + size + "条数据");
                    ab.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startNewActivity(LoginActivity.class, R.anim.activity_slide_left_in, R.anim.activity_slide_left_out, true, null);
                        }
                    });
                    ab.create().show();
                    break;
            }
        }
    };
    private int size;
    private int flag = 1;

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onEventBusCome(ClassEvent event) {
        switch (event.Msg) {
            case EventBusInfoCode.Connect_OK:
                CommonResponse commonResponse = (CommonResponse) event.postEvent;
                share.setDatabaseIp(edServerip.getText().toString());
                share.setDatabasePort(edPort.getText().toString());
                share.setDataBaseUser(edUsername.getText().toString());
                share.setDataBasePass(edPass.getText().toString());
                LoadingUtil.dismiss();
                ConnectResponseBean connectBean = gson.fromJson(commonResponse.returnJson, ConnectResponseBean.class);
                dataSearchRyAdapter.addAll(connectBean.DataBaseList);
                adapter = new DataBaseAdapter(mContext, container);
                lvDatabase.setAdapter(adapter);
                Toast.showText(mContext, "获取了" + connectBean.DataBaseList.size() + "条数据");
                break;
            case EventBusInfoCode.Connect_Error:
//                String str = (String) event.postEvent;
//                LoadingUtil.dismiss();
//                Toast.showText(mContext, "连接错误:" + str);
                LoadingUtil.showAlter(mContext,"连接错误",(String)event.postEvent);
                LoadingUtil.dismiss();
                break;
            case EventBusInfoCode.Prop_OK:
                CommonResponse prop = (CommonResponse) event.postEvent;
                final AlertDialog.Builder ab = new AlertDialog.Builder(mContext);
                if (prop.state) {
                    if (isClearAll) {
                        DataService.deleteAll(mContext);
                        ShareUtil.getInstance(mContext).clear();
                    }
                    LoadingUtil.dismiss();
                    ab.setTitle("配置结果");
                    ab.setMessage("配置成功，请继续下一步操作");
                    ab.setPositiveButton("确认", null);
                    ab.create().show();

                    App.getRService().doIOAction(WebApi.DetailTableDeleteAll,BasicShareUtil.getInstance(mContext).getIMIE(), new MySubscribe<CommonResponse>() {
                        @Override
                        public void onNext(CommonResponse commonResponse) {
                            Lg.e("删除临时表成功1");
                        }
                        @Override
                        public void onError(Throwable e) {
                            Lg.e("删除临时表失败");
                        }
                    });
                    share.setVersion(prop.returnJson);
                    share.setDataBase(chooseDatabase);
                } else {
                    LoadingUtil.dismiss();
                    ab.setTitle("配置结果");
                    ab.setMessage(prop.returnJson);
                    ab.setPositiveButton("确认", null);
                    ab.setNegativeButton("重试", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            prop();
                        }
                    });
                    ab.create().show();
                }
                isClearAll = false;
                break;
            case EventBusInfoCode.Prop_Error:
                String str2 = (String) event.postEvent;
                final AlertDialog.Builder ab2 = new AlertDialog.Builder(mContext);
                LoadingUtil.dismiss();
                ab2.setTitle("配置结果");
                ab2.setMessage(str2);
                ab2.setPositiveButton("确认", null);
                ab2.setNegativeButton("重试", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        prop();
                    }
                });
                ab2.create().show();
                break;
            case EventBusInfoCode.Updata_OK://回单成功
                String time = (String)event.Msg2;
                String size = (String)event.Msg3;
                long endTime = System.currentTimeMillis();
                AlertDialog.Builder ab4 = new AlertDialog.Builder(mContext);
                ab4.setTitle("下载完成");
                ab4.setMessage("耗时:" + (endTime - Long.parseLong(time)) + "ms" + ",共插入" + size + "条数据");
                ab4.setPositiveButton("确认",null);
                ab4.create().show();
                break;
            case EventBusInfoCode.Updata_Error://回单失败
                LoadingUtil.dismiss();
                LoadingUtil.showAlter(mContext,"下载错误",(String)event.postEvent);
//                Toast.showText(mContext,(String)event.postEvent);
                break;
        }
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        tvTitle.setText("下载配置");
    }

    @Override
    public void initData() {
        mContext = this;
        container = new ArrayList<>();
        share = BasicShareUtil.getInstance(mContext);

        //为了测试
        if (!share.getDatabaseIp().equals("")) {
            edServerip.setText(share.getDatabaseIp());
            edPort.setText(share.getDatabasePort());
            edUsername.setText(share.getDataBaseUser());
            edPass.setText(share.getDataBasePass());
        }

        dataSearchRyAdapter = new DataSearchRyAdapter(mContext, container);
        ryDataSearch.setAdapter(dataSearchRyAdapter);
        ryDataSearch.setItemAnimator(new DefaultItemAnimator());
        ryDataSearch.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        dataSearchRyAdapter.setOnItemClickListener(this);
        dataSearchRyAdapter.notifyDataSetChanged();
    }

    @Override
    public void initListener() {
    }
    @Override
    protected void OnReceive(String code) {
    }
    @Override
    public void onItemClick(View view, int position) {
        dataSearchRyAdapter.setIsCheck(position);
        chooseDatabase = container.get(position).dataBase;
        Toast.showText(mContext, chooseDatabase);
        share.setDataBase(chooseDatabase);
        Hawk.put(Config.Cloud_ID,container.get(position).dataBaseID);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @OnClick({R.id.btn_back, R.id.btn_connect, R.id.btn_prop, R.id.btn_download})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_connect:
                connectToSQL();
                break;
            case R.id.btn_prop:
                prop();
                break;
            case R.id.btn_download:
                daoSession.clear();
                UpdataLocData.getInstance(mContext).alertToChoose();
//                DownLoadData.getInstance(mContext, containerView, handler).alertToChoose();
                break;
        }
    }


    private void prop() {
        AlertDialog.Builder ab1 = new AlertDialog.Builder(mContext);
        ab1.setTitle("是否配置");
        ab1.setMessage("配置将会清空所有数据（包括已做单据）");
        ab1.setPositiveButton("清空", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setprop(true);
            }
        });
        ab1.setNeutralButton("不清空", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setprop(false);
            }
        });
        ab1.setNegativeButton("取消", null);
        ab1.create().show();
    }

    private boolean isClearAll = false;

    private void setprop(final boolean isClear) {
        if (null == chooseDatabase) {
            Toast.showText(mContext, "请选择账套");
            return;
        }
        isClearAll = isClear;
        LoadingUtil.show(mContext, "正在配置...");
        DataModel.SetProp(JsonCreater.ConnectSQL(
                share.getDatabaseIp(),
                share.getDatabasePort(),
                share.getDataBaseUser(),
                share.getDataBasePass(),
                chooseDatabase));
    }

    private void connectToSQL() {
        LoadingUtil.showDialog(mContext, "正在连接...");
        DataModel.SetConnectSQL(JsonCreater.ConnectSQL(
                edServerip.getText().toString(),
                edPort.getText().toString(),
                edUsername.getText().toString(),
                edPass.getText().toString(),
                Info.DATABASESETTING));
    }


}
