package com.example.travelhubapp_mobile.notifications

import android.content.Context

object TokenRepository {
    private const val PREFS = "fcm_prefs"
    private const val KEY_TOKEN = "fcm_token"

    fun saveToken(context: Context, token: String) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(context: Context): String? =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(KEY_TOKEN, null)
}
