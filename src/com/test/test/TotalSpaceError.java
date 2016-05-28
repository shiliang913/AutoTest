package com.test.test;

import java.io.IOException;

import android.os.RemoteException;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.test.util.Operation;
import com.test.util.UiAutomatorHelper;

public class TotalSpaceError extends Operation {

	public static void main(String[] args) {
		String android_id = "3";
		String test_class = "com.test.test.TotalSpaceError";
		String jar_name = "AutoTest";
		String test_name = "testRun";
		new UiAutomatorHelper(jar_name, test_class, test_name, android_id);
	} 

	public void testRun() throws IOException, UiObjectNotFoundException, RemoteException{
		try {
			sleep(15000);
			new SetupIni().running();
			wakeUpAndUnlock();
			launchApp("com.android.settings");
			clickFound("Storage");
			sleep(3000);
			UiObject Unmount = getObjByText("Unmount SD card");
			UiObject Mount = getObjByText("Mount SD card");
			UiObject OK = getObjByText("OK");
			UiObject total = getObjByText("Total space").getFromParent(new UiSelector().resourceId("android:id/summary"));
			UiScrollable scrollable = getScrByID("android:id/content");
			while(true){
				clickFound(Unmount);
				click(OK);
				sleep(15000);
				scrollable.flingToBeginning(5);
				sleep(3000);
				if("0.00B".equals(total.getText())){
					sleep(30000);
					if("0.00B".equals(total.getText())){
						screenShot("/sdcard/TotalSpaceError.png");
						break;
					}
				}
				clickFound(Mount);
				click(OK);
				sleep(15000);
				scrollable.flingToBeginning(5);
				sleep(3000);
				if("0.00B".equals(total.getText())){
					sleep(30000);
					if("0.00B".equals(total.getText())){
						screenShot("/sdcard/TotalSpaceError.png");
						break;
					}
				}
			}
		} catch (Exception e) {
			screenShot("/sdcard/Exit.png");
		}

	}
}
