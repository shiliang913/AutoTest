package com.test.batterylife;

import java.io.IOException;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;

public class ClearApps extends Operation {

	public void running(int n) throws IOException, UiObjectNotFoundException{
		try {
			wakeUpAndUnlock();
			pressHome();
			clearApps();
		} catch (Exception e) {
			exitApp();
		}
	}
}
