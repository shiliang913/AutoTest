package com.test.test;

import java.io.File;
import java.io.IOException;
import android.os.RemoteException;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;

public class SetupIni extends Operation {

	public void running() throws IOException, UiObjectNotFoundException, RemoteException{
		try{
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
			File file = new File("/sdcard/AutoTest");	//创建截图文件夹AutoTest
			deleteFile(file);
			sleep(3000);
			file.mkdirs();
			exitApp();
			System.out.println("\n");
			System.out.println("********************Initialization Succeed********************");
			System.out.println("\n");
		}catch(Exception e){
			System.out.println("\n");
			System.out.println("********************Initialization Failed*********************");
			System.out.println("*****Please set device as follows and re-run the script!******");
			System.out.println("********************Set language UK English*******************");
			System.out.println("********************Set screen lock Swipe*********************");
			System.out.println("\n");
			assertTrue(false);
		}
	}
}
