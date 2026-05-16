package com.example.travelhubapp_mobile.navigation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class RoutesTest {

    @Test
    fun bienvenidaRoute_isCorrect() {
        assertEquals("bienvenida", Routes.BIENVENIDA)
    }

    @Test
    fun loginRoute_isCorrect() {
        assertEquals("login", Routes.LOGIN)
    }

    @Test
    fun registroRoute_isCorrect() {
        assertEquals("registro", Routes.REGISTRO)
    }

    @Test
    fun homeRoute_isCorrect() {
        assertEquals("home", Routes.HOME)
    }

    @Test
    fun perfilRoute_isCorrect() {
        assertEquals("perfil", Routes.PERFIL)
    }

    @Test
    fun reservaRoute_isCorrect() {
        assertEquals("reserva", Routes.RESERVA)
    }

    @Test
    fun buscarHotelesRoute_isCorrect() {
        assertEquals("buscar_hoteles", Routes.BUSCAR_HOTELES)
    }

    @Test
    fun confirmacionRoute_isCorrect() {
        assertEquals("confirmacion", Routes.CONFIRMACION)
    }

    @Test
    fun misReservasRoute_isCorrect() {
        assertEquals("mis_reservas", Routes.MIS_RESERVAS)
    }

    @Test
    fun reservaPrintRoute_isCorrect() {
        assertEquals("reserva_print", Routes.RESERVA_PRINT)
    }

    @Test
    fun detalleHabitacionRoute_isCorrect() {
        assertEquals("detalle_habitacion", Routes.DETALLE_HABITACION)
    }

    @Test
    fun pagoRoute_isCorrect() {
        assertEquals("pago", Routes.PAGO)
    }

    @Test
    fun allRoutes_areUnique() {
        val routes = listOf(
            Routes.BIENVENIDA, Routes.LOGIN, Routes.REGISTRO, Routes.HOME,
            Routes.PERFIL, Routes.RESERVA, Routes.BUSCAR_HOTELES, Routes.CONFIRMACION,
            Routes.MIS_RESERVAS, Routes.RESERVA_PRINT, Routes.DETALLE_HABITACION, Routes.PAGO
        )
        assertEquals(routes.size, routes.distinct().size)
    }

    @Test
    fun routes_areNotEmpty() {
        listOf(
            Routes.BIENVENIDA, Routes.LOGIN, Routes.REGISTRO, Routes.HOME,
            Routes.PERFIL, Routes.RESERVA, Routes.BUSCAR_HOTELES, Routes.PAGO
        ).forEach { assertNotEquals("", it) }
    }
}
