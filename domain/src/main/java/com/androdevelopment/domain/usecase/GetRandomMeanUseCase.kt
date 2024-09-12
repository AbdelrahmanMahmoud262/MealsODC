package com.androdevelopment.domain.usecase

import com.androdevelopment.domain.entity.meals.MealEntity
import com.androdevelopment.domain.repository.MealsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRandomMeanUseCase @Inject constructor(
    configuration: UseCase.Configuration,
    private val repository: MealsRepository
) :UseCase<GetRandomMeanUseCase.Request,GetRandomMeanUseCase.Response>(configuration){

    override fun process(request: Request): Flow<Response> =
        repository.getRandomMeal()
            .map {
                Response(it)
            }

    data object Request:UseCase.Request
    data class Response(val meal:MealEntity):UseCase.Response
}