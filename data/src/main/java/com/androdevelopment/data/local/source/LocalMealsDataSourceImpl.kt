package com.androdevelopment.data.local.source

import com.androdevelopment.data.local.database.meals.MealCategoryDatabaseEntity
import com.androdevelopment.data.local.database.meals.MealDatabaseEntity
import com.androdevelopment.data.local.database.meals.MealsDao
import com.androdevelopment.data.repository.source.local.LocalMealsDataSource
import com.androdevelopment.domain.entity.meals.MealCategoryEntity
import com.androdevelopment.domain.entity.meals.MealEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalMealsDataSourceImpl @Inject constructor(
    private val mealsDao: MealsDao
) : LocalMealsDataSource {

    override fun searchMeals(query: String): Flow<List<MealEntity>> = mealsDao.searchMeals(query)
        .map {
            it.map {meal->
                convert(meal)
            }
        }

    override fun getMealsCategories(): Flow<List<MealCategoryEntity>> = mealsDao.getMealsCategories()
        .map {
            it.map { meal->
                convert(meal)
            }
        }

    override fun getMealsByCategory(category: String): Flow<List<MealEntity>> = mealsDao.getMealsByCategory(category)
        .map {
            it.map {categoryEntity->
                convert(categoryEntity)
            }
        }

    override fun getMealById(id: String): Flow<MealEntity> = mealsDao.getMealById(id)
        .map {
            convert(it)
        }

    override fun getRandomMeal(): Flow<MealEntity> = mealsDao.getRandomMeal()
        .map {
            convert(it)
        }

    override suspend fun insertMeal(meal: MealEntity) = mealsDao.insertMeal(
        meal.let { com.androdevelopment.data.local.database.meals.MealDatabaseEntity(
            mealId = it.mealId,
            area = it.area,
            category = it.category,
            imageUrl = it.imageUrl,
            ingredients = it.ingredients,
            instructions = it.instructions,
            mealName = it.mealName,
            mealThumbUrl = it.mealThumbUrl,
            measures = it.measures,
            tags = it.tags,
            youtubeUrl = it.youtubeUrl
        ) }
    )

    override suspend fun insertCategory(category: MealCategoryEntity) = mealsDao.insertCategory(category.let {
        com.androdevelopment.data.local.database.meals.MealCategoryDatabaseEntity(
            idCategory = it.categoryId,
            strCategory = it.categoryName,
            strCategoryDescription = it.categoryDescription,
            strCategoryThumb = it.categoryThumbUrl
        )
    })

    private fun convert(mealEntity: MealDatabaseEntity) = MealEntity(
        mealId = mealEntity.mealId,
        area = mealEntity.area,
        category = mealEntity.category,
        imageUrl = mealEntity.imageUrl,
        ingredients = mealEntity.ingredients,
        instructions = mealEntity.instructions,
        mealName = mealEntity.mealName,
        mealThumbUrl = mealEntity.mealThumbUrl,
        measures = mealEntity.measures,
        tags = mealEntity.tags,
        youtubeUrl = mealEntity.youtubeUrl
    )

    private fun convert(categoryEntity: MealCategoryDatabaseEntity) = MealCategoryEntity(
        categoryId = categoryEntity.idCategory,
        categoryName = categoryEntity.strCategory ?: "",
        categoryDescription = categoryEntity.strCategoryDescription?:"",
        categoryThumbUrl = categoryEntity.strCategoryThumb?: "",
    )
}