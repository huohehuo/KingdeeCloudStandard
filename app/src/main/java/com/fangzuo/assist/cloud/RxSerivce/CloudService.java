package com.fangzuo.assist.cloud.RxSerivce;


import android.util.Log;

import com.fangzuo.assist.cloud.Activity.Crash.App;
import com.fangzuo.assist.cloud.Beans.BackData;
import com.fangzuo.assist.cloud.Beans.BackDataLogin;
import com.fangzuo.assist.cloud.Beans.SearchBackData;
import com.fangzuo.assist.cloud.Utils.BasicShareUtil;
import com.fangzuo.assist.cloud.Utils.Config;
import com.fangzuo.assist.cloud.Utils.Info;
import com.orhanobut.hawk.Hawk;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by aid on 11/10/16.
 */

public class CloudService {

    private ServiceRequest request;
    private Retrofit retrofit;

    public CloudService() {
        //当ip地址发生变化时，替换掉原有对象
//        if (!App.isChangeIp) {
//            request = App.getRetrofit().create(ServiceRequest.class);
//        } else {
            retrofit = new Retrofit.Builder()
                    .client(App.getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(Hawk.get(Config.Cloud_Url,"http://120.77.206.67/K3Cloud/"))
                    .build();
            request = retrofit.create(ServiceRequest.class);
//            App.setRetrofit(retrofit);
//        }
    }

    public void doIOActionLoginT(String io, RequestBody body, ToSubscribe<BackDataLogin> mySubscribe) {
        toSubscribe(request.actionIOLoginT(io, body), mySubscribe);
    }
    public void doIOActionT(String io, RequestBody data,ToSubscribe<BackData> mySubscribe) {
        toSubscribe(request.actionIOT(io, data), mySubscribe);
    }
    //登录
    public void doIOActionLogin(String io,String loginJson, ToSubscribe<BackDataLogin> mySubscribe) {
        toSubscribe(request.actionIOLogin(io, getMap(loginJson)), mySubscribe);
    }

    //执行接口
    public void doIOAction(String io, String data,ToSubscribe<BackData> mySubscribe) {
        toSubscribe(request.actionIO(io, getMap(data)), mySubscribe);
    }
    //执行接口
    public void doIOActionSearch(String io, String data,ToSubscribe<SearchBackData> mySubscribe) {
        toSubscribe(request.actionIOSearch(io, getMap(data)), mySubscribe);
    }


    /**
     * retrofit 线程管理
     */
    private static <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request();
//                        Request.Builder requestBuilder = request.newBuilder();
//                        request = requestBuilder
//                                .addHeader("Content-Type", "application/json;charset=UTF-8")
//                                .post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),
//                                        bodyToString(request.body())))//关键部分，设置requestBody的编码格式为json
//                                .build();
//                        return chain.proceed(request);
//                    }
//                })
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        //这里获取请求返回的cookie
                        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                            final StringBuffer cookieBuffer = new StringBuffer();
                            //最近在学习RxJava,这里用了RxJava的相关API大家可以忽略,用自己逻辑实现即可.大家可以用别的方法保存cookie数据
                            Observable.from(originalResponse.headers("Set-Cookie"))
                                    .map(new Func1<String, String>() {
                                        @Override
                                        public String call(String s) {
                                            String[] cookieArray = s.split(";");
                                            return cookieArray[0];
                                        }
                                    })
                                    .subscribe(new Action1<String>() {
                                        @Override
                                        public void call(String cookie) {
                                            if (cookie.startsWith("kdservice-sessionid")){
                                                Hawk.put("cookie",cookie);
                                            }
//                                            cookieBuffer.append(cookie).append(";");
                                        }
                                    });
//                            Lg.e("cooking:"+cookieBuffer.toString());
                        }

                        return originalResponse;
                    }
                })
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        final Request.Builder builder = chain.request().newBuilder();
                        builder.addHeader("Cookie", Hawk.get("cookie",""));
                        Hawk.delete("cookie");
                        return chain.proceed(builder.build());
                    }
                })
                .addInterceptor(App.getInterceptor())
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(200, TimeUnit.SECONDS)
                .writeTimeout(200, TimeUnit.SECONDS)
                .build();
    }
//
//    private static class OkHttpClientHolder {
//        private static final OkHttpClient client;
//
//        static {
//            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            if (BuildConfig.DEBUG) {
//                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//                builder.addInterceptor(interceptor);
//            }
//            client = builder.addNetworkInterceptor(new HttpLoggingInterceptor())
//                    .connectTimeout(5, TimeUnit.SECONDS)
//                    .build();
//        }
//
//    }



    private static Map<String, Object> getMap(String json) {
        Map<String, Object> jObj = new HashMap<>();
        UUID uuid = UUID.randomUUID();
        int hashCode = uuid.toString().hashCode();
        jObj.put("format", Info.format);
        jObj.put("useragent", Info.useragent);
        jObj.put("rid", hashCode);
        jObj.put("parameters", chinaToUnicode(json));
        jObj.put("timestamp", new Date().toString());
        jObj.put("v", "1.0");
        Log.e("请求：", "网络数据：" + jObj.toString());
        return jObj;
    }

    private static Map<String, String> getParams(String json) {
        BasicShareUtil share = BasicShareUtil.getInstance(App.getContext());
        Map<String, String> params = new HashMap<>();
        params.put("json", json);
        params.put("sqlip", share.getDatabaseIp());
        params.put("sqlport", share.getDatabasePort());
        params.put("sqluser", share.getDataBaseUser());
        params.put("sqlpass", share.getDataBasePass());
        params.put("sqlname", share.getDataBase());
        params.put("version", share.getVersion());
        Log.e("请求：", "网络数据：" + params.toString());
        return params;
    }

    /**
     * 把中文转成Unicode码
     *
     * @param str
     * @return
     */
    public static String chinaToUnicode(String str) {
        StringBuffer result=new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            int chr1 = (char) str.charAt(i);
            if (chr1 >= 19968 && chr1 <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
                result.append("\\u" + Integer.toHexString(chr1));
            } else {
                result.append(str.charAt(i));
            }
        }
        return result.toString();
    }
}
