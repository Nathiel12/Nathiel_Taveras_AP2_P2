package edu.ucne.nathiel_taveras_ap2_p2.Domain.UseCases

import edu.ucne.nathiel_taveras_ap2_p2.Data.Repository.GastosRepository
import edu.ucne.nathiel_taveras_ap2_p2.Domain.Model.Gastos
import javax.inject.Inject

class GetGastosUseCase @Inject constructor(
    private val repository: GastosRepository
) {
    suspend operator fun invoke(id: Int): Gastos? {
        if (id <= 0) throw IllegalArgumentException("El ID debe ser mayor que 0")
        return repository.getGastos(id)
    }
}
