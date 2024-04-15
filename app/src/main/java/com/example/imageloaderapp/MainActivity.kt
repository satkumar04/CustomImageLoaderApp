package com.example.imageloaderapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imageloaderapp.adapter.ImageAdapter
import com.example.imageloaderapp.data.ImageData
import com.example.myapplication.core.MyImageLoader
import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto
import com.unsplash.pickerandroid.photopicker.presentation.UnsplashPickerActivity

class MainActivity : AppCompatActivity() {
    lateinit var imageRV: RecyclerView
    lateinit var imageAdapter: ImageAdapter
    lateinit var imageList: ArrayList<ImageData>
     val CACHE_SIZE = 1024*1024*1024
    private lateinit var imageLoader:MyImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageRV = findViewById(R.id.idGVImages)
        imageRV.setHasFixedSize(true)
        imageRV.itemAnimator = null
        imageRV.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        imageLoader = MyImageLoader.getInstance(this , CACHE_SIZE)
        val layoutManager = GridLayoutManager(this, 2)

        imageRV.layoutManager = layoutManager
        imageAdapter = ImageAdapter(this)
        imageRV.adapter = imageAdapter
        startActivityForResult(
            UnsplashPickerActivity.getStartingIntent(
                this,
                true
            ), REQUEST_CODE
        )

    }

    // here we are receiving the result from the picker activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            // getting the photos
            val photos: ArrayList<UnsplashPhoto>? = data?.getParcelableArrayListExtra(UnsplashPickerActivity.EXTRA_PHOTOS)
            // showing the preview
            imageAdapter.setListOfPhotos(photos)
        }
    }

    companion object {
        private const val REQUEST_CODE = 123
    }

    override fun onDestroy() {
        super.onDestroy()
        imageLoader.clearcache()
        imageLoader.cancelAll()
    }
}


