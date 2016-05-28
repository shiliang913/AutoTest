package com.test.batterylife;

import java.io.IOException;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;
import com.test.util.UiAutomatorHelper;

import android.os.RemoteException;

public class BatteryLife extends Operation {

	public static void main(String[] args) {
		String android_id = "3";
		String test_class = "com.test.batterylife.BatteryLife";
		String jar_name = "BatteryLife";
		String test_name = "testRun";
		new UiAutomatorHelper(jar_name, test_class, test_name, android_id);
	} 

	public void testRun() throws RemoteException, IOException, UiObjectNotFoundException {
		int n = 1;
		new BatteryMonitor().running(n);
		while(true){
			new Call().running(n);
			new Game().running(n);
			new Music().running(n);
			new Video().running(n);
			new Camera().running(n);
			new Browsing().running(n);
			new Map().running(n);
			new Weibo().running(n);
			new Wechat().running(n);
			new ClearApps().running(n);
			n++;
			//		break;
		}

	}

	protected void setUp() throws Exception {
		super.setUp();
		new SetupIni().running();
	}

	public void testDebug() throws IOException, UiObjectNotFoundException, RemoteException{
		new Map().running(1);
	}

}
