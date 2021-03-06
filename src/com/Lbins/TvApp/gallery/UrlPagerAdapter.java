/*
 Copyright (c) 2013 Roman Truba

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial
 portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.Lbins.TvApp.gallery;

import android.content.Context;
import android.view.ViewGroup;
import com.Lbins.TvApp.gallery.*;
import com.Lbins.TvApp.gallery.UrlTouchImageView;

import java.util.List;


/**
 * Class wraps URLs to adapter, then it instantiates {@link com.Lbins.TvApp.gallery.UrlTouchImageView} objects to paging up through them.
 */
public class UrlPagerAdapter extends BasePagerAdapter {
    private int width;
    private int height;

    public UrlPagerAdapter(Context context, List<String> resources, int width, int height) {
        super(context, resources);
        this.width = width;
        this.height = height;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        ((GalleryViewPager) container).mCurrentView = ((com.Lbins.TvApp.gallery.UrlTouchImageView) object).getImageView();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        final com.Lbins.TvApp.gallery.UrlTouchImageView iv = new UrlTouchImageView(mContext);
        iv.setUrl(mResources.get(position));
        iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        collection.addView(iv, 0);
        return iv;
    }

}
