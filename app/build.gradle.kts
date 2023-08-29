plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("kotlinx-serialization")
    id("com.google.devtools.ksp")
}

android {
    namespace = "ke.co.banit.e_distributor_android"
    compileSdk = 34

    defaultConfig {
        applicationId = "ke.co.banit.e_distributor_android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    kapt {
        correctErrorTypes=true
    }
    hilt {
        enableAggregatingTask = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.9.10"))

    //Navigation, lifecycle
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")


    // Picasso
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("jp.wasabeef:picasso-transformations:2.4.0")

    //circular image view
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Coroutines to make the HTTP requests asynchronous(In the background thread)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // Okhttp3 for the POST requests
    implementation("com.squareup.okhttp3:okhttp")
    platform("com.squareup.okhttp3:okhttp-bom:5.0.0-alpha.11")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")

    //RxJava
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("io.reactivex.rxjava2:rxjava:2.2.21")

    //Dependency Injection
    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-compiler:2.47")

    //shared preference
    implementation("androidx.preference:preference-ktx:1.2.1")

    //lottie animations
    implementation("com.airbnb.android:lottie:6.1.0")

    //clan floatingButton
    implementation("com.github.clans:fab:1.6.4")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
    implementation("com.github.stfalcon-studio:StfalconImageViewer:v1.0.1")

    //Recyclerview
    implementation("androidx.recyclerview:recyclerview:1.3.1")

    // For control over item selection of both touch and mouse driven selection
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")

    //swipe to refresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    //MaterialSearchBar
    implementation("com.github.mancj:MaterialSearchBar:0.8.5")

    //step view
    implementation("com.github.shuhart:stepview:1.5.1")

    //ROOM DATABASE Library
    val roomVersion = "2.5.2"
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation ("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    //Dialog Libraries
    implementation("com.github.BantosBen:besid:1.0.2")
    implementation("com.github.f0ris.sweetalert:library:1.6.2")
    implementation("com.github.javiersantos:BottomDialogs:1.2.1")
    implementation("com.saadahmedev.popup-dialog:popup-dialog:1.0.5")

    //Tab Layout
    implementation("com.ogaclejapan.smarttablayout:library:2.0.0@aar")
    implementation("com.ogaclejapan.smarttablayout:utils-v4:2.0.0@aar")

    //Logging
    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("com.jakewharton:process-phoenix:2.1.2")

    //SpinKit
    implementation("com.github.ybq:Android-SpinKit:1.4.0")

    //clan floatingButton
    implementation("com.github.clans:fab:1.6.4")

    //Google Maps
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.android.gms:play-services-maps:18.1.0")

    //Shimmering
    implementation("com.facebook.shimmer:shimmer:0.5.0@aar")

    //Bottom Dialog
    implementation("com.github.andrefrsousa:SuperBottomSheet:2.0.0")
    implementation("com.maxkeppeler.sheets:core:2.3.1")
    implementation("com.maxkeppeler.sheets:info:2.3.1")
    implementation("com.maxkeppeler.sheets:option:2.3.1")

    //OTP
    implementation("com.github.aabhasr1:OtpView:v1.1.2-ktx")
    implementation("com.github.mukeshsolanki.android-otpview-pinview:otpview:3.1.0")

    //BottomBar
    implementation("com.github.ibrahimsn98:SmoothBottomBar:1.7.9")

    //Blur
    implementation("jp.wasabeef:blurry:4.0.1")

    //Searchbar
    implementation("com.paulrybitskyi.persistentsearchview:persistentsearchview:1.1.4")

    //Image selector
    implementation("com.github.chintan369:MultiImagePicker:1.0.11")
    implementation("com.github.dhaval2404:imagepicker:2.1")
    implementation("io.github.sangcomz:fishbun:1.1.1")

    //Image Compressor
    implementation("id.zelory:compressor:3.0.1")

    //Custom pop-up
    implementation("com.github.skydoves:powermenu:2.2.4")

    //Like anime
    implementation("pub.hanks:smallbang:1.2.2")
}