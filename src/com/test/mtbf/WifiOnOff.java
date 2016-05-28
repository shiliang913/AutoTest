package com.test.mtbf;

import java.io.IOException;

import android.os.RemoteException;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;

public class WifiOnOff extends Operation {

	public void running(int n) throws IOException, UiObjectNotFoundException, RemoteException{
		try {
			wakeUpAndUnlock();
			launchApp("com.android.settings");
			UiObject wifi = getObjByText("Wi.*Fi");
			if(getObjByText("WLAN").exists())
				wifi = getObjByText("WLAN");
			clickFound(wifi);
			sleep(1000);
			UiObject onButton = getObjByID("com.android.settings:id/switch_widget");
			int loop = 20;
			if(n==0)
				loop = 2;
			if(n<0)
				loop = 99999999;
			for(int i=1;i<=loop;i++){
				wakeUpAndUnlock();
				click(onButton);
				sleep(5000);
				if(n<0 && i%10==0){
					MTBF.testTimes = MTBF.testTimes+10;
					writeFile("Validated "+MTBF.testTimes+" times", "/sdcard/MTBF/ValidatedTimes.txt", false, true);
				}
			}
			exitApp();
		} catch (Exception e) {
			if(n<0)
				n = Math.abs(n);
			exitAndScreenShot("/sdcard/MTBF/WifiOnOff_"+n+"_"+getTime()+".png");
		}
	}
}
