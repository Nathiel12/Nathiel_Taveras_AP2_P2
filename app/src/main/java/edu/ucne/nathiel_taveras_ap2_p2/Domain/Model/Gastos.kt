package edu.ucne.nathiel_taveras_ap2_p2.Domain.Model

class Gastos(
    val gastoId: Int,
    val fecha: String,
    val suplidor: String,
    val ncf: String,
    val itbis: Double?,
    val monto: Double?
)