package com.example.newsapp.db

import androidx.room.TypeConverter
import com.example.newsapp.models.Source

/**
 * This class takes non-primitive data types such as classes and tells Room how to interpret them.
 *
 * So for example, our "Source" data class contains an id and a String, but for our purposes, we can
 * have room just read the String and vice versa.
 *
 * This is only strictly necessary because our primary entity data response class (Article) returns
 * a Source data type.
 */

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}