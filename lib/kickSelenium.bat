@echo off
rem Amazon EC2上の自動テスト用実行bat
rem %1 実行するクラス名
rem %2 実行するクラスに渡す引数
rem log4j、seleniumtest.propはカレントディレクトリに存在し、
rem カレントディレクトリ/lib以下に全ての必要なjarが存在している前提で動作する。

set LOCALCLASSPATH=.\;seleniumtest.prop;log4j.properties
for %%i in (.\lib\*.jar) do call :setpath %%i
goto :endsubs
:setpath
set LOCALCLASSPATH=%LOCALCLASSPATH%;%1
goto :EOF
:endsubs

java -classpath "%LOCALCLASSPATH%" %*
set javaResult=%ERRORLEVEL%
set LOCALCLASSPATH=
exit /B %javaResult%


