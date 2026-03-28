package com.example.travelhubapp_mobile.ui.theme

import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Test

class ColorTest {

    @Test
    fun blue50_isCorrect() {
        assertEquals(Color(0xFFEFF6FF), Blue50)
    }

    @Test
    fun blue100_isCorrect() {
        assertEquals(Color(0xFFDBEAFE), Blue100)
    }

    @Test
    fun blue600_isCorrect() {
        assertEquals(Color(0xFF155DFC), Blue600)
    }

    @Test
    fun blue900_isCorrect() {
        assertEquals(Color(0xFF1C398E), Blue900)
    }

    @Test
    fun gray300_isCorrect() {
        assertEquals(Color(0xFFD1D5DC), Gray300)
    }

    @Test
    fun gray400_isCorrect() {
        assertEquals(Color(0xFF9CA3AF), Gray400)
    }

    @Test
    fun gray500_isCorrect() {
        assertEquals(Color(0xFF6A7282), Gray500)
    }

    @Test
    fun gray600_isCorrect() {
        assertEquals(Color(0xFF4A5565), Gray600)
    }

    @Test
    fun gray700_isCorrect() {
        assertEquals(Color(0xFF364153), Gray700)
    }

    @Test
    fun gray900_isCorrect() {
        assertEquals(Color(0xFF1E2939), Gray900)
    }

    @Test
    fun white_isCorrect() {
        assertEquals(Color(0xFFFFFFFF), White)
    }
}
