package com.test.batterylife;

import java.io.IOException;
import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;

public class Wechat extends Operation {

	public void running(int n) throws IOException, UiObjectNotFoundException, RemoteException{
		try {
			wakeUpAndUnlock();
			pressHome();
			setWatcher("下次再说", "Wechat");
			launchApp("com.tencent.mm/.ui.LauncherUI");
			sleep(20000);
			clickExist("Cancel");
			if(findViewByText("Log In").exists()){
				click("Log In");
				click("Region");
				clickFound("China");
				findViewById("com.tencent.mm:id/aq6").setText("18681467504");
				findViewById("com.tencent.mm:id/df").setText("tablettest");
				sleep(3000);
				clickId("com.tencent.mm:id/wu");
				if(findViewByClass("android.widget.LinearLayout").exists())
					findViewByClass("android.widget.LinearLayout").waitUntilGone(30000);
				sleep(3000);
				clickExist("No");
			}
			findViewByText("Chats").waitForExists(20000);
			click("Contacts");
			click("像云像风像雾又雨");
			click("Message");
			UiObject input = findViewById("com.tencent.mm:id/r1");
			UiObject send = findViewByText("Send");
			for(int i=0;i<9;i++){	//9次
				click(input);
				input.setText("Microsoft  ");
				click(send);
				UiDevice.getInstance().sleep();
				Thread.sleep(180000);	//3min
				wakeUpAndUnlock();
			}
			screenShot("/sdcard/BatteryLife/Wechat.png");
			pressHome();
			UiDevice.getInstance().removeWatcher("Wechat");
			writeFile("PASS "+getTime()+" "+cmdResult("cat /sys/class/power_supply/battery/capacity")+"% Wechat\n","/sdcard/BatteryLife/BatteryLife.txt", true, true);
		} catch (Exception e) {
			exitAndScreenShot("/sdcard/BatteryLife/Wechat_"+n+".png");
			writeFile("FAIL "+getTime()+" "+cmdResult("cat /sys/class/power_supply/battery/capacity")+"% Wechat\n","/sdcard/BatteryLife/BatteryLife.txt", true, true);
		}
	}
}
