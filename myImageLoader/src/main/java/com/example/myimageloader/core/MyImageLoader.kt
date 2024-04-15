package com.example.myapplication.core

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.example.myimageloader.async.DownloadImageTask
import com.example.myimageloader.async.DownloadTask
import com.example.myimageloader.cache.CacheRepository
import com.example.myimageloader.cache.Config

import java.util.concurrent.Executors
import java.util.concurrent.Future

class MyImageLoader private constructor(context: Context, cacheSize: Int) {
    private val cache = CacheRepository(context, cacheSize)
    private val executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
    private val mRunningDownloadList:HashMap<String,Future<Bitmap?>> = hashMapOf()



    fun displayImage(url: String, imageview: ImageView, placeholder: Int,listener: (String) -> Unit?) {

        var bitmap = cache.get(url)
        bitmap?.let {
            imageview.setImageBitmap(it)
            return
        }
            ?: run {
                imageview.tag = url
                if (placeholder != null)
                    imageview.setImageResource(placeholder)
                addDownloadImageTask( url, DownloadImageTask(url , imageview , cache ){
                    listener(it)
                }) }

    }


    fun addDownloadImageTask(url: String,downloadTask: DownloadTask<Bitmap?>) {
        mRunningDownloadList.put(url,executorService.submit(downloadTask))
    }


    fun clearcache() {
        cache.clear()
    }

    fun cancelTask(url: String){
        synchronized(this){
            mRunningDownloadList.forEach {
                if (it.key == url &&  !it.value.isDone)
                    it.value.cancel(true)
            }
        }
    }

    fun  cancelAll() {
        synchronized (this) {
            mRunningDownloadList.forEach{
                if ( !it.value.isDone)
                    it.value.cancel(true)
            }
            mRunningDownloadList.clear()
        }
    }


    companion object {
        private val INSTANCE: MyImageLoader? = null
        @Synchronized
        fun getInstance(context: Context, cacheSize: Int = Config.defaultCacheSize): MyImageLoader {
            return INSTANCE?.let { return INSTANCE }
                ?: run {
                    return MyImageLoader(context, cacheSize )
                }
        }
    }
}