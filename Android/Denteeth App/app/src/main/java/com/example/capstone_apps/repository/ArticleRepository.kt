package com.example.capstone_apps.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone_apps.data.remote.response.ResponseArticle
import com.example.capstone_apps.data.remote.retrofit.ApiService
import com.example.capstone_apps.helper.ResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleRepository constructor(private val apiService: ApiService){
  private var _result = MediatorLiveData<ResultResponse<ResponseArticle>>()
  private var _resultResponse = MutableLiveData<ResponseArticle>()


  fun getAllArticle(): LiveData<ResultResponse<ResponseArticle>> {
    _result.value = ResultResponse.Loading
    val client = apiService.requestGetArticle()
    client.enqueue(object : Callback<ResponseArticle> {
      override fun onResponse(call: Call<ResponseArticle>, response: Response<ResponseArticle>) {
        if(response.isSuccessful){
          _resultResponse.value = response.body()
        }else{
          _result.value = ResultResponse.Error(response.message())
        }
      }

      override fun onFailure(call: Call<ResponseArticle>, t: Throwable) {
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
    private var instance : ArticleRepository? = null
    fun getInstance(apiService: ApiService): ArticleRepository =
      instance ?: synchronized(this) {
        instance ?: ArticleRepository(apiService)
      }.also { instance = it }
  }
}