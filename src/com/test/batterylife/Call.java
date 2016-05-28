package com.test.batterylife;

import java.io.IOException;
import android.os.RemoteException;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;

public class Call extends Operation {

	public void running(int n) throws IOException, UiObjectNotFoundException, RemoteException{
		try {
			if(n>1)
				return;
			wakeUpAndUnlock();
			pressHome();
			UiObject call = findViewById("com.android.dialer:id/photo");
			System.out.println("******请给测试机拨打电话******");
			call.waitForExists(120000);
			sleep(5000);
			call.swipeRight(50);
			sleep(3000);
			UiDevice.getInstance().sleep();
			Thread.sleep(3600000);	//60min
			wakeUpAndUnlock();
			screenShot("/sdcard/BatteryLife/Call.png");
			UiDevice.getInstance().click(getWidth()/2, getHeight()*9/10);
			sleep(3000);
			wakeUpAndUnlock();
			writeFile("PASS "+getTime()+" "+cmdResult("cat /sys/class/power_supply/battery/capacity")+"% Call\n","/sdcard/BatteryLife/BatteryLife.txt", true, true);
		} catch (Exception e) {
			exitAndScreenShot("/sdcard/BatteryLife/Call_"+n+".png");
			writeFile("FAIL "+getTime()+" "+cmdResult("cat /sys/class/power_supply/battery/capacity")+"% Call\n","/sdcard/BatteryLife/BatteryLife.txt", true, true);
		}
	}
}
