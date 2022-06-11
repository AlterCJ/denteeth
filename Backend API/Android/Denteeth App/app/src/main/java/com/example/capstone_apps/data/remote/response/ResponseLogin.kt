package com.example.capstone_apps.data.remote.response

data class ResponseLogin(
  val error: Boolean,
  val message: String,
  val token: String
)