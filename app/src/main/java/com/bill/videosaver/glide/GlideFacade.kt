package com.bill.videosaver.glide

import android.content.Context
import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder

class GlideFacade(private val context: Context) {
    private val requestBuilder: RequestBuilder<PictureDrawable> by lazy {
        Glide.with(context)
            .`as`(PictureDrawable::class.java)
            .listener(SvgSoftwareLayerSetter())
    }
}