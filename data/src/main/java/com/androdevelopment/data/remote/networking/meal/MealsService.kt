package com.androdevelopment.data.remote.networking.meal

import com.androdevelopment.data.remote.networking.meal.models.MealApiModel
import com.androdevelopment.data.remote.networking.meal.models.MealCategoryResponse
import com.androdevelopment.data.remote.networking.meal.models.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsService {

    @GET("search.php")
    suspend fun searchMeals(
        @Query("s") query: String,
    ): MealResponse

    @GET("categories.php")
    suspend fun getMealsCategories(): MealCategoryResponse

    @GET("filter.php")
    suspend fun getMealsByCategory(
        @Query("c") category: String,
    ): MealResponse

    @GET("lookup.php")
    suspend fun getMealById(
        @Query("i") id: String,
    ): MealResponse

    @GET("random.php")
    suspend fun getRandomMeal(): MealResponse

}