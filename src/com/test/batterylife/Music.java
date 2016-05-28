package com.test.batterylife;

import java.io.IOException;
import android.os.RemoteException;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.test.util.Operation;

public class Music extends Operation {

	public void running(int n) throws IOException, UiObjectNotFoundException, RemoteException{
		try {
			wakeUpAndUnlock();
			pressHome();
			launchApp("com.alcatel.music5/.activities.MusicPlayerActivity");
			click(findViewByDes("Open navigation drawer"));
			click("Library");
			sleep(3000);
			UiScrollable playlist = findScrById("com.alcatel.music5:id/view_pager");
			UiObject unknow = findViewByText("<unknown>");
			while(playlist.scrollIntoView(unknow)){
				int unknowBound[] = getBound(unknow);
				UiDevice.getInstance().click(unknowBound[2]+10,unknowBound[1]);
				click("Remove");
				click("OK");
				sleep(3000);
			}
			playlist.scrollToBeginning(10);
			click("Shuffle all");
			UiDevice.getInstance().sleep();
			Thread.sleep(1800000);	//30min
			wakeUpAndUnlock();
			screenShot("/sdcard/BatteryLife/Music.png");
			UiObject pause = findViewById("com.alcatel.music5:id/track_play_pause_image_btn");
			if(pause.isSelected())
				click(pause);
			else {
				sleep(5000);
				click(pause);
			}
			pressHome();
			writeFile("PASS "+getTime()+" "+cmdResult("cat /sys/class/power_supply/battery/capacity")+"% Music\n","/sdcard/BatteryLife/BatteryLife.txt", true, true);
		} catch (Exception e) {
			exitAndScreenShot("/sdcard/BatteryLife/Music_"+n+".png");
			writeFile("FAIL "+getTime()+" "+cmdResult("cat /sys/class/power_supply/battery/capacity")+"% Music\n","/sdcard/BatteryLife/BatteryLife.txt", true, true);
		}
	}
}
