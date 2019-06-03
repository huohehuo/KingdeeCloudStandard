package com.fangzuo.assist.cloud.Fragment.TabForActivity.PDBox;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangzuo.assist.cloud.ABase.BaseFragment;
import com.fangzuo.assist.cloud.Activity.PagerForActivity;
import com.fangzuo.assist.cloud.Activity.ProductSearchActivity;
import com.fangzuo.assist.cloud.Beans.CommonBean;
import com.fangzuo.assist.cloud.Beans.EventBusEvent.ClassEvent;
import com.fangzuo.assist.cloud.Dao.Client;
import com.fangzuo.assist.cloud.Dao.Department;
import com.fangzuo.assist.cloud.Dao.Org;
import com.fangzuo.assist.cloud.Dao.Suppliers;
import com.fangzuo.assist.cloud.R;
import com.fangzuo.assist.cloud.Utils.CommonUtil;
import com.fangzuo.assist.cloud.Utils.Config;
import com.fangzuo.assist.cloud.Utils.EventBusInfoCode;
import com.fangzuo.assist.cloud.Utils.EventBusUtil;
import com.fangzuo.assist.cloud.Utils.Info;
import com.fangzuo.assist.cloud.Utils.Lg;
import com.fangzuo.assist.cloud.Utils.LocDataUtil;
import com.fangzuo.assist.cloud.widget.NumberClick;
import com.fangzuo.assist.cloud.widget.SpinnerCommon;
import com.fangzuo.assist.cloud.widget.SpinnerDepartMent;
import com.fangzuo.assist.cloud.widget.SpinnerDepartMentDlg;
import com.fangzuo.assist.cloud.widget.SpinnerHuozhu;
import com.fangzuo.assist.cloud.widget.SpinnerOrg;
import com.fangzuo.assist.cloud.widget.SpinnerStoreMan;
import com.orhanobut.hawk.Hawk;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


//选择单据信息Fragment（所属：PushDownPagerActivity);
public class FragmentPYingMain extends BaseFragment {


    @BindView(R.id.cb_isStorage)
    CheckBox cbIsStorage;
    @BindView(R.id.tv_date)
    TextView tvDate;
//    @BindView(R.id.sp_department)
//    SpinnerDepartMent spDepartment;
    @BindView(R.id.sp_department_dlg)
    SpinnerDepartMentDlg spDepartmentDlg;
    @BindView(R.id.sp_org_send)
    SpinnerOrg spOrgSend;
    @BindView(R.id.sp_org_huozhu)
    SpinnerHuozhu spOrgHuozhu;
    //    @BindView(R.id.sp_getman)
//    SpinnerEmployee spGetman;
    @BindView(R.id.sp_storeman)
    SpinnerStoreMan spStoreman;
    @BindView(R.id.ed_not)
    EditText edNot;
    @BindView(R.id.sp_hz_type)
    SpinnerCommon spHzType;
    @BindView(R.id.ll_supplier_hz)
    LinearLayout llSupplierHz;
    @BindView(R.id.ed_supplier_hz)
    EditText edSupplierHz;
    @BindView(R.id.search_supplier_hz)
    RelativeLayout searchSupplierHz;
    @BindView(R.id.ed_client)
    EditText edClient;
    @BindView(R.id.search_client)
    RelativeLayout searchClient;
    @BindView(R.id.ll_client)
    LinearLayout llClient;
    @BindView(R.id.tv_txt_detail)
    TextView tvTxtDetail;
    @BindView(R.id.ed_supplier_hz_detail)
    EditText edSupplierHzDetail;
    @BindView(R.id.search_supplier_hz_detail)
    RelativeLayout searchSupplierHzDetail;
    @BindView(R.id.ll_supplier_hz_detail)
    LinearLayout llSupplierHzDetail;
    @BindView(R.id.ed_client_detail)
    EditText edClientDetail;
    @BindView(R.id.search_client_detail)
    RelativeLayout searchClientDetail;
    @BindView(R.id.ll_client_detail)
    LinearLayout llClientDetail;
    @BindView(R.id.cb_num)
    NumberClick cbNum;
    private FragmentActivity mContext;
    private PagerForActivity activityPager;
    Unbinder unbinder;
    private CommonBean hzType;
    private Suppliers supplierHz;
    private Department department;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveEvent(ClassEvent event) {
        switch (event.Msg) {
            case EventBusInfoCode.Supplier_Hz:
                supplierHz = (Suppliers) event.postEvent;
                Lg.e("获得供应商Hz：", supplierHz);
                if (supplierHz.FItemID.equals("")) {
                    activityPager.setHuozhuOut(null);
                    edSupplierHz.setText("");
                } else {
                    activityPager.setHuozhuOut(new Org(supplierHz.FMASTERID, supplierHz.FNumber, supplierHz.FName, supplierHz.FNote));
                    edSupplierHz.setText(supplierHz.FName);
                    Hawk.put(Config.SupplierHz + activityPager.getActivity(), supplierHz.FName);//
                }
                break;
            case EventBusInfoCode.Client:
                Client client = (Client) event.postEvent;
                Lg.e("获得客户Hz：", client);
                if ("".equals(client.FName)) {
                    activityPager.setHuozhuOut(null);
                    edClient.setText("");
                } else {
                    activityPager.setHuozhuOut(new Org(client.FItemID, client.FNumber, client.FName, client.FOrg));
                    edClient.setText(client.FName);
                    Hawk.put(Config.ClientHz + activityPager.getActivity(), client.FName);//
                }
                break;
            case EventBusInfoCode.Supplier_Hz_Detail:
                supplierHz = (Suppliers) event.postEvent;
                Lg.e("获得供应商HzDt：", supplierHz);
                if (supplierHz.FItemID.equals("")) {
                    activityPager.setHuozhuIn(null);
                    edSupplierHzDetail.setText("");
                } else {
                    activityPager.setHuozhuIn(new Org(supplierHz.FMASTERID, supplierHz.FNumber, supplierHz.FName, supplierHz.FNote));
                    edSupplierHzDetail.setText(supplierHz.FName);
                    Hawk.put(Config.SupplierHzDetail + activityPager.getActivity(), supplierHz.FName);//
                }
                break;
            case EventBusInfoCode.Client_Detail:
                Client clientdt = (Client) event.postEvent;
                Lg.e("获得客户HzDt：", clientdt);
                if ("".equals(clientdt.FName)) {
                    activityPager.setHuozhuIn(null);
                    edClientDetail.setText("");
                } else {// TODO: 2019/5/20 0020 客户表要新增字段FMASTERID,替代下面的FItemID  PS:这里临时用FItemID代替FMASTERID,FOrg代替FNote
                    activityPager.setHuozhuIn(new Org(clientdt.FItemID, clientdt.FNumber, clientdt.FName, clientdt.FOrg));
                    edClientDetail.setText(clientdt.FName);
                    Hawk.put(Config.ClientHzDetail + activityPager.getActivity(), clientdt.FName);//
                }
                break;
            case EventBusInfoCode.Lock_Main:
                String lock = (String) event.postEvent;
                if (Config.Lock.equals(lock)) {
                    activityPager.setHasLock(true);
//                    spDepartment.setEnable(false);
                    spOrgSend.setEnable(false);
                    spOrgHuozhu.setEnable(false);
                    searchSupplierHz.setClickable(false);
                    searchClient.setClickable(false);
                    spHzType.setEnable(false);
                    spStoreman.setEnable(false);
                    edNot.setFocusable(false);

                    edNot.setText(Hawk.get(Config.Note + activityPager.getActivity(), ""));
                    Hawk.put(Config.Note + activityPager.getActivity(), edNot.getText().toString());//保存业务单号
                } else {
                    activityPager.setHasLock(false);
//                    spDepartment.setEnable(true);
                    spOrgSend.setEnable(true);
                    spOrgHuozhu.setEnable(true);
                    searchSupplierHz.setClickable(true);
                    searchClient.setClickable(true);
                    spHzType.setEnable(true);
                    spStoreman.setEnable(true);
                    edNot.setFocusable(true);
                    edNot.setFocusableInTouchMode(true);

                    edNot.setText("");
                    Hawk.put(Config.OrderNo + activityPager.getActivity(), "");//清空存储的业务单号
                    Hawk.put(Config.Note + activityPager.getActivity(), "");//清空存储的业务单号
                }
                break;

        }
    }

    public FragmentPYingMain() {
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
        View view = inflater.inflate(R.layout.fragment_pying_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        mContext = getActivity();
        EventBusUtil.register(this);
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

        //判断是否有保存的业务单号，不存在的话，解锁表头
        if (!LocDataUtil.hasTDetail(activityPager.getActivity())) {
            EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Lock_Main, Config.Lock + "NO"));
        } else {
            EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.Lock_Main, Config.Lock));
        }
        setfocus(tvDate);
    }

    @Override
    public void onResume() {
        super.onResume();
        tvDate.setText(CommonUtil.getTime(true));
        //第一个参数用于保存上一个值，第二个为自动跳转到该默认值
        spHzType.setData(Info.Type_Hz_type_All);
        cbNum.setSaveKey(activityPager.getActivity()+"printnum");
//        spHzType.setEnable(false);
        spHzType.setAutoSelection(Info.HuoZhuType + activityPager.getActivity(), Hawk.get(Info.HuoZhuType + activityPager.getActivity(), ""));


        spOrgSend.setAutoSelection(Info.OrgOut + activityPager.getActivity(), Hawk.get(Info.OrgOut + activityPager.getActivity(), ""));
//        spOrgHuozhu.setAutoSelection(Info.HuoZhu + activityPager.getActivity(), activityPager.getOrgOut(), Hawk.get(Info.HuoZhu + activityPager.getActivity(), ""));
//        spDepartment.setAuto(Info.Department + activityPager.getActivity(), Hawk.get(Info.Department + activityPager.getActivity(), ""), activityPager.getOrgOut(), activityPager.getActivity());
//        spStoreman.setAuto(Info.StoreMan + activityPager.getActivity(), Hawk.get(Info.StoreMan + activityPager.getActivity(), ""), activityPager.getOrgOut());
        cbIsStorage.setChecked(Hawk.get(Info.Storage + activityPager.getActivity(), false));
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
//                activityPager.setDepartMent(spDepartment.getDataNumber());
                activityPager.setDepartMent(department==null?"":department.FNumber);
                activityPager.setPrintNum(cbNum.getNum());
                Hawk.put(Config.Note + activityPager.getActivity(), edNot.getText().toString());//保存业务单号
            }
        }
    }


    @Override
    protected void initListener() {
        spHzType.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                hzType = (CommonBean) spHzType.getAdapter().getItem(i);
                Lg.e("货主类型：", hzType);
                activityPager.setDBType(hzType.FNumber);
                Hawk.put(Info.HuoZhuType + activityPager.getActivity(), hzType.FName);
                if ("BD_OwnerOrg".equals(hzType.FNumber)) {//业务组织
                    llSupplierHz.setVisibility(View.GONE);
                    llClient.setVisibility(View.GONE);
                    spOrgHuozhu.setVisibility(View.VISIBLE);
                    spOrgHuozhu.setAutoSelection(Info.HuoZhu + activityPager.getActivity(), activityPager.getOrgOut(), "");
                    //隐藏明细货主选项
                    tvTxtDetail.setVisibility(View.GONE);
                    llClientDetail.setVisibility(View.GONE);
                    llSupplierHzDetail.setVisibility(View.GONE);
                } else if ("BD_Supplier".equals(hzType.FNumber)) {//供应商
                    activityPager.setOrgIn(null);
                    spOrgHuozhu.setVisibility(View.GONE);
                    llClient.setVisibility(View.GONE);
                    llSupplierHz.setVisibility(View.VISIBLE);
                    LocDataUtil.getSuppliers(Hawk.get(Config.SupplierHz + activityPager.getActivity(), ""), activityPager.getOrgOut(1), EventBusInfoCode.Supplier_Hz);
                    //隐藏明细货主选项
                    tvTxtDetail.setVisibility(View.VISIBLE);
                    llClientDetail.setVisibility(View.GONE);
                    llSupplierHzDetail.setVisibility(View.VISIBLE);
                } else {
                    llSupplierHz.setVisibility(View.GONE);
                    spOrgHuozhu.setVisibility(View.GONE);
                    llClient.setVisibility(View.VISIBLE);
                    LocDataUtil.getClient(Hawk.get(Config.ClientHz + activityPager.getActivity(), ""), activityPager.getOrgOut(1), EventBusInfoCode.Client);
                    //隐藏明细货主选项
                    tvTxtDetail.setVisibility(View.VISIBLE);
                    llClientDetail.setVisibility(View.VISIBLE);
                    llSupplierHzDetail.setVisibility(View.GONE);
                }
            }
        });

        spOrgSend.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                activityPager.setOrgOut((Org) spOrgSend.getAdapter().getItem(i));
                Hawk.put(Info.OrgOut + activityPager.getActivity(), activityPager.getOrgOut().FName);
                if ("BD_OwnerOrg".equals(hzType.FNumber)) {//业务组织
                    spOrgHuozhu.setAutoSelection(Info.HuoZhu + activityPager.getActivity(), activityPager.getOrgOut(), "");
                } else if ("BD_Supplier".equals(hzType.FNumber)) {
                    LocDataUtil.getSuppliers(Hawk.get(Config.SupplierHz+ activityPager.getActivity(), ""), activityPager.getOrgOut(1), EventBusInfoCode.Supplier_Hz);
                    LocDataUtil.getSuppliers(Hawk.get(Config.SupplierHzDetail+ activityPager.getActivity(), ""), activityPager.getOrgOut(1), EventBusInfoCode.Supplier_Hz_Detail);
                } else {
                    LocDataUtil.getClient(Hawk.get(Config.ClientHz + activityPager.getActivity(), ""), activityPager.getOrgOut(1), EventBusInfoCode.Client);
                    LocDataUtil.getClient(Hawk.get(Config.ClientHzDetail + activityPager.getActivity(), ""), activityPager.getOrgOut(1), EventBusInfoCode.Client_Detail);
                }
                spDepartmentDlg.setAuto(Info.Department+activityPager.getActivity(),Hawk.get(Info.Department+activityPager.getActivity(),""), activityPager.getOrgOut(),activityPager.getActivity());
                spStoreman.setAuto(Info.StoreMan + activityPager.getActivity(), Hawk.get(Info.StoreMan + activityPager.getActivity(), ""), activityPager.getOrgOut());
//                spDepartment.setAuto(Info.Department + activityPager.getActivity(), Hawk.get(Info.Department + activityPager.getActivity(), ""), activityPager.getOrgOut(), activityPager.getActivity());
                EventBusUtil.sendEvent(new ClassEvent(EventBusInfoCode.UpdataView, ""));
            }
        });
        spOrgHuozhu.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                activityPager.setHuozhuOut((Org) spOrgHuozhu.getAdapter().getItem(i));
                Hawk.put(Info.HuoZhu + activityPager.getActivity(), activityPager.getHuozhuOut().FName);
                if ("BD_OwnerOrg".equals(hzType.FNumber)){//当表头货主类型为业务组织，表头要与明细一致
                    activityPager.setHuozhuIn((Org) spOrgHuozhu.getAdapter().getItem(i));
                }
            }
        });
        cbIsStorage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                activityPager.setStorage(b);
            }
        });
        spDepartmentDlg.setOnItemSelectedListener(new ItemListener() {
            @Override
            protected void ItemSelected(AdapterView<?> parent, View view, int i, long id) {
                department = (Department) spDepartmentDlg.getAdapter().getItem(i);
                spDepartmentDlg.setTitleText(department.FName);
                Hawk.put(Info.Department+activityPager.getActivity(),department.FName);
                Lg.e("选中部门：", department);
//                waveHouseIn = null;
//                spWavehouseIn.setAuto(mContext, storageIn, "");

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Hawk.put(Info.Storage + activityPager, cbIsStorage.isChecked());
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            EventBusUtil.unregister(this);
        } catch (Exception e) {
        }
    }

    @OnClick({R.id.tv_date,R.id.search_supplier_hz, R.id.search_client, R.id.search_supplier_hz_detail, R.id.search_client_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_date:
                datePicker(tvDate);
                break;
            case R.id.search_supplier_hz://表头供应商货主
                Bundle b2 = new Bundle();
                b2.putString("search", edSupplierHz.getText().toString());
                b2.putInt("where", Info.SearchSupplier);
                b2.putString("org", activityPager.getOrgOut().FOrgID);
                startNewActivity(activityPager, ProductSearchActivity.class, R.anim.activity_open, 0, false, b2);
                break;
            case R.id.search_client://表头客户货主
                Bundle b = new Bundle();
                b.putString("search", edClient.getText().toString());
                b.putInt("where", Info.SEARCHCLIENT);
                b.putString("org", activityPager.getOrgOut().FOrgID);
                startNewActivity(activityPager, ProductSearchActivity.class, R.anim.activity_open, 0, false, b);
                break;
            case R.id.search_supplier_hz_detail://明细供应商货主
                Bundle b21 = new Bundle();
                b21.putString("search", edSupplierHzDetail.getText().toString());
                b21.putInt("where", Info.SearchSupplierDetail);
                b21.putString("org", activityPager.getOrgOut().FOrgID);
                startNewActivity(activityPager, ProductSearchActivity.class, R.anim.activity_open, 0, false, b21);
                break;
            case R.id.search_client_detail://明细客户货主
                Bundle b22 = new Bundle();
                b22.putString("search", edClientDetail.getText().toString());
                b22.putInt("where", Info.SearchClientDetail);
                b22.putString("org", activityPager.getOrgOut().FOrgID);
                startNewActivity(activityPager, ProductSearchActivity.class, R.anim.activity_open, 0, false, b22);
                break;
        }
    }
}
