# ViewDebugHelper(ActivityAnalyzer)
A tool help you to analyze current activity information  
Have your ever handled with follow issues for lots of time?
- analyze what activity current showing page is.
- find the activity jumping path By log? (TODO)
- help the newbies get familiar with your project, especially big projects.
- to familiar with a new project in a short time.

If your answer is YES, it is for you, saving your time to do all of those easily.
A tool help you to do the follow stuffs.
- get the current top running activity.
- get the activity jumping path. (TODO)
- analyze fragments in current activity(root device required). (TODO)

Support Lollipop(Android 5.0)+.  

Additional, with the help of xpose, we can get all running fragments in current activity.
But using xpose may make you android device unstable, USE AT YOUR OWN RISK.

# Feedback
Feel free to talk about your advice and  using experience.

# Download link
[Download APK](https://github.com/waylife/ViewDebugHelper/releases)


# Compile

 1. add gradle.properties to root folder, set ANDROID_API and ANDROID_BUILD_TOOLS as you switch.
    Here is an example.
    ```
    ANDROID_API=26
    ANDROID_BUILD_TOOLS=26.0.2
    ```

 2. press the **'Run App' button on toolbar** or run ./gradlew clean assembleDebug,  you can find the apk file in app/build/outputs/apk/debug folder
 3. for release build, please send a merge request.