package com.Lbins.TvApp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.LruCache;
import com.Lbins.TvApp.R;
import com.Lbins.TvApp.base.InternetURL;
import com.Lbins.TvApp.dao.DBHelper;
import com.Lbins.TvApp.data.EmpData;
import com.Lbins.TvApp.huanxin.DemoHelper;
import com.Lbins.TvApp.module.*;
import com.Lbins.TvApp.module.CityObj;
import com.Lbins.TvApp.module.CountryObj;
import com.Lbins.TvApp.module.Emp;
import com.Lbins.TvApp.module.HangYeType;
import com.Lbins.TvApp.module.ProvinceObj;
import com.Lbins.TvApp.util.StringUtil;
import com.Lbins.TvApp.module.*;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.easemob.redpacketsdk.RedPacket;
import com.google.gson.Gson;
import com.hyphenate.easeui.controller.EaseUI;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.umeng.socialize.PlatformConfig;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * author: ${zhanghailong}
 * Date: 2015/1/29
 * Time: 17:04
 * 类的功能、说明写在此处.
 */
public class TvApplication extends Application  {
    // 运用list来保存们每一个activity是关键
    private List<Activity> mList = new LinkedList<Activity>();
    private ExecutorService lxThread;
    private Gson gson;
    private RequestQueue requestQueue;
    private SharedPreferences sp;

    private static TvApplication application;

    public static Context applicationContext;

    public static String latStr;
    public static String lngStr;
    public static String locationAddress;

    public static String locationProvinceName;
    public static String locationCityName;
    public static String locationAreaName;

    public static List<com.Lbins.TvApp.module.HangYeType>  listsTypeHy = new ArrayList<com.Lbins.TvApp.module.HangYeType>();
    public  static List<com.Lbins.TvApp.module.ProvinceObj> provinces = new ArrayList<com.Lbins.TvApp.module.ProvinceObj>();
    public  static List<com.Lbins.TvApp.module.CityObj> cities = new ArrayList<com.Lbins.TvApp.module.CityObj>();
    public  static List<com.Lbins.TvApp.module.CountryObj> areas = new ArrayList<com.Lbins.TvApp.module.CountryObj>();


    // login user name
    public final String PREF_USERNAME = "username";

    public static String currentUserNick = "";
    public static String currentCover = "";
    public static String currentName = "";

    @Override
    public void onCreate() {
        super.onCreate();
//        MultiDex.install(this);
        applicationContext = this;
        instance = this;
        application = this;
        requestQueue = Volley.newRequestQueue(this);
        gson = new Gson();
        lxThread = Executors.newFixedThreadPool(20);
        sp = getSharedPreferences("guiren_manage", Context.MODE_PRIVATE);
        imageLoader = new com.android.volley.toolbox.ImageLoader(requestQueue, new BitmapCache());
        initImageLoader(this);
        //init demo helper
        com.Lbins.TvApp.huanxin.DemoHelper.getInstance().init(applicationContext);
        //red packet code : 初始化红包上下文，开启日志输出开关
        RedPacket.getInstance().initContext(applicationContext);
        RedPacket.getInstance().setDebugMode(true);
        //end of red packet code
        //微信 wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        PlatformConfig.setWeixin(com.Lbins.TvApp.base.InternetURL.WEIXIN_APPID, com.Lbins.TvApp.base.InternetURL.WEIXIN_SECRET);
        //微信 appid appsecret
        PlatformConfig.setQQZone("1105560084", "939gBlg60YfMVERY");
        EaseUI.getInstance().init(this, null);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    /**
     * 获取自定义线程池
     *
     * @return
     */
    public ExecutorService getLxThread() {
        if (lxThread == null) {
            lxThread = Executors.newFixedThreadPool(20);
        }
        return lxThread;
    }



    /**
     * 获取Gson
     *
     * @return
     */
    public Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    /**
     * 获取Volley请求队列
     *
     * @return
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this);
        }
        return requestQueue;
    }

    /**
     * 获取SharedPreferences
     *
     * @return
     */
    public SharedPreferences getSp() {
        if (sp == null) {
            sp = getSharedPreferences("guiren_manage", Context.MODE_PRIVATE);
        }
        return sp;
    }

    public static DisplayImageOptions options;
    public static DisplayImageOptions txOptions;//头像图片

    public TvApplication() {

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.no_data)
                .showImageForEmptyUri(R.drawable.no_data)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.no_data)        // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                           // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                             // 设置下载的图片是否缓存在内存卡中
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565)          //图片的解码类型
                .build();                                       // 创建配置过得DisplayImageOption对象

        txOptions = new DisplayImageOptions.Builder()//头像
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher)        // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                           // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                             // 设置下载的图片是否缓存在内存卡中
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)          //图片的解码类型头像
                .build();
    }

    /**
     * 初始化图片加载组件ImageLoader
     *
     * @param context
     */
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
    }

    private com.android.volley.toolbox.ImageLoader imageLoader;

    private class BitmapCache implements com.android.volley.toolbox.ImageLoader.ImageCache {

        private LruCache<String, Bitmap> mCache;

        public BitmapCache() {
            int maxSize = 10 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }

    }

    public com.android.volley.toolbox.ImageLoader getImageLoader() {
        return imageLoader;
    }


    private static TvApplication instance;

    // 构造方法
    // 实例化一次
    public synchronized static TvApplication getInstance() {
        if (null == instance) {
            instance = new TvApplication();
        }
        return instance;
    }

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    // 关闭每一个list内的activity
    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    //查询用户信息
    public void getEmpId(final String hxusername) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                com.Lbins.TvApp.base.InternetURL.GET_EMP_BY_HXUSERNAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (com.Lbins.TvApp.util.StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code = jo.getString("code");
                                if (Integer.parseInt(code) == 200) {
                                    com.Lbins.TvApp.data.EmpData data = getGson().fromJson(s, com.Lbins.TvApp.data.EmpData.class);
                                    com.Lbins.TvApp.module.Emp emp = data.getData();
                                    //查询是否存在该动态
//                                    Boolean flag = DBHelper.getInstance(applicationContext).isRecord(emp.getHxusername());
//                                    if (flag) {
//                                        //已经存在了 不需要插入了
//                                    } else {
                                    com.Lbins.TvApp.dao.DBHelper.getInstance(applicationContext).saveEmp(emp);
//                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("hxusername", hxusername);
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

