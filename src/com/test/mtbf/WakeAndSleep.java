package com.test.mtbf;

import java.io.IOException;

import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;

public class WakeAndSleep extends Operation {

	public void running(int n) throws IOException, UiObjectNotFoundException, RemoteException{
		try {
			wakeUpAndUnlock();
			int loop = 10;
			if(n==0)
				loop = 1;
			if(n<0)
				loop = 99999999;
			for(int i=1;i<=loop;i++){
				UiDevice.getInstance().sleep();
				sleep(1000);
				if(UiDevice.getInstance().isScreenOn())
					sleep(1000);
				UiDevice.getInstance().wakeUp();
				sleep(1000);
				UiDevice.getInstance().wakeUp();
				sleep(1000);
				UiDevice.getInstance().wakeUp();
				sleep(1000);
				swipeUp();
				sleep(3000);
				if(n<0 && i%10==0){
					MTBF.testTimes = MTBF.testTimes+10;
					writeFile("Validated "+MTBF.testTimes+" times", "/sdcard/MTBF/ValidatedTimes.txt", false, true);
				}
			}
		} catch (Exception e) {
			if(n<0)
				n = Math.abs(n);
			exitAndScreenShot("/sdcard/MTBF/WakeAndSleep_"+n+"_"+getTime()+".png");
		}
	}
}
