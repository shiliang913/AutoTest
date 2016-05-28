package com.test.test;

import java.io.File;
import java.io.IOException;
import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;
import com.test.util.UiAutomatorHelper;

public class CameraStress extends Operation {

	public static void main(String[] args) {
		String android_id = "3";
		String test_class = "com.test.test.CameraStress";
		String jar_name = "AutoTest";
		String test_name = "testRun4";
		new UiAutomatorHelper(jar_name, test_class, test_name, android_id);
	} 

	public static final String PATH = "/sdcard/CameraStress.txt";

	public void testIni() throws IOException, UiObjectNotFoundException, RemoteException{
		wakeUpAndUnlock();	//亮屏解锁
		if(UiDevice.getInstance().getDisplayRotation()!=0){		//旋转屏至竖直
			UiDevice.getInstance().setOrientationNatural();	
			sleep(3000);
			UiDevice.getInstance().unfreezeRotation();
			sleep(2000);
		}
		clearApps();	//清除后台程序
		exitApp();
		launchApp("com.android.settings");	//设置背光常亮
		clickFound("Display");
		click("Sleep");
		if(findViewByText("Never").exists())
			click("Never");
		else{
			click("30 minutes");
		}
		launchApp("com.android.settings");	//设置锁屏为无
		clickFound("Security");
		click("Screen lock");
		click("None");
		exitApp();
		System.out.println("\n");
		System.out.println("********************Initialization Succeed********************");
		System.out.println("\n");
	}

	public void testRun4() throws IOException, UiObjectNotFoundException, RemoteException{
		launchApp("com.android.camera2");
		sleep(10000);
		screenShot("/sdcard/DCIM/"+getTime()+".png");
		sleep(5000);
	}

	//	1.Launch camera
	//	2.Capture 200 pictures continuously
	//	3.Switch to front camera
	//	4.Capture 200 pictures continuously
	public void testRun1() throws IOException, UiObjectNotFoundException, RemoteException{
		launchApp("com.android.camera2");
		sleep(5000);
		UiObject shutter = findViewById("com.android.camera2:id/shutter_button");
		for (int i = 0; i < 200; i++) {
			click(shutter);
			sleep(3000);
		}
		writeFile("200 Back PASS", PATH, false, true);
		clickId("com.android.camera2:id/three_dots");
		sleep(3000);
		clickId("com.android.camera2:id/camera_toggle_button");
		sleep(5000);
		for (int i = 0; i < 200; i++) {
			click(shutter);
			sleep(3000);
		}
		writeFile("200 Front PASS", PATH, true, true);
	}

	//	1.Launch camcorder
	//	2.Record 30 videos continuously
	//	3.Switch to front camera
	//	4.Record 30 videos continuously
	public void testRun2() throws IOException, UiObjectNotFoundException, RemoteException{
		launchApp("com.android.camera2");
		sleep(5000);
		UiObject shutter = findViewById("com.android.camera2:id/shutter_button");
		for (int i = 0; i < 30; i++) {
			click(shutter);
			sleep(6000);
			click(shutter);
			sleep(3000);
		}
		writeFile("30 Back PASS", PATH, false, true);
		clickId("com.android.camera2:id/three_dots");
		sleep(3000);
		clickId("com.android.camera2:id/camera_toggle_button");
		sleep(5000);
		for (int i = 0; i < 30; i++) {
			click(shutter);
			sleep(6000);
			click(shutter);
			sleep(3000);
		}
		writeFile("30 Front PASS", PATH, true, true);
	}

	//	1.Take photos till store space is full
	//	2.Enter preview screen,delete some photos
	//	3.Repeat step 1,2 more than 100 times
	//	4.Switch to front camera
	//	5.Repeat steps 1,2 more than 100 times
	public void testRun3() throws IOException, UiObjectNotFoundException, RemoteException{
		int n = 0;
		boolean b = false;
		for (int i = 0; i < 100; i++) {
			try {
				launchApp("com.android.camera2");
				sleep(5000);
				UiObject shutter = findViewById("com.android.camera2:id/shutter_button");
				while (true) {
					if(b = shutter.isEnabled()){
						click(shutter);
						sleep(4000);
					}else {
						throw new Exception();
					}
				}
			} catch (Exception e) {
				exitApp();
				if(b){
					deleteFile(new File("/sdcard/DCIM/Camera"));
					writeFile(n+"", PATH, false, true);
					n++;
					b = false;
				}
				sleep(10000);
			}
		}
	}

}
