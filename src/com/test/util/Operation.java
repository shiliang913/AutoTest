package com.test.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.graphics.Rect;
import android.os.RemoteException;
import android.view.KeyEvent;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.core.UiWatcher;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class Operation extends UiAutomatorTestCase {

	//换输入法打字
	public void inputWithAndroid(String editBoxResourceId,String inputContent,String inputMethod,boolean isEnter) throws UiObjectNotFoundException{
		UiObject editBox = getObjByID(editBoxResourceId);
		click(editBox);
		UiDevice.getInstance().openNotification();
		click("Change keyboard");
		click(getObjByText(".*Android.*"));
		editBox.setText(inputContent);
		sleep(2000);
		UiDevice.getInstance().openNotification();
		click("Change keyboard");
		click(getObjByText(inputMethod));
		sleep(6000);
		click(editBox);
		if(isEnter){
			UiDevice.getInstance().pressKeyCode(KeyEvent.KEYCODE_ENTER);
		}
	}

	//监听器
	public void setWatcher(final String text,String watcherName){
		UiDevice.getInstance().registerWatcher(watcherName, new UiWatcher() {
			public boolean checkForCondition() {
				try {
					UiObject obj = getObjByText(text);
					if(obj.exists()){
						obj.click();
						sleep(1000);
						return true;
					}		
					else{
						return false;
					}
				} catch (Exception e) {
					return false;
				}
			}
		});
	}

	//获取系统时间
	public String getTime(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMdd_HHmm");
		Date date = new Date(System.currentTimeMillis());
		String time = simpleDateFormat.format(date);
		return time;
	}

	//获取电量
	public int getBattery() throws NumberFormatException, IOException{
		return Integer.parseInt(cmdResult("cat /sys/class/power_supply/battery/capacity"));
	}

	//打印log
	public void logcat(final String logPath){
		new Thread(){
			public void run() {
				cmdRun("logcat -v time", logPath);
			};
		}.start();
	}

	/* 执行cmd
	 * adb shell dumpsys activity | findstr "mFocusedActivity" 获取包名
	 * adb shell cat /sys/class/power_supply/battery/capacity 获取电量
	 * adb shell am start -a android.intent.action.VIEW -d http://www.baidu.com 带网址启动浏览器
	 */
	public void cmdExec(String command) throws IOException{
		Runtime.getRuntime().exec(command);
	}

	public void cmdRun(String command,String filePath) {
		try {
			Process p = Runtime.getRuntime().exec(command);
			InputStream input = p.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			String line = "";
			while ((line = reader.readLine()) != null) {
				writeFile(line,filePath,true,false);
			}
			InputStream errorInput = p.getErrorStream();
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorInput));
			String eline = "";
			while ((eline = errorReader.readLine()) != null) {
				writeFile(eline,filePath,true,false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String cmdResult(String command) throws IOException{
		Process p = Runtime.getRuntime().exec(command);
		InputStream inputstream = p.getInputStream();
		InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
		BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
		return bufferedreader.readLine();
	}

	//写文件
	public void writeFile(String content,String filePath,boolean isContinueWrite,boolean isClose){
		try {
			File file = new File(filePath);
			BufferedWriter bufferedWriter = null;
			if(isContinueWrite){
				bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
			}
			else {
				bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			}
			bufferedWriter.append(content);
			bufferedWriter.newLine();
			bufferedWriter.flush();
			if(isClose){
				bufferedWriter.close();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//清理后台应用
	public void clearApps() throws UiObjectNotFoundException, RemoteException{
		sleep(1000);
		UiDevice.getInstance().pressRecentApps();
		sleep(2000);
		UiObject clearButton = getObjByID("com.android.systemui:id.*clear.*");
		if(clearButton.exists()){
			clearButton.click();
			while(!UiDevice.getInstance().getCurrentPackageName().contains("com.android.launcher")){
				sleep(3000);
			}
		}
		else {
			pressBack();
			sleep(2000);
		}
	}

	//亮屏解锁
	public void wakeUpAndUnlock() throws RemoteException{
		if(!UiDevice.getInstance().isScreenOn()){
			UiDevice.getInstance().wakeUp();
			sleep(2000);
		}
		if("com.android.systemui".equals(UiDevice.getInstance().getCurrentPackageName())){
			swipeUp();
			sleep(3000);
		}
	}

	//删除文件
	public void deleteFile(File file) { 
		if (file.isFile()) {  
			file.delete();  
			return;  
		}    
		if(file.isDirectory()){  
			File[] childFiles = file.listFiles();  
			if (childFiles == null || childFiles.length == 0) {  
				file.delete();  
				return;  
			}        
			for (int i = 0; i < childFiles.length; i++) {  
				deleteFile(childFiles[i]);  
			}  
			file.delete();  
		}  
	} 

	//组件坐标
	public int[] getBound(UiObject obj) throws UiObjectNotFoundException{
		Rect r = obj.getBounds();
		int x1 = r.left;
		int y1 = r.top;
		int x2 = r.right;
		int y2 = r.bottom;
		int centerX = r.centerX();
		int centerY = r.centerY();
		int a[] = {x1,y1,x2,y2,centerX,centerY};
		return a;
	}

	//截图
	public void screenShot(String path){
		UiDevice.getInstance().takeScreenshot(new File(path));
	}

	//启动APP
	public void launchApp(String packageName) throws IOException{
		String pkg = "am start "+packageName;
		Runtime.getRuntime().exec(pkg);
		sleep(3000);
	}

	//退出APP
	public void exitApp() throws IOException, UiObjectNotFoundException{
		UiObject ok = getObjByText("OK");
		UiObject sure = getObjByText("确定");
		UiObject dismiss = getObjByText("Dismiss");
		while(!UiDevice.getInstance().getCurrentPackageName().contains("com.android.launcher")){
			UiDevice.getInstance().pressBack();
			UiDevice.getInstance().pressBack();
			UiDevice.getInstance().pressBack();
			clickExist(ok);
			clickExist(sure);
			clickExist(dismiss);
		}
		pressHome();
		sleep(3000);
	}

	public void exitAndScreenShot(String path) throws UiObjectNotFoundException, IOException, RemoteException{
		wakeUpAndUnlock();
		screenShot(path);
		exitApp();
	}

	//获取像素
	public int getHeight(){
		return UiDevice.getInstance().getDisplayHeight();	
	}
	public int getWidth(){
		return UiDevice.getInstance().getDisplayWidth();
	}

	//滑动
	public void swipeRight(){
		UiDevice.getInstance().swipe(50,getHeight()/2,getWidth()-50,getHeight()/2,20);
		sleep(1000);
	}
	public void swipeLeft(){
		UiDevice.getInstance().swipe(getWidth()-50,getHeight()/2,50,getHeight()/2,20);
		sleep(1000);
	}	
	public void swipeUp(){
		UiDevice.getInstance().swipe(getWidth()/2,getHeight()-50,getWidth()/2,50,20);
		sleep(1000);
	}	
	public void swipeDown(){
		UiDevice.getInstance().swipe(getWidth()/2,50,getWidth()/2,getHeight()-50,20);
		sleep(1000);
	}

	//组件对象
	public UiObject getObjByText(String text){
		return new UiObject(new UiSelector().textMatches(text));
	}
	public UiObject getObjByID(String resourceId){
		return new UiObject(new UiSelector().resourceIdMatches(resourceId));
	}
	public UiObject getObjByClass(String className){
		return new UiObject(new UiSelector().classNameMatches(className));
	}
	public UiObject getObjByDes(String description){
		return new UiObject(new UiSelector().descriptionMatches(description));
	}
	public UiScrollable getScrByID(String resourceId){
		return new UiScrollable(new UiSelector().resourceIdMatches(resourceId));
	}
	public UiScrollable getScrByActivity(){
		return new UiScrollable(new UiSelector().index(0));
	}

	//点击
	public void click(UiObject obj) throws UiObjectNotFoundException{
		obj.click();
		sleep(1000);
	}
	public void click(String text) throws UiObjectNotFoundException{
		getObjByText(text).click();
		sleep(1000);
	}
	public void clickID(String resourceId) throws UiObjectNotFoundException{
		getObjByID(resourceId).click();
		sleep(1000);
	}
	public void clickExist(String text) throws UiObjectNotFoundException{
		UiObject obj = getObjByText(text);
		if(obj.exists()){
			obj.click();
			sleep(1000);
		}		
	}
	public void clickExist(UiObject obj) throws UiObjectNotFoundException {
		if(obj.exists()){
			obj.click();
			sleep(1000);
		}
	}	
	public void clickFound(String text) throws UiObjectNotFoundException{
		UiObject obj = getObjByText(text);
		if(!obj.exists()){
			getScrByActivity().scrollIntoView(obj);
			sleep(1000);
		}
		obj.click();
		sleep(1000);
	}
	public void clickFound(UiObject obj) throws UiObjectNotFoundException{
		if(!obj.exists()){
			getScrByActivity().scrollIntoView(obj);
			sleep(1000);
		}
		obj.click();
		sleep(1000);
	}

	//按键
	public void pressHome(){
		UiDevice.getInstance().pressHome();
		sleep(1000);
	}
	public void pressBack(){
		UiDevice.getInstance().pressBack();
		sleep(1000);
	}
	public void pressRecentApps() throws RemoteException{
		UiDevice.getInstance().pressRecentApps();
		sleep(2000);	
	}
}



