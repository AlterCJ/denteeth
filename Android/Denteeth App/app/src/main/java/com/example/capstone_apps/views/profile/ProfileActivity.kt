package com.example.capstone_apps.views.profile

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.capstone_apps.R
import com.example.capstone_apps.data.remote.response.ResponseProfile
import com.example.capstone_apps.databinding.ActivityProfileBinding
import com.example.capstone_apps.helper.Key
import com.example.capstone_apps.viewmodels.PreferenceViewModel
import com.example.capstone_apps.viewmodels.ProfileViewModel
import com.example.capstone_apps.viewmodels.ViewModelFactory
import com.example.capstone_apps.views.authentication.LoginActivity

class ProfileActivity : AppCompatActivity(), View.OnClickListener {
  private lateinit var binding: ActivityProfileBinding
  private lateinit var preferenceViewModel: PreferenceViewModel
  private lateinit var profileViewModel: ProfileViewModel

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityProfileBinding.inflate(layoutInflater)
    setContentView(binding.root)
    supportActionBar?.hide()


    getDetailProfile()

    binding.buttonEdit.setOnClickListener(this)
    binding.buttonLogout.setOnClickListener(this)
  }

  override fun onClick(view: View) {
    when (view.id) {
      R.id.button_logout -> {
        preferenceViewModel = obtainPreferenceViewModel(this)
        preferenceViewModel.clearPreferences()
        startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
        finish()
      }
      R.id.button_edit -> {
        val intent = Intent(this@ProfileActivity, UpdateProfileActivity::class.java)
        startActivity(intent)
      }
    }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private fun getDetailProfile() {
    preferenceViewModel = obtainPreferenceViewModel(this)
    profileViewModel = obtainProfileViewModel(this)
    profileViewModel.getDetailProfile(preferenceViewModel.getToken().token.toString())
    profileViewModel.getResult().observe(this) {
      setProfile(it)
    }
    profileViewModel.getErrorMessage().observe(this) {
      Toast.makeText(this, "failed get data", Toast.LENGTH_SHORT).show()
      startActivity(Intent(this@ProfileActivity, CreateProfileActivity::class.java))
    }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private fun setProfile(response: ResponseProfile) {
    binding.usernameProfile.text = response.data.username
    binding.nameProfile.text = response.data.fullname
    binding.emailProfile.text = response.data.email
    binding.dateProfile.text = response.data.data
    binding.location.text = response.data.location
    binding.gender.text = response.data.gender
    setImage(response.data.imageprofile)
  }

  private fun setImage(image: String) {
    binding.let {
      Glide.with(this)
        .load(Key.BASE_URL+image)
        .circleCrop()
        .into(it.imageProfile)
    }
  }

  private fun obtainProfileViewModel(activity: AppCompatActivity): ProfileViewModel {
    val factory = ViewModelFactory.getInstance(activity)
    return ViewModelProvider(activity, factory).get(ProfileViewModel::class.java)
  }

  private fun obtainPreferenceViewModel(activity: AppCompatActivity): PreferenceViewModel {
    val factory = ViewModelFactory.getInstance(activity)
    return ViewModelProvider(activity, factory).get(PreferenceViewModel::class.java)
  }
}