package com.test.batterylife;

import java.io.IOException;
import android.os.RemoteException;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;

public class Camera extends Operation {

	public void running(int n) throws IOException, UiObjectNotFoundException, RemoteException{
		try {
			wakeUpAndUnlock();
			pressHome();
			launchApp("com.tct.camera");
			Thread.sleep(900000);	//15min
			screenShot("/sdcard/BatteryLife/Camera.png");
			pressHome();
			writeFile("PASS "+getTime()+" "+cmdResult("cat /sys/class/power_supply/battery/capacity")+"% Camera\n","/sdcard/BatteryLife/BatteryLife.txt", true, true);
		} catch (Exception e) {
			exitAndScreenShot("/sdcard/BatteryLife/Camera_"+n+".png");
			writeFile("FAIL "+getTime()+" "+cmdResult("cat /sys/class/power_supply/battery/capacity")+"% Camera\n","/sdcard/BatteryLife/BatteryLife.txt", true, true);
		}
	}
}
