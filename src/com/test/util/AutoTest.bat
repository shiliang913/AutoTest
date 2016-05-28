for /f "tokens=2" %%a in ('adb shell ps ^| findstr "uiautomator" ') do adb shell kill %%a
pause