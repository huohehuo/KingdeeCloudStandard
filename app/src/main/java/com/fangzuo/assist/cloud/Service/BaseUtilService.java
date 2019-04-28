package com.fangzuo.assist.cloud.Service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.fangzuo.assist.cloud.Activity.Crash.App;
import com.fangzuo.assist.cloud.Beans.CommonResponse;
import com.fangzuo.assist.cloud.RxSerivce.MySubscribe;
import com.fangzuo.assist.cloud.Utils.Info;
import com.fangzuo.assist.cloud.Utils.WebApi;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class BaseUtilService extends IntentService {
    private static final String Service_updateRegisterMsg = "Service_updateRegisterMsg";//更新注册表信息中的手机信息

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.fangzuo.assist.cloud.Service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.fangzuo.assist.cloud.Service.extra.PARAM2";

    public BaseUtilService() {
        super("BaseUtilService");
    }

    //更新注册表的手机信息：手机型号，imei码，当前版本号
    public static void updateRegisterMsg(Context context,String json) {
        Intent intent = new Intent(context, BaseUtilService.class);
        intent.setAction(Service_updateRegisterMsg);
        intent.putExtra(EXTRA_PARAM1, json);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            switch (action){
                case Service_updateRegisterMsg:
                    final String json = intent.getStringExtra(EXTRA_PARAM1);
                    handleActionFoo(json);
                    break;

            }
        }
    }
    //更新注册表的手机信息：手机型号，imei码，当前版本号
    private void handleActionFoo(String json) {
        App.getRService().doIOAction(WebApi.RegisterUpdateMsg,  json, new MySubscribe<CommonResponse>() {
            @Override
            public void onNext(CommonResponse commonResponse) {
            }
            @Override
            public void onError(Throwable e) {
            }
        });
    }
}
