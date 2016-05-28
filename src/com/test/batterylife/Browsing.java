package com.test.batterylife;

import java.io.IOException;
import android.os.RemoteException;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;

public class Browsing extends Operation {

	public void running(int n) throws IOException, UiObjectNotFoundException, RemoteException{
		try {
			wakeUpAndUnlock();
			pressHome();
			launchApp("com.android.chrome");
			sleep(5000);
			clickExist("Accept & continue");
			sleep(2000);
			click(getObjByID("com.android.chrome:id/url_bar"));
			clickID("com.android.chrome:id/delete_button");
			inputWithAndroid("com.android.chrome:id/url_bar", "211.140.14.22:8480/wxgj/main.jsp", "搜狗输入法", true);
			UiObject menu = getObjByID("com.android.chrome:id/document_menu_button");
			UiObject cancel = getObjByID("com.android.systemui:id/recents_clearall_button");
			UiObject refresh = getObjByID("com.android.chrome:id/button_three");
			for(int i=0;i<85;i++){	//85次
				sleep(20000);
				click(menu);
				sleep(1000);
				if(cancel.exists()){
					click(cancel);
					sleep(2000);
					click(menu);
					sleep(1000);
				}
				click(refresh);
			}
			screenShot("/sdcard/BatteryLife/Browsing.png");
			pressHome();
			writeFile("PASS "+getTime()+" "+cmdResult("cat /sys/class/power_supply/battery/capacity")+"% Browsing\n","/sdcard/BatteryLife/BatteryLife.txt", true, true);
		} catch (Exception e) {
			exitAndScreenShot("/sdcard/BatteryLife/Browsing_"+n+".png");
			writeFile("FAIL "+getTime()+" "+cmdResult("cat /sys/class/power_supply/battery/capacity")+"% Browsing\n","/sdcard/BatteryLife/BatteryLife.txt", true, true);
		}
	}
}
