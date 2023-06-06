package com.moon.network

import com.moon.network.data.Resource
import com.moon.network.data.api.Api
import com.moon.network.data.model.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class Repo @Inject constructor(
    val api: Api
) {
    suspend operator fun invoke(q:String): Flow<Resource<Weather>> = flow {
        emit(Resource.Loading())
        try {
            val articles = api.getLondon(q)
            emit(Resource.Success(articles))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Http Error"))
        } catch (e: IOException) {
            emit(Resource.Error("Server is not Available"))
        }
    }

    suspend fun getDays(q:String, day:String): Flow<Resource<Weather>> = flow {
        emit(Resource.Loading())
        try {
            val articles = api.getDays(q, days = day)
            emit(Resource.Success(articles))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Http Error"))
        } catch (e: IOException) {
            emit(Resource.Error("Server is not Available"))
        }
    }

}