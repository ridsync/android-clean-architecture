# Android Clean Architecture

[![Kotlin Version](https://img.shields.io/badge/kotlin-1.5.21-blue.svg)](http://kotlinlang.org/)
[![AGP](https://img.shields.io/badge/AGP-7.0.2-blue)](https://developer.android.com/studio/releases/gradle-plugin)
[![Gradle](https://img.shields.io/badge/Gradle-7.0-blue)](https://gradle.org)
[![License](https://img.shields.io/badge/License-Apache%202.0-red.svg)](http://www.apache.org/licenses/LICENSE-2.0)

## Introduction

- This repository contains a detailed example app that implements Android MVVM Clean Architecture using Kotlin, Jetpack, Hilt, Coroutine/Flow, 
Retrofit and Room 
- 이 리포지토리에는 Kotlin, Jetpackm, Hilt, Coroutine/Flow, Retrofit 및 Room을 사용하여 Android MVVM 클린 아키텍처를 구현하는 자세한 샘플 앱이 포함되어 있습니다.

## Environment
  1. Android Studio Artic Fox : 2020.3.1
  1. Kotlin : 1.5.21
  2. Gradle : 7.0.0
  3. Android compileSdkVersion : 30
  4. Hilt : 2.38.1
  5. Coroutine/Flow : kotlinx-coroutines-core:1.5.0
  6. Retrofit : 2.9.0
  7. Room : 2.3.0
    


## Architecture Concept
  - **Android MVVM CleanArchitecture**
  ![](https://github.com/ridsync/android-clean-architecture/blob/main/clean.png)
  
  - **Implement Business Logic**
      1. 앱 실행 - 로그인 ( 서버 상태 확인 후 로그인 프로세스)
      2. 회원가입
  
  - **Presenstaion**
      1. UserInterface
          - 유저에게 시각적으로 보이고, 유저와 상호작용하는 뷰 영역
          - Android 에서는 Activity 와 Fragment 클래스가 이에 해당한다.
          - 이 클래스들에서 유저에게 보여질 UI를 Draw하고, Event를 수신 하고 처리하는 로직을 작성 한다.
          - ex) Package | Presentation > Signup > SignUpFragment
      2. ViewModel 
          - 안드로이드 수명 주기를 고려하여 UI 관련 데이터를 저장하고 관리하도록 설계됨
          - 뷰에 노출되는 뷰데이터(LiveData)를 갖고있다.
          - ViewModel은 View(UI)클래스와 상관이 없이 만들어져 있기에, 어느 뷰에서나 사용 할 수 있다. 
          (실제, 공통기능의 ViewModel이 아닌 이상 대부분 View :ViiewModel은 1:1 관계이긴 하다.)
          - ex) Package | Presentation > Signup > SignUpViewModel
  - **Domain**
      1. UseCase
          - 어플리케이션의 주요 로직(?)을 담당하는 영역
          - UI,Data Layer 의 데이터 처리를 제외한 실제 소프트웨어 비즈니스의 주요 로직을 작성한다.
          - ex) Package | Domain > Interactor > SignUpUseCase
      2. DomainDTO
          - 어플리케이션의 도메인 데이터 전달 객체 (Data Transfer Object)
          - ex) Package | Domain > Model > DomainDTO
      3. State
          - 어플리케이션의 주요 뷰,액션의 상태 정의 객체
          - ex) Package | Domain > State > SignUpActionState
      4. Repository
          - 유스케이스에서 사용 할 Repository Interface 정의 클래스
          - ex) Package | Domain > Repository > AuthRepository
  - **Data**
      1. RepositoryImpl
          - 유스케이스가 필요로 하는 데이터 저장/수정 등의 기능을 제공하는 영역
          - ex) Package | Data > Repository > AuthRepositoryImpl
      2. DataSource(Retrofit, Room)
          - 실제 데이터의 입출력을 위한 로직이 위치하는 영역
          - 외부 Interface나 Device등의 FrameWork에 직접 접근하여 데이터를 처리한다.
          - ex) Package | Data > Source > Remote(Local) > Retrofit(Room) > AuthRemoteDataSourceImpl(AuthLocalDataSourceImpl)
            
## References
  - [https://github.com/android/architecture-samples](https://github.com/android/architecture-samples)
  - [https://itnext.io/android-architecture-hilt-mvvm-kotlin-coroutines-live-data-room-and-retrofit-ft-8b746cab4a06](https://itnext.io/android-architecture-hilt-mvvm-kotlin-coroutines-live-data-room-and-retrofit-ft-8b746cab4a06)
  - [https://levelup.gitconnected.com/clean-architecture-with-mvvm-34cc05ab3bc5](https://levelup.gitconnected.com/clean-architecture-with-mvvm-34cc05ab3bc5)
  - [https://medium.com/@justfaceit/clean-architecture는-모바일-개발을-어떻게-도와주는가-1-경계선-계층을-정의해준다-b77496744616](https://medium.com/@justfaceit/clean-architecture%EB%8A%94-%EB%AA%A8%EB%B0%94%EC%9D%BC-%EA%B0%9C%EB%B0%9C%EC%9D%84-%EC%96%B4%EB%96%BB%EA%B2%8C-%EB%8F%84%EC%99%80%EC%A3%BC%EB%8A%94%EA%B0%80-1-%EA%B2%BD%EA%B3%84%EC%84%A0-%EA%B3%84%EC%B8%B5%EC%9D%84-%EC%A0%95%EC%9D%98%ED%95%B4%EC%A4%80%EB%8B%A4-b77496744616)


## License
```
Copyright (P) 2021 DevOKs

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this project except in compliance with the License.
You may obtain a copy of the License at

   <http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
