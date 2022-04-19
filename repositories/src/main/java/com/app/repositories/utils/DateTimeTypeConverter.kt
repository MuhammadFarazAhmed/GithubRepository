package com.app.repositories.utils

import androidx.room.TypeConverter
import org.joda.time.DateTime


class DateTimeTypeConverter {

    //private val gson = Gson()

    @TypeConverter
    fun stringToDate(data: Long?): DateTime? {
        return if (data != null) DateTime(data) else null
    }

    @TypeConverter
    fun dateToString(dateTime: DateTime?): Long? {
        return dateTime?.millis
    }

}

