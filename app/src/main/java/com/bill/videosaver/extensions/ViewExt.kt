package com.bill.videosaver.extensions

import android.graphics.Typeface
import android.widget.TextView

fun TextView.setTextBold() {
    setTypeface(null, Typeface.BOLD)
}

fun TextView.setTextNormal() {
    setTypeface(null, Typeface.NORMAL)
}

fun TextView.setTextItalic() {
    setTypeface(null, Typeface.ITALIC)
}