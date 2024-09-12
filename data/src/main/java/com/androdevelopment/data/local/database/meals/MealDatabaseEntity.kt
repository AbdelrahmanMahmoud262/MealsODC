package com.androdevelopment.data.local.database.meals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class MealDatabaseEntity(
    @PrimaryKey val mealId: String,
    val area: String?,
    val category: String?,
    val imageUrl:String?,
    val ingredients: List<String>,
    val instructions: String?,
    val mealName: String?,
    val mealThumbUrl: String?,
    val measures: List<String>,
    val tags: String?,
    val youtubeUrl: String?,
)