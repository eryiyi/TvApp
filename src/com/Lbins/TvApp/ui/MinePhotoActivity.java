package com.Lbins.TvApp.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.Lbins.TvApp.R;
import com.Lbins.TvApp.adapter.OnClickContentItemListener;
import com.Lbins.TvApp.adapter.RecordAdapter;
import com.Lbins.TvApp.base.BaseActivity;
import com.Lbins.TvApp.base.InternetURL;
import com.Lbins.TvApp.data.RecordDATA;
import com.Lbins.TvApp.data.SuccessData;
import com.Lbins.TvApp.library.PullToRefreshBase;
import com.Lbins.TvApp.library.PullToRefreshListView;
import com.Lbins.TvApp.module.Record;
import com.Lbins.TvApp.ui.DetailPageAcitvity;
import com.Lbins.TvApp.ui.PublishCommentAcitvity;
import com.Lbins.TvApp.util.Constants;
import com.Lbins.TvApp.util.GuirenHttpUtils;
import com.Lbins.TvApp.util.StringUtil;
import com.Lbins.TvApp.widget.CustomProgressDialog;
import com.Lbins.TvApp.widget.DeletePopWindow;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2016/7/18.
 */
public class MinePhotoActivity extends BaseActivity implements View.OnClickListener,OnClickContentItemListener {
    private String mm_emp_id;
    private ImageView no_record;
    //    RECORD_URL
    private PullToRefreshListView lstv;
    private RecordAdapter adapter;
    private int pageIndex = 1;
    private static boolean IS_REFRESH = true;
    private List<Record> recordList = new ArrayList<Record>();
    Record recordtmp;//转换用
    boolean isMobileNet, isWifiNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBoradcastReceiver();
        setContentView(R.layout.mine_photo_activity);
        mm_emp_id = getIntent().getExtras().getString("mm_emp_id");
        this.findViewById(R.id.back).setOnClickListener(this);
        no_record = (ImageView) this.findViewById(R.id.no_record);
        lstv = (PullToRefreshListView) this.findViewById(R.id.lstv);

        adapter = new RecordAdapter(recordList, com.Lbins.TvApp.ui.MinePhotoActivity.this, getGson().fromJson(getSp().getString("mm_emp_id", ""), String.class));
        lstv.setMode(PullToRefreshBase.Mode.BOTH);
        lstv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                IS_REFRESH = true;
                pageIndex = 1;
                //判断是否有网
                try {
                    isMobileNet = GuirenHttpUtils.isMobileDataEnable(com.Lbins.TvApp.ui.MinePhotoActivity.this);
                    isWifiNet = GuirenHttpUtils.isWifiDataEnable(com.Lbins.TvApp.ui.MinePhotoActivity.this);
                    if (!isMobileNet && !isWifiNet) {
                        lstv.onRefreshComplete();
                    }else {
                        initData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                IS_REFRESH = false;
                pageIndex++;
                //判断是否有网
                try {
                    isMobileNet = GuirenHttpUtils.isMobileDataEnable(com.Lbins.TvApp.ui.MinePhotoActivity.this);
                    isWifiNet = GuirenHttpUtils.isWifiDataEnable(com.Lbins.TvApp.ui.MinePhotoActivity.this);
                    if (!isMobileNet && !isWifiNet) {
                        lstv.onRefreshComplete();
                    }else {
                        initData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        lstv.setAdapter(adapter);
        adapter.setOnClickContentItemListener(this);
        lstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position >1){
                    Record record = recordList.get(position );
                    if (!record.getMm_msg_type().equals("1")) {
                        Intent detail = new Intent(com.Lbins.TvApp.ui.MinePhotoActivity.this, DetailPageAcitvity.class);
                        detail.putExtra(Constants.INFO, record);
                        startActivity(detail);
                    }
                }
            }
        });


        //判断是否有网
        try {
            isMobileNet = GuirenHttpUtils.isMobileDataEnable(com.Lbins.TvApp.ui.MinePhotoActivity.this);
            isWifiNet = GuirenHttpUtils.isWifiDataEnable(com.Lbins.TvApp.ui.MinePhotoActivity.this);
            if (!isMobileNet && !isWifiNet) {
                showMsg(com.Lbins.TvApp.ui.MinePhotoActivity.this ,"请检查您网络链接");
            }else {
                progressDialog = new CustomProgressDialog(com.Lbins.TvApp.ui.MinePhotoActivity.this, "正在加载中",R.anim.custom_dialog_frame);
                progressDialog.setCancelable(true);
                progressDialog.setIndeterminate(true);
                progressDialog.show();
                initData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initData() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.RECORD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            RecordDATA data = getGson().fromJson(s, RecordDATA.class);
                            if (Integer.parseInt(data.getCode()) == 200) {
                                if (IS_REFRESH) {
                                    recordList.clear();
                                }
                                recordList.addAll(data.getData());
                                lstv.onRefreshComplete();
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(com.Lbins.TvApp.ui.MinePhotoActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(com.Lbins.TvApp.ui.MinePhotoActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                        }
                        if(progressDialog != null){
                            progressDialog.dismiss();
                        }
                        if(recordList.size() == 0){
                            no_record.setVisibility(View.VISIBLE);
                            lstv.setVisibility(View.GONE);
                        }else {
                            no_record.setVisibility(View.GONE);
                            lstv.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if(progressDialog != null){
                            progressDialog.dismiss();
                        }
                        Toast.makeText(com.Lbins.TvApp.ui.MinePhotoActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("page", String.valueOf(pageIndex));
                params.put("mm_emp_id", mm_emp_id);
                params.put("mm_msg_type",  Constants.RECORD_TYPE);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(request);
    }
    Record record;
    private int tmpSelected;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
    @Override
    public void onClickContentItem(int position, int flag, Object object) {
        //判断是否有网
        try {
            isMobileNet = GuirenHttpUtils.isMobileDataEnable(com.Lbins.TvApp.ui.MinePhotoActivity.this);
            isWifiNet = GuirenHttpUtils.isWifiDataEnable(com.Lbins.TvApp.ui.MinePhotoActivity.this);
            if (!isMobileNet && !isWifiNet) {
                showMsg(com.Lbins.TvApp.ui.MinePhotoActivity.this, "请检查网络链接");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        record = recordList.get(position);
        switch (flag) {
            case 1:
                Intent comment = new Intent(com.Lbins.TvApp.ui.MinePhotoActivity.this, PublishCommentAcitvity.class);
                comment.putExtra(Constants.FATHER_PERSON_NAME, "");
                comment.putExtra(Constants.FATHER_UUID, "0");
                comment.putExtra(Constants.RECORD_UUID, record.getMm_msg_id());
                comment.putExtra(Constants.FATHER_PERSON_UUID, record.getMm_emp_id());
                comment.putExtra("fplempid", "");
                startActivity(comment);
                break;
            case 2:
                recordtmp = record;//放到中间存储
                zan_click(record);
                break;
            case 3:
                break;
            case 4:
//                if (!emp_id.equals(record.getMm_emp_id())) {
//                    Intent profile = new Intent(getActivity(), ProfilePersonalActivity.class);
//                    profile.putExtra(Constants.EMPID, record.getRecordEmpId());
//                    startActivity(profile);
//                } else {
//                    Intent profile = new Intent(getActivity(), UpdateProfilePersonalActivity.class);
//                    startActivity(profile);
//                }
                break;
            case 5:
//                String videoUrl = record.getRecordVideo();
//                Intent intent = new Intent(getActivity(), VideoPlayerActivity2.class);
//                VideoPlayer video = new VideoPlayer(videoUrl);
//                intent.putExtra(Constants.EXTRA_LAYOUT, "0");
//                intent.putExtra(VideoPlayer.class.getName(), video);
//                startActivity(intent);
                break;
            case 6:
                //删除该动态
//                recordtmp = record;//放到中间存储
//                tmpSelected = position;
//                showSelectImageDialog();
                break;
            case 7:
                if (record.getMm_msg_type().equals("1"))
                {
//                    //是推广
//                    Intent webView = new Intent(getActivity(), WebViewActivity.class);
//                    webView.putExtra("strurl", record.get());
//                    startActivity(webView);

                }
                break;
            case 8:
                //网址链接
            {
                String strcont = record.getMm_msg_content();//内容
                if (strcont.contains("http")){
                    //如果包含http
                    String strhttp = strcont.substring(strcont.indexOf("http"), strcont.length());
                    Intent webView = new Intent(com.Lbins.TvApp.ui.MinePhotoActivity.this, WebViewActivity.class);
                    webView.putExtra("strurl", strhttp);
                    webView.putExtra("strname", "贵人");
                    startActivity(webView);
                }
            }
            break;
            case 9:
                //点击学校
//                schoolId = record.getRecordSchoolId();
//                initData();
                break;
            case 11:
            {
                //删除该动态
                recordtmp = record;//放到中间存储
                tmpSelected = position;
                showSelectImageDialog();
            }
            break;
        }
    }


    private DeletePopWindow deleteWindow;

    // 选择是否删除
    private void showSelectImageDialog() {
        deleteWindow = new DeletePopWindow(com.Lbins.TvApp.ui.MinePhotoActivity.this, itemsOnClick);
        //显示窗口
        deleteWindow.showAtLocation(com.Lbins.TvApp.ui.MinePhotoActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            deleteWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_sure:
                    delete();
                    break;
                default:
                    break;
            }
        }
    };

    //删除方法
    private void delete() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.DELETE_RECORDS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            SuccessData data = getGson().fromJson(s, SuccessData.class);
                            if (Integer.parseInt(data.getCode()) == 200) {
                                Toast.makeText(com.Lbins.TvApp.ui.MinePhotoActivity.this, R.string.delete_success, Toast.LENGTH_SHORT).show();
                                recordList.remove(tmpSelected);
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(com.Lbins.TvApp.ui.MinePhotoActivity.this, R.string.delete_error, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(com.Lbins.TvApp.ui.MinePhotoActivity.this, R.string.delete_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(com.Lbins.TvApp.ui.MinePhotoActivity.this, R.string.delete_error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("recordId", recordtmp.getMm_msg_id());
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

    //赞
    private void zan_click(final Record record) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.CLICK_LIKE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            RecordDATA data = getGson().fromJson(s, RecordDATA.class);
                            if (Integer.parseInt(data.getCode()) == 200) {
                                //赞+1
                                Toast.makeText(com.Lbins.TvApp.ui.MinePhotoActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
                                recordtmp.setZanNum(String.valueOf((Integer.valueOf(recordtmp.getZanNum()) + 1)));
                                adapter.notifyDataSetChanged();
                            }
                            if (Integer.parseInt(data.getCode())  == 1) {
                                Toast.makeText(com.Lbins.TvApp.ui.MinePhotoActivity.this, "已经赞过", Toast.LENGTH_SHORT).show();
                            }
                            if (Integer.parseInt(data.getCode())  == 2) {
                                Toast.makeText(com.Lbins.TvApp.ui.MinePhotoActivity.this, "点赞失败，请稍后重试", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(com.Lbins.TvApp.ui.MinePhotoActivity.this, "点赞失败，请稍后重试", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(com.Lbins.TvApp.ui.MinePhotoActivity.this, "点赞失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("recordId", record.getMm_msg_id());
                params.put("empId", getGson().fromJson(getSp().getString("mm_emp_id", ""), String.class));
                params.put("sendEmpId", record.getMm_emp_id());
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

    //广播接收动作
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constants.SEND_SUCCESS)) {
                String str = intent.getExtras().getString(Constants.SEND_VALUE_ONE);
//                if ("0".equals(str)) {
//                    schoolId = "";
//                    maintitle.setText("所有大学");
//                }
//                if ("1".equals(str)) {
//                    schoolId = getGson().fromJson(getSp().getString(Constants.SCHOOLID, ""), String.class);
//                    maintitle.setText("我的大学");
//                }
                IS_REFRESH = true;
                pageIndex = 1;
                initData();
            }
            if (action.equals(Constants.SEND_PIC_TX_SUCCESS)) {
                //更改头像的广播事件
//                imageLoader.displayImage(getGson().fromJson(getSp().getString(Constants.EMPCOVER, ""), String.class), main_cover, UniversityApplication.txOptions, animateFirstListener);
            }
            if (action.equals(Constants.SEND_COMMENT_RECORD_SUCCESS)) {
                //刷新内容,评论+1
                String recordId =  intent.getExtras().getString("recordId");
                for(Record record:recordList){
                    if(record.getMm_msg_id().equals(recordId)){
                        record.setPlNum(String.valueOf(Integer.parseInt(record.getPlNum())+1));
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }
            if(action.equals(Constants.SEND_DELETE_RECORD_SUCCESS)){
                String recordId =  intent.getExtras().getString("recordId");
                for(Record record:recordList){
                    if(record.getMm_msg_id().equals(recordId)){
                        recordList.remove(record);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }
            if(action.equals(Constants.SEND_INDEX_SUCCESS)){
                Record record1 = (Record) intent.getExtras().get("addRecord");
                recordList.add(0, record1);
                adapter.notifyDataSetChanged();
            }
        }

    };

    //注册广播
    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constants.SEND_SUCCESS);//设置下拉按钮的广播事件
        myIntentFilter.addAction(Constants.SEND_PIC_TX_SUCCESS);//设置头像的广播事件
        myIntentFilter.addAction(Constants.SEND_COMMENT_RECORD_SUCCESS);//动态评论添加  更新评论数量
        myIntentFilter.addAction(Constants.SEND_DELETE_RECORD_SUCCESS);//动态详情页删除动态，更新首页
        myIntentFilter.addAction(Constants.SEND_INDEX_SUCCESS);//添加说说和添加视频成功，刷新首页
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }


}
