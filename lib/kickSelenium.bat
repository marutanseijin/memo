@echo off
rem Amazon EC2��̎����e�X�g�p���sbat
rem %1 ���s����N���X��
rem %2 ���s����N���X�ɓn������
rem log4j�Aseleniumtest.prop�̓J�����g�f�B���N�g���ɑ��݂��A
rem �J�����g�f�B���N�g��/lib�ȉ��ɑS�Ă̕K�v��jar�����݂��Ă���O��œ��삷��B

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


