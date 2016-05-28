package com.test.batterylife;

import java.io.IOException;
import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;

public class Video extends Operation {

	public void running(int n) throws IOException, UiObjectNotFoundException, RemoteException{
		try {
			wakeUpAndUnlock();
			pressHome();
			setWatcher("下次再说","Video");
			launchApp("com.tencent.qqlive/.model.home.HomeActivity");
			getObjByText("搜索").waitForExists(10000);
			click("搜索");
			sleep(3000);
			inputWithAndroid("com.tencent.qqlive:id/edit_layout", "ljxdyj", "搜狗输入法", true);
			getObjByText("绿箭侠第一季").waitForExists(30000);
			click("绿箭侠第一季");
			getObjByID("com.tencent.qqlive:id/full_screen_btn").waitForExists(20000);
			clickID("com.tencent.qqlive:id/full_screen_btn");
			Thread.sleep(1800000);	//30min
			screenShot("/sdcard/BatteryLife/Video.png");
			pressBack();
			pressBack();
			pressHome();
			UiDevice.getInstance().removeWatcher("Video");
			writeFile("PASS "+getTime()+" "+cmdResult("cat /sys/class/power_supply/battery/capacity")+"% Video\n","/sdcard/BatteryLife/BatteryLife.txt", true, true);
		} catch (Exception e) {
			exitAndScreenShot("/sdcard/BatteryLife/Video_"+n+".png");
			writeFile("FAIL "+getTime()+" "+cmdResult("cat /sys/class/power_supply/battery/capacity")+"% Video\n","/sdcard/BatteryLife/BatteryLife.txt", true, true);
		}
	}
}
