package com.test.test;

import java.io.IOException;
import android.os.RemoteException;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;
import com.test.util.UiAutomatorHelper;

public class MMSCamera extends Operation {

	public static void main(String[] args) {
		String android_id = "3";
		String test_class = "com.test.test.MMSCamera";
		String jar_name = "AutoTest";
		String test_name = "testRun";
		new UiAutomatorHelper(jar_name, test_class, test_name, android_id);
	} 

	public void testRun() throws IOException, UiObjectNotFoundException, RemoteException{
		int n = 1;
		while(true){
			try {
				wakeUpAndUnlock();
				launchApp("com.android.mms");
				sleep(3000);
				click("New message");
				sleep(2000);
				UiObject attachment = getObjByID("com.android.mms:id/add_attachment_first");
				UiObject capture = getObjByText("Capture video");
				UiObject cancel = getObjByID("com.android.camera2:id/cancel_button");
				UiObject shutter = getObjByID("com.android.camera2:id/shutter_button");
//				UiObject switcher = getObjByResourceId("com.android.camera2:id/camera_toggle_button");
//				UiObject menu = getObjByResourceId("com.android.camera2:id/three_dots");
				while(true){
					wakeUpAndUnlock();
					click(attachment);
					clickExist("Allow");
					click(capture);
					sleep(4000);
					click(shutter);
					sleep(10000);
					click(shutter);
					sleep(2000);
					click(cancel);
					if(n%10 == 0)
						writeFile("Validated "+n+" times", "/sdcard/MMSCamera.txt", false, true);
					sleep(3000);
					n++;
				}
			}catch (Exception e) {
				exitApp();
			}
		} 
	}
}
