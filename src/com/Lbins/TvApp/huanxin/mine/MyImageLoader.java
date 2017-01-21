package com.Lbins.TvApp.huanxin.mine;

import android.graphics.Bitmap;
import android.util.LruCache;
import com.Lbins.TvApp.TvApplication;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2015/3/13.
 */
public class MyImageLoader extends ImageLoader {
    private static com.Lbins.TvApp.huanxin.mine.MyImageLoader imageLoader;

    public MyImageLoader(RequestQueue queue, ImageCache imageCache) {
        super(queue, imageCache);
    }


    public static com.Lbins.TvApp.huanxin.mine.MyImageLoader getInstance() {
        if (imageLoader == null) {
            imageLoader = new com.Lbins.TvApp.huanxin.mine.MyImageLoader(Volley.newRequestQueue(TvApplication.applicationContext), new BitmapCache());
        }
        return imageLoader;

    }

    private static class BitmapCache implements ImageCache {

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

}
