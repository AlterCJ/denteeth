package com.example.capstone_apps.data.remote.response

data class ResponseArticle(
	val data: List<DataItem>,
	val error: Boolean,
	val message: String
)

data class DataItem(
	val image: String,
	val createdAt: String,
	val description: String,
	val id: Int,
	val detail: String,
	val title: String,
	val updatedAt: String
)

