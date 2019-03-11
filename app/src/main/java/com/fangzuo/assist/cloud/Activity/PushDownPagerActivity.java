package com.fangzuo.assist.cloud.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fangzuo.assist.cloud.ABase.BaseActivity;
import com.fangzuo.assist.cloud.Adapter.StripAdapter;
import com.fangzuo.assist.cloud.Fragment.pushdown.ChooseFragment;
import com.fangzuo.assist.cloud.Fragment.pushdown.DownLoadPushFragment;
import com.fangzuo.assist.cloud.R;
import com.fangzuo.assist.cloud.Utils.PagerSlidingTabStrip;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;


//          下载，选择单据页面（包含：下载单据Fragment，选择单据Fragment）
public class PushDownPagerActivity extends BaseActivity {
    @BindView(R.id.tabstrip)
    PagerSlidingTabStrip tabstrip;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindColor(R.color.cpb_blue)
    int cpb_blue;
    public int tag;
    @BindView(R.id.tv_pdname)
    TextView tvPdname;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_push_down_pager);
        ButterKnife.bind(this);
        tag = getIntent().getExtras().getInt("123");
        Log.e("获取到--tag--", tag + "");
        setPDTitle(tag);//设置页面标题
    }

    @Override
    protected void initData() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        fragments.add(new DownLoadPushFragment());
        fragments.add(new ChooseFragment());
        titles.add("下载单据");
        titles.add("选择单据");
        StripAdapter stripAdapter = new StripAdapter(getSupportFragmentManager(), fragments, titles);
        Log.e("stripAdapter", stripAdapter + "");
        viewpager.setAdapter(stripAdapter);
        tabstrip.setShouldExpand(true);
        tabstrip.setViewPager(viewpager);
        tabstrip.setDividerColor(cpb_blue);
        tabstrip.setUnderlineHeight(3);
        tabstrip.setIndicatorHeight(6);
        tabstrip.setIndicatorColor(cpb_blue);

    }

    @Override
    protected void initListener() {

    }

//    //接收
//    Intent intent = getIntent();
//        if (intent != null) {
//        autoCode =   intent.getStringExtra("code");
//        fidcontainer = intent.getStringArrayListExtra("fid");
//        Lg.e("Intent:"+autoCode);
//        Lg.e("Intent:"+fidcontainer.toString());
//    }
//    //代数据启动页面
//    public static void start(Context context, String code, ArrayList<String> fid) {
//        Intent starter = new Intent(context, PushDownPagerActivity.class);
//        starter.putExtra("code", code);
//        starter.putStringArrayListExtra("fid", fid);
//        context.startActivity(starter);
//    }

    @Override
    protected void OnReceive(String code) {
        Log.e("code:", "PushDownPagerActivity-" + code);
    }

    public int getTitles() {
        return tag;
    }

    //根据tag设置头部标题
    private void setPDTitle(int tag) {
        String string = "";
        switch (tag) {
            case 1:
                string = "采购订单下推外购入库单";
                break;
            case 2:
                string = "销售订单下推销售出库单";
                break;
            case 3:
                string = "销售订单下推销售退货单";
                break;
            case 4:
                string = "销售出库单下推销售退货单";
                break;
            case 5:
                string = "发货通知单下推销售出库单";
                break;
            case 6:
                string = "退货通知单下推销售退货单";
                break;
            case 7:
                string = "调拨申请单下推分布式调入单";
                break;
            case 8:
                string = "调拨申请单下推分布式调出单";
                break;
        }
        if (!"".equals(string)){
            tvPdname.setText(string);
        }else{
            tvPdname.setVisibility(View.GONE);
        }
    }
}
