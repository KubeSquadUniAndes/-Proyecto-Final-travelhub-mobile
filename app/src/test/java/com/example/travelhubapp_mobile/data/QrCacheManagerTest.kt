package com.example.travelhubapp_mobile.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class QrCacheManagerTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun saveAndGetQr_returnsCorrectValue() {
        QrCacheManager.saveQr(context, "booking-123", "base64qrdata")
        assertEquals("base64qrdata", QrCacheManager.getQr(context, "booking-123"))
    }

    @Test
    fun getQr_returnsNullWhenNotSet() {
        assertNull(QrCacheManager.getQr(context, "nonexistent-booking"))
    }

    @Test
    fun clearQr_removesEntry() {
        QrCacheManager.saveQr(context, "booking-456", "somedata")
        QrCacheManager.clearQr(context, "booking-456")
        assertNull(QrCacheManager.getQr(context, "booking-456"))
    }

    @Test
    fun saveQr_overwritesPreviousValue() {
        QrCacheManager.saveQr(context, "booking-789", "first")
        QrCacheManager.saveQr(context, "booking-789", "second")
        assertEquals("second", QrCacheManager.getQr(context, "booking-789"))
    }

    @Test
    fun multipleBookings_storedIndependently() {
        QrCacheManager.saveQr(context, "b1", "qr1")
        QrCacheManager.saveQr(context, "b2", "qr2")
        assertEquals("qr1", QrCacheManager.getQr(context, "b1"))
        assertEquals("qr2", QrCacheManager.getQr(context, "b2"))
    }
}
