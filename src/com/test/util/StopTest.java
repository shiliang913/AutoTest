package com.test.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class StopTest {

	public static int i, n;
	public static String adb = "cmd /c adb";

	public static void main(String[] args) {		
		if(System.getProperty("os.name").contains("Mac")){
			adb = "/Users/shiliang/Downloads/sdk/platform-tools/adb";
	}
		ArrayList<String> devices = getDeviceID();
		for (int i = 0; i < devices.size(); i++) {
			try {
				Process process = Runtime.getRuntime().exec(adb + " -s " + devices.get(i) +" shell ps uiautomator");
				InputStream inputStream = process.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String line;
				while((line = bufferedReader.readLine()) != null ){
					if (line.startsWith("shell")) {
						line = line.replace("shell", "").trim();
						int end = line.indexOf(" ");
						String command = adb + " -s " + devices.get(i) + " shell kill " + line.substring(0, end);
						System.out.println(command);
						Runtime.getRuntime().exec(command);
					}
				}
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static ArrayList<String> getDeviceID(){
		ArrayList<String> ids = new ArrayList<String>();
		try {
			Process process = Runtime.getRuntime().exec(adb + " devices");
			InputStream inputStream = process.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line;
			while((line=bufferedReader.readLine()) != null){
				if(line.endsWith("device")){
					int end = line.indexOf("\t");
					ids.add(line.substring(0, end));
				}
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ids;
	}


}
