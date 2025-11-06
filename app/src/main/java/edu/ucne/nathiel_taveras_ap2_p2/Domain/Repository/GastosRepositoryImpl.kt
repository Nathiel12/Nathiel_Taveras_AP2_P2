package edu.ucne.nathiel_taveras_ap2_p2.Domain.Repository

import edu.ucne.nathiel_taveras_ap2_p2.Data.Remote.GastosRemoteDataSource
import edu.ucne.nathiel_taveras_ap2_p2.Data.Repository.GastosRepository
import edu.ucne.nathiel_taveras_ap2_p2.Data.Mappers.toDomain
import edu.ucne.nathiel_taveras_ap2_p2.Data.Mappers.toRequestDto
import edu.ucne.nathiel_taveras_ap2_p2.Data.Remote.GastosApi
import edu.ucne.nathiel_taveras_ap2_p2.Domain.Model.Gastos
import javax.inject.Inject

class GastosRepositoryImpl @Inject constructor(
    private val remoteDataSource: GastosRemoteDataSource,
    private val gastosApi: GastosApi
) : GastosRepository {

    override suspend fun getAllGastos(): List<Gastos> {
        return try {
            gastosApi.getAllGastos().map { it.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getGastos(id: Int): Gastos?{
        return try {
            gastosApi.getGastos(id).toDomain()
        } catch (e: Exception) {
            null
        }
    }


    override suspend fun crearGasto(gastos: Gastos): Gastos? {
        return try {
            val dto = gastos.toRequestDto()
            val resultado = gastosApi.createGastos(dto)
            resultado.toDomain()
        } catch (e: Exception) {
            null
        }
    }

}
