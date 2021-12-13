package com.sample.jetretrofit.services

import com.sample.jetretrofit.data.Todo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://jsonplaceholder.typicode.com/"

interface APIService {
  @GET("todos")
  suspend fun getTodos(): List<Todo>

  companion object {
    var apiService: APIService? = null
    fun getInstance(): APIService {
      if (apiService == null) {
        apiService = Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .build().create(APIService::class.java)
      }
      return apiService!!
    }
  }

}
