package com.example.scooterx.presentation.components

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory


fun bitmapDescriptorFromVector(
    context: Context,
    vectorResId: Int,
    @ColorRes tintColor: Int? = null,

): BitmapDescriptor? {

    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    tintColor?.let {
        DrawableCompat.setTint(drawable, ContextCompat.getColor(context, it))
    }


    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}