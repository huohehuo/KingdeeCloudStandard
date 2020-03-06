package com.fangzuo.assist.cloud.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.device.ScanDevice;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangzuo.assist.cloud.ABase.BaseActivity;
import com.fangzuo.assist.cloud.Adapter.PushDownListAdapter;
import com.fangzuo.assist.cloud.Adapter.ReViewCheckP233Cy2Adapter;
import com.fangzuo.assist.cloud.Beans.PushDownListReturnBean;
import com.fangzuo.assist.cloud.Dao.PushDownMain;
import com.fangzuo.assist.cloud.Dao.T_main;
import com.fangzuo.assist.cloud.Dao.TempDetil;
import com.fangzuo.assist.cloud.R;
import com.fangzuo.assist.cloud.Utils.CommonUtil;
import com.fangzuo.assist.cloud.Utils.GreedDaoUtil.GreenDaoManager;
import com.fangzuo.assist.cloud.Utils.Lg;
import com.fangzuo.assist.cloud.Utils.MathUtil;
import com.fangzuo.assist.cloud.widget.LoadingUtil;
import com.fangzuo.greendao.gen.DaoSession;
import com.fangzuo.greendao.gen.PushDownMainDao;
import com.fangzuo.greendao.gen.PushDownSubDao;
import com.fangzuo.greendao.gen.T_mainDao;
import com.fangzuo.greendao.gen.TempDetilDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckCy2Activity extends BaseActivity {

    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_add_num)
    TextView tvAddNum;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.lv_result)
    ListView lvResult;
    @BindView(R.id.btn_back)
    RelativeLayout btnBack;
    private int tag;
    private int activityWg;
    private FragmentActivity mContext;
    private PushDownListAdapter pushDownListAdapter;
    private ArrayList<PushDownMain> downloadIDs;            //用于listview选择时，添加临时对象
    private PushDownListReturnBean puBean;
    private DaoSession daosession;
    private ArrayList<PushDownMain> container;
    private ScanDevice sm;
    private Intent intent;
    private String TAG = "DownLoadPushFragment";
    private PushDownMainDao pushDownMainDao;
    private PushDownSubDao pushDownSubDao;
    private TempDetilDao tempDetilDao;
    private T_mainDao t_mainDao;
    private int activity;
    private List<TempDetil> list;
    private List<T_main> list_main;
    private ReViewCheckP233Cy2Adapter adapter;
    private List<Boolean> isCheck;
    private String billNo;
    private String interId;
    private String enterId;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_check_cy2);
        ButterKnife.bind(this);
        mContext = this;
        isCheck = new ArrayList<>();
        downloadIDs = new ArrayList<>();
        daosession = GreenDaoManager.getmInstance(mContext).getDaoSession();
        Intent in = getIntent();
        Bundle b = in.getExtras();
        activityWg = b.getInt("activity");
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        tempDetilDao = daosession.getTempDetilDao();
        isCheck = new ArrayList<>();
        list = tempDetilDao.queryBuilder().where(
                TempDetilDao.Properties.FAccountID.eq(CommonUtil.getAccountID()),
//                TempDetilDao.Properties.FID.eq(interId),
//                TempDetilDao.Properties.FEntryID.eq(enterId),
                TempDetilDao.Properties.Activity.eq(activityWg)
        ).build().list();
        Lg.e("tmp数据", list);
        Double res = 0.0;
        Double resAdd = 0.0;
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                isCheck.add(false);
                res += MathUtil.toD(list.get(i).FRealQty);
                resAdd += MathUtil.toD(list.get(i).FNum);
            }
        }
        tvNum.setText("总数量：" + MathUtil.D2saveInt(res));
        tvAddNum.setText("总支量：" + MathUtil.D2saveInt(resAdd));
        adapter = new ReViewCheckP233Cy2Adapter(mContext, list, isCheck);
        lvResult.setAdapter(adapter);
//        adapter.setInnerListener(this);
        adapter.notifyDataSetChanged();
    }

    private ArrayList<TempDetil> tempDetils;

    @Override
    protected void initListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if (isCheck.get(i)) {
                    isCheck.set(i, false);
                } else {
                    isCheck.set(i, true);
                }
                adapter.notifyDataSetChanged();

            }
        });
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder delete = new AlertDialog.Builder(mContext);
                delete.setTitle("确认删除");
                delete.setMessage("确认删除选中单据么？");
                delete.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoadingUtil.showDialog(mContext, "正在删除...");
                        tempDetils = new ArrayList<>();

                        for (int j = 0; j < isCheck.size(); j++) {
                            if (isCheck.get(j)) {
//                                Log.e(i + "", isCheck.get(j) + "");
                                final TempDetil t_detail = tempDetilDao.queryBuilder().where(
                                        TempDetilDao.Properties.FIndex.eq(list.get(j).FIndex)
                                ).build().unique();
                                tempDetils.add(t_detail);
                            }
                        }
                        tempDetilDao.deleteInTx(tempDetils);
                        LoadingUtil.dismiss();
                        initData();
                    }
                });
                delete.setNegativeButton("取消", null);
                delete.create().show();
            }
        });
    }

    @Override
    protected void OnReceive(String code) {

    }

    public static void start(Context context, int activity) {
        Intent intent = new Intent(context, CheckCy2Activity.class);
        intent.putExtra("activity", activity);
        context.startActivity(intent);
    }

}
