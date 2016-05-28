package com.test.batterylife;

import java.io.IOException;
import android.os.RemoteException;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;

public class Game extends Operation {

	public void running(int n) throws IOException, UiObjectNotFoundException, RemoteException{
		try {
			wakeUpAndUnlock();
			pressHome();
			launchApp("com.happyelements.AndroidAnimal/com.happyelements.hellolua.MainActivity");
			sleep(30000);
			UiDevice.getInstance().click(500,417);//村长来信
			sleep(3000);
			UiDevice.getInstance().click(264, 815);//游客登录
			sleep(10000);
			UiDevice.getInstance().click(500,417);//动画
			sleep(1000);
			UiDevice.getInstance().click(500,417);
			sleep(1000);
			UiDevice.getInstance().click(500,417);
			sleep(6000);
			UiDevice.getInstance().click(147, 639);//第一关
			sleep(3000);
			UiDevice.getInstance().click(271, 572);//开始
			Thread.sleep(1800000);	//30min
			screenShot("/sdcard/BatteryLife/Game.png");
			pressHome();
			writeFile("PASS "+getTime()+" "+cmdResult("cat /sys/class/power_supply/battery/capacity")+"% Game\n","/sdcard/BatteryLife/BatteryLife.txt", true, true);
		} catch (Exception e) {
			exitAndScreenShot("/sdcard/BatteryLife/Game_"+n+".png");
			writeFile("FAIL "+getTime()+" "+cmdResult("cat /sys/class/power_supply/battery/capacity")+"% Game\n","/sdcard/BatteryLife/BatteryLife.txt", true, true);
		}
	}
}
