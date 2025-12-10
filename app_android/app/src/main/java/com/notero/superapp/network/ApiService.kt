package com.notero.superapp.network

import okhttp3.OkHttpClient
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Path

object AuthInterceptor : Interceptor {
    var token: String = ""
    fun setToken(newToken: String?) {
        token = newToken ?: ""
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        if (token.isNotBlank()) {
            builder.addHeader("Authorization", "Bearer $token")
        }
        val request = builder.build()
        return chain.proceed(request)
    }
}

private val client = OkHttpClient.Builder()
    .addInterceptor(AuthInterceptor)
    .build()

private val retrofit: Retrofit = Retrofit.Builder()
    // NOTE: 10.0.2.2 apunta al localhost del host cuando se usa el emulador Android
    .baseUrl("http://10.0.2.2:8000/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("token/")
    suspend fun login(@Body request: LoginRequest): TokenResponse

    @Headers("Content-Type: application/json")
    @POST("usuarios/registro/")
    suspend fun register(@Body request: RegisterRequest): Unit

    // Ejemplo de otro endpoint existente
    @Headers("Content-Type: application/json")
    @POST("add-items-bulk")
    suspend fun addItemsBulk(@Body request: BulkItemsRequest): BulkItemsResponse

    @GET("listas/")
    suspend fun getLists(): List<ListaDto>

    @POST("listas/")
    suspend fun createList(@Body body: CreateListRequest): ListaDto

    @GET("listas/{id}/")
    suspend fun getList(@Path("id") id: Int): ListaDto

    @POST("listas/{id}/add_items_bulk/")
    suspend fun addItemsBulk(@Path("id") id: Int, @Body req: CodigosRequest): BulkItemsResponse

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

// Data classes extras para auth
data class LoginRequest(val email: String, val password: String)

data class TokenResponse(val access: String, val refresh: String)

data class RegisterRequest(
    val email: String,
    val password: String,
    val first_name: String? = null,
    val last_name: String? = null
)

// Data classes lista
data class ListaDto(
    val id: Int,
    val nombre: String,
    val limite_presupuesto: Double?,
    val total: Double,
    val detalles: List<DetalleDto>
)

data class DetalleDto(
    val id: Int,
    val producto: ProductDto,
    val cantidad: Int,
    val precio_unitario: Double,
    val subtotal: Double
)

data class CreateListRequest(val nombre: String, val limite_presupuesto: Double?)

data class CodigosRequest(val codigos: List<String>)

