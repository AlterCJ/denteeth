package com.example.capstone_apps.viewmodels

import androidx.lifecycle.ViewModel
import com.example.capstone_apps.data.entity.TokenEntity
import com.example.capstone_apps.repository.PreferenceRepository

class PreferenceViewModel(private val preferenceRepository: PreferenceRepository): ViewModel() {
  fun saveToken(token: String) {
    val dataToken = TokenEntity(token)
    preferenceRepository.setToken(dataToken)
  }

  fun getToken(): TokenEntity =
    preferenceRepository.getToken()

  fun saveSessionLogin(session: Boolean) {
    preferenceRepository.setIsLogin(session)
  }

  fun clearPreferences() {
    preferenceRepository.clear()
  }

  fun checkIsLogin(): Boolean =
    preferenceRepository.checkIsLogin()
}