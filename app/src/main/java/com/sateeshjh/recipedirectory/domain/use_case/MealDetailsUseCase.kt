package com.sateeshjh.recipedirectory.domain.use_case

import com.sateeshjh.recipedirectory.common.Resource
import com.sateeshjh.recipedirectory.data.model.toDomainMealDetails
import com.sateeshjh.recipedirectory.domain.model.MealDetails
import com.sateeshjh.recipedirectory.domain.repository.MealDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class MealDetailsUseCase @Inject constructor(private val repository: MealDetailsRepository){

    operator fun invoke(id: String): Flow<Resource<MealDetails>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getMealDetails(id).meals[0].toDomainMealDetails()
            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage?: "Unknown Error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage?: "Check Internet Connectivity"))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage?: "Unknown Error"))
        }
    }
}