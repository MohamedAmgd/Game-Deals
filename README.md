<h1 align="center">
	<img alt="Logo" src=".github/logo.png" width="200px" />
</h1>

<h3 align="center">
  Game Deals
</h3>

<p align="center">Android App</p>

<p align="center">
  <img alt="GitHub top language" src="https://img.shields.io/github/languages/top/MohamedAmgd/Game-Deals">

  <a href="https://www.linkedin.com/in/mohamedamgd/">
    <img alt="Made by" src="https://img.shields.io/badge/made_by-Mohamed_Amgd-green">
  </a>
  
  <img alt="Repository size" src="https://img.shields.io/github/repo-size/MohamedAmgd/Game-Deals">
  
  <a href="https://github.com/MohamedAmgd/Game-Deals/commits/master">
    <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/MohamedAmgd/Game-Deals">
  </a>
  
  <a href="https://github.com/MohamedAmgd/Game-Deals/issues">
    <img alt="Repository issues" src="https://img.shields.io/github/issues/MohamedAmgd/Game-Deals">
  </a>
  
  <img alt="GitHub" src="https://img.shields.io/github/license/MohamedAmgd/Game-Deals">
  <img alt="Downloads" src="https://img.shields.io/github/downloads/MohamedAmgd/Game-Deals/total">
</p>

<p align="center">
  <a href="#-about-the-project">About the project</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-technologies">Technologies</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-getting-started">Getting started</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-how-to-contribute">How to contribute</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-license">License</a>
</p>
<p align="center">
  <a href="https://github.com/MohamedAmgd/Game-Deals/releases/latest/download/Game-Deals.apk">
    <img alt="Download Lastest Apk" src="https://custom-icon-badges.demolab.com/badge/-Download_Lastest_Apk-Grean?style=for-the-badge&logo=download&logoColor=white">
  </a>
</p>

## 👨🏻‍💻 About the project

- <p style="color: red;">An Android app that shows most of the deals and discounts on PC games and notify you about games that discounted free for limited time
- It shows the discounts in famous stores like:-
  <ul>
- Steam
- Origin
- Epic Games
- GOG
  </ul>
</p>

| <img src=".github/Screenshot_1.png"> | <img src=".github/Screenshot_2.png"> | <img src=".github/Screenshot_3.png"> |
| ------------------------------------ | ------------------------------------ | ------------------------------------ |

This app gets these data from an external API. To see this **API**, click here: [Game Deals Rest API](https://github.com/MohamedAmgd/Game-Deals-API)</br>

## 🚀 Technologies

Technologies that I used to develop this mobile client

- [Android 13 (API level 33)](https://developer.android.com/about/versions/13)
- [Kotlin](https://kotlinlang.org/)
- [Material Design](https://m2.material.io/design)
- [Dagger Hilt](https://dagger.dev/hilt/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Shared Preferences](https://developer.android.com/training/data-storage/shared-preferences)
- [Work Manager](https://developer.android.com/topic/libraries/architecture/workmanager)
- [Accompanist](https://github.com/google/accompanist)
- [Shimmer loading](https://github.com/valentinilk/compose-shimmer)
- [Coil](https://coil-kt.github.io/coil/)
- [Retrofit](https://github.com/square/retrofit)
- [Room](https://developer.android.com/jetpack/androidx/releases/room)

## 💻 Getting started

### Requirements

- Have this application's [API](https://github.com/MohamedAmgd/Game-Deals-API) running

**Clone the project and access the folder**

```bash
$ git clone https://github.com/MohamedAmgd/Game-Deals.git && cd Game-Deals
```

**Configre API**

- Add the API url in DEALS_API_URL in [This file](app/src/main/java/com/mohamed_amgd/gamedeals/GameDealsAppModule.kt)

### Working with Android Studio.

[Android Studio](https://developer.android.com/studio) is currently the official Android IDE. Due to this, it is recommended as the IDE to use in your development environment.

It is recommended to use the last version available in the stable channel of Android Studio updates. See what update channel is your Android Studio checking for updates in the menu path 'Help'/'Check for Update...'/link 'Updates' in the dialog.

To set up the project in Android Studio follow the next steps:

- Open Android Studio and select 'Import Project (Eclipse ADT, Gradle, etc)'. Browse through your file system to the folder 'android' where the project is located. Android Studio will then create the '.iml' files it needs. If you ever close the project but the files are still there, you just select 'Open Project...'. The file chooser will show an Android face as the folder icon, which you can select to reopen the project.
- Android Studio will try to build the project directly after importing it. To build it manually, follow the menu path 'Build'/'Make Project', or just click the 'Play' button in the tool bar to build and run it in a mobile device or an emulator. The resulting APK file will be saved in the 'build/outputs/apk/' subdirectory in the project folder.

### Working in a terminal with Gradle:

[Gradle](https://gradle.org/) is the build system used by Android Studio to manage the building operations on Android apps. You do not need to install Gradle in your system, and Google recommends not to do it, but instead trusting on the [Gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) included in the project.

- Open a terminal and go to the 'android' directory that contains the repository.
- Run the 'clean' and 'build' tasks using the Gradle wrapper provided
  - Windows: `gradlew.bat clean build`
  - Mac OS/Linux: `./gradlew clean build`

The first time the Gradle wrapper is called, the correct Gradle version will be downloaded automatically. An Internet connection is needed for it works.
The generated APK file is saved in android/build/outputs/apk as android-debug.apk

## 🤔 How to contribute

**Make a fork of this repository**

```bash
# Fork using GitHub official command line
# If you don't have the GitHub CLI, use the web site to do that.

$ gh repo fork MohamedAmgd/Game-Deals
```

**Follow the steps below**

```bash
# Clone your fork
$ git clone your-fork-url && cd Game-Deals

# Create a branch with your feature
$ git checkout -b my-feature

# Make the commit with your changes
$ git commit -m 'feat: My new feature'

# Send the code to your remote branch
$ git push origin my-feature
```

After your pull request is merged, you can delete your branch

## 📝 License

This project is licensed under the GPLv3 License - see the [LICENSE](LICENSE) file for details.

---

Made by Mohamed Amgd 👋 [See my linkedin](https://www.linkedin.com/in/mohamedamgd/)
