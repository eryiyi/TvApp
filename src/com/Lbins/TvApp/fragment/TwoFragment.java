package com.Lbins.TvApp.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.Lbins.TvApp.R;
import com.Lbins.TvApp.base.BaseFragment;

/**
 * Created by zhl on 2016/7/9.
 */
public class TwoFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private Resources res;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.two_fragment, null);
        res = getActivity().getResources();
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
