package com.test.test;

import java.io.File;

import com.android.uiautomator.core.UiObject;
import com.test.util.Operation;
import com.test.util.UiAutomatorHelper;

public class BTSearchSlow extends Operation {

	public static void main(String[] args) {
		String android_id = "3";
		String test_class = "com.test.test.BTSearchSlow";
		String jar_name = "AutoTest";
		String test_name = "testRun";
		new UiAutomatorHelper(jar_name, test_class, test_name, android_id);
	} 

	public void testRun() throws Exception{
		File file = new File("/sdcard/AutoTest");
		deleteFile(file);
		sleep(3000);
		file.mkdirs();
		clickExist("MANU");
		UiObject btList  = findViewById("android:id/text1");
		UiObject bt  = findViewByText("Bluetooth");
		int n = 1;
		while(true){
			click(bt);
			if(btList.waitForExists(15000))
				pressBack();
			else {
				screenShot("/sdcard/AutoTest/"+n+"_"+getTime()+".png");
				break;
			}
			sleep(3000);
			n++;
			if(n%5 == 0){
				writeFile(n+"", "/sdcard/AutoTest/time.txt", false, true);
				System.out.println(n+"times");
			}
		}
	}
	
}
