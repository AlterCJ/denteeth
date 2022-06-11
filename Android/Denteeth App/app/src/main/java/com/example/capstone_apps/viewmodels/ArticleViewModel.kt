package com.example.capstone_apps.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.capstone_apps.data.remote.response.ResponseArticle
import com.example.capstone_apps.helper.ResultResponse
import com.example.capstone_apps.repository.ArticleRepository

class ArticleViewModel (private val articleRepository: ArticleRepository) : ViewModel() {

  fun getAllListArticle(): LiveData<ResultResponse<ResponseArticle>> =
    articleRepository.getAllArticle()

}