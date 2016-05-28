package com.test.batterylife;

import java.io.IOException;
import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;

public class Weibo extends Operation {

	public void running(int n) throws IOException, UiObjectNotFoundException, RemoteException{
		try {
			wakeUpAndUnlock();
			pressHome();
			setWatcher("Update later","Weibo1");
			setWatcher("No，thanks","Weibo2");		
			launchApp("com.sina.weibo/.MainTabActivity");
			sleep(10000);
			if(getObjByText("Login").exists()){
				click("Login");
				inputWithAndroid("com.sina.weibo:id/etLoginUsername", "yyz0061@163.com", "搜狗输入法", false);
				inputWithAndroid("com.sina.weibo:id/etPwd", "1478963250", "搜狗输入法", false);
				click("Login");
				sleep(10000);
				clickExist("开始微博》");
				sleep(3000);
			}
			int x = getWidth()/2;
			int y1 = getHeight()/4;
			int y2 = getHeight()*3/4;
			for(int i=0;i<85;i++){	//85次
				sleep(20000);
				clickExist("No，thanks");
				UiDevice.getInstance().swipe(x, y1, x, y2, 50);
			}
			sleep(2000);
			screenShot("/sdcard/BatteryLife/Weibo.png");
			pressHome();
			UiDevice.getInstance().removeWatcher("Weibo1");
			UiDevice.getInstance().removeWatcher("Weibo2");
			writeFile("PASS "+getTime()+" "+cmdResult("cat /sys/class/power_supply/battery/capacity")+"% Weibo\n","/sdcard/BatteryLife/BatteryLife.txt", true, true);
		} catch (Exception e) {
			exitAndScreenShot("/sdcard/BatteryLife/Weibo_"+n+".png");
			writeFile("FAIL "+getTime()+" "+cmdResult("cat /sys/class/power_supply/battery/capacity")+"% Weibo\n","/sdcard/BatteryLife/BatteryLife.txt", true, true);
		}
	}
}
