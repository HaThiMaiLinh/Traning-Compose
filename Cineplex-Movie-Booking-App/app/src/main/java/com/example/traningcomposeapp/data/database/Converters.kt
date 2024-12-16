package com.example.traningcomposeapp.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromSeatList(seatList: List<String>?): String? {
        val gson = Gson()
        return gson.toJson(seatList)
    }

    @TypeConverter
    fun toSeatList(seatListString: String?): List<String>? {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(seatListString, type)
    }
}