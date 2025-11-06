package edu.ucne.nathiel_taveras_ap2_p2.Data.Remote

import edu.ucne.nathiel_taveras_ap2_p2.Data.Remote.dto.GastosRequestDto
import edu.ucne.nathiel_taveras_ap2_p2.Data.Remote.dto.GastosResponseDto
import javax.inject.Inject

class GastosRemoteDataSource @Inject constructor(
    private val api: GastosApi
) {
    suspend fun getAllGastos(): Resource<List<GastosResponseDto>> {
        return try {
            val response = api.getAllGastos()
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Error de red")
        }
    }

    suspend fun getGastos(id: Int): Resource<GastosResponseDto> {
        return try {
            val response = api.getGastos(id)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Error de red")
        }
    }

    suspend fun createGastos(request: GastosRequestDto): Resource<GastosResponseDto> {
        return try {
            val response = api.createGastos(request)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Error de red")
        }
    }

}