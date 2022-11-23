package com.sateeshjh.recipedirectory.domain.use_case

import com.sateeshjh.recipedirectory.common.Resource
import com.sateeshjh.recipedirectory.data.model.MealDTO
import com.sateeshjh.recipedirectory.data.model.toDomainMeal
import com.sateeshjh.recipedirectory.domain.model.Meal
import com.sateeshjh.recipedirectory.domain.repository.MealSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class MealSearchListUseCase @Inject constructor(private val repository: MealSearchRepository){

    operator fun invoke(s: String): Flow<Resource<List<Meal>>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getMealList(s)
            val list = if (response.meals.isNullOrEmpty()) emptyList<Meal>() else response.meals.map {
                it.toDomainMeal()
            }
            emit(Resource.Success(data = list))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage?: "Unknown Error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage?: "Check Internet Connectivity"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage?: "Unknown Error"))
        }
    }
}