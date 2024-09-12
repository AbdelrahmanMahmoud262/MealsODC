package com.androdevelopment.domain.usecase

import com.androdevelopment.domain.entity.Result
import com.androdevelopment.domain.entity.Result.*
import com.androdevelopment.domain.entity.UseCaseException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

abstract class UseCase<I:UseCase.Request,O:UseCase.Response>(private val configuration: Configuration) {

    fun invoke(request:I) = process(request)
        .map {
            Success(it) as Result<O>
        }.flowOn(configuration.dispatcher)
        .catch {
            emit(Error(UseCaseException.createFromThrowable(it)))
        }

    internal abstract fun process(request:I): Flow<O>

    class Configuration(val dispatcher:CoroutineDispatcher)

    interface Request
    interface Response
}