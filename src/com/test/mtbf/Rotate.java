package com.test.mtbf;

import java.io.IOException;

import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;

public class Rotate extends Operation {

	public void running(int n) throws IOException, UiObjectNotFoundException, RemoteException{
		try {
			wakeUpAndUnlock();
			launchApp("com.android.settings");
			UiDevice.getInstance().setOrientationNatural();
			sleep(3000);
			int loop = 5;
			if(n==0)
				loop = 1;
			if(n<0)
				loop = 99999999;
			for(int i=1;i<=loop;i++){
				wakeUpAndUnlock();
				UiDevice.getInstance().setOrientationRight();
				sleep(3000);		
				UiDevice.getInstance().setOrientationNatural();
				sleep(3000);		
				UiDevice.getInstance().setOrientationLeft();
				sleep(3000);
				UiDevice.getInstance().setOrientationNatural();
				sleep(3000);
				if(n<0 && i%5==0){
					MTBF.testTimes = MTBF.testTimes+20;
					writeFile("Validated "+MTBF.testTimes+" times", "/sdcard/MTBF/ValidatedTimes.txt", false, true);
				}
			}
			UiDevice.getInstance().unfreezeRotation();
			sleep(2000);
			exitApp();
		} catch (Exception e) {
			if(n<0)
				n = Math.abs(n);
			UiDevice.getInstance().setOrientationNatural();
			sleep(3000);
			UiDevice.getInstance().unfreezeRotation();
			sleep(2000);
			exitAndScreenShot("/sdcard/MTBF/Rotate_"+n+"_"+getTime()+".png");
		}
	}
}
