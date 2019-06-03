package com.fangzuo.assist.cloud.Fragment.TabForActivity.TbManagerBox;

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
import com.fangzuo.assist.cloud.Dao.Storage;
import com.fangzuo.assist.cloud.R;
import com.fangzuo.assist.cloud.Utils.CommonUtil;
import com.fangzuo.assist.cloud.Utils.Config;
import com.fangzuo.assist.cloud.Utils.EventBusInfoCode;
import com.fangzuo.assist.cloud.Utils.EventBusUtil;
import com.fangzuo.assist.cloud.Utils.Info;
import com.fangzuo.assist.cloud.Utils.Lg;
import com.fangzuo.assist.cloud.Utils.LocDataUtil;
import com.fangzuo.assist.cloud.widget.NumberClick;
import com.fangzuo.assist.cloud.widget.SpinnerDepartMent;
import com.fangzuo.assist.cloud.widget.SpinnerHuozhu;
import com.fangzuo.assist.cloud.widget.SpinnerOrg;
import com.fangzuo.assist.cloud.widget.SpinnerStorage;
import com.fangzuo.assist.cloud.widget.SpinnerStoreMan;
import com.orhanobut.hawk.Hawk;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


//选择单据信息Fragment（所属：PushDownPagerActivity);
public class FragmentPrisTBMain extends BaseFragment {

    @BindView(R.id.cb_isStorage)
    CheckBox cbIsStorage;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.sp_org_in)
    SpinnerOrg spOrgIn;
    @BindView(R.id.sp_org_create)
    SpinnerHuozhu spOrgCreate;
//    @BindView(R.id.sp_org_huozhu)
//    SpinnerOrg spOrgHuozhu;
    @BindView(R.id.sp_storeman)
    SpinnerStoreMan spStoreman;
    @BindView(R.id.ed_not)
    EditText edNot;
    @BindView(R.id.ed_ff_order)
    EditText edFfOrder;
    @BindView(R.id.sp_department_get)
    SpinnerDepartMent spDepartmentGet;
    @BindView(R.id.sp_which_storage)
    SpinnerStorage spWhichStorage;
    @BindView(R.id.cb_num)
    NumberClick cbNum;
    private FragmentActivity mContext;
    private PagerForActivity activityPager;
    Unbinder unbinder;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveEvent(ClassEvent event) {
        switch (event.Msg) {
            case EventBusInfoCode.UpdataStorage:
                String id = (String) event.postEvent;
                Lg.e("更改仓库数据"+id);
                spWhichStorage.setAuto(getString(R.string.spWhichStorage_pris_tb)+activityPager.getActivity(),id, activityPager.getOrgOut());
                break;
            case EventBusInfoCode.Lock_Main://是否锁住表头
                String lock = (String) event.postEvent;
                if (Config.Lock.equals(lock)){          //锁住表头数据
                    activityPager.setHasLock(true);
                    spDepartmentGet.setEnable(false);
                    spOrgCreate.setEnable(false);
                    spOrgIn.setEnable(false);
//                    spOrgHuozhu.setEnable(false);
                    spStoreman.setEnable(false);
                    edFfOrder.setFocusable(false);
                    edNot.setFocusable(false);

                    edFfOrder.setText(Hawk.get(Config.OrderNo+activityPager.getActivity(),""));
                    edNot.setText(Hawk.get(Config.Note+activityPager.getActivity(),""));
                    Hawk.put(Config.OrderNo+activityPager.getActivity(),edFfOrder.getText().toString());//保存业务单号
                    Hawk.put(Config.Note+activityPager.getActivity(),edNot.getText().toString());//保存业务单号
                }else{
                    activityPager.setHasLock(false);
                    spDepartmentGet.setEnable(true);
                    spOrgCreate.setEnable(true);
                    spOrgIn.setEnable(true);
//                    spOrgHuozhu.setEnable(true);
                    spStoreman.setEnable(true);
                    edFfOrder.setFocusable(true);
                    edFfOrder.setFocusableInTouchMode(true);
                    edNot.setFocusable(true);
                    edNot.setFocusableInTouchMode(true);

                    edFfOrder.setText("");
                    edNot.setText("");
                    Hawk.put(Config.OrderNo+activityPager.getActivity(),"");//清空存储的业务单号
                    Hawk.put(Config.Note+activityPager.getActivity(),"");//清空存储的业务单号
                }
                break;
            case EventBusInfoCode.ScanResult://是否锁住表头
                Lg.e("收到信号。.......");
                break;

        }
    }

    public FragmentPrisTBMain() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {//与activity绑定
        super.onAttach(activity);
        activityPager = ((PagerForActivity) activity);
        Lg.e("Fg_M:"+"onAttach");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prismain, container, false);
        unbinder = ButterKnife.bind(this, view);
        mContext = getActivity();
        EventBusUtil.register(this);
        Lg.e("Fg_M:"+"onCreateView");
        return view;
    }

    @Override
    protected void initView() {
        Lg.e("Fg_M:"+"initView");
    }

    @Override
    protected void OnReceive(String barCode) {

    }

    @Override
    protected void initData() {
//判断是否有保存的业务单号，决定是否锁住表头
        if (!LocDataUtil.hasTDetail(activityPager.getActivity())){
            EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Lock_Main, Config.Lock+"NO"));
        }else{
            EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Lock_Main, Config.Lock));
        }

    }


    //在oncreateView之前使用 不要使用控件
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
            Lg.e("fragment显示");
        } else {
            Lg.e("fragment隐藏");
            //相当于Fragment的onPause
            if (null != activityPager) {
                Lg.e("保存值");
                activityPager.setDate(tvDate == null ? "" : tvDate.getText().toString());
                activityPager.setNote(edNot == null ? "" : edNot.getText().toString());
                activityPager.setFOrderNo(edFfOrder == null ? "" : edFfOrder.getText().toString());
                activityPager.setManStore(spStoreman.getDataNumber());
                activityPager.setDepartMent(spDepartmentGet.getDataNumber());
                activityPager.setPrintNum(cbNum.getNum());
                Hawk.put(Config.OrderNo+activityPager.getActivity(),edFfOrder.getText().toString());//保存业务单号
                Hawk.put(Config.Note+activityPager.getActivity(),edNot.getText().toString());//保存业务单号
            }
        }
    }


    @Override
    protected void initListener() {
        spWhichStorage.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                Storage storage =(Storage) spWhichStorage.getAdapter().getItem(i);
                activityPager.setStorage(storage);
                spWhichStorage.setTitleText(storage.FName);
                Hawk.put(Info.Storage+activityPager.getActivity(),storage.FName);
                EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.UpdataWaveHouse, storage));
                Lg.e("选中仓库：", storage);
            }
        });
        spOrgIn.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                activityPager.setOrgOut((Org) spOrgIn.getAdapter().getItem(i));
                Hawk.put(Info.OrgOut+activityPager.getActivity(),activityPager.getOrgOut().FName);
                spStoreman.setAuto(Info.StoreMan+activityPager.getActivity(), Hawk.get(Info.StoreMan+activityPager.getActivity(),""), activityPager.getOrgOut());
                spOrgCreate.setAutoSelection(Info.HuoZhu+activityPager.getActivity(),activityPager.getOrgOut(), "");
                spWhichStorage.setAuto(Info.Storage+activityPager.getActivity(),Hawk.get(Info.Storage+activityPager.getActivity(),""), activityPager.getOrgOut());
                EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.UpdataView, ""));
            }
        });
        spOrgCreate.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                activityPager.setOrgIn((Org) spOrgCreate.getAdapter().getItem(i));
                spDepartmentGet.setAuto(Info.Department+activityPager.getActivity(), Hawk.get(Info.Department+activityPager.getActivity(),""), activityPager.getOrgIn(), activityPager.getActivity());
                Hawk.put(Info.HuoZhu+activityPager.getActivity(),activityPager.getOrgIn().FName);
            }
        });
//        spOrgHuozhu.setOnItemSelectedListener(new ItemListener() {
//            @Override
//            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
//                activityPager.setHuozhuOut((Org) spOrgHuozhu.getAdapter().getItem(i));
//                Hawk.put(getString(R.string.spOrgHuozhu_pris),activityPager.getHuozhuOut().FName);
//            }
//        });
//        cbIsStorage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                activityPager.setStorage(b);
//            }
//        });

    }

    @OnClick({R.id.tv_date, R.id.sp_org_create})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_date:
                datePicker(tvDate);
//                activityPager.setNote(edNot.getText().toString());
                break;
            case R.id.sp_org_create:
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        //执行该方法时，Fragment处于活动状态，用户可与之交互。
        Lg.e("A-onResume");
        Lg.e("Fg_M:"+"initData");
        tvDate.setText(CommonUtil.getTime(true));
        cbNum.setSaveKey(activityPager.getActivity()+"printnum");
        activityPager.setDate(tvDate.getText().toString());
        edFfOrder.setText(Hawk.get(Config.OrderNo+activityPager.getActivity(),""));
        edNot.setText(Hawk.get(Config.Note+activityPager.getActivity(),""));
        //第一个参数用于保存上一个值，第二个为自动跳转到该默认值
        spOrgIn.setAutoSelection(Info.OrgOut+activityPager.getActivity(), Hawk.get(Info.OrgOut+activityPager.getActivity(), ""));//仓库，仓管员，部门都以组织id来过滤
//        spOrgCreate.setAutoSelection(Info.HuoZhu+activityPager.getActivity(), activityPager.getOrgOut(),Hawk.get(Info.HuoZhu+activityPager.getActivity(), ""));
//        spOrgHuozhu.setAutoSelection(getString(R.string.spOrgHuozhu_pris), Hawk.get(getString(R.string.spOrgHuozhu_pris), ""));
//        spStoreman.setAuto(Info.StoreMan+activityPager.getActivity(), Hawk.get(Info.StoreMan+activityPager.getActivity(),""), activityPager.getOrgOut());
//        spDepartmentGet.setAuto(Info.Department+activityPager.getActivity(), Hawk.get(Info.Department+activityPager.getActivity(),""), activityPager.getOrgIn(), activityPager.getActivity());
//        spWhichStorage.setAuto(Info.Storage+activityPager.getActivity(),Hawk.get(Info.Storage+activityPager.getActivity(),""), activityPager.getOrgOut());
//        binding.spOrgIn.setEnable(false);
//        spOrgCreate.setEnable(false);
//        cbIsStorage.setChecked(Hawk.get(Info.Storage + activityPager.getActivity(), false));

        setfocus(tvDate);
    }

    @Override
    public void onStart() {
        super.onStart();
        //执行该方法时，Fragment由不可见变为可见状态。
        Lg.e("A-onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        //执行该方法时，Fragment处于暂停状态，但依然可见，用户不能与之交互
        Lg.e("A-onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        //执行该方法时，Fragment完全不可见。
        Lg.e("A-onStop");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        //解除与Activity的绑定。在onDestroy方法之后调用。
        Lg.e("A-onDetach");
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存当前Fragment的状态。该方法会自动保存Fragment的状态，比如EditText键入的文本，即使Fragment被回收又重新创建，一样能恢复EditText之前键入的文本
        Lg.e("A-onSaveInstanceState");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Lg.e("A-onDestroy");
        try {
            EventBusUtil.unregister(this);
        } catch (Exception e) { }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Lg.e("A-onDestroyView");
        Hawk.put(Info.Storage + activityPager, cbIsStorage.isChecked());
        unbinder.unbind();
    }


}
