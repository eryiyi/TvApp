package com.Lbins.TvApp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.Lbins.TvApp.R;
import com.Lbins.TvApp.module.ProvinceObj;

import java.util.List;

/**
 */
public class ItemPorvinceAdapter extends BaseAdapter {
    private ViewHolder holder;
    private List<ProvinceObj> records;
    private Context mContext;


    public ItemPorvinceAdapter(List<ProvinceObj> records, Context mContext) {
        this.records = records;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_work, null);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ProvinceObj cell = records.get(position);//获得元素
        if (cell != null) {
            holder.title.setText(cell.getProvince());
        }
        return convertView;
    }

    class ViewHolder {

        TextView title;
    }
}
