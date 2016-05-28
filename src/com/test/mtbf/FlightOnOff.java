package com.test.mtbf;

import java.io.IOException;

import android.os.RemoteException;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;

public class FlightOnOff extends Operation {

	public void running(int n) throws IOException, UiObjectNotFoundException, RemoteException{
		try {
			wakeUpAndUnlock();
			launchApp("com.android.settings");
			clickFound(findViewByText("More.*"));
			sleep(1000);
			UiObject onButton = findViewById("android:id/switchWidget");
			int loop = 20;
			if(n==0)
				loop = 2;
			if(n<0)
				loop = 99999999;
			for(int i=1;i<=loop;i++){
				wakeUpAndUnlock();
				click(onButton);
				sleep(10000);
				if(n<0 && i%10==0){
					MTBF.testTimes = MTBF.testTimes+10;
					writeFile("Validated "+MTBF.testTimes+" times", "/sdcard/MTBF/ValidatedTimes.txt", false, true);
				}
			}
			exitApp();
		} catch (Exception e) {
			if(n<0)
				n = Math.abs(n);
			exitAndScreenShot("/sdcard/MTBF/FlightOnOff_"+n+"_"+getTime()+".png");
		}
	}
}
