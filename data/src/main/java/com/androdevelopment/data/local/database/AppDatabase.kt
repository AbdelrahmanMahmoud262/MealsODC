package com.androdevelopment.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.androdevelopment.data.local.database.meals.MealCategoryDatabaseEntity
import com.androdevelopment.data.local.database.meals.MealDatabaseEntity
import com.androdevelopment.data.local.database.meals.MealsDao
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

@Database(entities = [
    MealDatabaseEntity::class,
    MealCategoryDatabaseEntity::class,
],
    version = 1)
@TypeConverters(ListConverter::class)
abstract class AppDatabase :RoomDatabase(){

    abstract fun mealsDao(): MealsDao
}

object ListConverter{
    @TypeConverter
    fun fromListToJson(list: List<String>): String{
        return kotlinx.serialization.json.Json.encodeToString(list)
    }

    @TypeConverter
    fun fromStringToList(string: String): List<String>{
        return kotlinx.serialization.json.Json.decodeFromString(string)
    }
}