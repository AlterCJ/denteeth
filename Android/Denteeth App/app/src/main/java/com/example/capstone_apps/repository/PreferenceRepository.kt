package com.example.capstone_apps.repository

import android.content.Context
import com.example.capstone_apps.data.entity.TokenEntity
import com.example.capstone_apps.helper.Key

class PreferenceRepository constructor(context: Context) {
  private val preferences = context.getSharedPreferences(Key.PREFS_NAME, Context.MODE_PRIVATE)
  private val editor = preferences.edit()

  fun setToken(tokenEntity: TokenEntity) {
    editor.putString(Key.TOKEN, tokenEntity.token)
    editor.apply()
  }

  fun getToken(): TokenEntity {
    val modelToken = TokenEntity()
    modelToken.token = preferences.getString(Key.TOKEN, "")
    return modelToken
  }

  fun setIsLogin(key: Boolean) {
    editor.putBoolean(Key.IS_LOGIN, key)
    editor.apply()
  }

  fun checkIsLogin() : Boolean {
    return preferences.getBoolean(Key.IS_LOGIN, false)
  }

  fun clear() {
    editor.clear()
    editor.apply()
  }

  companion object {
    @Volatile
    private var instance: PreferenceRepository? = null
    fun getInstance(context: Context): PreferenceRepository =
      instance ?: synchronized(this) {
        instance ?: PreferenceRepository(context)
      }.also { instance = it }
  }
}