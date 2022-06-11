package com.example.capstone_apps.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.capstone_apps.DataDummy
import com.example.capstone_apps.data.remote.response.ResponseProfile
import com.example.capstone_apps.helper.getOrAwaitValue
import com.example.capstone_apps.repository.ProfileRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ProfileViewModelTest {

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @Mock
  private lateinit var profileRepository: ProfileRepository
  private lateinit var profileViewModel: ProfileViewModel
  private var dataDummy = DataDummy.generateDummyProfile()

  @Before
  fun setup() {
    profileViewModel = ProfileViewModel(profileRepository)
  }

  @Test
  fun `when get profile`() {
    val expectedProfile = MutableLiveData<ResponseProfile>()
    expectedProfile.value = dataDummy
    `when`(profileViewModel.getResult()).thenReturn(expectedProfile)
    val actualProfile = profileViewModel.getResult().getOrAwaitValue()
    Mockito.verify(profileRepository).result()
    Assert.assertNotNull(actualProfile)
    Assert.assertEquals(expectedProfile.value, actualProfile)
  }

  @Test
  fun `when return error`() {
    val expectedProfile = MutableLiveData<String>()
    expectedProfile.value = "error networking"
    `when`(profileViewModel.getErrorMessage()).thenReturn(expectedProfile)
    val actualProfile = profileViewModel.getErrorMessage().getOrAwaitValue()
    Mockito.verify(profileRepository).error()
    Assert.assertNotNull(actualProfile)
    Assert.assertEquals(expectedProfile.value, actualProfile)
  }
}