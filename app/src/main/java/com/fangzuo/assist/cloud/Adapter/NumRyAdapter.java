package com.fangzuo.assist.cloud.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangzuo.assist.cloud.Activity.Crash.App;
import com.fangzuo.assist.cloud.R;
import com.fangzuo.assist.cloud.Utils.Lg;
import com.fangzuo.assist.cloud.widget.SquareRelativeLayout;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;

public class NumRyAdapter extends RecyclerArrayAdapter<String> {
    Context context;
    public ArrayList<String> items;
    public String clickNum="0";
    public NumRyAdapter(Context context) {
        super(context);
//        this.items = items;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        MarkHolder markHolder = new MarkHolder(parent);
        Lg.e("onCreateView?????"+clickNum);
        if (markHolder.name.getText().toString().equals(clickNum)){
            Lg.e("相同click");
            if (Build.VERSION.SDK_INT >= 21) {
                markHolder.bg.setBackground(App.getInstance().getDrawable(R.drawable.circlr_clicked));
            }
        }else{
            if (Build.VERSION.SDK_INT >= 21) {
                markHolder.bg.setBackground(App.getInstance().getDrawable(R.drawable.circlr_clicked));
            }
        }
        return markHolder;
    }
    class MarkHolder extends BaseViewHolder<String> {

        private TextView name;
        private RelativeLayout bg;
        public MarkHolder(ViewGroup parent) {
            super(parent, R.layout.item_rv2_text);
            name= $(R.id.textView);
            bg= $(R.id.bg_num);
//            checkBox = $(R.id.view_cb);
            Lg.e("BaseViewHolder?????"+clickNum);
            if (name.getText().toString().equals(clickNum)){
                if (Build.VERSION.SDK_INT >= 21) {
                    bg.setBackground(App.getInstance().getDrawable(R.drawable.circlr_clicked));
                }
            }else{
                if (Build.VERSION.SDK_INT >= 21) {
                    bg.setBackground(App.getInstance().getDrawable(R.drawable.circlr_clicked));
                }
            }
        }

        @Override
        public void setData(String s) {
            super.setData(s);
            name.setText(s);
        }
    }
    public void setColor(String s){
        clickNum=s;
        notifyDataSetChanged();
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
