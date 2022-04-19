package com.app.repositories.utils

import androidx.room.TypeConverter
import com.google.gson.Gson


class CustomTypeConverter {

    private val gson: Gson = Gson()

//    @TypeConverter
//    fun stringToDocument(data: String?): DocumentOutput? {
//        return if (data != null) gson.fromJson(data, DocumentOutput::class.java) else null
//    }
//
//    @TypeConverter
//    fun documentToString(doc: DocumentOutput?): String? {
//        return if (doc != null) gson.toJson(doc) else null
//    }
//
//
//    @TypeConverter
//    fun stringToAddress(data: String?): Address? {
//        return if (data != null) gson.fromJson(data, Address::class.java) else null
//    }
//
//    @TypeConverter
//    fun documentToAddress(doc: Address?): String? {
//        return if (doc != null) gson.toJson(doc) else null
//    }

}

