package com.example.capstone_apps.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone_apps.data.remote.response.ResponseBenners
import com.example.capstone_apps.data.remote.retrofit.ApiService
import com.example.capstone_apps.helper.ResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BennersRepository constructor(private val apiService: ApiService) {
  private var _result = MediatorLiveData<ResultResponse<ResponseBenners>>()
  private var _resultResponse = MutableLiveData<ResponseBenners>()

  fun requestGetBenners() : LiveData<ResultResponse<ResponseBenners>> {
    _result.value = ResultResponse.Loading
    val client = apiService.requestGetBenners()
    client.enqueue(object: Callback<ResponseBenners> {
      override fun onResponse(call: Call<ResponseBenners>, response: Response<ResponseBenners>) {
        if(response.isSuccessful) {
          _resultResponse.value = response.body()
        }else {
          _result.value = ResultResponse.Error(response.message())
        }
      }

      override fun onFailure(call: Call<ResponseBenners>, t: Throwable) {
        _result.value = ResultResponse.Error(t.message.toString())
      }
    })
    _result.removeSource(_resultResponse)
    _result.addSource(_resultResponse) {
      _result.value = ResultResponse.Success(it)
    }
    return _result
  }

  companion object {
    @Volatile
    private var instance : BennersRepository? = null
    fun getInstance(apiService: ApiService): BennersRepository =
      instance ?: synchronized(this) {
        instance ?: BennersRepository(apiService)
      }.also { instance = it }
  }
}
































































