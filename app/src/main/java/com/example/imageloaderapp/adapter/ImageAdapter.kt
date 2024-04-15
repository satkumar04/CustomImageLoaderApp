package com.example.imageloaderapp.adapter


import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.imageloaderapp.R
import com.example.myapplication.core.MyImageLoader
import com.unsplash.pickerandroid.photopicker.data.UnsplashPhoto


class ImageAdapter constructor(private val context: Context) :
    RecyclerView.Adapter<ImageAdapter.PhotoViewHolder>() {
    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var mListOfPhotos: List<UnsplashPhoto> = mutableListOf()
    private val imageLoader = MyImageLoader.getInstance(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(mLayoutInflater.inflate(R.layout.item_photo, parent, false))
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {

        // item
        val photo = mListOfPhotos[position]
        // image background
        holder.itemView.setBackgroundColor(Color.parseColor(photo.color))
        // loading the photo
        imageLoader.displayImage(
            photo.urls.small,
            holder.imageView,
            R.drawable.placeholder
        ) { errMessage -> onError(errMessage) }

    }

    private fun onError(error: String) {
        Toast.makeText(context, "Error message: $error", Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int {
        return mListOfPhotos.size
    }

    fun setListOfPhotos(listOfPhotos: ArrayList<UnsplashPhoto>?) {
        if (listOfPhotos != null) {
            mListOfPhotos = listOfPhotos
            notifyDataSetChanged()
        }
    }

    /**
     * UnsplashPhoto view holder.
     */
    class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.item_photo_iv)
    }
}

