package com.androdevelopment.domain.entity.meals

data class MealEntity(
    val mealId: String,
    val area: String?,
    val category: String?,
    val imageUrl: String?,
    val ingredients: List<String>,
    val instructions: String?,
    val mealName: String?,
    val mealThumbUrl: String?,
    val measures: List<String>,
    val tags: String?,
    val youtubeUrl: String?,
)