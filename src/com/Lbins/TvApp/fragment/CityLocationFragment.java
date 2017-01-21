package com.Lbins.TvApp.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.Lbins.TvApp.R;
import com.Lbins.TvApp.adapter.GridCityLocationViewAdapter;
import com.Lbins.TvApp.base.BaseFragment;
import com.Lbins.TvApp.base.InternetURL;
import com.Lbins.TvApp.data.CityData;
import com.Lbins.TvApp.module.CityObj;
import com.Lbins.TvApp.module.ProvinceObj;
import com.Lbins.TvApp.ui.CityLocationActivity;
import com.Lbins.TvApp.util.StringUtil;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CityLocationFragment extends com.Lbins.TvApp.base.BaseFragment {

	private ArrayList<com.Lbins.TvApp.module.CityObj> list = new ArrayList<com.Lbins.TvApp.module.CityObj>();
	private GridView gridView;
	private com.Lbins.TvApp.adapter.GridCityLocationViewAdapter adapter;
	private com.Lbins.TvApp.module.ProvinceObj provinceObj;
	private TextView toptype;
	private ImageView icon;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pro_type1, null);
		gridView = (GridView) view.findViewById(R.id.listView);
		int index = getArguments().getInt("index");
		provinceObj = com.Lbins.TvApp.ui.CityLocationActivity.list.get(index);
		toptype = (TextView) view.findViewById(R.id.toptype);
		icon = (ImageView) view.findViewById(R.id.icon);
		if(provinceObj != null){
			toptype.setText(provinceObj.getProvince());
		}
		adapter = new com.Lbins.TvApp.adapter.GridCityLocationViewAdapter(getActivity(), list);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				if(list !=null){
					if(list.size() > i){
						com.Lbins.TvApp.module.CityObj cityObj = list.get(i);
						if(cityObj != null){
							save("location_city", cityObj.getCity());
							save("location_city_id", cityObj.getCityID());
							Intent intent1 = new Intent("update_location_success");
							getActivity().sendBroadcast(intent1);
							getActivity().finish();
						}
					}
				}

			}
		});

		getCitys1();

		return view;
	}

	//获得城市
	public void getCitys1() {
		StringRequest request = new StringRequest(
				Request.Method.POST,
				com.Lbins.TvApp.base.InternetURL.GET_CITY_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						if (com.Lbins.TvApp.util.StringUtil.isJson(s)) {
							try {
								JSONObject jo = new JSONObject(s);
								String code1 = jo.getString("code");
								if (Integer.parseInt(code1) == 200) {
									com.Lbins.TvApp.data.CityData data = getGson().fromJson(s, com.Lbins.TvApp.data.CityData.class);
									list.clear();
									list.addAll(data.getData());
									adapter.notifyDataSetChanged();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(getActivity(), R.string.get_data_error, Toast.LENGTH_SHORT).show();
						}
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
						Toast.makeText(getActivity(), R.string.get_data_error, Toast.LENGTH_SHORT).show();
					}
				}
		) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("father", provinceObj.getProvinceID());
				params.put("is_use", "1");
				return params;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("Content-Type", "application/x-www-form-urlencoded");
				return params;
			}
		};
		getRequestQueue().add(request);
	}
}
