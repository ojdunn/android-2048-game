# 2048 Number Sliding Game for Android

A clone of the popular 2048 number sliding game. Move a grid of even numbers to reach a sum of 2048. 

The game starts with a few random cells filled with even numbers. The player makes a move by sliding the board left, right, up, or down. The numbers will then slide the respective direction. If a number collides with an equal number, the two numbers will sum into one cell towards the direction chosen. With each move one new number will randomly appear on a cell. The game is over when no more moves can be taken because the board is full or a winning sum of 2048 has been made in any cell.

Use of API 32, supporting back to API 26, using Kotlin.

In progress.

## Links

[Android documentation](https://developer.android.com/reference)
[Kotlin documentation](https://kotlinlang.org/docs/getting-started.html)
[App Github Page](https://github.com/ojdunn/android-2048-game)

## Technology used

### Android

An Android app starts with a Linux process called Zygote. Zygote instantiates the Java Virtual Machine (JVM) running on Android Run Time (ART). When it spawns processes it passes along the JVM and core Android libraries. 

At launch, an app's manifest file is read by the Android runtime system to get information on the application class and main launcher activity. A new child process instantiates an `ActivityThread` object, which uses the `android.app.Application` application class by default (may use another via the manifest). An activity is instantiated after `ActivityThread` sends a request to the Activity manager. Once an activity starts it invokes the `OnCreate()` method.

A running app makes use of a callback function, usually of the `Activity` subclass `AppCompatActivity` today, to provide hooks for system events and other apps. The various callback methods have the *on* prefix such as `onCreate()`, `onResume()`, and `onStop()`.

While an app is running it can be in seven states. The most important states include (with their associated hook calls) Active (`onResume()`), Paused (`onPause()`), Stopped (`onStop()`), Restarted (`onRestart()` then `onResume()` called). Overriding these methods allows you to modify them. 

`onCreate()`, the first hook of the lifecycle of an Android app, can be overriden to allow the programmer to set the views, widgets, listeners, resources, and more.

When a new activity is started the sequence of hook calls is `onCreate(), onStart(), onResume()`. On a transition to a new activity with an intent call of `startActivity(intentName)` then `onPause(), onStop()` are called before the same start calls are made for the next activity. `onStop()` hides a screen. If the back button is pressed then `onDestroy()` is called after `onStop()`, forcing the activity to be recreated if it is ever accessed again. A stack data structure is used for activity transitions similar to function call stacks.

The `finish()` method of the `Activity` class allows you to terminate an activity and remove it from the activity stack. Use it to set transition behavior when moving back. Place within `onStop()` override?

The functions `onSaveInstanceState()` and `onRestoreInstanceState()` exist to restore a state after an interruption.

The manifest file `AndroidManifest.xml` defines the overall structure of the app. It includes the unique package name, permissions, themes, components, activity classes, and entry point.

#### Libraries

##### Architecture Components

This is part of the Jetpack bundle. It includes ways to improve adherence to MVC-like architectures (such as `livedata`, `databinding`, `room` libraries), app lifecycle (such as `lifecycle` library) management, running concurrent tasks in the background (such as `workmanager`), UI transitions (such as `navigation`).

####### navigation

This can be used to design an app with one Activity class consisting of a Fragment class for each screen/destination and a host Activity containing a navigation host fragment to control screen transitions.

This library consists of a navigation graph xml file. It handle the activity stack automatically, allows type safe argument passing between screens, and more.

It also has a navigation host fragment. This is a UI fragment that is added to a layout to swap screen destinations in and out of the current view.

Third, it has a navigation controller. This is a fragment object used with the navigation host fragment to tell the UI fragment what screen to load or unload.

##### ButterKnife

Allows data binding. This can be used to set up a Model-View-Viewmodel (MVVM).

### Kotlin

Developed by JetBrains and officially endorsed by Google for Android Development, Kotlin compiles into Java bytecode for use on a JVM. It also works with Java code within the same and other files of a compiled project. Any Java libraries supported by Android should work with Kotlin. In addition, old Java code can be translated to Kotlin by Android Studio. 

### Firebase



## Code plans

The goal is to follow an architecture to allow easier testing, changing of components, etc.. The more separation between the model, view, and controller the better. In this architecture the model handles logic and data, the view has communication with the model and controller, and the controller receives user actions from view and updates the model. Following this exactly with Android may be impossible.

The Android `Activity` class is used for handling events and updating the view. Some libraries exist to help with architecture. These include [Architecture Components](#Architecture Components) and [ButterKnife](#ButterKnife).

A **Model-View-Viewmodel (MVVM)** architecture may work best with Android. In this setup a passive view binds data from the viewmodel, which produces data that is consumed by the view. In Android the view itself consists of the views and their controllers so it may be called a passive view, making it a good fit for this architecture. Data binding would require a library such as [ButterKnife](#ButterKnife). 

![MVVM Diagram](./MVVM.PNG "MVVM Diagram")
<br>*Figure 1: MVVM Diagram, an architecture well suited to Android; Source: Mobile Application Development, 2nd, Engelsma*

In the data binding process, the view subscribes to a data stream produced by the viewmodel. The view also displays data to the UI and captures user UI actions. The viewmodel does not need a reference to the view and it handles the UI data. The model handles application logic and data, notifies the viewmodel of changes, and receives updates from the viewmodel.

The app is to consist of a welcome screen, a register account screen, an options screen, and a game screen. It is recommended by experts to use the Jetpack `navigation` library to create an app with one activity, a navigation graph, and a navigation controller to transition between screens. Each screen would be written as a single `Fragment` class hosted by a single `Activity` class. Fragments all have independent lifecycles like Activities and similar hook methods. Some additional hook methods of Fragments include onCreateView(), onAttach(), onDetach(), onDestroyView(). They use layout files like Activities.

Another option is to use a separate activity for each screen and changing between screens using the `Intent` class. This class also allows data to be sent between screens.

The title screen simply introduces the game and allows the user to sign in or start a game without signing in. If the player doesn't sign in local data will be used. If the player signs in remote data will overwrite local data. Local data will be written remotely before the player exits the app. [Firebase](#Firebase) might be used for this purpose. 

The fit Android material design recommendations (see more on design section of Android docs) the options should be selected from the top app bar (the "action menu") and starting the game should be selected by a floating action bar (FAB) as starting the game is the primary action.

The register screen allows a user to save their credentials to the cloud to allow the storage of their game data. This is an extra feature done for educational reasons. A game like this likely wouldn't need cloud storage unless connected to cloud game services, such as Google Play.

The options screen allows the player to choose various game options such as winning score, difficulty, and board size. The device running the game can only display a certain sized grid of squares (height, width) with readable numbers. The maximum size needs to be calculated based on the phone screen dimensions, if this is possible to do. Otherwise, a sensible range of game board sizes will be set.

The game screen itself features a grid of squares with numerical text. A floating action bar, directional swipes, or tilting motions might be used to control the game. If possible, I will first implement a floating action bar with the four directions: left, right, up, and down.

The logic of the game is summarized [above](#2048-number-sliding-game-for-android). The data for the game board must be updated to the UI every turn. The viewmodel will get this data from the model component to allow the UI view to update through data binding.

## Code overview

A passive view consists of the views and their controllers. As the user uses buttons, swipes a direction, etc., listeners take actions to 

The model consists of the game logic.


## TODO

- [ ] A playable game with basic graphics.
- [ ] Allow user to choose and set several different play options.
- [ ] Based on the phones screen size, determine the range of permitted number grid x and y axes. 
- [ ] An options screen or popup menu allows you to select the number grid size.
- [ ] Allow user accounts and cloud storage.
- [ ] Improve presentation: more attractive visuals, sounds, color, etc.