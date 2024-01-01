package ru.kpfu.itis.core_ui.extension

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun Long.convertLongToTime(): String {
    return SimpleDateFormat("yyyy.MM.dd HH:mm").format(Date(this))
}