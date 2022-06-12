package com.example.capstone_apps.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone_apps.data.remote.response.ResponseLogin
import com.example.capstone_apps.data.remote.retrofit.ApiService
import com.example.capstone_apps.helper.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository constructor(private val apiService: ApiService) {
  private var _result = MutableLiveData<Event<ResponseLogin>>()
  fun result(): LiveData<Event<ResponseLogin>> = _result

  private var _error = MutableLiveData<Event<String>>()
  fun loading(): LiveData<Event<Boolean>> = _loading

  private var _loading = MutableLiveData<Event<Boolean>>()
  fun error(): LiveData<Event<String>> = _error

  fun requestLogin(email: String, password:String) {
    _loading.value = Event(true)
    val client = apiService.requestLogin(email, password)
    client.enqueue(object : Callback<ResponseLogin> {
      override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
        _loading.value = Event(false)
        if(response.isSuccessful) {
          _result.value = Event(response.body()!!)
        }else{
          _error.value = Event(response.message().toString())
        }
      }

      override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
        _loading.value = Event(false)
        _error.value = Event(t.message.toString())
      }
    })
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