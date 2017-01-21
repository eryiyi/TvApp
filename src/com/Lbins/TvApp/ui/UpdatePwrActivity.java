package com.Lbins.TvApp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.Lbins.TvApp.R;
import com.Lbins.TvApp.base.BaseActivity;
import com.Lbins.TvApp.base.InternetURL;
import com.Lbins.TvApp.util.GuirenHttpUtils;
import com.Lbins.TvApp.util.StringUtil;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhl on 2016/7/10.
 */
public class UpdatePwrActivity extends BaseActivity implements View.OnClickListener {
    private EditText pwr_one;
    private EditText pwr_two;
    private EditText pwr_three;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatepwr_activtity);

        pwr_one = (EditText) this.findViewById(R.id.pwr_one);
        pwr_two = (EditText) this.findViewById(R.id.pwr_two);
        pwr_three = (EditText) this.findViewById(R.id.pwr_three);
        this.findViewById(R.id.btn_sure).setOnClickListener(this);
        this.findViewById(R.id.back).setOnClickListener(this);
    }

    boolean isMobileNet, isWifiNet;
    @Override
    public void onClick(View v) {
        //判断是否有网
        try {
            isMobileNet = GuirenHttpUtils.isMobileDataEnable(com.Lbins.TvApp.ui.UpdatePwrActivity.this);
            isWifiNet = GuirenHttpUtils.isWifiDataEnable(com.Lbins.TvApp.ui.UpdatePwrActivity.this);
            if (!isMobileNet && !isWifiNet) {
                showMsg(com.Lbins.TvApp.ui.UpdatePwrActivity.this, "请检查网络链接");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (v.getId()){
            case R.id.btn_sure:
                //确定修改
                if(StringUtil.isNullOrEmpty(pwr_one.getText().toString().trim())){
                    showMsg(com.Lbins.TvApp.ui.UpdatePwrActivity.this, "请输入原始密码！");
                    return;
                }
                if(StringUtil.isNullOrEmpty(pwr_two.getText().toString().trim())){
                    showMsg(com.Lbins.TvApp.ui.UpdatePwrActivity.this, "请输入新密码！");
                    return;
                }
                if(StringUtil.isNullOrEmpty(pwr_three.getText().toString().trim())){
                    showMsg(com.Lbins.TvApp.ui.UpdatePwrActivity.this, "请输入确认密码！");
                    return;
                }
                if(!pwr_three.getText().toString().trim().equals(pwr_two.getText().toString().trim())){
                    showMsg(com.Lbins.TvApp.ui.UpdatePwrActivity.this, "两次输入密码不一致！");
                    return;
                }
                update();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    void update() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.UPDATE_PWR_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code = jo.getString("code");
                                if (Integer.parseInt(code) == 200) {
                                    showMsg(com.Lbins.TvApp.ui.UpdatePwrActivity.this, getResources().getString(R.string.pwr_success_one));
                                    save("password", pwr_two.getText().toString().trim());
                                    finish();
                                } else {
                                    showMsg(com.Lbins.TvApp.ui.UpdatePwrActivity.this, getResources().getString(R.string.pwr_error_nine));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            showMsg(com.Lbins.TvApp.ui.UpdatePwrActivity.this, getResources().getString(R.string.pwr_error_nine));
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
                        showMsg(com.Lbins.TvApp.ui.UpdatePwrActivity.this, getResources().getString(R.string.pwr_error_nine));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mm_emp_mobile", getGson().fromJson(getSp().getString("mm_emp_mobile", ""), String.class));
                params.put("newpass", pwr_three.getText().toString().trim());
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
}
