package com.test.util;

public class Debug extends Operation {

	public static void main(String[] args) throws Exception {
		String android_id = "3";
		String test_class = "com.test.util.Debug";
		String jar_name = "AutoTest";
		String test_name = "testRun";
		new UiAutomatorHelper(jar_name, test_class, test_name, android_id);
	} 

	public void testRun() throws Exception{
		pressRecentApps();
	}
	
}
