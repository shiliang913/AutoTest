@echo off
color 17
title MTBF
echo.
echo  1.全部功能压力
echo.
echo  2.WIFI开关压力
echo.
echo  3.蓝牙开关压力
echo.
echo  4.GPS开关压力
echo.
echo  5.飞行模式开关压力
echo.
echo  6.屏幕旋转压力
echo.
echo  7.前后置相机拍照压力
echo.
echo  8.息屏亮屏解锁压力
echo.
echo  0.停止脚本
echo.
echo  ***测试前请设置：UK英文，默认解锁Swipe
echo  ***异常截图保存于根目录下MTBF文件夹中
echo  ***测试次数记录在MTBF文件夹ValidatedTimes.txt文件
echo  ***出现Initialization Succeed提示后可拔掉USB线测试

:start
echo.
set /p n=      请连接adb，并输入数字选择功能：
if %n%==1 goto all
if %n%==2 goto wifi
if %n%==3 goto bt
if %n%==4 goto gps
if %n%==5 goto flight
if %n%==6 goto rotate
if %n%==7 goto camera
if %n%==8 goto sleep
if %n%==0 goto stop else(
echo 输入错误，请重新选择功能！
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