package com.test.test;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;
import com.test.util.UiAutomatorHelper;

public class SendEmail extends Operation {

	public static void main(String[] args) {
		String android_id = "3";
		String test_class = "com.test.test.SendEmail";
		String jar_name = "AutoTest";
		String test_name = "testRun";
		new UiAutomatorHelper(jar_name, test_class, test_name, android_id);
	} 

	public void testRun() throws UiObjectNotFoundException{
		UiObject edit = getObjByID("com.android.email:id/compose_button");
		UiObject send = getObjByID("com.android.email:id/send");
		UiObject recipient = getObjByID("com.android.email:id/to");
		UiObject editBox = getObjByID("com.android.email:id/body");
		UiObject subject = getObjByID("com.android.email:id/subject");
		for(int i=457;i<=500;i++){
			click(edit);
			sleep(2000);
			recipient.setText("tabtest10@tcl.com");
			sleep(1000);
			pressBack();
			sleep(1000);
			subject.setText(i+" EmailTestEmailTest");
			sleep(1000);
			editBox.setText(i+" EmailTestEmailTestEmailTestEmailTestEmailTestEmailTestEmailTestEmailTestEmailTestEmailTestEmailTestEmailTestEmailTestEmailTestEmailTest");
			sleep(2000);
			click(send);
			sleep(4000);
		}
	}

}
