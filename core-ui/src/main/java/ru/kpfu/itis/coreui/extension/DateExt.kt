package ru.kpfu.itis.coreui.extension

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@SuppressLint("SimpleDateFormat")
fun Long.convertLongToTime(): String {
    val currentDate = Calendar.getInstance()
    val providedDate = Calendar.getInstance()
    providedDate.timeInMillis = this

    return when {
        isSameDate(currentDate, providedDate) -> {
            "Today ${formatTime(this)}"
        }

        isYesterday(currentDate) -> {
            "Yesterday ${formatTime(this)}"
        }

        else -> SimpleDateFormat("d MMMM HH:mm").format(Date(this))
    }
}

private fun isSameDate(date1: Calendar, date2: Calendar): Boolean {
    return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) &&
            date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH) &&
            date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH)
}

private fun isYesterday(date2: Calendar): Boolean {
    val yesterday = Calendar.getInstance()
    yesterday.add(Calendar.DAY_OF_MONTH, -1)
    return isSameDate(yesterday, date2)
}

private fun formatTime(dateInMillis: Long): String {
    val pattern = "HH:mm"
    val dateFormat = SimpleDateFormat(pattern, Locale("nl"))
    return dateFormat.format(Date(dateInMillis))
}
