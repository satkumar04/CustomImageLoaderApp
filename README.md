# Custom Image Loader App

This is sample app using the custom image loader library called 'MyImageLoader'.
This custom Library is handling following features:
  * Asynchronous Image loading from the Api.
  * Caching mechanism to ensure the performance.
  * Error handling.

In the sample application, Image url response data is getting from unsplash api which was handling by the unsplash Android SDK and same pass to MyImageLoader library to load the images on the grid layout asynchronously.

How to use MyImageLoader library :
 * Add it as a dependency in the project.

    implementation(project(":myImageLoader"))


MyImageLoader imageLoader = MyImageLoader.getInstance(this, CACHE_SIZE)
imageLoader.displayImage(
            photo.urls.small,
            holder.imageView,
            R.drawable.placeholder
        ) { errMessage -> onError(errMessage) }


Clone the app using following url :

https://github.com/satkumar04/CustomImageLoaderApp.git
