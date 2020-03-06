package com.fangzuo.assist.cloud.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fangzuo.assist.cloud.Dao.T_Detail;
import com.fangzuo.assist.cloud.Dao.T_main;
import com.fangzuo.assist.cloud.R;
import com.fangzuo.assist.cloud.Utils.Config;
import com.fangzuo.assist.cloud.Utils.GreedDaoUtil.GreenDaoManager;
import com.fangzuo.greendao.gen.DaoSession;
import com.fangzuo.greendao.gen.T_mainDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NB on 2017/8/3.
 */

public class ReViewReAddAdapter extends BaseAdapter implements View.OnClickListener {
    Context context;
    List<T_Detail> detail;
    private ViewHolder viewHolder;
    InnerClickListener mListener;
    List<Boolean> isCheck;

    public ReViewReAddAdapter(Context context, List<T_Detail> detail, List<Boolean> isCheck) {
        this.context = context;
        this.detail = detail;
        this.isCheck = isCheck;
    }

    @Override
    public int getCount() {
        return detail.size();
    }

    @Override
    public Object getItem(int i) {
        return detail.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_review_readd_list, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        DaoSession daoSession = GreenDaoManager.getmInstance(context).getDaoSession();
        T_mainDao t_mainDao = daoSession.getT_mainDao();
        List<T_main> mains = t_mainDao.queryBuilder().where(
                T_mainDao.Properties.FOrderId.eq(detail.get(i).FOrderId),
                T_mainDao.Properties.Activity.eq(detail.get(i).activity)).build().list();


//            if(t_mains.get(0).activity== 1){
//                viewHolder.llWavehosue.setVisibility(View.VISIBLE);
//                viewHolder.productname.setText("单据编号:" + t_mains.get(0).orderId);
//                viewHolder.productId.setText("物料编码:" + detail.get(i).FProductCode);
//                viewHolder.productxh.setText("调入仓库:" + detail.get(i).FStorage);
//                viewHolder.num.setText("数量:" + detail.get(i).FQuantity+detail.get(i).FUnit);
//                viewHolder.price.setText("价格:" + detail.get(i).FTaxUnitPrice);
//                viewHolder.storage.setText("调出仓库:" + detail.get(i).FoutStoragename);
//                viewHolder.date.setText("物料名:" + detail.get(i).FProductName);

        viewHolder.productname.setText("物料名称:" + detail.get(i).FProductName);
        viewHolder.productNumber.setText("物料编码:" + detail.get(i).FMaterialId);
//                viewHolder.productxh.setText("物料名:" + detail.get(i).FProductName);
        viewHolder.num.setText("采购数量:" + detail.get(i).FRemainInStockQty + detail.get(i).FUnit);
        viewHolder.unit.setText("单位:" + detail.get(i).FUnit);
        if (detail.get(i).activity== Config.DBActivity||detail.get(i).activity== Config.DB2Activity||detail.get(i).activity== Config.DBCopy2P2Activity||
                detail.get(i).activity== Config.DBClientActivity||detail.get(i).activity== Config.DBStorageActivity||
                detail.get(i).activity== Config.PdDbApply2DBActivity||detail.get(i).activity== Config.PdDbApply2DB4VMIActivity){
            viewHolder.storage.setVisibility(View.GONE);
            viewHolder.wavehouse.setVisibility(View.GONE);
            viewHolder.storagein.setVisibility(View.VISIBLE);
            viewHolder.storageout.setVisibility(View.VISIBLE);
//            viewHolder.wavehouseout.setVisibility(View.VISIBLE);
//            viewHolder.wavehousein.setVisibility(View.VISIBLE);
            viewHolder.storageout.setText("调出仓库:" + detail.get(i).FStorageOut);
            viewHolder.storagein.setText("调入仓库:" + detail.get(i).FStorageIn);
//            viewHolder.wavehouseout.setText("调出仓位:" + detail.get(i).FWaveHouseOut == null ? "" : detail.get(i).FWaveHouseOut);
//            viewHolder.wavehousein.setText("调入仓位:" + detail.get(i).FWaveHouseIn == null ? "" : detail.get(i).FWaveHouseIn);
        }else{
            viewHolder.storage.setText("仓库:" + detail.get(i).FStorage);
            viewHolder.wavehouse.setText("仓位:" + detail.get(i).FWaveHouse == null ? "" : detail.get(i).FWaveHouse);
        }
        viewHolder.tvModel.setText("规格:" + detail.get(i).model);
//        if (detail.get(i).activity==Config.ProductInStoreActivity||detail.get(i).activity==Config.TbInActivity
//                ||detail.get(i).activity==Config.DgInActivity||detail.get(i).activity==Config.SimpleInActivity){
            viewHolder.tvStorenum.setText("数量:" + detail.get(i).FRealQty+" "+detail.get(i).FStoreUnit);
//            viewHolder.tvBasenum.setText("基本数量:" + detail.get(i).FBaseNum+" "+detail.get(i).FBaseUnit);
//        }else{
//            viewHolder.tvStorenum.setText("添加数量:" + detail.get(i).FRemainInStockQty + detail.get(i).FUnit);
//        }

        if (mains.size()>0){
            viewHolder.date.setText("入库日期:" + mains.get(0).FDate);
        }
//            }

        viewHolder.delete.setOnClickListener(this);
        viewHolder.delete.setTag(i);
        viewHolder.fix.setOnClickListener(this);
        viewHolder.fix.setTag(i);
        viewHolder.cbIscheck.setChecked(isCheck.get(i));

        return view;
    }

    @Override
    public void onClick(View view) {
        mListener.InOnClick(view);
    }

    public void setInnerListener(InnerClickListener mListener) {
        this.mListener = mListener;
    }

    public interface InnerClickListener {
        void InOnClick(View v);
    }


    class ViewHolder {
        @BindView(R.id.cb_ischeck)
        CheckBox cbIscheck;
        @BindView(R.id.productname)
        TextView productname;
        @BindView(R.id.tv_storenum)
        TextView tvStorenum;
        @BindView(R.id.tv_model)
        TextView tvModel;
        @BindView(R.id.tv_basenum)
        TextView tvBasenum;
        @BindView(R.id.productNumber)
        TextView productNumber;
        @BindView(R.id.storageout)
        TextView storageout;
//        @BindView(R.id.wavehouseout)
//        TextView wavehouseout;
        @BindView(R.id.storagein)
        TextView storagein;
//        @BindView(R.id.wavehousein)
//        TextView wavehousein;
        @BindView(R.id.storage)
        TextView storage;
        @BindView(R.id.wavehouse)
        TextView wavehouse;
        @BindView(R.id.unit)
        TextView unit;
        @BindView(R.id.num)
        TextView num;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.delete)
        Button delete;
        @BindView(R.id.fix)
        Button fix;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}