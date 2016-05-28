package com.test.mtbf;

import java.io.File;
import java.io.IOException;

import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;

public class Camera extends Operation {

	public void running(int n) throws IOException, UiObjectNotFoundException, RemoteException{
		try {
			wakeUpAndUnlock();
//*********************************Camera Package Name*************************************************************
			launchApp("com.android.camera2");
			launchApp("com.tct.camera");
			launchApp("org.codeaurora.snapcam");
//*********************************Camera Package Name*************************************************************
			sleep(5000);
			clickExist("Allow");
			clickExist("Allow");
			clickExist("Allow");
			clickExist("NEXT");
			clickExist("Yes");
			clickExist("Cancel");
			clickExist("OK");
			sleep(3000);
			String cameraPkg = UiDevice.getInstance().getCurrentPackageName();
			boolean isMenu = false;
			UiObject shutter = null;
			UiObject switcher = null;
//*********************************Shutter Switcher Menu***********************************************************
			UiObject menu = getObjByID("com.android.camera2:id/mode_options_toggle");		//menu button
			if(menu.exists()){
				isMenu = true;
				click(menu);
				sleep(1000);
			}
			if(cameraPkg.equals("com.android.camera2")){											//com.android.camera2
				if(getObjByID("com.android.camera2:id/shutter_button_photo").exists())
					shutter = getObjByID("com.android.camera2:id/shutter_button_photo");
				if(getObjByID("com.android.camera2:id/mThumbnailLayout").exists())		
					shutter = getObjByID("com.android.camera2:id/mThumbnailLayout");
				if(getObjByID("com.android.camera2:id/shutter_button").exists())
					shutter = getObjByID("com.android.camera2:id/shutter_button");
				if(getObjByID("com.android.camera2:id/onscreen_camera_picker").exists())
					switcher = getObjByID("com.android.camera2:id/onscreen_camera_picker");
				if(getObjByID("com.android.camera2:id/camera_toggle_button").exists())
					switcher = getObjByID("com.android.camera2:id/camera_toggle_button");
			}
			if(cameraPkg.equals("com.tct.camera")){													//com.tct.camera
				if(getObjByID("com.tct.camera:id/shutter_button_photo").exists())
					shutter = getObjByID("com.tct.camera:id/shutter_button_photo");
				if(getObjByID("com.tct.camera:id/shutter_button").exists())
					shutter = getObjByID("com.tct.camera:id/shutter_button");
				if(getObjByID("com.tct.camera:id/onscreen_camera_picker").exists())
					switcher = getObjByID("com.tct.camera:id/onscreen_camera_picker");
				if(getObjByID("com.tct.camera:id/camera_toggle_button").exists())
					switcher = getObjByID("com.tct.camera:id/camera_toggle_button");
			}
			if(cameraPkg.equals("org.codeaurora.snapcam")){											//org.codeaurora.snapcam
				if(getObjByID("org.codeaurora.snapcam:id/shutter_button").exists())
					shutter = getObjByID("org.codeaurora.snapcam:id/shutter_button");
				if(getObjByID("org.codeaurora.snapcam:id/front_back_switcher").exists())
					switcher = getObjByID("org.codeaurora.snapcam:id/front_back_switcher");
			}
//*********************************Shutter Switcher Menu***********************************************************
			if(shutter == null || switcher == null){
				System.out.println("**********Camera APK is not adapted !!!**********");
				sleep(30000);
				exitApp();
				return;
			}
			int switchTimeLag = MTBF.cameraTimeLag;
			switchTimeLag = switchTimeLag >= 1000 ? switchTimeLag : 1000;
			switchTimeLag = switchTimeLag <= 4000 ? switchTimeLag : 4000;
			int loop = 20;
			if(n==0)
				loop = 2;
			if(n<0)
				loop = 99999999;
			for(int i=1;i<=loop;i++){
				wakeUpAndUnlock();
				click(shutter);
				sleep(MTBF.cameraTimeLag);
				click(shutter);
				sleep(MTBF.cameraTimeLag);
				if(isMenu)
					click(menu);
				click(switcher);
				sleep(switchTimeLag);
				if(i%60==0){
					deleteFile(new File("/sdcard/DCIM/Camera"));
					sleep(5000);
				}
				if(n<0 && i%5==0){
					MTBF.testTimes = MTBF.testTimes+10;
					writeFile("Validated "+MTBF.testTimes+" times", "/sdcard/MTBF/ValidatedTimes.txt", false, true);
				}
			}
			if(n%4==0){
				deleteFile(new File("/sdcard/DCIM/Camera"));
				sleep(5000);
			}
			exitApp();
		} catch (Exception e) {
			if(n<0)
				n = Math.abs(n);
			exitAndScreenShot("/sdcard/MTBF/Camera_"+n+"_"+getTime()+".png");
		}
	}
}
