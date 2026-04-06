package com.example.travelhubapp_mobile.ui.theme

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.junit.Assert.assertEquals
import org.junit.Test

class TypographyTest {

    @Test
    fun displayLarge_fontWeight() {
        assertEquals(FontWeight.Bold, Typography.displayLarge.fontWeight)
    }

    @Test
    fun displayLarge_fontSize() {
        assertEquals(30.sp, Typography.displayLarge.fontSize)
    }

    @Test
    fun headlineLarge_fontWeight() {
        assertEquals(FontWeight.Bold, Typography.headlineLarge.fontWeight)
    }

    @Test
    fun headlineLarge_fontSize() {
        assertEquals(24.sp, Typography.headlineLarge.fontSize)
    }

    @Test
    fun headlineSmall_fontWeight() {
        assertEquals(FontWeight.Bold, Typography.headlineSmall.fontWeight)
    }

    @Test
    fun headlineSmall_fontSize() {
        assertEquals(18.sp, Typography.headlineSmall.fontSize)
    }

    @Test
    fun titleLarge_fontWeight() {
        assertEquals(FontWeight.SemiBold, Typography.titleLarge.fontWeight)
    }

    @Test
    fun titleLarge_fontSize() {
        assertEquals(18.sp, Typography.titleLarge.fontSize)
    }

    @Test
    fun titleMedium_fontWeight() {
        assertEquals(FontWeight.SemiBold, Typography.titleMedium.fontWeight)
    }

    @Test
    fun titleMedium_fontSize() {
        assertEquals(16.sp, Typography.titleMedium.fontSize)
    }

    @Test
    fun titleSmall_fontWeight() {
        assertEquals(FontWeight.SemiBold, Typography.titleSmall.fontWeight)
    }

    @Test
    fun titleSmall_fontSize() {
        assertEquals(14.sp, Typography.titleSmall.fontSize)
    }

    @Test
    fun bodyLarge_fontWeight() {
        assertEquals(FontWeight.Normal, Typography.bodyLarge.fontWeight)
    }

    @Test
    fun bodyLarge_fontSize() {
        assertEquals(16.sp, Typography.bodyLarge.fontSize)
    }

    @Test
    fun bodyMedium_fontSize() {
        assertEquals(14.sp, Typography.bodyMedium.fontSize)
    }

    @Test
    fun bodySmall_fontSize() {
        assertEquals(12.sp, Typography.bodySmall.fontSize)
    }

    @Test
    fun labelLarge_fontWeight() {
        assertEquals(FontWeight.SemiBold, Typography.labelLarge.fontWeight)
    }

    @Test
    fun labelMedium_fontWeight() {
        assertEquals(FontWeight.Medium, Typography.labelMedium.fontWeight)
    }

    @Test
    fun labelSmall_fontWeight() {
        assertEquals(FontWeight.Medium, Typography.labelSmall.fontWeight)
    }

    @Test
    fun labelSmall_fontSize() {
        assertEquals(12.sp, Typography.labelSmall.fontSize)
    }
}
