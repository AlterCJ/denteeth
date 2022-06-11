package com.example.capstone_apps.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.capstone_apps.data.remote.response.ResponseHistory
import com.example.capstone_apps.data.remote.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryRepository constructor(private val apiService: ApiService) {
  private var result = MediatorLiveData<ResponseHistory>()
  fun getResult(): LiveData<ResponseHistory> = result

  private var error = MediatorLiveData<String>()
  fun getError(): LiveData<String> = error

  private var loading = MediatorLiveData<Boolean>()
  fun getLoading(): LiveData<Boolean> = loading

  fun requestPostHistory(token: String, status: String) {
    val client = apiService.requestPostHistory(token, status)
    client.enqueue(object : Callback<ResponseHistory> {
      override fun onResponse(call: Call<ResponseHistory>, response: Response<ResponseHistory>) {
        loading.value = false
        if(response.isSuccessful) {
          result.value = response.body()
        }else{
          error.value = response.message()
        }
      }

      override fun onFailure(call: Call<ResponseHistory>, t: Throwable) {
        loading.value = false
        error.value = t.message
      }
    })
  }

  fun requestGetHistory(token: String) {
    val client = apiService.requestGetHistory(token)
    client.enqueue(object : Callback<ResponseHistory> {
      override fun onResponse(call: Call<ResponseHistory>, response: Response<ResponseHistory>) {
        loading.value = false
        if(response.isSuccessful) {
          result.value = response.body()
        }else{
          error.value = response.message()
        }
      }

      override fun onFailure(call: Call<ResponseHistory>, t: Throwable) {
        loading.value = false
        error.value = t.message
      }
    })
  }

  companion object {
    @Volatile
    private var instance : HistoryRepository? = null
    fun getInstance(apiService: ApiService) : HistoryRepository =
      instance ?: synchronized(this) {
        instance ?: HistoryRepository(apiService)
      }.also { instance = it }
  }
}