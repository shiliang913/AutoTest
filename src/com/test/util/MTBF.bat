@echo off
color 17
title MTBF
echo.
echo  1.ȫ������ѹ��
echo.
echo  2.WIFI����ѹ��
echo.
echo  3.��������ѹ��
echo.
echo  4.GPS����ѹ��
echo.
echo  5.����ģʽ����ѹ��
echo.
echo  6.��Ļ��תѹ��
echo.
echo  7.ǰ�����������ѹ��
echo.
echo  8.Ϣ����������ѹ��
echo.
echo  0.ֹͣ�ű�
echo.
echo  ***����ǰ�����ã�UKӢ�ģ�Ĭ�Ͻ���Swipe
echo  ***�쳣��ͼ�����ڸ�Ŀ¼��MTBF�ļ�����
echo  ***���Դ�����¼��MTBF�ļ���ValidatedTimes.txt�ļ�
echo  ***����Initialization Succeed��ʾ��ɰε�USB�߲���

:start
echo.
set /p n=      ������adb������������ѡ���ܣ�
if %n%==1 goto all
if %n%==2 goto wifi
if %n%==3 goto bt
if %n%==4 goto gps
if %n%==5 goto flight
if %n%==6 goto rotate
if %n%==7 goto camera
if %n%==8 goto sleep
if %n%==0 goto stop else(
echo �������������ѡ���ܣ�
goto start
)

:all
set current_path="%cd%"
adb push %current_path%\AutoTest.jar /data/local/tmp
adb shell uiautomator runtest AutoTest.jar --nohup -c com.test.mtbf.MTBF#testRun
goto end
:wifi
set current_path="%cd%"
adb push %current_path%\AutoTest.jar /data/local/tmp
adb shell uiautomator runtest AutoTest.jar --nohup -c com.test.mtbf.MTBF#testWifiOnOff
goto end
:bt
set current_path="%cd%"
adb push %current_path%\AutoTest.jar /data/local/tmp
adb shell uiautomator runtest AutoTest.jar --nohup -c com.test.mtbf.MTBF#testBTOnOff
goto end
:gps
set current_path="%cd%"
adb push %current_path%\AutoTest.jar /data/local/tmp
adb shell uiautomator runtest AutoTest.jar --nohup -c com.test.mtbf.MTBF#testGPSOnOff
goto end
:flight
set current_path="%cd%"
adb push %current_path%\AutoTest.jar /data/local/tmp
adb shell uiautomator runtest AutoTest.jar --nohup -c com.test.mtbf.MTBF#testFlightOnOff
goto end
:rotate
set current_path="%cd%"
adb push %current_path%\AutoTest.jar /data/local/tmp
adb shell uiautomator runtest AutoTest.jar --nohup -c com.test.mtbf.MTBF#testRotate
goto end
:camera
set current_path="%cd%"
adb push %current_path%\AutoTest.jar /data/local/tmp
adb shell uiautomator runtest AutoTest.jar --nohup -c com.test.mtbf.MTBF#testCamera
goto end
:sleep
set current_path="%cd%"
adb push %current_path%\AutoTest.jar /data/local/tmp
adb shell uiautomator runtest AutoTest.jar --nohup -c com.test.mtbf.MTBF#testWakeAndSleep
goto end
:stop
for /f "tokens=2" %%a in ('adb shell ps ^| findstr "uiautomator" ') do adb shell kill %%a
goto end
:end
pause