package com.androdevelopment.data.local.database.meals

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MealsDao {

    @Query("select * from meals where mealName like '%' || :query || '%'")
    fun searchMeals(query: String): Flow<List<MealDatabaseEntity>>

    @Query("select * from categories")
    fun getMealsCategories(): Flow<List<MealCategoryDatabaseEntity>>

    @Query("select * from meals where category = :category")
    fun getMealsByCategory(category: String): Flow<List<MealDatabaseEntity>>

    @Query("select * from meals where mealId = :id")
    fun getMealById(id: String): Flow<MealDatabaseEntity>

    @Query("select * from meals order by random() limit 1")
    fun getRandomMeal(): Flow<MealDatabaseEntity>

    @Upsert
    suspend fun insertMeal(meal: com.androdevelopment.data.local.database.meals.MealDatabaseEntity)

    @Upsert
    suspend fun insertCategory(category: com.androdevelopment.data.local.database.meals.MealCategoryDatabaseEntity)
}