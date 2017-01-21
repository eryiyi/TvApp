package com.Lbins.TvApp.data;


import com.Lbins.TvApp.data.Data;
import com.Lbins.TvApp.module.Videos;

import java.util.List;

/**
 * Created by Administrator on 2015/12/15.
 */
public class VideosData extends Data{
    private List<Videos> data;

    public List<Videos> getData() {
        return data;
    }

    public void setData(List<Videos> data) {
        this.data = data;
    }
}
