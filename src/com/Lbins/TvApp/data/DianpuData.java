package com.Lbins.TvApp.data;


import com.Lbins.TvApp.data.*;
import com.Lbins.TvApp.data.Data;
import com.Lbins.TvApp.module.EmpDianpu;

import java.util.List;

/**
 * Created by zhl on 2016/5/23.
 */
public class DianpuData extends Data {
    private List<EmpDianpu> data;

    public List<EmpDianpu> getData() {
        return data;
    }

    public void setData(List<EmpDianpu> data) {
        this.data = data;
    }
}
