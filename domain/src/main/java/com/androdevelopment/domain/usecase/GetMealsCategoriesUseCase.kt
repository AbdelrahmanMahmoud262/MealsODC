package com.androdevelopment.domain.usecase

import com.androdevelopment.domain.entity.meals.MealCategoryEntity
import com.androdevelopment.domain.repository.MealsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMealsCategoriesUseCase @Inject constructor(
    configuration: Configuration,
    private val mealsRepository: MealsRepository
): UseCase<GetMealsCategoriesUseCase.Request, GetMealsCategoriesUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        mealsRepository.getMealsCategories()
            .map {
                Response(it)
            }


    data object Request:UseCase.Request
    data class Response(val mealCategories: List<MealCategoryEntity>):UseCase.Response
}