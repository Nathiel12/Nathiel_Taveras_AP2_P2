package edu.ucne.nathiel_taveras_ap2_p2.Data.Remote.dto

data class GastosRequestDto(
    val fecha: String,
    val suplidor: String,
    val ncf: String,
    val itbis: Double?,
    val monto: Double?
)

data class GastosResponseDto(
    val gastoId: Int? = null,
    val fecha: String,
    val suplidor: String,
    val ncf: String,
    val itbis: Double?,
    val monto: Double?
)