package com.test.mtbf;

import java.io.IOException;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;
import com.test.util.UiAutomatorHelper;

import android.os.RemoteException;

public class MTBF extends Operation {

	public static void main(String[] args) {
		String android_id = "3";
		String test_class = "com.test.mtbf.MTBF";
		String jar_name = "AutoTest";
		String test_name = "testRun";
		new UiAutomatorHelper(jar_name, test_class, test_name, android_id);
	} 

	public static int testTimes = 0;	//Camera拍照计数
	public static int cameraTimeLag = 3000;		//Camera拍照时间间隔

	public void testRun() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException{
		int n=1;
//		n = 0;
		while(true)
		{
			new WifiOnOff().running(n);
			new BTOnOff().running(n);
			new GPSOnOff().running(n);
			new FlightOnOff().running(n);
			new Rotate().running(n);
			new Camera().running(n);
			new WakeAndSleep().running(n);
			writeFile("Validated "+n+" times", "/sdcard/MTBF/ValidatedTimes.txt", false, true);
			n++;
//			break;
		}
	}

	protected void setUp() throws Exception {
		super.setUp();
		new SetupIni().running();
	}

	public void testWifiOnOff() throws RemoteException, IOException, UiObjectNotFoundException{
		int n = -1;
		while(true){
			new WifiOnOff().running(n);
			n--;
		}
	}

	public void testBTOnOff() throws RemoteException, IOException, UiObjectNotFoundException{
		int n = -1;
		while(true){
			new BTOnOff().running(n);
			n--;
		}
	}

	public void testGPSOnOff() throws RemoteException, IOException, UiObjectNotFoundException{
		int n = -1;
		while(true){
			new GPSOnOff().running(n);
			n--;
		}
	}

	public void testFlightOnOff() throws RemoteException, IOException, UiObjectNotFoundException{
		int n = -1;
		while(true){
			new FlightOnOff().running(n);
			n--;
		}
	}

	public void testCamera() throws RemoteException, IOException, UiObjectNotFoundException{
		int n = -1;
		while(true){
			new Camera().running(n);
			n--;
			if(n < -20)
				fail();
		}
	}

	public void testRotate() throws RemoteException, IOException, UiObjectNotFoundException{
		int n = -1;
		while(true){
			new Rotate().running(n);
			n--;
		}
	}

	public void testWakeAndSleep() throws RemoteException, IOException, UiObjectNotFoundException{
		int n = -1;
		while(true){
			new WakeAndSleep().running(n);
			n--;
		}
	}

}
