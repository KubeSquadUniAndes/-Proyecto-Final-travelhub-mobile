package com.example.travelhubapp_mobile.data

import kotlinx.coroutines.delay

data class Hospedaje(
    val id: Int,
    val nombre: String,
    val ubicacion: String,
    val precioPorNoche: String,
    val rating: String,
    val reviews: String,
    val descripcion: String
)

object HospedajeRepository {
    suspend fun getHospedajes(): List<Hospedaje> {
        delay(1500) // Simula carga de red
        return listOf(
            Hospedaje(1, "Hotel Grand Luxury", "Bogotá, Colombia", "COP 600,000", "4.8", "(342)", "Hotel de lujo con vistas panorámicas"),
            Hospedaje(2, "Modern Boutique Hotel", "Medellín, Colombia", "COP 480,000", "4.6", "(215)", "Boutique moderno en El Poblado"),
            Hospedaje(3, "Beachfront Paradise Resort", "Cartagena, Colombia", "COP 800,000", "4.9", "(523)", "Resort frente al mar Caribe"),
            Hospedaje(4, "Mountain View Lodge", "Santa Marta, Colombia", "COP 350,000", "4.5", "(189)", "Lodge con vista a la Sierra Nevada"),
            Hospedaje(5, "Pool View Resort", "San Andrés, Colombia", "COP 720,000", "4.7", "(298)", "Resort con piscina infinita"),
        )
    }
}
