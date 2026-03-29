package com.example.travelhubapp_mobile.ui.theme

import org.junit.Assert.*
import org.junit.Test

class ColorTest {

    @Test
    fun blue600_isCorrect() {
        assertEquals(0xFF155DFC.toInt(), Blue600.hashCode())
    }

    @Test
    fun white_isCorrect() {
        assertEquals(0xFFFFFFFF.toInt(), White.hashCode())
    }

    @Test
    fun gray50_exists() {
        assertNotNull(Gray50)
    }

    @Test
    fun success_exists() {
        assertNotNull(Success)
    }

    @Test
    fun allColors_areDifferent() {
        val colors = listOf(Blue50, Blue100, Blue600, Blue900, Gray50, Gray300, Gray400, Gray500, Gray600, Gray700, Gray900, White, Success)
        assertEquals(colors.size, colors.distinct().size)
    }
}
