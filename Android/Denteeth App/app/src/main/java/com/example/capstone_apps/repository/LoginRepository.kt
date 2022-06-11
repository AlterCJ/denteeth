package com.example.capstone_apps.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone_apps.data.remote.response.ResponseLogin
import com.example.capstone_apps.data.remote.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository constructor(private val apiService: ApiService) {
  private var _result = MutableLiveData<ResponseLogin>()
  private var _error = MutableLiveData<String>()
  private var _loading = MutableLiveData<Boolean>()

  fun requestLogin(email: String, password:String) {
    _loading.value = true
    val client = apiService.requestLogin(email, password)
    client.enqueue(object : Callback<ResponseLogin> {
      override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
        if(response.isSuccessful) {
          _result.value = response.body()
        }else{
          _error.value = response.message()
        }
      }

      override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
       _error.value = t.message.toString()
      }
    })
  }

  fun result(): LiveData<ResponseLogin> {
    return _result
  }

  fun loading(): LiveData<Boolean> {
    return _loading
  }

  fun error(): LiveData<String> {
    return _error
  }

  companion object {
    @Volatile
    private var instance : LoginRepository? = null
    fun getInstance(apiService: ApiService) : LoginRepository =
      instance ?: synchronized(this) {
        instance ?: LoginRepository(apiService)
      }.also { instance = it }
  }
}