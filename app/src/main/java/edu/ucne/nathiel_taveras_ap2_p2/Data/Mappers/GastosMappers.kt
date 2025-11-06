package edu.ucne.nathiel_taveras_ap2_p2.Data.Mappers

import edu.ucne.nathiel_taveras_ap2_p2.Data.Remote.dto.GastosRequestDto
import edu.ucne.nathiel_taveras_ap2_p2.Data.Remote.dto.GastosResponseDto
import edu.ucne.nathiel_taveras_ap2_p2.Domain.Model.Gastos

fun Gastos.toRequestDto(): GastosRequestDto {
    return GastosRequestDto(
        fecha = fecha,
        suplidor = suplidor,
        ncf = ncf,
        itbis = itbis,
        monto = monto
    )
}

fun GastosResponseDto.toDomain(): Gastos {
    return Gastos(
        gastoId = gastoId ?: 0,
        fecha = fecha,
        suplidor = suplidor,
        ncf = ncf,
        itbis = itbis,
        monto = monto
    )
}
