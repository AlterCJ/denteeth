package com.example.capstone_apps.data.remote.response

data class ResponseBenners(
	val data: List<DataItemBenners>,
	val error: Boolean,
	val message: String
)

data class DataItemBenners(
	val createdAt: String,
	val imageBenner: String,
	val id: Int,
	val updatedAt: String
)

