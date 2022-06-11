package com.example.capstone_apps.di

import com.example.capstone_apps.data.remote.retrofit.ApiConfig
import com.example.capstone_apps.repository.*

object Injection {
  fun providerLoginRepository(): LoginRepository {
    val apiService = ApiConfig.getApiService()
    return LoginRepository.getInstance(apiService)
  }

  fun providerRegisterRepository(): RegisterRepository {
    val apiService = ApiConfig.getApiService()
    return RegisterRepository.getInstance(apiService)
  }

  fun providerArticleRepository(): ArticleRepository {
    val apiService = ApiConfig.getApiService()
    return ArticleRepository.getInstance(apiService)
  }

  fun providerProfileRepository(): ProfileRepository {
    val apiService = ApiConfig.getApiService()
    return ProfileRepository.getInstance(apiService)
  }

  fun providerBennersRepository(): BennersRepository {
    val apiService = ApiConfig.getApiService()
    return BennersRepository.getInstance(apiService)
  }
}