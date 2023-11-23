package com.rahul.imgur.ui.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    companion object {
        fun getDateLocal(timeInMillis: Int): String? {
            var convertedDate = ""
            try {
                val dateFormat = "dd/MM/YY hh:mm a"
                // Create a DateFormatter object for displaying date in specified format.
                val formatter = SimpleDateFormat(dateFormat)
                //Convert Timezone to local
                formatter.timeZone = TimeZone.getDefault()
                // Create a calendar object that will convert the date and time value in milliseconds to date.
                val calendar = Calendar.getInstance()
                var convertedTimeMillis:Long = timeInMillis.toLong()
                convertedTimeMillis *= 1000
                calendar.timeInMillis = convertedTimeMillis
                convertedDate = formatter.format(calendar.time)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return convertedDate
        }

    }
}