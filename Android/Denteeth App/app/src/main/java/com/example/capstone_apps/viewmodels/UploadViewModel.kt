package com.example.capstone_apps.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.capstone_apps.data.remote.response.ResponseUploadImage
import com.example.capstone_apps.repository.UploadImageRepository
import okhttp3.MultipartBody

class UploadImageViewModel(private val uploadImageRepository: UploadImageRepository): ViewModel() {
  fun uploadImage(token: String, imageUrl: MultipartBody.Part) {
    uploadImageRepository.requestUploadImage(token, imageUrl)
  }

  fun getResult(): LiveData<ResponseUploadImage> =
    uploadImageRepository.getResult()

  fun getError(): LiveData<String> =
    uploadImageRepository.getError()

  fun getLoading(): LiveData<Boolean> =
    uploadImageRepository.getLoading()
}