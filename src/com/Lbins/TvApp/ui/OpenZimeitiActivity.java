package com.Lbins.TvApp.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import com.Lbins.TvApp.R;
import com.Lbins.TvApp.adapter.AnimateFirstDisplayListener;
import com.Lbins.TvApp.base.BaseActivity;
import com.Lbins.TvApp.base.InternetURL;
import com.Lbins.TvApp.data.GdTypeObjData;
import com.Lbins.TvApp.module.GdTypeObj;
import com.Lbins.TvApp.ui.ProfileZmtActivity;
import com.Lbins.TvApp.util.CompressPhotoUtil;
import com.Lbins.TvApp.util.StringUtil;
import com.Lbins.TvApp.widget.CustomProgressDialog;
import com.Lbins.TvApp.widget.CustomerSpinner;
import com.Lbins.TvApp.widget.SelectPhoPopWindow;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2016/7/29.
 */
public class OpenZimeitiActivity extends BaseActivity implements View.OnClickListener {
    private EditText company_name;
    private EditText company_person;
    private EditText company_tel;
    private EditText company_address;
    private ImageView add_pic;
    private EditText company_detail;
    private Button sign_in_button;

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类

    private String pics = "";
    private static final File PHOTO_CACHE_DIR = new File(Environment.getExternalStorageDirectory() + "/liangxun/PhotoCache");

    private SelectPhoPopWindow deleteWindow;
    AsyncHttpClient client = new AsyncHttpClient();


    //省市县
    private CustomerSpinner province;
    private List<GdTypeObj> provinces = new ArrayList<GdTypeObj>();
    private ArrayList<String> provinceNames = new ArrayList<String>();//省份名称
    ArrayAdapter<String> ProvinceAdapter;
    private String provinceName = "";
    private String provinceCode = "";

    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_zimeiti_activity);
        res = getResources();
        company_name = (EditText) this.findViewById(R.id.company_name);
        company_person = (EditText) this.findViewById(R.id.company_person);
        company_tel = (EditText) this.findViewById(R.id.company_tel);
        company_address = (EditText) this.findViewById(R.id.company_address);
        company_detail = (EditText) this.findViewById(R.id.company_detail);
        add_pic = (ImageView) this.findViewById(R.id.add_pic);
        sign_in_button = (Button) this.findViewById(R.id.sign_in_button);
        add_pic.setOnClickListener(this);
        sign_in_button.setOnClickListener(this);
        this.findViewById(R.id.back).setOnClickListener(this);
        ProvinceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, provinceNames);
        ProvinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        province = (CustomerSpinner) findViewById(R.id.mm_emp_provinceId);
        province.setAdapter(ProvinceAdapter);
        province.setList(provinceNames);
        province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GdTypeObj province = null;
                if (provinces != null && position > 0) {
                    province = provinces.get(position - 1);
                    provinceName = province.getGd_type_name();
                    provinceCode = province.getGd_type_id();
                } else if (provinces != null) {
                    province = provinces.get(position);
                    provinceName = province.getGd_type_name();
                    provinceCode = province.getGd_type_id();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        getType();

    }


    public void getType() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.GET_GD_TYPE_LISTS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code1 = jo.getString("code");
                                if (Integer.parseInt(code1) == 200) {
                                    provinceNames.add(res.getString(R.string.select_province));
                                    GdTypeObjData data = getGson().fromJson(s, GdTypeObjData.class);
                                    provinces = data.getData();
                                    if (provinces != null) {
                                        for (int i = 0; i < provinces.size(); i++) {
                                            provinceNames.add(provinces.get(i).getGd_type_name());
                                        }
                                    }
                                    ProvinceAdapter.notifyDataSetChanged();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(com.Lbins.TvApp.ui.OpenZimeitiActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(com.Lbins.TvApp.ui.OpenZimeitiActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_pic:
                ShowPickDialog();
                break;
            case R.id.sign_in_button:
                if(StringUtil.isNullOrEmpty(company_name.getText().toString()) ||
                        StringUtil.isNullOrEmpty(company_person.getText().toString())||
                        StringUtil.isNullOrEmpty(company_tel.getText().toString())||
                        StringUtil.isNullOrEmpty(company_address.getText().toString())||
                        StringUtil.isNullOrEmpty(company_detail.getText().toString())||
                        StringUtil.isNullOrEmpty(provinceCode)||
                        StringUtil.isNullOrEmpty(pics)){
                    showMsg(com.Lbins.TvApp.ui.OpenZimeitiActivity.this, "请完善信息");
                    return;
                }
                progressDialog = new CustomProgressDialog(com.Lbins.TvApp.ui.OpenZimeitiActivity.this, "正在加载中",R.anim.custom_dialog_frame);
                progressDialog.setCancelable(true);
                progressDialog.setIndeterminate(true);
                progressDialog.show();
                uploadCover();
                break;
            case R.id.back:
                finish();
                break;

        }
    }
    // 选择相册，相机
    private void ShowPickDialog() {
        deleteWindow = new SelectPhoPopWindow(com.Lbins.TvApp.ui.OpenZimeitiActivity.this, itemsOnClick);
        //显示窗口
        deleteWindow.showAtLocation(this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            deleteWindow.dismiss();
            switch (v.getId()) {
                case R.id.camera: {
                    Intent camera = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);
                    //下面这句指定调用相机拍照后的照片存储的路径
                    camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                            .fromFile(new File(Environment
                                    .getExternalStorageDirectory(),
                                    "ppCover.jpg")));
                    startActivityForResult(camera, 2);
                }
                break;
                case R.id.mapstorage: {
                    Intent mapstorage = new Intent(Intent.ACTION_PICK, null);
                    mapstorage.setDataAndType(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            "image/*");
                    startActivityForResult(mapstorage, 1);
                }
                break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // 如果是直接从相册获取
            case 1:
                if (data != null) {
                    startPhotoZoom(data.getData());
                }
                break;
            // 如果是调用相机拍照时
            case 2:
                File temp = new File(Environment.getExternalStorageDirectory()
                        + "/ppCover.jpg");
                startPhotoZoom(Uri.fromFile(temp));
                break;
            // 取得裁剪后的图片
            case 3:
                if (data != null) {
                    setPicToView(data);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 2);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            if (photo != null) {
                pics = CompressPhotoUtil.saveBitmap2file(photo, System.currentTimeMillis() + ".jpg", PHOTO_CACHE_DIR);
                add_pic.setImageBitmap(photo);
            }
        }
    }

    //上传到七牛云存贮
    void uploadCover(){
        Map<String,String> map = new HashMap<String,String>();
        map.put("space", InternetURL.QINIU_SPACE);
        RequestParams params = new RequestParams(map);
        client.get(InternetURL.UPLOAD_TOKEN, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String token = response.getString("data");
                    UploadManager uploadManager = new UploadManager();
                    uploadManager.put(StringUtil.getBytes(pics), StringUtil.getUUID(), token,
                            new UpCompletionHandler() {
                                @Override
                                public void complete(String key, ResponseInfo info, JSONObject response) {
                                    publishAll(key);
                                }
                            }, null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
    private void publishAll(final String uploadpic) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.OPEN_ZIMEITI_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code = jo.getString("code");
                                if(Integer.parseInt(code) == 200) {
                                    showMsg(com.Lbins.TvApp.ui.OpenZimeitiActivity.this, "自媒体信息完善成功");
                                    save("mm_emp_type", "1");
                                    Intent intentzmt = new Intent(com.Lbins.TvApp.ui.OpenZimeitiActivity.this, ProfileZmtActivity.class);
                                    intentzmt.putExtra("mm_emp_id", getGson().fromJson(getSp().getString("mm_emp_id", ""), String.class));
                                    startActivity(intentzmt);
                                }else{
                                    showMsg(com.Lbins.TvApp.ui.OpenZimeitiActivity.this, "操作失败，请稍后重试！");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            showMsg(com.Lbins.TvApp.ui.OpenZimeitiActivity.this, "操作失败，请稍后重试！");
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
                        showMsg(com.Lbins.TvApp.ui.OpenZimeitiActivity.this, "操作失败，请稍后重试！");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("emp_id", getGson().fromJson(getSp().getString("mm_emp_id", ""), String.class));
                params.put("company_pic", uploadpic);
                params.put("company_name", company_name.getText().toString());
                params.put("company_detail", company_detail.getText().toString());
                params.put("company_tel", company_tel.getText().toString());
                params.put("company_person", company_person.getText().toString());
                params.put("company_address", company_address.getText().toString());
                params.put("gd_type_id", provinceCode);

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
