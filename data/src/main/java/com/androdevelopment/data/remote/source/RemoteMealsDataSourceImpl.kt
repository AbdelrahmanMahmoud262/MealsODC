package com.androdevelopment.data.remote.source

import android.util.Log
import com.androdevelopment.data.remote.networking.meal.MealsService
import com.androdevelopment.data.remote.networking.meal.models.MealApiModel
import com.androdevelopment.data.remote.networking.meal.models.MealCategoryApiModel
import com.androdevelopment.data.repository.source.remote.RemoteMealsDataSource
import com.androdevelopment.domain.entity.UseCaseException
import com.androdevelopment.domain.entity.meals.MealCategoryEntity
import com.androdevelopment.domain.entity.meals.MealEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.text.DateFormat
import javax.inject.Inject

class RemoteMealsDataSourceImpl @Inject constructor(
    private val mealsService: MealsService,
) : RemoteMealsDataSource {

    override fun searchMeals(query: String): Flow<List<MealEntity>> = flow {
        emit(mealsService.searchMeals(query).meals)
    }.map { meals ->
        meals.map { mealApiModel ->
            convert(mealApiModel)
        }
    }.catch {
        throw UseCaseException.MealsException(it)
    }

    override fun getMealsCategories(): Flow<List<MealCategoryEntity>> = flow {
        emit(mealsService.getMealsCategories().categories)
    }.map { meals ->
        meals.map { mealApiModel ->
            Log.d("Meal", mealApiModel.toString())
            convert(mealApiModel)
        }
    }.catch {
        throw UseCaseException.MealsException(it)
    }

    override fun getMealsByCategory(category: String): Flow<List<MealEntity>> = flow {
        emit(mealsService.getMealsByCategory(category).meals)
    }.map { meals ->
        meals.map {
            Log.d("Meal", it.toString())
            convert(it).copy(
                category = category
            )
        }
    }

    override fun getMealById(id: String): Flow<MealEntity> = flow {
        emit(mealsService.getMealById(id).meals.first())
    }.map { meal ->
        Log.d("Meal", meal.toString())
        convert(meal)
    }

    override fun getRandomMeal(): Flow<MealEntity> = flow {
        emit(mealsService.getRandomMeal().meals.first())
    }.map { meal ->
        Log.d("Random Meal", meal.toString())
        convert(meal)
    }

    private fun convert(mealApiModel: MealApiModel) = MealEntity(
        mealId = mealApiModel.idMeal ?: "",
        area = mealApiModel.strArea,
        category = mealApiModel.strCategory,
        imageUrl = mealApiModel.strMealThumb,
        ingredients = listOfNotNull(
            mealApiModel.strIngredient1,
            mealApiModel.strIngredient2,
            mealApiModel.strIngredient3,
            mealApiModel.strIngredient4,
            mealApiModel.strIngredient5,
            mealApiModel.strIngredient6,
            mealApiModel.strIngredient7,
            mealApiModel.strIngredient8,
            mealApiModel.strIngredient9,
            mealApiModel.strIngredient10,
            mealApiModel.strIngredient11,
            mealApiModel.strIngredient12,
            mealApiModel.strIngredient13,
            mealApiModel.strIngredient14,
            mealApiModel.strIngredient15,
            mealApiModel.strIngredient16,
            mealApiModel.strIngredient17,
            mealApiModel.strIngredient18,
            mealApiModel.strIngredient19,
            mealApiModel.strIngredient20,
        ),
        instructions = mealApiModel.strInstructions,
        mealName = mealApiModel.strMeal,
        mealThumbUrl = mealApiModel.strMealThumb,
        measures = listOfNotNull(
            mealApiModel.strMeasure1,
            mealApiModel.strMeasure2,
            mealApiModel.strMeasure3,
            mealApiModel.strMeasure4,
            mealApiModel.strMeasure5,
            mealApiModel.strMeasure6,
            mealApiModel.strMeasure7,
            mealApiModel.strMeasure8,
            mealApiModel.strMeasure9,
            mealApiModel.strMeasure10,
            mealApiModel.strMeasure11,
            mealApiModel.strMeasure12,
            mealApiModel.strMeasure13,
            mealApiModel.strMeasure14,
            mealApiModel.strMeasure15,
            mealApiModel.strMeasure16,
            mealApiModel.strMeasure17,
            mealApiModel.strMeasure18,
            mealApiModel.strMeasure19,
            mealApiModel.strMeasure20,
        ),
        tags = mealApiModel.strTags ?: "",
        youtubeUrl = mealApiModel.strYoutube ?: "",
    )

    private fun convert(mealApiModel: MealCategoryApiModel) = MealCategoryEntity(
        categoryId = mealApiModel.idCategory ?: "",
        categoryName = mealApiModel.strCategory ?: "",
        categoryDescription = mealApiModel.strCategoryDescription ?: "",
        categoryThumbUrl = mealApiModel.strCategoryThumb ?: "",
    )
}