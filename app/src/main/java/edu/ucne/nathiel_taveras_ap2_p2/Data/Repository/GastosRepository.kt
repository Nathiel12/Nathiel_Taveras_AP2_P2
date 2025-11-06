package edu.ucne.nathiel_taveras_ap2_p2.Data.Repository

import edu.ucne.nathiel_taveras_ap2_p2.Domain.Model.Gastos

interface GastosRepository {
    suspend fun getAllGastos(): List<Gastos>
    suspend fun getGastos(id: Int): Gastos?
    suspend fun crearGasto(gastos: Gastos): Gastos?
}