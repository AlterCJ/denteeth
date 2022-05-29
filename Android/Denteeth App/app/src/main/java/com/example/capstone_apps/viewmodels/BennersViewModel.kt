package com.example.capstone_apps.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.capstone_apps.data.remote.response.ResponseBenners
import com.example.capstone_apps.helper.ResultResponse
import com.example.capstone_apps.repository.BennersRepository

class BennersViewModel (private val bennersRepository: BennersRepository): ViewModel() {
  fun getAllBenners(): LiveData<ResultResponse<ResponseBenners>> =
    bennersRepository.requestGetBenners()
}