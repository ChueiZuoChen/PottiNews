package com.czchen.pottinews.db

import androidx.room.TypeConverter
import com.czchen.pottinews.models.Source


/**作用是因為Article 裡面有包含Source必須告訴RoomDatabase當接到Soruce型態的資料將它轉換成String並放入Source*/
class Converts {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}