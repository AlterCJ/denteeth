package com.example.capstone_apps.data.remote.response

data class ResponseHistory(
  val data: List<DataHistory>,
  val error: Boolean,
  val message: String
)

data class DataHistory(
  val createdAt: String,
  val id: Int,
  val userId: Int,
  val status: String,
  val updatedAt: String
)
