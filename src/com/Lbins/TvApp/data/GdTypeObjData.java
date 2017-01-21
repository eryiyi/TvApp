package com.Lbins.TvApp.data;

import com.Lbins.TvApp.data.Data;
import com.Lbins.TvApp.module.GdTypeObj;

import java.util.List;

/**
 * Created by zhl on 2016/9/26.
 */
public class GdTypeObjData extends Data {
    private List<GdTypeObj> data;

    public List<GdTypeObj> getData() {
        return data;
    }

    public void setData(List<GdTypeObj> data) {
        this.data = data;
    }
}
