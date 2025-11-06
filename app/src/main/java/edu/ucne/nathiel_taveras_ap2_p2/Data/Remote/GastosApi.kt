package edu.ucne.nathiel_taveras_ap2_p2.Data.Remote

import edu.ucne.nathiel_taveras_ap2_p2.Data.Remote.dto.GastosRequestDto
import edu.ucne.nathiel_taveras_ap2_p2.Data.Remote.dto.GastosResponseDto
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GastosApi {
    @GET("api/Gastos")
    suspend fun getAllGastos(): List<GastosResponseDto>

    @GET("api/Gastos/{id}")
    suspend fun getGastos(@Path("id") id: Int): GastosResponseDto

    @POST("api/Gastos")
    suspend fun createGastos(@Body gastos: GastosRequestDto): GastosResponseDto

}