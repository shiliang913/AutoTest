package com.test.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.os.RemoteException;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.test.util.Operation;
import com.test.util.UiAutomatorHelper;

public class UserDefined extends Operation {

	public static void main(String[] args) {
		String android_id = "3";
		String test_class = "com.test.test.UserDefined";
		String jar_name = "AutoTest";
		String test_name = "testRun";
		new UiAutomatorHelper(jar_name, test_class, test_name, android_id);
	} 

	int listIndex, listSize, i, j, k, picCount = 1, testTimes = 1;
	ArrayList<String> actions = new ArrayList<String>();

	public void testRun() throws IOException, UiObjectNotFoundException, RemoteException{
		try {
			readUserFile();
			ini();
			for(listIndex=0;listIndex<listSize;listIndex++)
				parseAction(actions.get(listIndex));
			System.out.println("Test Completed");
		} catch (Exception e) {
			System.out.println(actions.get(listIndex)+" Executed Failed !!!");
			fail();
		} finally{
			System.out.println("********************Test Finished********************\n");
			sleep(10000);
		}
	}

	public void ini() {
		File file = new File("/sdcard/UserDefined");
		deleteFile(file);
		sleep(3000);
		file.mkdirs();
	}

	public void parseAction(String action) throws Exception {
		System.out.println(action);
		if("HOME".equals(action))
			pressHome();
		else if("BACK".equals(action))
			pressBack();
		else if("RECENT".equals(action))
			pressRecentApps();
		else if("WAIT".equals(action))
			sleep(Integer.parseInt(actions.get(++listIndex)));
		else if("WAIT2".equals(action))
			sleep(2000);
		else if("CLICK".equals(action)){
			String clickArgs[] = actions.get(++listIndex).split(",");
			UiDevice.getInstance().click(Integer.parseInt(clickArgs[0]), Integer.parseInt(clickArgs[1]));
			sleep(1000);
		}
		else if("CLICKT".equals(action))
			click(getObjByText(".*"+actions.get(++listIndex)+".*"));
		else if("CLICKID".equals(action))
			click(getObjByID(".*"+actions.get(++listIndex)+".*"));
		else if("CLICKE".equals(action))
			clickExist(getObjByText(".*"+actions.get(++listIndex)+".*"));
		else if("CLICKF".equals(action))
			clickFound(getObjByText(".*"+actions.get(++listIndex)+".*"));
		else if("SCREEN".equals(action)){
			screenShot("/sdcard/UserDefined/"+getTime()+"_"+picCount+".png");
			picCount++;
			sleep(1000);
		}
		else if("SLIDE".equals(action)){
			String slideArgs[] = actions.get(++listIndex).split(",");
			UiDevice.getInstance().swipe(Integer.parseInt(slideArgs[0]),Integer.parseInt(slideArgs[1]),Integer.parseInt(slideArgs[2]),Integer.parseInt(slideArgs[3]),15);
			sleep(1000);
		}
		else if("WRITE".equals(action)){
			String content = actions.get(++listIndex);
			if("COUNT".equals(content)){
				writeFile("Validated "+testTimes+" times", "/sdcard/UserDefined/ValidatedTimes.txt", false, true);
				testTimes++;
			}
			else
				writeFile(content+"\r\n", "/sdcard/UserDefined/Report.txt", true, true);
		}
		else if("LOOP".equals(action)){
			int loopEnd = actions.indexOf("LOOPEND");
			if(loopEnd == -1){
				System.out.println("LOOPEND is missing !!!");
				fail();
			}
			int loopTimes = Integer.parseInt(actions.get(++listIndex));
			int currentIndex = ++listIndex;
			for(i=0;i<loopTimes;i++){
				listIndex = currentIndex;
				for (; listIndex < loopEnd; listIndex++)
					parseAction(actions.get(listIndex));
			}
			System.out.println("LOOPEND");
		}
		else if("LOOP2".equals(action)){
			int loopEnd2 = actions.indexOf("LOOPEND2");
			if(loopEnd2 == -1){
				System.out.println("LOOPEND2 is missing !!!");
				fail();
			}
			int loopTimes2 = Integer.parseInt(actions.get(++listIndex));
			int currentIndex2 = ++listIndex;
			for(j=0;j<loopTimes2;j++){
				listIndex = currentIndex2;
				for (; listIndex < loopEnd2; listIndex++)
					parseAction(actions.get(listIndex));
			}
			System.out.println("LOOPEND2");
		}
		else if("LOOP3".equals(action)){
			int loopEnd3 = actions.indexOf("LOOPEND3");
			if(loopEnd3 == -1){
				System.out.println("LOOPEND3 is missing !!!");
				fail();
			}
			int loopTimes3 = Integer.parseInt(actions.get(++listIndex));
			int currentIndex3 = ++listIndex;
			for(k=0;k<loopTimes3;k++){
				listIndex = currentIndex3;
				for (; listIndex < loopEnd3; listIndex++)
					parseAction(actions.get(listIndex));
			}
			System.out.println("LOOPEND3");
		}
		else if("INPUT".equals(action)){
			cmdExec("input text "+actions.get(++listIndex));
			sleep(4000);
		}
		else if("POWER".equals(action)){
			if(UiDevice.getInstance().isScreenOn())
				UiDevice.getInstance().sleep();
			else
				UiDevice.getInstance().wakeUp();
			sleep(2000);
		}
		else if("DRAG".equals(action)){
			String dragArgs[] = actions.get(++listIndex).split(",");
			UiDevice.getInstance().drag(Integer.parseInt(dragArgs[0]),Integer.parseInt(dragArgs[1]),Integer.parseInt(dragArgs[2]),Integer.parseInt(dragArgs[3]),40);
			sleep(1000);
		}
		else if("ROTATE".equals(action)){
			switch (UiDevice.getInstance().getDisplayRotation()) {
			case 0:
				UiDevice.getInstance().setOrientationLeft();
				break;
			case 1:
				UiDevice.getInstance().setOrientationNatural();
				break;
			case 2:
				UiDevice.getInstance().setOrientationLeft();
				break;
			case 3:
				UiDevice.getInstance().setOrientationNatural();
				break;
			}
			UiDevice.getInstance().unfreezeRotation();
			sleep(2000);
		}
		else{
			System.out.println(action+" is not defined !!!");
			fail();
		}
	}

	public void readUserFile() {
		try {
			System.out.println("********************Test Started********************");
			File file = new File("/sdcard/UserFile.txt");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line;
			while((line = bufferedReader.readLine()) != null){
				line = line.replace(" ", "");
				if(!("".equals(line))){
					if(!(line.startsWith("###")))
						actions.add(line);
				}
			}
			listSize = actions.size();
			bufferedReader.close();
			System.out.println("Read UserFile Succeed");
		} catch (Exception e) {
			System.out.println("Read UserFile Failed !!!");
			fail();
		}
	}

}
