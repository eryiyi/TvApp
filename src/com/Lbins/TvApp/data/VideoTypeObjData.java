package com.Lbins.TvApp.data;

import com.Lbins.TvApp.data.Data;
import com.Lbins.TvApp.module.VideoTypeObj;

import java.util.List;

/**
 * Created by zhl on 2016/9/11.
 */
public class VideoTypeObjData extends Data {
    private List<VideoTypeObj> data;

    public List<VideoTypeObj> getData() {
        return data;
    }

    public void setData(List<VideoTypeObj> data) {
        this.data = data;
    }
}
