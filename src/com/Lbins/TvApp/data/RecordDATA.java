package com.Lbins.TvApp.data;

import com.Lbins.TvApp.data.Data;
import com.Lbins.TvApp.module.Record;

import java.util.List;

/**
 * Created by zhl on 2016/5/12.
 */
public class RecordDATA extends Data {
    private List<Record> data;

    public List<Record> getData() {
        return data;
    }

    public void setData(List<Record> data) {
        this.data = data;
    }
}
