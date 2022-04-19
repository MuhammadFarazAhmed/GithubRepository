package com.app.repositories.utils

import android.util.JsonReader
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.StringReader


class ListTypeConverter {

    private val gson: Gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): List<String> {
        val reader = JsonReader(StringReader(data))
        reader.isLenient = true
        return gson.fromJson(
            data ?: "[]",
            object : TypeToken<List<String>>() {}.type
        ) ?: listOf()
    }

    @TypeConverter
    fun listToString(list: List<String>?): String? {
        return gson.toJson(list ?: listOf<String>())
    }

}

