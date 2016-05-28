package com.test.batterylife;

import java.io.File;
import java.io.IOException;
import android.os.RemoteException;
import com.android.uiautomator.core.Configurator;
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
			exitApp();
			launchApp("com.android.settings");	//设置背光常亮
			clickFound("Display");
			click("Sleep");
			if(getObjByText("Never").exists())
				click("Never");
			else{
				click("30 minutes");
			}
			Configurator.getInstance().setKeyInjectionDelay(200);	//设置打字速度
			File file = new File("/sdcard/BatteryLife");	//创建文件夹BatteryLife
			deleteFile(file);
			sleep(3000);
			file.mkdirs();
			sleep(3000);
			writeFile("START TEST "+getTime()+" "+cmdResult("cat /sys/class/power_supply/battery/capacity")+"%\n","/sdcard/BatteryLife/BatteryLife.txt", false, true);
			exitApp();
			System.out.println("******初始化成功，开始测试******");
		}catch(Exception e){
			System.out.println("******初始化失败，请重新运行脚本！******");
			System.out.println("******测试前请按如下设置******");
			System.out.println("******语言设置为UK英文******");
			System.out.println("******锁屏设置为默认的swipe******");
			System.out.println("******连接WIFI且检查能上网******");
			assertTrue(false);
		}
	}
}
