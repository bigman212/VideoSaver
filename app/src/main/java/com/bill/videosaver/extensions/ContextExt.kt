package com.bill.videosaver.extensions

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import com.bill.videosaver.R

fun Context.copyToClipBoard(content: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(getString(R.string.app_name), content)
    clipboard.setPrimaryClip(clip)
}

fun Context.getClipboardText(trimmed: Boolean = true): String? {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val text = clipboard.primaryClip?.getItemAt(0)?.text?.toString()
    return if (trimmed) text?.trim() else text
}

fun Context.shareText(value: String) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_TEXT, value)
    startActivity(Intent.createChooser(shareIntent, "Share Text"))
}

fun Context.getDrawableCompat(@DrawableRes drawableId: Int): Drawable? =
    ContextCompat.getDrawable(this, drawableId)

fun Context.getColorStateListCompat(@ColorRes colorRes: Int): ColorStateList? =
    ContextCompat.getColorStateList(this, colorRes)
