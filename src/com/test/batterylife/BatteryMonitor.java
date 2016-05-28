package com.test.batterylife;

import java.io.IOException;
import android.os.RemoteException;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;

public class BatteryMonitor extends Operation {

	public void running(int n) throws IOException, UiObjectNotFoundException, RemoteException{
		try {
			Monitor monitor = new Monitor();
			monitor.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class Monitor extends Thread{
		public void run(){
			try {
				int batteryPercent = 100;
				while(true){
					batteryPercent = Integer.parseInt(cmdResult("cat /sys/class/power_supply/battery/capacity"));
					if (batteryPercent>50){
						sleep(3600000);//1小时
						continue;
					}
					else if (batteryPercent>30){
						sleep(1800000);//半小时
						continue;
					}
					else if (batteryPercent>20){
						sleep(900000);//15分钟
						continue;
					}
					else {
						writeFile(getTime()+" "+cmdResult("cat /sys/class/power_supply/battery/capacity")+"%\n","/sdcard/BatteryLife/BatteryMonitor.txt", true, true);
						sleep(300000);//5分钟
						continue;
					}
				} 
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}

}
