package com.example.capstone_apps.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstone_apps.di.Injection
import com.example.capstone_apps.helper.Key
import com.example.capstone_apps.repository.*

class ViewModelFactory (
  private val loginRepository: LoginRepository,
  private val registerRepository: RegisterRepository,
  private val articleRepository: ArticleRepository,
  private val profileRepository: ProfileRepository,
  private val bennersRepository: BennersRepository
  ): ViewModelProvider.NewInstanceFactory() {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return when {
      modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
        LoginViewModel(loginRepository) as T
      }
      modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
        RegisterViewModel(registerRepository) as T
      }
      modelClass.isAssignableFrom(ArticleViewModel::class.java) -> {
        ArticleViewModel(articleRepository) as T
      }
      modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
        ProfileViewModel(profileRepository) as T
      }
      modelClass.isAssignableFrom(BennersViewModel::class.java) -> {
        BennersViewModel(bennersRepository) as T
      }
      else -> throw IllegalArgumentException(Key.UNKNOWN_MODEL_CLASS + modelClass)
    }
  }

  companion object {
    @Volatile
    private var instance: ViewModelFactory? = null
    fun getInstance(context: Context): ViewModelFactory =
      instance ?: synchronized(this) {
        instance ?: ViewModelFactory(
          Injection.providerLoginRepository(),
          Injection.providerRegisterRepository(),
          Injection.providerArticleRepository(),
          Injection.providerProfileRepository(),
          Injection.providerBennersRepository()
        )
      }
  }
}