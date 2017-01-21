package com.Lbins.TvApp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.Lbins.TvApp.R;
import com.Lbins.TvApp.module.HelpType;

import java.util.List;

/**
 */
public class HelpTypeAdapter extends BaseAdapter {
    private ViewHolder holder;
    private List<HelpType> records;
    private Context mContext;

    public HelpTypeAdapter(List<HelpType> records, Context mContext) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_help_type, null);
            holder.card_number = (TextView) convertView.findViewById(R.id.card_number);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final HelpType cell = records.get(position);//获得元素
        if (cell != null) {
            holder.card_number.setText(cell.getHelp_type_name());
        }

        return convertView;
    }

    class ViewHolder {
        TextView card_number;
    }
}
