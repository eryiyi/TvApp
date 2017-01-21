package com.Lbins.TvApp.data;

import com.Lbins.TvApp.data.Data;
import com.Lbins.TvApp.module.MinePicObj;

import java.util.List;

/**
 * Created by zhl on 2016/8/20.
 */
public class MinePicObjData extends Data {
    private List<MinePicObj> data;

    public List<MinePicObj> getData() {
        return data;
    }

    public void setData(List<MinePicObj> data) {
        this.data = data;
    }
}
