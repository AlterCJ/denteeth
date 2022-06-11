package com.example.capstone_apps.data.remote.response

data class ResponseUploadImage(
  val data: DataImage,
  val error: Boolean,
  val message: String
)

data class DataImage(
  val src: String
)