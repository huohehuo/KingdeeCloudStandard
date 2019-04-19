package com.fangzuo.assist.cloud.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.fangzuo.assist.cloud.ABase.BaseFragment;
import com.fangzuo.assist.cloud.Activity.CheckStoreActivity;
import com.fangzuo.assist.cloud.Activity.OtherInStoreActivity;
import com.fangzuo.assist.cloud.Activity.OtherOutStoreActivity;
import com.fangzuo.assist.cloud.Activity.PDActivity;
import com.fangzuo.assist.cloud.Activity.PagerForActivity;
import com.fangzuo.assist.cloud.Activity.PrintBeforeDataActivity;
import com.fangzuo.assist.cloud.Activity.PrintHistoryActivity;
import com.fangzuo.assist.cloud.Activity.PurchaseInStoreActivity;
import com.fangzuo.assist.cloud.Activity.PurchaseOrderActivity;
import com.fangzuo.assist.cloud.Activity.PushDownActivity;
import com.fangzuo.assist.cloud.Activity.PushDownPagerActivity;
import com.fangzuo.assist.cloud.Activity.SaleOrderActivity;
import com.fangzuo.assist.cloud.Activity.SaleOutActivity;
import com.fangzuo.assist.cloud.Activity.ScanProductActivity;
import com.fangzuo.assist.cloud.Adapter.GridViewAdapter;
import com.fangzuo.assist.cloud.Beans.SettingList;
import com.fangzuo.assist.cloud.R;
import com.fangzuo.assist.cloud.Utils.Config;
import com.fangzuo.assist.cloud.Utils.GetSettingList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;



public class PurchaseFragment extends BaseFragment {
    @BindView(R.id.gv)
    GridView gv;
    Unbinder unbinder;
    private FragmentActivity mContext;

    public PurchaseFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_purchase, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void initView() {
        mContext = getActivity();

    }

    @Override
    protected void OnReceive(String barCode) {

    }

    GridViewAdapter ada;

    @Override
    protected void initData() {
//        String getPermit=share.getString(ShareInfo.USER_PERMIT);
//        String[] arylist = getPermit.split("\\-"); // 这样才能得到正确的结果
        ada = new GridViewAdapter(mContext, GetSettingList.getPurchaseList());
        gv.setAdapter(ada);
        ada.notifyDataSetChanged();
    }

    private AlertDialog.Builder builder;
    String[] items_sout = new String[]{"原单", "销售订单下推销售出库单","退货通知单下推销售退货单"};
    String[] items_tb = new String[]{"挑板领料", "挑板入库"};
    String[] items_gb = new String[]{"改板领料", "改板入库"};
    String[] items_dc = new String[]{"代存出库", "代存入库"};
    String[] items_in_out = new String[]{"样板出库", "第三方货物入库","第三方货物出库"};
    @Override
    protected void initListener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SettingList tv = (SettingList) ada.getItem(i);
                Log.e("listitem", tv.tv);
                switch (tv.tv) {
                    case "采购入库"://实际是采购订单下推外购入库
                        PushDownPagerActivity.start(getActivity(),1);
                        break;
//                    case "采购入库":
//                        PagerForActivity.start(mContext, Config.PurchaseInStoreActivity);
//                        startNewActivity(PurchaseInStoreActivity.class, null);
//                        break;
                    case "调拨单":
                        PagerForActivity.start(mContext, Config.DBActivity);
//                        startNewActivity(DBActivity.class, null);
                        break;
                    case "标签补打":
                        startNewActivity(PrintHistoryActivity.class, null);
                        break;
                    case "期初物料补打":
                        startNewActivity(PrintBeforeDataActivity.class, null);
                        break;
                    case "到货入库":
                        PagerForActivity.start(mContext, Config.DhInActivity);
                        break;
                    case "销售出库":
                        // 创建对话框构建器
                        builder = new AlertDialog.Builder(getActivity());
                        // 设置参数
                        builder.setAdapter(
                                new ArrayAdapter<String>(getActivity(),
                                        R.layout.item_choose, R.id.textView, items_sout),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        switch (which) {
                                            case 0:
                                                PagerForActivity.start(mContext, Config.SaleOutActivity);
//                                                startNewActivity(SaleOutActivity.class, null);
                                                break;
                                            case 1:
                                                PushDownPagerActivity.start(getActivity(),2);
//                                                Bundle b = new Bundle();
//                                                b.putInt("123", 2);
////                                                startNewActivity(PushDownPagerActivity.class, R.anim.activity_fade_in, R.anim.activity_fade_out, false, b);
//                                                startNewActivity(PushDownPagerActivity.class, b);
                                                break;
                                            case 2:
                                                PushDownPagerActivity.start(getActivity(),6);
                                                break;
                                        }
                                    }
                                });
                        builder.create().show();
//                        builder.setTitle("选择").setAdapter(new ArrayAda)
//                                .setItems(items,  new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        switch (which){
//                                            case 0:
//                                                startNewActivity(SaleOutActivity.class, null);
//                                                break;
//                                            case 1:
//                                                startNewActivity(SaleOutActivity.class, null);
//                                                break;
//                                        }
//                                    }
//                                });
//                        builder.create().show();
                        break;
                    case "挑板业务":
                        builder = new AlertDialog.Builder(getActivity());
                        // 设置参数
                        builder.setAdapter(
                                new ArrayAdapter<String>(getActivity(),
                                        R.layout.item_choose, R.id.textView, items_tb),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        switch (which) {
                                            case 0:
                                                PagerForActivity.start(mContext, Config.TbGetActivity);
                                                break;
                                            case 1:
                                                PagerForActivity.start(mContext, Config.TbInActivity);
                                                break;
                                        }
                                    }
                                });
                        builder.create().show();
                        break;
                    case "改板业务":
                        builder = new AlertDialog.Builder(getActivity());
                        // 设置参数
                        builder.setAdapter(
                                new ArrayAdapter<String>(getActivity(),
                                        R.layout.item_choose, R.id.textView, items_gb),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        switch (which) {
                                            case 0:
                                                PagerForActivity.start(mContext, Config.GbGetActivity);
                                                break;
                                            case 1:
                                                PagerForActivity.start(mContext, Config.GbInActivity);
                                                break;
                                        }
                                    }
                                });
                        builder.create().show();
                        break;
                    case "代存业务":
                        builder = new AlertDialog.Builder(getActivity());
                        // 设置参数
                        builder.setAdapter(
                                new ArrayAdapter<String>(getActivity(),
                                        R.layout.item_choose, R.id.textView, items_dc),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        switch (which) {
                                            case 0:
                                                PagerForActivity.start(mContext, Config.DcOutActivity);
                                                break;
                                            case 1:
                                                PagerForActivity.start(mContext, Config.DcInActivity);
                                                break;
                                        }
                                    }
                                });
                        builder.create().show();
                        break;
                    case "其他出库":
//                        startNewActivity(OtherOutStoreActivity.class, null);
                        PagerForActivity.start(mContext, Config.OtherOutStoreActivity);
                        break;

                    case "其他入库":
//                        startNewActivity(OtherInStoreActivity.class, null);
                        PagerForActivity.start(mContext, Config.OtherInStoreActivity);
                        break;

                    case "其他出入库":
                        builder = new AlertDialog.Builder(getActivity());
                        // 设置参数
                        builder.setAdapter(
                                new ArrayAdapter<String>(getActivity(),
                                        R.layout.item_choose, R.id.textView, items_in_out),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        switch (which) {
                                            case 0:
                                                PagerForActivity.start(mContext, Config.YbOutActivity);
                                                break;
                                            case 1:
                                                PagerForActivity.start(mContext, Config.HwIn3Activity);
                                                break;
                                            case 2:
                                                PagerForActivity.start(mContext, Config.HwOut3Activity);
                                                break;
                                        }
                                    }
                                });
                        builder.create().show();
                        break;



                    case "库存查询":
                        startNewActivity(CheckStoreActivity.class, null);
                        break;
                    case "盘点":
                        startNewActivity(PDActivity.class, null);
                        break;
                    case "单据下推":
                        startNewActivity(PushDownActivity.class, null);
                        break;
                    case "采购订单":
                        startNewActivity(PurchaseOrderActivity.class, null);
                        break;

                    case "销售订单":
                        startNewActivity(SaleOrderActivity.class, null);
                        break;


//                    case 1://外购入库
//                        startNewActivity(PurchaseInStoreActivity.class, null);
//                        break;
//                    case 2://产品入库
////                        startNewActivity(ProductInStorageActivity.class, null);
//                        break;
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
