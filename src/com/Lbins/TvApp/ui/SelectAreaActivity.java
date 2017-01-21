package com.Lbins.TvApp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.Lbins.TvApp.R;
import com.Lbins.TvApp.adapter.ItemAreaAdapter;
import com.Lbins.TvApp.base.BaseActivity;
import com.Lbins.TvApp.base.InternetURL;
import com.Lbins.TvApp.data.CountrysData;
import com.Lbins.TvApp.library.PullToRefreshBase;
import com.Lbins.TvApp.library.PullToRefreshListView;
import com.Lbins.TvApp.module.CityObj;
import com.Lbins.TvApp.module.CountryObj;
import com.Lbins.TvApp.util.Constants;
import com.Lbins.TvApp.util.GuirenHttpUtils;
import com.Lbins.TvApp.util.StringUtil;
import com.Lbins.TvApp.widget.CustomProgressDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2016/6/9.
 */
public class SelectAreaActivity extends BaseActivity implements View.OnClickListener {

    private ItemAreaAdapter adapter;
    private PullToRefreshListView listView;
    private ImageView no_collections;
    private int pageIndex = 1;
    private static boolean IS_REFRESH = true;
    private List<CountryObj> recordList = new ArrayList<CountryObj>();
    boolean isMobileNet, isWifiNet;
    CityObj city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_area);
        city = (CityObj) getIntent().getExtras().get("city");
        this.findViewById(R.id.back).setOnClickListener(this);
        listView = (PullToRefreshListView) this.findViewById(R.id.lstv);
        adapter = new ItemAreaAdapter(recordList, com.Lbins.TvApp.ui.SelectAreaActivity.this);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//                IS_REFRESH = true;
//                pageIndex = 1;
//                initData();
                listView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
//                IS_REFRESH = false;
//                pageIndex++;
//                initData();
                listView.onRefreshComplete();
            }
        });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CountryObj countryObj = recordList.get(position - 1);
                //发送广播
                Intent intent1 = new Intent(Constants.SEND_SELECT_AREA_SUCCESS);
                intent1.putExtra("countryObj", countryObj);
                sendBroadcast(intent1);
                finish();
            }
        });
        //判断是否有网
        try {
            isMobileNet = GuirenHttpUtils.isMobileDataEnable(com.Lbins.TvApp.ui.SelectAreaActivity.this);
            isWifiNet = GuirenHttpUtils.isWifiDataEnable(com.Lbins.TvApp.ui.SelectAreaActivity.this);
            if (!isMobileNet && !isWifiNet) {
                showMsg(com.Lbins.TvApp.ui.SelectAreaActivity.this ,"请检查您网络链接");
            }else {
                progressDialog = new CustomProgressDialog(com.Lbins.TvApp.ui.SelectAreaActivity.this, "正在加载中",R.anim.custom_dialog_frame);
                progressDialog.setCancelable(true);
                progressDialog.setIndeterminate(true);
                progressDialog.show();
                initData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    public void initData() {
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
                                        if (IS_REFRESH) {
                                            recordList.clear();
                                        }
                                        recordList.addAll(data.getData());
                                        listView.onRefreshComplete();
                                        if(recordList.size() > 0){
                                            no_collections.setVisibility(View.GONE);
                                            listView.setVisibility(View.VISIBLE);
                                        }else {
                                            no_collections.setVisibility(View.VISIBLE);
                                            listView.setVisibility(View.GONE);
                                        }
                                        adapter.notifyDataSetChanged();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(com.Lbins.TvApp.ui.SelectAreaActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(com.Lbins.TvApp.ui.SelectAreaActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("father", city.getCityID());
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
}
