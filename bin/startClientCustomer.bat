@echo off

set JAVA=%JAVA_HOME%\bin\java
set DEPLOY_DIR=..\build
set LIB_DIR=..\lib

set CLASSPATH=%DEPLOY_DIR%\clientCustomer.jar;%LIB_DIR%\mysql-connector.jar

%JAVA% -cp %CLASSPATH% com.yaps.petstore.ui.MenuCustomer