# autoprefs [![Build Status](https://travis-ci.com/int02h/autoprefs.svg?branch=master)](https://travis-ci.com/int02h/autoprefs)
Autoprefs library based on compile-time code generation lets you eliminate boilerplate code for working with Android SharedPreferences. Generated code is fast as if you would wrote it by yourself. Only one reflection call is used to create instance of generated class.

Example
-------
You need to declare interface annotated with @AutoPrefs annotation to work with SharedPreferences:
```java
@AutoPrefs("current-user")
interface User {
    @DefaultLong(-1L)
    LongPref id();
    
    @DefaultString("")
    StringPref name();
    
    IntPref age();
    
    @PrefKey("access-token")
    StringPref accessToken();
    
    BooleanPref loggedIn();
}
```
Then get the instance:
```java
User user = PrefsProvider.get(User.class);
```
and start to use SharedPreferences representing some user.

Preferences name
-----------------------
You are able to specify preferences name with @AutoPrefs annotation argument. By default the name of the interface (without package name) will be used. So keep in mind that two interfaces with the same name but in different packages will represent the same preferences instance.

Preferences keys
----------------
Method name defines preference key. You can change it with @PrefKey annotation.

Default values
--------------
There are six annotations that can be used to set up preference default value. Each for corresponding data type:
* DefaultBoolean
* DefaultFloat
* DefaultInt
* DefaultLong
* DefaultString
* DefaultStringSet

Clear preferences
-----------------
Declare void method without arguments and place @ClearMethod annotation on it. When called this method will clear all values in SharedPreferences.

Download
--------
To use latest version of the library just add the following code to your Gradle build script:
```groovy
implementation 'com.dpforge:autoprefs:1.0.2'
implementation 'com.dpforge:autoprefs-annotation:1.0.2'
annotationProcessor 'com.dpforge:autoprefs-processor:1.0.2'
```

License
-------
Copyright (c) 2018 Daniil Popov

Licensed under the [MIT](LICENSE) License.
