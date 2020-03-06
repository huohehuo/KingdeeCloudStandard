package com.fangzuo.assist.cloud.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fangzuo.assist.cloud.Dao.PushDownMain;
import com.fangzuo.assist.cloud.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NB on 2017/8/24.
 */

public class PushDownListAdapter extends BaseAdapter {
    Context context;
    List<PushDownMain> items;
    private ArrayList<Boolean> isCheck;
    private ArrayList<String> strings;

    public PushDownListAdapter(Context context, List<PushDownMain> items, ArrayList<Boolean> isCheck) {
        this.context = context;
        this.items = items;
        this.isCheck = isCheck;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.pushdown_main_list, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.cbIscheck.setClickable(false);
        viewHolder.cbIscheck.setChecked(isCheck.get(i));
        viewHolder.tvCode.setText(items.get(i).FBillNo);
        viewHolder.tvSupplier.setText(items.get(i).FSupply);
        if ("29".equals(items.get(i).tag+"") || "33".equals(items.get(i).tag+"") ){
            viewHolder.tvCarNo.setVisibility(View.VISIBLE);
            viewHolder.tvCarNo.setText("车厢柜号："+items.get(i).FCarBoxNo);
        }
        if ("27".equals(items.get(i).tag+"")){
            viewHolder.tvCj.setVisibility(View.VISIBLE);
            viewHolder.tvCarNo.setVisibility(View.VISIBLE);
            viewHolder.tvCarNo.setText("车厢柜号："+(items.get(i).FCarBoxNo));
            viewHolder.tvCj.setText("生产车间："+items.get(i).FWordShop);
        }
        if ("35".equals(items.get(i).tag+"")){
            viewHolder.tvCj.setVisibility(View.VISIBLE);
            viewHolder.tvCj.setText("采购组织："+items.get(i).FDept);
        }
        viewHolder.tvDate.setText(items.get(i).FDate);
        if (null!=strings && strings.size()>0){
            for (int j = 0; j < strings.size(); j++) {
                if (strings.get(j).equals(items.get(i).FBillNo)){
                    viewHolder.tvDowned.setText("已下载");
                    break;
                }else{
                    viewHolder.tvDowned.setText("");
                }
            }
        }
        return view;
    }
    //标识已有单据
    public void setDownList(ArrayList<String> list){
        strings = list;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.cb_ischeck)
        CheckBox cbIscheck;
        @BindView(R.id.tv_code)
        TextView tvCode;
        @BindView(R.id.tv_supplier)
        TextView tvSupplier;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_car_no)
        TextView tvCarNo;
        @BindView(R.id.tv_cj)
        TextView tvCj;
        @BindView(R.id.tv_down)
        TextView tvDowned;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);

        }
    }
}
