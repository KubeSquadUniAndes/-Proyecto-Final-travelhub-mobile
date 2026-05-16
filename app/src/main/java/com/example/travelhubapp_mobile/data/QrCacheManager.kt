package com.example.travelhubapp_mobile.data

import android.content.Context

object QrCacheManager {
    private const val PREFS = "qr_cache"

    fun saveQr(context: Context, bookingId: String, base64Qr: String) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(bookingId, base64Qr).apply()
    }

    fun getQr(context: Context, bookingId: String): String? =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(bookingId, null)

    fun clearQr(context: Context, bookingId: String) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().remove(bookingId).apply()
    }
}
