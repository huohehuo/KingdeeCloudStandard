package com.fangzuo.assist.cloud.Fragment.TabForActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.fangzuo.assist.cloud.ABase.BaseFragment;
import com.fangzuo.assist.cloud.Activity.PagerForActivity;
import com.fangzuo.assist.cloud.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.cloud.Dao.Org;
import com.fangzuo.assist.cloud.R;
import com.fangzuo.assist.cloud.Utils.CommonUtil;
import com.fangzuo.assist.cloud.Utils.EventBusInfoCode;
import com.fangzuo.assist.cloud.Utils.EventBusUtil;
import com.fangzuo.assist.cloud.Utils.Info;
import com.fangzuo.assist.cloud.widget.SpinnerDepartMent;
import com.fangzuo.assist.cloud.widget.SpinnerEmployee;
import com.fangzuo.assist.cloud.widget.SpinnerOrg;
import com.fangzuo.assist.cloud.widget.SpinnerStoreMan;
import com.orhanobut.hawk.Hawk;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


//选择单据信息Fragment（所属：PushDownPagerActivity);
public class FragmentPrGetMain extends BaseFragment {


    @BindView(R.id.cb_isStorage)
    CheckBox cbIsStorage;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.sp_department)
    SpinnerDepartMent spDepartment;
    @BindView(R.id.sp_org_send)
    SpinnerOrg spOrgSend;
    @BindView(R.id.sp_org_create)
    SpinnerOrg spOrgCreate;
    @BindView(R.id.sp_org_huozhu)
    SpinnerOrg spOrgHuozhu;
    @BindView(R.id.sp_getman)
    SpinnerEmployee spGetman;
    @BindView(R.id.sp_storeman)
    SpinnerStoreMan spStoreman;
    @BindView(R.id.ed_not)
    EditText edNot;
    @BindView(R.id.ed_ff_order)
    EditText edFfOrder;
    private FragmentActivity mContext;
    private PagerForActivity activityPager;
    Unbinder unbinder;

    public FragmentPrGetMain() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityPager = ((PagerForActivity) activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prgetmain, container, false);
        unbinder = ButterKnife.bind(this, view);
        mContext = getActivity();
        return view;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void OnReceive(String barCode) {

    }

    @Override
    protected void initData() {
        tvDate.setText(CommonUtil.getTime(true));
        //第一个参数用于保存上一个值，第二个为自动跳转到该默认值
        spOrgCreate.setAutoSelection(getString(R.string.spOrgCreate_pg), Hawk.get(Info.user_org,""));
        spOrgSend.setAutoSelection(getString(R.string.spOrgSend_pg), Hawk.get(Info.user_org,""));
        spOrgHuozhu.setAutoSelection(getString(R.string.spOrgHuozhu_pg), Hawk.get(Info.user_org,""));
        spGetman.setAuto(getString(R.string.spBuyer_pg), "",activityPager.getOrgOut());
        spDepartment.setAuto(getString(R.string.spDepartmentCreate_pg), "",activityPager.getOrgOut(),activityPager.getActivity());
        spStoreman.setAuto(getString(R.string.spStoreman_pg), "",activityPager.getOrgOut());

//        binding.spOrgIn.setEnable(false);
//        binding.spOrgCreate.setEnable(false);
        cbIsStorage.setChecked(Hawk.get(Info.Storage + activityPager.getActivity(), false));
        setfocus(tvDate);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
        } else {
            //相当于Fragment的onPause
            if (null!=activityPager){
                activityPager.setDate(tvDate==null?"":tvDate.getText().toString());
                activityPager.setNote(edNot==null?"":edNot.getText().toString());
                activityPager.setManStore(spStoreman.getDataNumber());
                activityPager.setManGet(spGetman.getDataNumber());
                activityPager.setDepartMent(spDepartment.getDataNumber());
                activityPager.setFOrderNo(edFfOrder == null ? "" : edFfOrder.getText().toString());
            }
        }
    }


    @Override
    protected void initListener() {
        spOrgSend.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                activityPager.setOrgOut((Org) spOrgSend.getAdapter().getItem(i));
                spDepartment.setAuto(getString(R.string.spDepartmentCreate_pg), "",activityPager.getOrgOut(),activityPager.getActivity());
                spGetman.setAuto(getString(R.string.spBuyer_pg), "",activityPager.getOrgOut());
                spStoreman.setAuto(getString(R.string.spStoreman_pg), "",activityPager.getOrgOut());
                EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.UpdataView,""));
            }
        });
        spOrgCreate.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                activityPager.setOrgIn((Org) spOrgCreate.getAdapter().getItem(i));
            }
        });
        spOrgHuozhu.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                activityPager.setHuozhuOut((Org) spOrgHuozhu.getAdapter().getItem(i));
            }
        });
        cbIsStorage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                activityPager.setStorage(b);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Hawk.put(Info.Storage+activityPager,cbIsStorage.isChecked());
        unbinder.unbind();
    }


    @OnClick({R.id.tv_date, R.id.ed_not})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_date:
                datePicker(tvDate);
                break;
            case R.id.ed_not:
                break;
        }
    }
}
