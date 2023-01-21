package com.bill.videosaver.extensions

import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import android.content.ActivityNotFoundException
import android.content.Intent
import com.bill.videosaver.BuildConfig
import timber.log.Timber
import java.io.File

fun Fragment.openFile(file: File) {
    val fromFile = FileProvider.getUriForFile(
        requireContext(),
        BuildConfig.APPLICATION_ID + ".provider",
        file
    )

    val target = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(fromFile, "application/pdf")
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    try {
        startActivity(target)
    } catch (e: ActivityNotFoundException) {
        Timber.e(e, "Cannot open file")
    }
}