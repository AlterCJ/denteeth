package com.example.capstone_apps.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.capstone_apps.DataDummy
import com.example.capstone_apps.data.remote.response.ResponseLogin
import com.example.capstone_apps.helper.getOrAwaitValue
import com.example.capstone_apps.repository.LoginRepository
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
class LoginViewModelTest {
  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @Mock
  private lateinit var loginRepository: LoginRepository
  private lateinit var loginViewModel: LoginViewModel
  private val dataDummy = DataDummy.generateDummyLoginEntity()

  @Before
  fun setup() {
    loginViewModel = LoginViewModel(loginRepository)
  }

  @Test
  fun `when login and return success`() {
    val expectedLogin = MutableLiveData<ResponseLogin>()
    expectedLogin.value = dataDummy
    `when`(loginViewModel.getResult()).thenReturn(expectedLogin)
    val actualLogin = loginViewModel.getResult().getOrAwaitValue()
    Mockito.verify(loginRepository).result()
    Assert.assertNotNull(actualLogin)
    Assert.assertEquals(expectedLogin.value, actualLogin)
  }

  @Test
  fun `when login and network error`() {
    val expectedLogin = MutableLiveData<String>()
    expectedLogin.value = "network error"
    `when`(loginViewModel.getError()).thenReturn(expectedLogin)
    val actualLogin = loginViewModel.getError().getOrAwaitValue()
    Mockito.verify(loginRepository).error()
    Assert.assertNotNull(actualLogin)
    Assert.assertEquals(expectedLogin.value, actualLogin)
  }

  @Test
  fun `when loading networking`() {
    val expectedLogin = MutableLiveData<Boolean>()
    expectedLogin.value = true
    `when`(loginViewModel.getLoading()).thenReturn(expectedLogin)
    val actualLogin = loginViewModel.getLoading().getOrAwaitValue()
    Mockito.verify(loginRepository).loading()
    Assert.assertNotNull(actualLogin)
    Assert.assertEquals(expectedLogin.value, actualLogin)
  }
}






























































