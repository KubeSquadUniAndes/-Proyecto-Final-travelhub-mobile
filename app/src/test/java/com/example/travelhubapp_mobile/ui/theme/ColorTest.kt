package com.example.travelhubapp_mobile.ui.theme

import org.junit.Assert.assertNotNull
import org.junit.Test

class ColorTest {

    @Test
    fun primaryColors_exist() {
        assertNotNull(Blue50)
        assertNotNull(Blue100)
        assertNotNull(Blue600)
        assertNotNull(Blue900)
    }

    @Test
    fun grayColors_exist() {
        assertNotNull(Gray50)
        assertNotNull(Gray300)
        assertNotNull(Gray400)
        assertNotNull(Gray500)
        assertNotNull(Gray600)
        assertNotNull(Gray700)
        assertNotNull(Gray900)
    }

    @Test
    fun utilityColors_exist() {
        assertNotNull(White)
        assertNotNull(Success)
    }
}
