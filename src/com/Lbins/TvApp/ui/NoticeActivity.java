package com.Lbins.TvApp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.Lbins.TvApp.R;
import com.Lbins.TvApp.adapter.NoticeAdapter;
import com.Lbins.TvApp.base.BaseActivity;
import com.Lbins.TvApp.base.InternetURL;
import com.Lbins.TvApp.data.NoticesDATA;
import com.Lbins.TvApp.library.PullToRefreshBase;
import com.Lbins.TvApp.library.PullToRefreshListView;
import com.Lbins.TvApp.module.Notice;
import com.Lbins.TvApp.ui.NoticeDetailActivity;
import com.Lbins.TvApp.util.Constants;
import com.Lbins.TvApp.util.StringUtil;
import com.Lbins.TvApp.widget.CustomProgressDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: ${zhanghailong}
 * Date: 2015/2/6
 * Time: 21:48
 * 类的功能、说明写在此处.
 */
public class NoticeActivity extends BaseActivity implements View.OnClickListener {

    private PullToRefreshListView notice_lstv;
    private NoticeAdapter adapter;
    private int pageIndex = 1;
    private static boolean IS_REFRESH = true;
    private List<Notice> notices;
    private String schoolId = "";
    private String emp_id = "";//当前登陆者UUID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_lstv);
        initView();
        emp_id = getGson().fromJson(getSp().getString("mm_emp_id", ""), String.class);
        progressDialog = new CustomProgressDialog(com.Lbins.TvApp.ui.NoticeActivity.this, "正在加载中",R.anim.custom_dialog_frame);
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        initData();
    }

    private void initView() {
        notices = new ArrayList<Notice>();
       this.findViewById(R.id.back).setOnClickListener(this);

        notice_lstv = (PullToRefreshListView) this.findViewById(R.id.notice_lstv);
        adapter = new NoticeAdapter(notices, com.Lbins.TvApp.ui.NoticeActivity.this);
        notice_lstv.setMode(PullToRefreshBase.Mode.BOTH);
        notice_lstv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(com.Lbins.TvApp.ui.NoticeActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                IS_REFRESH = true;
                pageIndex = 1;

                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(com.Lbins.TvApp.ui.NoticeActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                IS_REFRESH = false;
                pageIndex++;

                initData();
            }
        });
        notice_lstv.setAdapter(adapter);
        notice_lstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Notice notice = notices.get(position -1);
                if(notice != null){
                    Intent detail = new Intent(com.Lbins.TvApp.ui.NoticeActivity.this, NoticeDetailActivity.class);
                    detail.putExtra(Constants.NOTICE, notice);
                    startActivity(detail);
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    private void initData() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
               InternetURL.GET_NOTICE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            NoticesDATA data = getGson().fromJson(s, NoticesDATA.class);
                            if (Integer.parseInt(data.getCode()) == 200) {
                                if (IS_REFRESH) {
                                    notices.clear();
                                }
                                notices.addAll(data.getData());
                                notice_lstv.onRefreshComplete();
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(com.Lbins.TvApp.ui.NoticeActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(com.Lbins.TvApp.ui.NoticeActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(com.Lbins.TvApp.ui.NoticeActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("page", String.valueOf(pageIndex));
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
