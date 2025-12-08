package com.notero.superapp.network

import okhttp3.OkHttpClient
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

object AuthInterceptor : Interceptor {
    var token: String = ""
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}

private val client = OkHttpClient.Builder()
    .addInterceptor(AuthInterceptor)
    .build()

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://your-backend-host/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("add-items-bulk")
    suspend fun addItemsBulk(@Body request: BulkItemsRequest): BulkItemsResponse

    companion object {
        val instance: ApiService by lazy { retrofit.create(ApiService::class.java) }
    }
}

// Data classes
data class BulkItemsRequest(val items: List<String>)

data class BulkItemsResponse(val added: List<ProductDto>, val missing: List<ProductDto>)

data class ProductDto(
    val id: Int,
    val name: String,
    val price: Double,
    val image: String
)

