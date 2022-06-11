package com.example.capstone_apps.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone_apps.data.remote.response.ResponseUploadImage
import com.example.capstone_apps.data.remote.retrofit.ApiService
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadImageRepository constructor(private val apiService: ApiService) {
  private var result = MutableLiveData<ResponseUploadImage>()
  fun getResult(): LiveData<ResponseUploadImage> = result

  private val error = MutableLiveData<String>()
  fun getError(): LiveData<String> = error

  private val loading = MutableLiveData<Boolean>()
  fun getLoading(): LiveData<Boolean> = loading

  fun requestUploadImage(token: String, imageUrlPart: MultipartBody.Part) {
    val client = apiService.requestPostImage(token, imageUrlPart)
    client.enqueue(object : Callback<ResponseUploadImage> {
      override fun onResponse(call: Call<ResponseUploadImage>, response: Response<ResponseUploadImage>) {
        loading.value = false
        if(response.isSuccessful) {
          result.value = response.body()
        }else{
          error.value = response.message()
        }
      }

      override fun onFailure(call: Call<ResponseUploadImage>, t: Throwable) {
        error.value = t.message.toString()
      }
    })
  }

  companion object {
    @Volatile
    private var instance: UploadImageRepository? = null
    fun getInstance(apiService: ApiService): UploadImageRepository =
      instance ?: synchronized(this) {
        instance ?: UploadImageRepository(apiService)
      }.also { instance = it }
  }
}