package com.example.imageloaderapp

import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imageloaderapp.adapter.ImageAdapter

import com.example.myapplication.core.MyImageLoader
import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto
import com.unsplash.pickerandroid.photopicker.presentation.UnsplashPickerActivity

class MainActivity : AppCompatActivity() {
    lateinit var imageRV: RecyclerView
    lateinit var fetchButton: Button
    lateinit var imageAdapter: ImageAdapter
    val CACHE_SIZE = 1024 * 1024 * 1024
    private lateinit var imageLoader: MyImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageRV = findViewById(R.id.idGVImages)
        fetchButton = findViewById(R.id.fetchButton)
        imageRV.setHasFixedSize(true)
        imageRV.itemAnimator = null
        imageRV.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        imageLoader = MyImageLoader.getInstance(this, CACHE_SIZE)
        val layoutManager = GridLayoutManager(this, 2)

        imageRV.layoutManager = layoutManager
        imageAdapter = ImageAdapter(this)
        imageRV.adapter = imageAdapter
        fetchImages()

        fetchButton.setOnClickListener {
            fetchImages()
        }

    }

    // here we are receiving the result from the picker activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            // getting the photos
            val photos: ArrayList<UnsplashPhoto>? =
                data?.getParcelableArrayListExtra(UnsplashPickerActivity.EXTRA_PHOTOS)
            // showing the preview
            if (photos!!.size > 0) {
                imageAdapter.setListOfPhotos(photos)
                imageRV.visibility = View.VISIBLE
                fetchButton.visibility = View.GONE
            }
            else{
                imageRV.visibility = View.GONE
                fetchButton.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        private const val REQUEST_CODE = 123
    }

    private fun fetchImages() {
        startActivityForResult(
            UnsplashPickerActivity.getStartingIntent(
                this,
                true
            ), REQUEST_CODE
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        imageLoader.clearcache()
        imageLoader.cancelAll()
    }
}


