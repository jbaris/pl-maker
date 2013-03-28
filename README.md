# Android playlist maker
This is an Android application sample to proof a comunication between an Android application and a SOAP web service.
The backend is a Java web application that exposes a SOAP service, implemented with CXF.
The frontend is an Android application that acts as client of the previous service.

# How to build
    git clone http://github.com/jbaris/pl-maker
    cd pl-maker
    mvn clean package
    
# How to run
The apk file will be created at ./pl-maker-frontend/target/
Run it on an Android environment.

To run the server:
* On Linux, run: $ java -jar ./pl-maker-backend/target/pl-maker-backend-1.0.jar
* On Windows, run: \pl-maker-backend\target\plmaker.exe
