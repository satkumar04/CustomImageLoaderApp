package com.example.imageloaderapp

import android.app.Application
import com.unsplash.pickerandroid.photopicker.UnsplashPhotoPicker

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // initializing the picker library

        UnsplashPhotoPicker.init(
            this,
            "ZOauhNXWuVP5rI6vqUBJvUwL7rSSAFSpZQxW20xNDrc",
            "MULYANh6qT10ejEgptax2zomLjqBx5f91H83u9dXVHs"
            /* optional page size (number of photos per page) */
        )
        /* .setLoggingEnabled(true) // if you want to see the http requests */
    }
}