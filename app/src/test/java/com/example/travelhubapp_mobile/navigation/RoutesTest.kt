package com.example.travelhubapp_mobile.navigation

import org.junit.Assert.assertEquals
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
    fun routes_areUnique() {
        val routes = listOf(Routes.BIENVENIDA, Routes.LOGIN, Routes.REGISTRO, Routes.HOME)
        assertEquals(routes.size, routes.distinct().size)
    }
}
