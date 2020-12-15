package com.example.podcastexercise.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import java.io.File


object ImgLoader {
    private const val SKIP_MEMORY_CACHE = false

    private var sBlurTransformation = BlurTransformation(25)

    fun display(context: Context?, url: String?, imageView: ImageView?, @DrawableRes placeHolder: Int) {
        if (context == null || TextUtils.isEmpty(url) || imageView == null) {
            return
        }

        Glide.with(context).asDrawable().load(GlideUrl(url)).placeholder(placeHolder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .skipMemoryCache(SKIP_MEMORY_CACHE).into(imageView)
    }

//    fun display(context: Context?, url: String?, imageView: ImageView?, onLoaded: (Drawable?) -> Unit) {
//        if (context == null || TextUtils.isEmpty(url) || imageView == null) {
//            return
//        }
//
//        Glide.with(context).asDrawable().load(GlideUrl(url))
//            .transition(DrawableTransitionOptions.withCrossFade())
//            .skipMemoryCache(SKIP_MEMORY_CACHE)
//            .listener(object : RequestListener<Drawable?> {
//                override fun onLoadFailed(
//                    e: GlideException?,
//                    model: Any,
//                    target: kotlin.annotation.Target<Drawable?>,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    return false
//                }
//
//                override fun onResourceReady(
//                    resource: Drawable?,
//                    model: Any,
//                    target: kotlin.annotation.Target<Drawable?>,
//                    dataSource: DataSource,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    onLoaded(resource)
//                    return false
//                }
//            })
//            .into(imageView)
//    }


    fun display(context: Context?, url: String?, imageView: ImageView?) {
        if (context == null || TextUtils.isEmpty(url) || imageView == null) {
            return
        }
        Glide.with(context).asDrawable().load(GlideUrl(url))
            .skipMemoryCache(SKIP_MEMORY_CACHE).into(imageView)
    }

    fun displayNoCache(context: Context?, url: String?, imageView: ImageView?, @DrawableRes placeHolder: Int) {
        if (context == null || TextUtils.isEmpty(url) || imageView == null) {
            return
        }
        Glide.with(context).asDrawable().load(GlideUrl(url))
            .placeholder(placeHolder)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(imageView)
    }

    fun displayBlur(context: Context?, url: String?, imageView: ImageView?) {
        if (context == null || TextUtils.isEmpty(url) || imageView == null) {
            return
        }
        Glide.with(context).asDrawable().load(url)
            .skipMemoryCache(SKIP_MEMORY_CACHE)
            .apply(RequestOptions.bitmapTransform(sBlurTransformation))
            .into(imageView)
    }

}