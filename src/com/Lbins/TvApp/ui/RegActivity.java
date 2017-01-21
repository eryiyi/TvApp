package com.Lbins.TvApp.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;
import com.Lbins.TvApp.R;
import com.Lbins.TvApp.base.ActivityTack;
import com.Lbins.TvApp.base.BaseActivity;
import com.Lbins.TvApp.base.InternetURL;
import com.Lbins.TvApp.data.CityData;
import com.Lbins.TvApp.data.CountrysData;
import com.Lbins.TvApp.data.ProvinceData;
import com.Lbins.TvApp.module.CityObj;
import com.Lbins.TvApp.module.CountryObj;
import com.Lbins.TvApp.module.HangYeType;
import com.Lbins.TvApp.module.ProvinceObj;
import com.Lbins.TvApp.receiver.SMSBroadcastReceiver;
import com.Lbins.TvApp.ui.LoginActivity;
import com.Lbins.TvApp.util.Constants;
import com.Lbins.TvApp.util.GuirenHttpUtils;
import com.Lbins.TvApp.util.StringUtil;
import com.Lbins.TvApp.widget.CustomProgressDialog;
import com.Lbins.TvApp.widget.CustomerSpinner;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2016/5/9.
 */
public class RegActivity extends BaseActivity implements View.OnClickListener {
    private Resources res;
    private EditText mm_emp_mobile;
    private EditText code;
    private EditText password;
    private EditText surepass;
    private EditText nickname;
    private Button btn_code;

    //mob短信
    // 填写从短信SDK应用后台注册得到的APPKEY
    private static String APPKEY = InternetURL.APP_MOB_KEY;//"69d6705af33d";0d786a4efe92bfab3d5717b9bc30a10d
    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = InternetURL.APP_MOB_SCRECT;
    public String phString;//手机号码

    //短信读取
    private SMSBroadcastReceiver mSMSBroadcastReceiver;
    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    private TextView select_hy;//行业
    HangYeType record1;//所选择的行业类别
    private EditText yaoqingma;//邀请码

    //省市县
    private CustomerSpinner province;
    private CustomerSpinner city;
    private CustomerSpinner country;
    private List<ProvinceObj> provinces = new ArrayList<ProvinceObj>();//省
    private ArrayList<String> provinceNames = new ArrayList<String>();//省份名称
    private List<CityObj> citys = new ArrayList<CityObj>();//市
    private ArrayList<String> cityNames = new ArrayList<String>();//市名称
    private List<CountryObj> countrys = new ArrayList<CountryObj>();//区
    private ArrayList<String> countrysNames = new ArrayList<String>();//区名称
    ArrayAdapter<String> ProvinceAdapter;
    ArrayAdapter<String> cityAdapter;
    ArrayAdapter<String> countryAdapter;
    private String provinceName = "";
    private String cityName = "";
    private String countryName = "";
    private String provinceCode = "";
    private String cityCode = "";
    private String countryCode = "";

    private String sex_selected = "";
    boolean isMobileNet, isWifiNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_activity);
        registerBoradcastReceiver();
        res = getResources();
        //mob短信无GUI
        SMSSDK.initSDK(this, APPKEY, APPSECRET, true);
        EventHandler eh=new EventHandler(){

            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                mHandler.sendMessage(msg);
            }

        };
        SMSSDK.registerEventHandler(eh);

        initView();

        //生成广播处理
        mSMSBroadcastReceiver = new SMSBroadcastReceiver();
        //实例化过滤器并设置要过滤的广播
        IntentFilter intentFilter = new IntentFilter(ACTION);
        intentFilter.setPriority(Integer.MAX_VALUE);
        //注册广播
        this.registerReceiver(mSMSBroadcastReceiver, intentFilter);
        mSMSBroadcastReceiver.setOnReceivedMessageListener(new SMSBroadcastReceiver.MessageListener() {
            @Override
            public void onReceived(String message) {
                if(!StringUtil.isNullOrEmpty(message)){
                    String codestr = StringUtil.valuteNumber(message);
                    if(!StringUtil.isNullOrEmpty(codestr)){
                        code.setText(codestr);
                    }
                }
            }
        });


        //判断是否有网
        try {
            isMobileNet = GuirenHttpUtils.isMobileDataEnable(com.Lbins.TvApp.ui.RegActivity.this);
            isWifiNet = GuirenHttpUtils.isWifiDataEnable(com.Lbins.TvApp.ui.RegActivity.this);
            if (!isMobileNet && !isWifiNet) {
                showMsg(com.Lbins.TvApp.ui.RegActivity.this ,"请检查您网络链接");
            }else {
                progressDialog = new CustomProgressDialog(com.Lbins.TvApp.ui.RegActivity.this, "正在加载中",R.anim.custom_dialog_frame);
                progressDialog.setCancelable(true);
                progressDialog.setIndeterminate(true);
                progressDialog.show();
                getProvince();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void reg(){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.REG_URL ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code =  jo.getString("code");
                                if(Integer.parseInt(code) == 200) {
                                    showMsg(com.Lbins.TvApp.ui.RegActivity.this, res.getString(R.string.reg_msg_four));
                                    save("mm_emp_mobile", mm_emp_mobile.getText().toString());
                                    save("password", password.getText().toString());
                                    Intent intent1 = new Intent("reg_success_guiren");
                                    sendBroadcast(intent1);
                                    finish();
                                    ActivityTack.getInstanse().popUntilActivity(LoginActivity.class);
                                }else if(Integer.parseInt(code) == 1){
                                    showMsg(com.Lbins.TvApp.ui.RegActivity.this, res.getString(R.string.reg_msg_three));
                                }else if(Integer.parseInt(code) == 2){
                                    showMsg(com.Lbins.TvApp.ui.RegActivity.this, res.getString(R.string.reg_msg_two));
                                    Intent loginV = new Intent(com.Lbins.TvApp.ui.RegActivity.this, LoginActivity.class);
                                    startActivity(loginV);
                                    finish();
                                }
                                else if(Integer.parseInt(code) == 3){
                                    showMsg(com.Lbins.TvApp.ui.RegActivity.this, "邀请码不正确！");
                                }
                                else {
                                    Toast.makeText(com.Lbins.TvApp.ui.RegActivity.this, R.string.reg_msg_three , Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(com.Lbins.TvApp.ui.RegActivity.this, R.string.reg_msg_three, Toast.LENGTH_SHORT).show();
                        }
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(com.Lbins.TvApp.ui.RegActivity.this, R.string.reg_msg_three, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mm_emp_mobile" , mm_emp_mobile.getText().toString());
                params.put("mm_emp_password" , password.getText().toString());
                params.put("mm_emp_nickname" , nickname.getText().toString());
                params.put("guiren_card_num" , yaoqingma.getText().toString());
                params.put("mm_hangye_id" , record1.getMm_hangye_id());

                params.put("mm_emp_provinceId", provinceCode);
                params.put("mm_emp_cityId", cityCode);
                params.put("mm_emp_countryId", countryCode);

                params.put("provinceName", provinceName);
                params.put("cityName", cityName);
                params.put("countryName", countryName);

                if(!StringUtil.isNullOrEmpty(sex_selected)){
                    params.put("mm_emp_sex" , sex_selected);
                }else {
                    params.put("mm_emp_sex" , "0");
                }

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        getRequestQueue().add(request);
    }

    Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event=" + event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                System.out.println("--------result"+event);
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
//                    Toast.makeText(getApplicationContext(), "提交验证码成功", Toast.LENGTH_SHORT).show();
                    reg();
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //已经验证
                    Toast.makeText(getApplicationContext(), R.string.code_msg_one, Toast.LENGTH_SHORT).show();
                }

            } else {
//				((Throwable) data).printStackTrace();
                Toast.makeText(com.Lbins.TvApp.ui.RegActivity.this, R.string.code_msg_two, Toast.LENGTH_SHORT).show();
//					Toast.makeText(MainActivity.this, "123", Toast.LENGTH_SHORT).show();
                int status = 0;
                try {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;

                    JSONObject object = new JSONObject(throwable.getMessage());
                    String des = object.optString("detail");
                    status = object.optInt("status");
                    if (!TextUtils.isEmpty(des)) {
                        Toast.makeText(com.Lbins.TvApp.ui.RegActivity.this, des, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    SMSLog.getInstance().w(e);
                }
            }
        };
    };

    void checkMObile(){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.GET_EMP_MOBILE ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code =  jo.getString("code");
                                if(Integer.parseInt(code) == 200) {
                                    showMsg(com.Lbins.TvApp.ui.RegActivity.this, getResources().getString(R.string.reg_error_is_use));
                                }else {
                                    SMSSDK.getVerificationCode("86", mm_emp_mobile.getText().toString());//发送请求验证码，手机10s之内会获得短信验证码
                                    phString=mm_emp_mobile.getText().toString();
                                    btn_code.setClickable(false);//不可点击
                                    MyTimer myTimer = new MyTimer(60000,1000);
                                    myTimer.start();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            SMSSDK.getVerificationCode("86", mm_emp_mobile.getText().toString());//发送请求验证码，手机10s之内会获得短信验证码
                            phString=mm_emp_mobile.getText().toString();
                            btn_code.setClickable(false);//不可点击
                            MyTimer myTimer = new MyTimer(60000,1000);
                            myTimer.start();
                        }
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        SMSSDK.getVerificationCode("86", mm_emp_mobile.getText().toString());//发送请求验证码，手机10s之内会获得短信验证码
                        phString=mm_emp_mobile.getText().toString();
                        btn_code.setClickable(false);//不可点击
                        MyTimer myTimer = new MyTimer(60000,1000);
                        myTimer.start();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mm_emp_mobile" , mm_emp_mobile.getText().toString());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        getRequestQueue().add(request);
    }

    class MyTimer extends CountDownTimer {

        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            btn_code.setText(res.getString(R.string.daojishi_three));
            btn_code.setClickable(true);//可点击
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn_code.setText(res.getString(R.string.daojishi_one) + millisUntilFinished / 1000 + res.getString(R.string.daojishi_two));
        }
    }

    public void onDestroy() {
        super.onPause();
        SMSSDK.unregisterAllEventHandler();
        //注销短信监听广播
        this.unregisterReceiver(mSMSBroadcastReceiver);
        unregisterReceiver(mBroadcastReceiver);
    };

    private void initView() {
        this.findViewById(R.id.back).setOnClickListener(this);
        this.findViewById(R.id.sign_in_button).setOnClickListener(this);
        this.findViewById(R.id.liner_one).setOnClickListener(this);
        btn_code = (Button) this.findViewById(R.id.btn_code);
        btn_code.setOnClickListener(this);
        nickname = (EditText) this.findViewById(R.id.nickname);
        mm_emp_mobile = (EditText) this.findViewById(R.id.mm_emp_mobile);
        code = (EditText) this.findViewById(R.id.code);
        password = (EditText) this.findViewById(R.id.password);
        surepass = (EditText) this.findViewById(R.id.surepass);
        select_hy = (TextView) this.findViewById(R.id.select_hy);
        yaoqingma = (EditText) this.findViewById(R.id.yaoqingma);
        ProvinceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, provinceNames);
        ProvinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        province = (CustomerSpinner) findViewById(R.id.mm_emp_provinceId);
        province.setAdapter(ProvinceAdapter);
        province.setList(provinceNames);
        province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                citys.clear();
                cityNames.clear();
                cityNames.add(getResources().getString(R.string.select_city));
                cityAdapter.notifyDataSetChanged();
                ProvinceObj province = null;
                if (provinces != null && position > 0) {
                    province = provinces.get(position - 1);
                    provinceName = province.getProvince();
                    provinceCode = province.getProvinceID();
                } else if (provinces != null) {
                    province = provinces.get(position);
                    provinceName = province.getProvince();
                    provinceCode = province.getProvinceID();
                }
                try {
                    getCitys();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cityNames);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city = (CustomerSpinner) findViewById(R.id.mm_emp_cityId);
        city.setAdapter(cityAdapter);
        city.setList(cityNames);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    countrys.clear();
                    countrysNames.clear();
                    countrysNames.add(getResources().getString(R.string.select_area));
                    CityObj city = citys.get(position - 1);
                    cityName = city.getCity();
                    cityCode = city.getCityID();
                    try {
                        getArea();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    country.setEnabled(true);
                    countrysNames.clear();
                    countrysNames.add(res.getString(R.string.select_area));
                    countrys.clear();
                    countryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        countryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countrysNames);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country = (CustomerSpinner) findViewById(R.id.mm_emp_countryId);
        country.setAdapter(countryAdapter);
        country.setList(countrysNames);
        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    CountryObj country = countrys.get(position - 1);
                    countryCode = country.getAreaID();
                    countryName = country.getArea();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    //获得省份
    public void getProvince() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.GET_PROVINCE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code1 = jo.getString("code");
                                if (Integer.parseInt(code1) == 200) {
                                    provinceNames.add(res.getString(R.string.select_province));
                                    ProvinceData data = getGson().fromJson(s, ProvinceData.class);
                                    provinces = data.getData();
                                    if (provinces != null) {
                                        for (int i = 0; i < provinces.size(); i++) {
                                            provinceNames.add(provinces.get(i).getProvince());
                                        }
                                    }
                                    ProvinceAdapter.notifyDataSetChanged();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(com.Lbins.TvApp.ui.RegActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                        }
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(com.Lbins.TvApp.ui.RegActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("is_use", "1");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        getRequestQueue().add(request);
    }

    //获得城市
    public void getCitys() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.GET_CITY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code1 = jo.getString("code");
                                if (Integer.parseInt(code1) == 200) {
                                    CityData data = getGson().fromJson(s, CityData.class);
                                    citys = data.getData();
                                    if (citys != null) {
                                        for (int i = 0; i < citys.size(); i++) {
                                            cityNames.add(citys.get(i).getCity());
                                        }
                                    }
                                    cityAdapter.notifyDataSetChanged();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(com.Lbins.TvApp.ui.RegActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                        }
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(com.Lbins.TvApp.ui.RegActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("access_token", getGson().fromJson(getSp().getString("access_token", ""), String.class));
                params.put("father", provinceCode);
                params.put("is_use", "1");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        getRequestQueue().add(request);
    }

    //获得地区
    public void getArea() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.GET_COUNTRY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code1 = jo.getString("code");
                                if (Integer.parseInt(code1) == 200) {
                                    CountrysData data = getGson().fromJson(s, CountrysData.class);
                                    countrys = data.getData();
                                    if (countrys != null) {
                                        for (int i = 0; i < countrys.size(); i++) {
                                            countrysNames.add(countrys.get(i).getArea());
                                        }
                                    }
                                    countryAdapter.notifyDataSetChanged();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(com.Lbins.TvApp.ui.RegActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                        }
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(com.Lbins.TvApp.ui.RegActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("father", cityCode);
                params.put("is_use", "1");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        getRequestQueue().add(request);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.sign_in_button:
            {
                //reg

                if(StringUtil.isNullOrEmpty(yaoqingma.getText().toString().trim())){
                    showMsg(com.Lbins.TvApp.ui.RegActivity.this, "请输入邀请码");
                    return;
                }
                if(StringUtil.isNullOrEmpty(nickname.getText().toString().trim())){
                    showMsg(com.Lbins.TvApp.ui.RegActivity.this, "请输入姓名");
                    return;
                }
                if(StringUtil.isNullOrEmpty(mm_emp_mobile.getText().toString().trim())){
                    showMsg(com.Lbins.TvApp.ui.RegActivity.this, "请输入手机号");
                    return;
                }
                if(mm_emp_mobile.getText().toString().trim().length() != 11){
                    showMsg(com.Lbins.TvApp.ui.RegActivity.this, "请输入正确的手机号");
                    return;
                }
                if(StringUtil.isNullOrEmpty(code.getText().toString().trim())){
                    showMsg(com.Lbins.TvApp.ui.RegActivity.this, "请输入验证码");
                    return;
                }
                if(StringUtil.isNullOrEmpty(password.getText().toString().trim())){
                    showMsg(com.Lbins.TvApp.ui.RegActivity.this, "请输入密码");
                    return;
                }
                if(password.getText().toString().trim().length() >16 || password.getText().toString().trim().length()<6 ){
                    showMsg(com.Lbins.TvApp.ui.RegActivity.this, "请输入密码，6到18位之间！");
                    return;
                }
                if(StringUtil.isNullOrEmpty(surepass.getText().toString().trim())){
                    showMsg(com.Lbins.TvApp.ui.RegActivity.this, "请输入确认密码");
                    return;
                }
                if(!password.getText().toString().trim().equals(surepass.getText().toString().trim())){
                    showMsg(com.Lbins.TvApp.ui.RegActivity.this, "两次输入密码不一致");
                    return;
                }

                if(record1 == null){
                    showMsg(com.Lbins.TvApp.ui.RegActivity.this, "请选择您所从事的职业");
                    return;
                }
                if (StringUtil.isNullOrEmpty(provinceCode)) {
                    showMsg(com.Lbins.TvApp.ui.RegActivity.this, res.getString(R.string.select_province));
                    return;
                }
                if (StringUtil.isNullOrEmpty(cityCode)) {
                    showMsg(com.Lbins.TvApp.ui.RegActivity.this, res.getString(R.string.select_city));
                    return;
                }
                if (StringUtil.isNullOrEmpty(countryCode)) {
                    showMsg(com.Lbins.TvApp.ui.RegActivity.this, res.getString(R.string.select_area));
                    return;
                }

                progressDialog = new CustomProgressDialog(com.Lbins.TvApp.ui.RegActivity.this, "正在加载中",R.anim.custom_dialog_frame);
                progressDialog.setCancelable(true);
                progressDialog.setIndeterminate(true);
                progressDialog.show();
                SMSSDK.submitVerificationCode("86", phString, code.getText().toString());
//                reg();
            }
                break;
            case R.id.btn_code:
                //验证码
                if(!TextUtils.isEmpty(mm_emp_mobile.getText().toString()) && mm_emp_mobile.getText().toString().length() == 11){
                    //先判断手机号是否注册了
                    checkMObile();
                }else {
                    showMsg(com.Lbins.TvApp.ui.RegActivity.this, "请输入手机号");
                    return;
                }
                break;
            case R.id.liner_one:
                //点击选择职业
                Intent selectHyV = new Intent(com.Lbins.TvApp.ui.RegActivity.this, SelectWorkActivity.class);
                startActivity(selectHyV);
                break;
        }
    }


    //广播接收动作
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Constants.SELECT_HANGYE_TYPE_SUCCESS)){
                record1 = (HangYeType) intent.getExtras().get("hangYeType");
                select_hy.setText(record1.getMm_hangye_name());
            }
        }
    };

    //注册广播
    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constants.SELECT_HANGYE_TYPE_SUCCESS);
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

}
