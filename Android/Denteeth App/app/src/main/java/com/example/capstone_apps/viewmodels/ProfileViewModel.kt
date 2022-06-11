package com.example.capstone_apps.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.capstone_apps.data.remote.response.ResponseProfile
import com.example.capstone_apps.repository.ProfileRepository

class ProfileViewModel constructor(private val profileRepository: ProfileRepository): ViewModel() {
  fun getDetailProfile(token:String, id: Int) {
    profileRepository.requestDetailProfileUser(token, id)
  }

  fun getResult(): LiveData<ResponseProfile> =
    profileRepository.result()

  fun getErrorMessage(): LiveData<String> =
    profileRepository.error()

  fun getLoading(): LiveData<Boolean> =
    profileRepository.loading()

  fun updateProfile(token: String, image: String,  fullName: String, data: String, location: String, gender: String) {
    profileRepository.requestUpdateProfile(token, image,  fullName, data, location, gender)
  }

  fun createProfile(token: String, image: String,  fullName: String, data: String, location: String, gender: String) {
    profileRepository.requestCreateProfile(token, image,  fullName, data, location, gender)
  }

  fun errorFromInternet(): LiveData<String> =
    profileRepository.errorInternet()
}