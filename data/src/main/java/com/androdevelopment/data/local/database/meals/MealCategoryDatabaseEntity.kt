package com.androdevelopment.data.local.database.meals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class MealCategoryDatabaseEntity(
    @PrimaryKey val idCategory: String,
    val strCategory: String? = null,
    val strCategoryDescription: String? = null,
    val strCategoryThumb: String? = null,
)