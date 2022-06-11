package com.example.capstone_apps.data.remote.response

data class ResponseProfile(
	val data: Data,
	val error: Boolean,
	val message: String
)

data class Data(
	val imageprofile: String,
	val createdAt: String,
	val data: String,
	val gender: String,
	val location: String,
	val id: Int,
	val fullname: String,
	val email: String,
	val username: String,
	val updatedAt: String
)

