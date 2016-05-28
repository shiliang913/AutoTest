package com.test.batterylife;

import java.io.IOException;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;
import com.test.util.UiAutomatorHelper;

public class ClearData extends Operation {

	public static void main(String[] args) {
		String android_id = "3";
		String test_class = "com.test.batterylife.ClearData";
		String jar_name = "Debug";
		String test_name = "testRun";
		new UiAutomatorHelper(jar_name, test_class, test_name, android_id);
	} 

	public void testRun() throws UiObjectNotFoundException, IOException{
		Runtime.getRuntime().exec("pm clear com.android.chrome");
		Runtime.getRuntime().exec("pm clear com.happyelements.AndroidAnimal");
		Runtime.getRuntime().exec("pm clear com.baidu.BaiduMap");
		Runtime.getRuntime().exec("pm clear com.tencent.qqlive");
		Runtime.getRuntime().exec("pm clear com.tencent.mm");
		Runtime.getRuntime().exec("pm clear com.sina.weibo");
		//		Runtime.getRuntime().exec("pm clear com.sohu.inputmethod.sogou");
	}
}
