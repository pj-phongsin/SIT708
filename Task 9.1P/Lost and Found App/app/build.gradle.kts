plugins {
    alias(libs.plugins.android.application)
}

// Load MAPS_API_KEY from local.properties safely
val mapsApiKey = run {
    val propsFile = rootProject.file("local.properties")
    if (propsFile.exists()) {
        val props = Class.forName("java.util.Properties").newInstance() as java.util.Properties
        props.load(propsFile.inputStream())
        props.getProperty("MAPS_API_KEY") ?: "DUMMY_API_KEY"
    } else {
        "DUMMY_API_KEY"
    }
}

android {
    namespace = "com.example.lostandfoundapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.lostandfoundapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Inject key from local.properties
        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation("androidx.cardview:cardview:1.0.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.android.libraries.places:places:3.3.0")
}