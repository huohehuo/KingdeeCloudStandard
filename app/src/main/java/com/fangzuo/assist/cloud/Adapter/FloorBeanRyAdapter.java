package com.fangzuo.assist.cloud.Adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangzuo.assist.cloud.Beans.FloorBean;
import com.fangzuo.assist.cloud.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;

public class FloorBeanRyAdapter extends RecyclerArrayAdapter<FloorBean> {
    Context context;
    private ArrayList<String> strings;
    private String changeColor;
    //标识已有单据
    public void setCheckList(ArrayList<String> list){
        strings = list;
        notifyDataSetChanged();
    }
    public void setCheckPosition(String list){
        changeColor = list;
        notifyDataSetChanged();
    }
    public FloorBeanRyAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MarkHolder(parent);
    }
    class MarkHolder extends BaseViewHolder<FloorBean> {

        private TextView item1;
        private TextView item2;
        private TextView item3;
//        private TextView item4;
//        private TextView item5;
//        private TextView item6;
//        private TextView item7;
        private CheckBox checkBox;
        private RelativeLayout rlBg;
//        private TextView item3;
//        private TextView item4;
        public MarkHolder(ViewGroup parent) {
            super(parent, R.layout.floorbean_list);
            item1= $(R.id.tv_item1);
            item2= $(R.id.tv_item2);
            item3= $(R.id.tv_item3);
//            item4= $(R.id.tv_item4);
//            item5= $(R.id.tv_item5);
//            item6= $(R.id.tv_item6);
//            item7= $(R.id.tv_item7);
            checkBox= $(R.id.cb_ischeck);
            rlBg= $(R.id.rl_bg);
//            item3= $(R.id.tv_item3);
//            item4= $(R.id.tv_item4);
//            checkBox = $(R.id.view_cb);
        }

        @Override
        public void setData(FloorBean data) {
            super.setData(data);
            item1.setText("批号："+data.FBatchNo);
            item2.setText("库存数量："+data.FQty);
            item3.setText("已添加："+(data.FQtying==null?"0":data.FQtying));

//            item4.setText("数量："+data.FQty);
//            item5.setText("已打印数量："+data.FPrintedQty);
//            item6.setText("计划跟踪号："+data.FMTONO);
//            item7.setText("辅助属性："+data.FAuxAttr);
            checkBox.setChecked(data.isClick);
            if (null!=changeColor && changeColor.equals(data.FBatchNo)){
                rlBg.setBackgroundColor(context.getResources().getColor(R.color.cpb_blue));
            }else{
                rlBg.setBackgroundColor(context.getResources().getColor(R.color.white));
            }
//            if (null!=strings && strings.size()>0){
//                for (int j = 0; j < strings.size(); j++) {
//                    if (strings.get(j).equals(data.FNumber)){
//                        rlBg.setBackgroundColor(context.getResources().getColor(R.color.print_checked));
//                        break;
//                    }else{
//                        rlBg.setBackgroundColor(context.getResources().getColor(R.color.white));
//                    }
//                }
//            }else{
//                rlBg.setBackgroundColor(context.getResources().getColor(R.color.white));
//            }
//            item3.setText("总行数"+data.FNumAll);
//            item4.setText(data.FBillNo);
        }
    }


//    //纯文字布局
//    class MainHolderForTxt extends BaseViewHolder<PlanBean> {
//
//        private TextView time;
//        private TextView eesay;
//        private ImageView favour;
//        private TextView num;
//        public MainHolderForTxt(ViewGroup parent) {
//            super(parent, R.layout.item_plan_for_txt);
//            time = $(R.id.tv_time);
//            eesay = $(R.id.tv_main_essay);
//            num = $(R.id.tv_favour);
//            favour = $(R.id.iv_favour);
//        }
//
//        @Override
//        public void setData(PlanBean data) {
//            super.setData(data);
//            eesay.setText(data.getEssay());
//            time.setText(data.getCreatedAt());
////            num.setText(data.getFavour().get__op());
//
//            favour.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(App.getContext(), "喜欢+1", Toast.LENGTH_SHORT).show();
//                }
//            });
//
////            Glide.with(getContext())
////                    .load(data.getPic())
////                    .placeholder(R.mipmap.ic_launcher)
////                    .centerCrop()
////                    .into(imageView);
//
//        }
//    }
}
