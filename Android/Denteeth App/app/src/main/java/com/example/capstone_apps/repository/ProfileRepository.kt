package com.example.capstone_apps.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone_apps.data.remote.response.ResponseProfile
import com.example.capstone_apps.data.remote.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileRepository constructor(private val apiService: ApiService) {
  private var _result = MutableLiveData<ResponseProfile>()
  private var _error = MutableLiveData<String>()
  private var _loading = MutableLiveData<Boolean>()

  private var _errorInternet = MutableLiveData<String>()
  fun errorInternet(): LiveData<String> = _errorInternet

  fun requestDetailProfileUser(token:String, id: Int) {
    _loading.value = true
    val client = apiService.requestGetDetailProfile(token,id)
    client.enqueue(object : Callback<ResponseProfile> {
      override fun onResponse(call: Call<ResponseProfile>, response: Response<ResponseProfile>) {
        if(response.isSuccessful) {
          _result.value = response.body()
        }else{
          _error.value = response.message()
        }
      }

      override fun onFailure(call: Call<ResponseProfile>, t: Throwable) {
        _errorInternet.value = t.message.toString()
      }
    })
  }

  fun requestUpdateProfile(token: String, image: String,  fullName: String, data: String, location: String, gender: String) {
    _loading.value = true
    val client = apiService.requestUpdateProfile(token,image,  fullName, data, location, gender)
    client.enqueue(object : Callback<ResponseProfile> {
      override fun onResponse(call: Call<ResponseProfile>, response: Response<ResponseProfile>) {
        if(response.isSuccessful) {
          _result.value = response.body()
        }else{
          _error.value = response.message()
        }
      }

      override fun onFailure(call: Call<ResponseProfile>, t: Throwable) {
        _error.value = t.message.toString()
      }
    })
  }


  fun requestCreateProfile(token: String, image: String,  fullName: String, data: String, location: String, gender: String) {
    _loading.value = true
    val client = apiService.requestPostProfile(token,image,  fullName, data, location, gender)
    client.enqueue(object: Callback<ResponseProfile> {
      override fun onResponse(call: Call<ResponseProfile>, response: Response<ResponseProfile>) {
        if(response.isSuccessful) {
          _result.value = response.body()
        }else{
          _error.value = response.message()
        }
      }

      override fun onFailure(call: Call<ResponseProfile>, t: Throwable) {
        _error.value = t.message.toString()
      }
    })
  }

  fun result(): LiveData<ResponseProfile> {
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
    private var instance: ProfileRepository? = null
    fun getInstance(apiService: ApiService): ProfileRepository =
      instance ?: synchronized(this) {
        instance ?: ProfileRepository(apiService)
      }.also { instance = it }
  }
}


































