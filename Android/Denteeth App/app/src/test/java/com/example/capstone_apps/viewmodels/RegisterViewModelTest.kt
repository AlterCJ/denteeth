package com.example.capstone_apps.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.capstone_apps.DataDummy
import com.example.capstone_apps.data.remote.response.ResponseRegister
import com.example.capstone_apps.helper.getOrAwaitValue
import com.example.capstone_apps.repository.RegisterRepository
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @Mock
  private lateinit var registerRepository: RegisterRepository
  private lateinit var registerViewModel: RegisterViewModel
  private val dataDummy = DataDummy.generateDummyRegisterEntity()

  @Before
  fun setup() {
    registerViewModel = RegisterViewModel(registerRepository)
  }

  @Test
  fun `when success register`() {
    val expectedRegister = MutableLiveData<ResponseRegister>()
    expectedRegister.value = dataDummy
    `when`(registerViewModel.getResult()).thenReturn(expectedRegister)
    val actualRegister = registerViewModel.getResult().getOrAwaitValue()
    Mockito.verify(registerRepository).result()
    Assert.assertNotNull(actualRegister)
    Assert.assertEquals(expectedRegister.value, actualRegister)
  }

  @Test
  fun `when return error`() {
    val expectedRegister = MutableLiveData<String>()
    expectedRegister.value = "error networking"
    `when`(registerViewModel.getErrorMessage()).thenReturn(expectedRegister)
    val actualRegister = registerViewModel.getErrorMessage().getOrAwaitValue()
    Mockito.verify(registerRepository).error()
    Assert.assertNotNull(actualRegister)
    Assert.assertEquals(expectedRegister.value, actualRegister)
  }

  @Test
  fun `when loading networking`() {
    val expectedRegister = MutableLiveData<Boolean>()
    expectedRegister.value = true
    `when`(registerViewModel.getLoading()).thenReturn(expectedRegister)
    val actualRegister = registerViewModel.getLoading().getOrAwaitValue()
    Mockito.verify(registerRepository).loading()
    Assert.assertNotNull(actualRegister)
    Assert.assertEquals(expectedRegister.value, actualRegister)
  }
}