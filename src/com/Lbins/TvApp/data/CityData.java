package com.Lbins.TvApp.data;



import com.Lbins.TvApp.data.Data;
import com.Lbins.TvApp.module.CityObj;

import java.util.List;

/**
 * Created by Administrator on 2016/2/10.
 */
public class CityData extends Data {
    private List<CityObj> data;

    public List<CityObj> getData() {
        return data;
    }

    public void setData(List<CityObj> data) {
        this.data = data;
    }
}
