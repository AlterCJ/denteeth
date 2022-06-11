package com.example.capstone_apps.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.capstone_apps.data.remote.response.ResponseHistory
import com.example.capstone_apps.repository.HistoryRepository

class HistoryViewModel(private val historyRepository: HistoryRepository): ViewModel() {

  fun getLoading(): LiveData<Boolean> =
    historyRepository.getLoading()

  fun getError(): LiveData<String> =
    historyRepository.getError()

  fun getResult(): LiveData<ResponseHistory> =
    historyRepository.getResult()

  fun saveHistory(token: String, status: String) {
    historyRepository.requestPostHistory(token, status)
  }

  fun getALlHistory(token: String) {
    historyRepository.requestGetHistory(token)
  }
}