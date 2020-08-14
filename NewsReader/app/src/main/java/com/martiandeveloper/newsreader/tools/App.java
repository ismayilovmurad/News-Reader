package com.martiandeveloper.newsreader.tools;

import android.app.Application;

public class App extends Application {

    @SuppressWarnings("EmptyMethod")
    @Override
    public void onCreate() {
        super.onCreate();
    }

    //todo API
    //API or application programming interface is a definition
    //of methods and procedures that lets us access the features of any software


    //todo REST
    //REST or Representational State Transfer is a way of getting different
    //computer systems on the internet to talk to each other

    //Stateless
    //Resources
    //Request made to a URI
    //Response in XML/HTML/JSON
    //A lot of the APIs are based on REST


    //todo Endpoints
    //Endpoints are the individual methods or resources in the API that let us perform
    // a particular functionality


    //todo JSON
    //JSON or Javascript object notation is a standard data interchange format that defines
    //how data is sent and receiver from endpoints in an API


    //todo HTTP
    //HTTP or hypertext transfer protocol is the protocol that forms the
    //foundation of all the communication that happens on the world wide web


    //todo Threading
    //Java threading-Runnable, Thread
    //AsyncTask
    //Handled by our networking library Retrofit
    //Also handled by image loading libraries like Glide


    //todo Keytool
    //General Format:
    //keytool -exportcert -list -v -alias <your-key-name> -keystore <path-to-keystore>
    //Mac/Linux:
    //keytool -exportcert -list -v -alias androiddebugkey -keystore~/.android/debug.keystore
    //Windows:
    //keytool -exportcert -list -v -alias androiddebugkey -keystore %USERPROFILE%\.android\debug.keystore
    //Output:
    //Certificate fingerprint: SHA1: DA:39:A3:EE:5E:6B:4B:0D:32:55:BF:EF:95:60:18:90:AF:D8:07:09

    //Source: https://developers.google.com/android/guides/client-auth


    //todo What is Testing?
    //Testing is an investigation conducted to see if a product satisfies its requirements or not
    //todo Testing Principles
    //*Architecture
    //*Understand what you're trying to test
    //*Isolation
    //*Integration
    //*All possible scenarios
    //*TDD (Test Driven Development)
    //todo Type of Tests
    //*Unit tests
    //*Integration tests
    //*End to end tests


    //todo Unit Testing in Android
    //*Objective of Unit Testing - What are you testing
    //*Options - Roboelectric, Junit
    //*Unit testing on android studio
    //*Junit
    //*Mockito
    //*PowerMock
    // Mockito and PowerMock is for Unit Testing
    // Mockito is for nonstatic functions, PowerMock is for static functions


    //todo Instrumentation Testing in Android
    //*Libraries on Android - Robotium, Espresso
    // Espresso is for Instrumentation Testing


    //todo Architecture
    //*Architectural patterns
    //*MVP
    //*MVVM
    //*MVC
    //*Easier to build, maintain, and test code

    //todo Libraries
    //*Basic
        //*LeakCanary
        //*EventBus
        //*GreenDao
    //*Advanced
        //*Dagger2
        //*RxJava
        //*Realm
}
