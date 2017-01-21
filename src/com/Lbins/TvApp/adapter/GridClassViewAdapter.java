package com.Lbins.TvApp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.Lbins.TvApp.R;
import com.Lbins.TvApp.module.HelpType;

import java.util.ArrayList;

public class GridClassViewAdapter extends BaseAdapter {

	private ArrayList<HelpType> list;
	private HelpType type;
	private Context context;
	Holder view;

	public GridClassViewAdapter(Context context, ArrayList<HelpType> list) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		if (list != null && list.size() > 0)
			return list.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_grid_view_text, null);
			view = new Holder(convertView);
			convertView.setTag(view);
		} else {
			view = (Holder) convertView.getTag();
		}
		if (list != null && list.size() > 0) {
			type = list.get(position);
//			view.icon.setBackground(context.getResources().getDrawable(
//					type.getIcon()));
			view.name.setText(type.getHelp_type_name());
		}

		return convertView;
	}

	private class Holder {
//		private ImageView icon;
		private TextView name;

		public Holder(View view) {
//			icon = (ImageView) view.findViewById(R.id.typeicon);
			name = (TextView) view.findViewById(R.id.typename);
		}
	}

}
