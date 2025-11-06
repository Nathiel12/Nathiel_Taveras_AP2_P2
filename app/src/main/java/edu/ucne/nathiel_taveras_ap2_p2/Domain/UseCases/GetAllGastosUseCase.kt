package edu.ucne.nathiel_taveras_ap2_p2.Domain.UseCases

import edu.ucne.nathiel_taveras_ap2_p2.Data.Repository.GastosRepository
import edu.ucne.nathiel_taveras_ap2_p2.Domain.Model.Gastos
import javax.inject.Inject

class GetAllGastosUseCase @Inject constructor(
    private val repository: GastosRepository
) {
    suspend operator fun invoke(): List<Gastos> {
        return repository.getAllGastos()
    }
}