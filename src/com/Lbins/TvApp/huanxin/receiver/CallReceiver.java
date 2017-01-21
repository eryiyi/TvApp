/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.Lbins.TvApp.huanxin.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.Lbins.TvApp.dao.DBHelper;
import com.Lbins.TvApp.huanxin.DemoHelper;
import com.Lbins.TvApp.huanxin.ui.VideoCallActivity;
import com.Lbins.TvApp.huanxin.ui.VoiceCallActivity;
import com.Lbins.TvApp.module.Emp;
import com.hyphenate.util.EMLog;

public class CallReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		if(!com.Lbins.TvApp.huanxin.DemoHelper.getInstance().isLoggedIn())
		    return;
		//username
		String from = intent.getStringExtra("from");
		//call type
		String type = intent.getStringExtra("type");
		com.Lbins.TvApp.module.Emp emp = com.Lbins.TvApp.dao.DBHelper.getInstance(context).getEmpById(from);
		if("video".equals(type)){ //video call
			if(emp != null){
				context.startActivity(new Intent(context, com.Lbins.TvApp.huanxin.ui.VideoCallActivity.class).
						putExtra("username", from).putExtra("isComingCall", true).putExtra("usernameStr", emp.getMm_emp_nickname()).
						addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			}else {
				context.startActivity(new Intent(context, com.Lbins.TvApp.huanxin.ui.VideoCallActivity.class).
						putExtra("username", from).putExtra("isComingCall", true).putExtra("usernameStr", "").
						addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			}

		}else{ //voice call
			if(emp != null){
				context.startActivity(new Intent(context, com.Lbins.TvApp.huanxin.ui.VoiceCallActivity.class).
						putExtra("username", from).putExtra("isComingCall", true).putExtra("usernameStr", emp.getMm_emp_nickname()).
						addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			}else {
				context.startActivity(new Intent(context, com.Lbins.TvApp.huanxin.ui.VoiceCallActivity.class).
						putExtra("username", from).putExtra("isComingCall", true).putExtra("usernameStr", "").
						addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			}

		}
		EMLog.d("CallReceiver", "app received a incoming call");
	}

}
