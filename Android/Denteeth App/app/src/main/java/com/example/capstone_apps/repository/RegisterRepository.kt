package com.example.capstone_apps.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone_apps.data.remote.response.ResponseRegister
import com.example.capstone_apps.data.remote.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterRepository constructor(private val apiService: ApiService) {
  private var _result = MutableLiveData<ResponseRegister>()
  private var _error = MutableLiveData<String>()
  private var _loading = MutableLiveData<Boolean>()

  fun requestRegister(username: String, email: String, password: String) {
    _loading.value = true
    val client = apiService.requestRegister(username, email, password)
    client.enqueue(object : Callback<ResponseRegister> {
      override fun onResponse(call: Call<ResponseRegister>, response: Response<ResponseRegister>) {
        if(response.isSuccessful) {
          _result.value = response.body()
        }else{
          _error.value = response.message()
        }
      }

      override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
        _error.value = t.message.toString()
      }
    })
  }

  fun result() : LiveData<ResponseRegister> {
    return _result
  }

  fun loading() : LiveData<Boolean> {
    return _loading
  }

  fun error() : LiveData<String> {
    return _error
  }

  companion object {
    @Volatile
    private var instance: RegisterRepository? = null
    fun getInstance(apiService: ApiService): RegisterRepository =
      instance ?: synchronized(this) {
        instance ?: RegisterRepository(apiService)
      }.also { instance = it }
  }
}