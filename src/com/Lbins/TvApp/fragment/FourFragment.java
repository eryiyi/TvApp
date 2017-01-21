package com.Lbins.TvApp.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.Lbins.TvApp.R;
import com.Lbins.TvApp.base.BaseFragment;
import com.Lbins.TvApp.ui.*;

/**
 * Created by zhl on 2016/5/6.
 */
public class FourFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private Resources res;
    boolean isMobileNet, isWifiNet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.four_fragment, null);
        res = getActivity().getResources();
        view.findViewById(R.id.msg).setOnClickListener(this);
        view.findViewById(R.id.liner_five).setOnClickListener(this);//附近的人
        view.findViewById(R.id.liner_one).setOnClickListener(this);//扫一扫
        view.findViewById(R.id.liner_two).setOnClickListener(this);//摇一摇
        view.findViewById(R.id.liner_three).setOnClickListener(this);//购物
        view.findViewById(R.id.liner_four).setOnClickListener(this);//游戏
        view.findViewById(R.id.liner_find).setOnClickListener(this);//游戏
//        view.findViewById(R.id.liner_news).setOnClickListener(this);//游戏
//        view.findViewById(R.id.liner_baike).setOnClickListener(this);//游戏
        view.findViewById(R.id.liner_zmt).setOnClickListener(this);//自媒体
        view.findViewById(R.id.liner_movie).setOnClickListener(this);//电影
        view.findViewById(R.id.liner_tv).setOnClickListener(this);//电影
        view.findViewById(R.id.liner_help).setOnClickListener(this);//帮忙
        return view;
    }

    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.liner_find:
           {
               Intent friendsV = new Intent(getActivity(), FindActivity.class);
               startActivity(friendsV);
           }
               break;
           case R.id.msg:
               //消息
           {
           }
               break;
           case R.id.liner_five://附近的人
               Toast.makeText(getActivity(), "功能尚未开发，敬请期待", Toast.LENGTH_LONG).show();
               break;
           case R.id.liner_one://扫一扫
           {

           }
               break;
           case R.id.liner_two:
           {
               Intent intent = new Intent(getActivity(), TongxunluActivity.class);
               startActivity(intent);
           }
               break;
           case R.id.liner_three://购物
               Toast.makeText(getActivity(),"功能尚未开发，敬请期待",Toast.LENGTH_LONG).show();
               break;
           case R.id.liner_four://游戏
               Toast.makeText(getActivity(),"功能尚未开发，敬请期待",Toast.LENGTH_LONG).show();
               break;
//           case R.id.liner_news:
//           {
//               //判断是否有网
//               try {
//                   isMobileNet = GuirenHttpUtils.isMobileDataEnable(getActivity());
//                   isWifiNet = GuirenHttpUtils.isWifiDataEnable(getActivity());
//                   if (!isMobileNet && !isWifiNet) {
//                       Toast.makeText(getActivity(), "请检查网络链接", Toast.LENGTH_SHORT).show();
//                   }else {               //今日头条
//                       Intent intent = new Intent(getActivity(), WebViewActivity.class);
//                       intent.putExtra("strurl", "http://m.thepaper.cn/");
//                       intent.putExtra("strname", "澎湃新闻");
//                       startActivity(intent);
//
//                   }
//               } catch (Exception e) {
//                   e.printStackTrace();
//               }
//
//           }
//               break;
//           case R.id.liner_baike:
//           {
//               //判断是否有网
//               try {
//                   isMobileNet = GuirenHttpUtils.isMobileDataEnable(getActivity());
//                   isWifiNet = GuirenHttpUtils.isWifiDataEnable(getActivity());
//                   if (!isMobileNet && !isWifiNet) {
//                       Toast.makeText(getActivity(), "请检查网络链接", Toast.LENGTH_SHORT).show();
//                   }else {               //今日头条
//                       //糗事百科
////                       Intent intent = new Intent(getActivity(), WebViewActivity.class);
////                       intent.putExtra("strurl", "http://www.qiushibaike.com/");
////                       intent.putExtra("strname", "糗事");
////                       startActivity(intent);
//                       final Uri uri = Uri.parse("http://www.qiushibaike.com");
//                       final Intent it = new Intent(Intent.ACTION_VIEW, uri);
//                       startActivity(it);
//                   }
//               } catch (Exception e) {
//                   e.printStackTrace();
//               }
//           }
//               break;
           case R.id.liner_zmt:
           {
               //自媒体
               Intent intent = new Intent(getActivity(), ZimeitiActivity.class);
               startActivity(intent);
           }
               break;
           case R.id.liner_movie:
           {
               //电影
               Intent intent = new Intent(getActivity(), VideosSearchActivity.class);
               startActivity(intent);
           }
               break;
           case R.id.liner_tv:
           {
               //视频
               Intent intent = new Intent(getActivity(), VideosActivity.class);
               startActivity(intent);
           }
               break;
           case R.id.liner_help:
           {
               //帮忙
               Intent intent = new Intent(getActivity(), GuirenHelpActivity.class);
               startActivity(intent);
           }
               break;

       }
    }


}
