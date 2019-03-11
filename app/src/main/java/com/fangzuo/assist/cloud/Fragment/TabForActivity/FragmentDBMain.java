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
import com.fangzuo.assist.cloud.Beans.CommonBean;
import com.fangzuo.assist.cloud.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.cloud.Dao.Org;
import com.fangzuo.assist.cloud.R;
import com.fangzuo.assist.cloud.Utils.CommonUtil;
import com.fangzuo.assist.cloud.Utils.EventBusInfoCode;
import com.fangzuo.assist.cloud.Utils.EventBusUtil;
import com.fangzuo.assist.cloud.Utils.Info;
import com.fangzuo.assist.cloud.Utils.Lg;
import com.fangzuo.assist.cloud.widget.SpinnerCommon;
import com.fangzuo.assist.cloud.widget.SpinnerHuozhu;
import com.fangzuo.assist.cloud.widget.SpinnerOrg;
import com.fangzuo.assist.cloud.widget.SpinnerStoreMan;
import com.orhanobut.hawk.Hawk;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


//选择单据信息Fragment（所属：PushDownPagerActivity);
public class FragmentDBMain extends BaseFragment {


    @BindView(R.id.cb_isStorage)
    CheckBox cbIsStorage;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.sp_db_type)
    SpinnerCommon spDbType;
    @BindView(R.id.sp_db_direction)
    SpinnerCommon spDbDirection;
    @BindView(R.id.sp_org_out)
    SpinnerOrg spOrgOut;
    @BindView(R.id.sp_org_huozhu_out)
    SpinnerHuozhu spOrgHuozhuOut;
    @BindView(R.id.sp_org_in)
    SpinnerOrg spOrgIn;
    @BindView(R.id.sp_org_huozhu_in)
    SpinnerHuozhu spOrgHuozhuIn;
    @BindView(R.id.sp_storeman)
    SpinnerStoreMan spStoreman;
    @BindView(R.id.ed_not)
    EditText edNot;
    private FragmentActivity mContext;
    private PagerForActivity activityPager;
    Unbinder unbinder;
    private CommonBean commonBean;
    private boolean isTheSame = false;//用于判断是否为组织内调拨true

    public FragmentDBMain() {
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
        View view = inflater.inflate(R.layout.fragment_dbmain, container, false);
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
        spDbType.setData(Info.Type_DB_type);
        spDbDirection.setData(Info.Type_DB_direction);
        //第一个参数用于保存上一个值，第二个为自动跳转到该默认值
        spOrgOut.setAutoSelection(getString(R.string.spOrgOut_db), Hawk.get(Info.user_org, ""));
        spOrgHuozhuOut.setAutoSelection(getString(R.string.spOrgHuozhuOut_db),activityPager.getOrgOut(), Hawk.get(Info.user_org, ""));
        spStoreman.setAuto(getString(R.string.spStoreman_db), "", activityPager.getOrgOut());

        spOrgIn.setAutoSelection(getString(R.string.spOrgIn_db), Hawk.get(Info.user_org, ""));
        spOrgHuozhuIn.setAutoSelection(getString(R.string.spOrgHuozhuIn_db),activityPager.getOrgIn(), Hawk.get(Info.user_org, ""));

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
            if (null != activityPager) {
                activityPager.setDate(tvDate == null ? "" : tvDate.getText().toString());
                activityPager.setNote(edNot == null ? "" : edNot.getText().toString());
                activityPager.setManStore(spStoreman.getDataNumber());
            }
        }
    }


    @Override
    protected void initListener() {
        spDbType.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                commonBean = (CommonBean) spDbType.getAdapter().getItem(i);
                Lg.e("调拨类型：", commonBean);
                activityPager.setDBType(commonBean.FNumber);
                if (commonBean.FNumber.equals("InnerOrgTransfer")) {
                    isTheSame = true;
                    spOrgIn.setEnable(false);
                    spOrgHuozhuIn.setEnable(false);
                } else {
                    spOrgIn.setEnable(true);
                    spOrgHuozhuIn.setEnable(true);
                    isTheSame = false;
                }
            }
        });
        spDbDirection.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                CommonBean commonBean = (CommonBean) spDbType.getAdapter().getItem(i);
                activityPager.setDBDirection(commonBean.FNumber);
            }
        });

        spOrgOut.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                activityPager.setOrgOut((Org) spOrgOut.getAdapter().getItem(i));
                Lg.e("a调出组织：",(Org) spOrgOut.getAdapter().getItem(i));
                spOrgHuozhuOut.setAutoSelection("", activityPager.getOrgOut(),activityPager.getOrgOut().FName);
                spStoreman.setAuto(getString(R.string.spStoreman_db), "", activityPager.getOrgOut());
                if (isTheSame) {
                    spOrgIn.setAutoSelection("", activityPager.getOrgOut().FName);
                }
                EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.UpdataView, ""));
            }
        });
        spOrgIn.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                activityPager.setOrgIn((Org) spOrgIn.getAdapter().getItem(i));
                Lg.e("a调入组织：",(Org) spOrgIn.getAdapter().getItem(i));
                if (isTheSame){//当为组织内调拨时，调出入货主必须相同
                    spOrgHuozhuIn.setAutoSelection("", activityPager.getOrgIn(),activityPager.getOrgOut().FName);
                }else{
                    spOrgHuozhuIn.setAutoSelection("", activityPager.getOrgIn(),activityPager.getOrgIn().FName);
                }
                EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.UpdataViewForDBInStorage, ""));
            }
        });
        spOrgHuozhuOut.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                activityPager.setHuozhuOut((Org) spOrgHuozhuOut.getAdapter().getItem(i));
                if (isTheSame) {
                    spOrgHuozhuIn.setAutoSelection("", activityPager.getOrgIn(),activityPager.getHuozhuOut().FName);
                }
            }
        });
        spOrgHuozhuIn.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                activityPager.setHuozhuIn((Org) spOrgHuozhuIn.getAdapter().getItem(i));
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
        Hawk.put(Info.Storage + activityPager, cbIsStorage.isChecked());
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
