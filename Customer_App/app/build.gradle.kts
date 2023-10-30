plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
 }

android {
    namespace = "com.example.maamagic"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.maamagic"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    viewBinding {
        enable = true
    }

    buildTypes {
        debug {
            // Get the STRIPE_PUBLISHABLE_KEY environment variable

            // Define the BuildConfigField
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.1.2")
    implementation("com.google.firebase:firebase-database:20.2.2")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.stripe:stripe-android:20.34.1")
    implementation ("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation ("com.squareup.okhttp3:okhttp:4.9.1")
    implementation ("androidx.viewpager2:viewpager2:1.0.0")
    implementation("com.google.code.gson:gson:2.10.1")


    implementation("com.google.firebase:firebase-auth-ktx:22.1.2")
    implementation("com.google.android.gms:play-services-fido:20.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.jakewharton:butterknife:10.2.3")
    annotationProcessor("com.jakewharton:butterknife-compiler:10.2.3")
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("com.google.code.gson:gson:2.10.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

}