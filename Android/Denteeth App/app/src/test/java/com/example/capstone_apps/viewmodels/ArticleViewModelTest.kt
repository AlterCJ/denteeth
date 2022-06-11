package com.example.capstone_apps.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.capstone_apps.DataDummy
import com.example.capstone_apps.data.remote.response.ResponseArticle
import com.example.capstone_apps.helper.ResultResponse
import com.example.capstone_apps.helper.getOrAwaitValue
import com.example.capstone_apps.repository.ArticleRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ArticleViewModelTest {

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @Mock
  private lateinit var articleRepository: ArticleRepository
  private lateinit var articleViewModel: ArticleViewModel
  private var dataDummy = DataDummy.generateDummyArticle()

  @Before
  fun setup() {
    articleViewModel = ArticleViewModel(articleRepository)
  }

  @Test
  fun `when get list article success`() {
    val expectedArticle = MutableLiveData<ResultResponse<ResponseArticle>>()
    expectedArticle.value = ResultResponse.Success(dataDummy)
    `when`(articleViewModel.getAllListArticle()).thenReturn(expectedArticle)
    val actualArticle = articleViewModel.getAllListArticle().getOrAwaitValue()
    Mockito.verify(articleRepository).getAllArticle()
    Assert.assertNotNull(actualArticle)
    Assert.assertTrue(actualArticle is ResultResponse.Success)
    Assert.assertEquals(dataDummy.message, (actualArticle as ResultResponse.Success).data.message)
  }

  @Test
  fun `when error networking `() {
    val expectedArticle = MutableLiveData<ResultResponse<ResponseArticle>>()
    expectedArticle.value = ResultResponse.Error("networking error")
    `when`(articleViewModel.getAllListArticle()).thenReturn(expectedArticle)
    val actualArticle = articleViewModel.getAllListArticle().getOrAwaitValue()
    Mockito.verify(articleRepository).getAllArticle()
    Assert.assertNotNull(actualArticle)
    Assert.assertTrue(actualArticle is ResultResponse.Error)
    Assert.assertEquals("networking error", (actualArticle as ResultResponse.Error).error)
  }

  @Test
  fun `when loading `() {
    val expectedArticle = MutableLiveData<ResultResponse<ResponseArticle>>()
    expectedArticle.value = ResultResponse.Loading
    `when`(articleViewModel.getAllListArticle()).thenReturn(expectedArticle)
    val actualArticle = articleViewModel.getAllListArticle().getOrAwaitValue()
    Mockito.verify(articleRepository).getAllArticle()
    Assert.assertNotNull(actualArticle)
    Assert.assertTrue(actualArticle is ResultResponse.Loading)
  }
}