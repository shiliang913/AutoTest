package com.test.batterylife;

import java.io.IOException;
import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;

public class Map extends Operation {

	public void running(int n) throws IOException, UiObjectNotFoundException, RemoteException{
		try {
			wakeUpAndUnlock();
			pressHome();
			setWatcher("以后再说","Map");
			launchApp("com.baidu.BaiduMap");
			sleep(5000);
			clickExist("接受");//百度地图服务条款
			clickExist(getObjByID("com.baidu.BaiduMap:id/guide_button_close"));//关闭向导
			sleep(2000);
			clickExist("稍后查看");//推送取消
			click("路线");
			clickID("com.baidu.BaiduMap:id/iv_topbar_middle_foot");
			clickID("com.baidu.BaiduMap:id/EditText_navsearch_start");
			sleep(3000);
			clickExist("以后再说");
			inputWithAndroid("com.baidu.BaiduMap:id/edittext_searchbox_search_input", "tcldasha", "搜狗输入法", false);
			getObjByText("tcl大厦").waitForExists(20000);
			click("tcl大厦");
			sleep(2000);
			clickID("com.baidu.BaiduMap:id/EditText_navsearch_end");
			sleep(3000);
			clickExist("以后再说");
			inputWithAndroid("com.baidu.BaiduMap:id/edittext_searchbox_search_input", "huanlegu", "搜狗输入法", false);
			getObjByText("欢乐谷").waitForExists(20000);
			click("欢乐谷");
			getObjByText("跟我走").waitForExists(10000);
			click("跟我走");
			clickExist("接受");
			Thread.sleep(1800000);	//30min
			screenShot("/sdcard/BatteryLife/Map.png");
			pressBack();
			click("确定");
			pressHome();
			UiDevice.getInstance().removeWatcher("Map");
			writeFile("PASS "+getTime()+" "+cmdResult("cat /sys/class/power_supply/battery/capacity")+"% Map\n","/sdcard/BatteryLife/BatteryLife.txt", true, true);
		} catch (Exception e) {
			exitAndScreenShot("/sdcard/BatteryLife/Map_"+n+".png");
			writeFile("FAIL "+getTime()+" "+cmdResult("cat /sys/class/power_supply/battery/capacity")+"% Map\n","/sdcard/BatteryLife/BatteryLife.txt", true, true);
		}
	}
}
