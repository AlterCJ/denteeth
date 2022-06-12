package com.example.capstone_apps.views.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.capstone_apps.R
import com.example.capstone_apps.data.remote.response.ResponseProfile
import com.example.capstone_apps.databinding.FragmentProfileBinding
import com.example.capstone_apps.helper.Key
import com.example.capstone_apps.viewmodels.PreferenceViewModel
import com.example.capstone_apps.viewmodels.ProfileViewModel
import com.example.capstone_apps.viewmodels.ViewModelFactory
import com.example.capstone_apps.views.authentication.LoginActivity
import com.example.capstone_apps.views.profile.CreateProfileActivity
import com.example.capstone_apps.views.profile.UpdateProfileActivity


class ProfileFragment : Fragment() , View.OnClickListener{

  private lateinit var binding: FragmentProfileBinding
  private lateinit var preferenceViewModel: PreferenceViewModel
  private lateinit var profileViewModel: ProfileViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.fragment_profile, container, false)
  }

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = FragmentProfileBinding.bind(view)
    getDetailProfile()

    preferenceViewModel = obtainPreferenceViewModel(requireActivity())
    profileViewModel = obtainProfileViewModel(requireActivity())


    binding.buttonEdit.setOnClickListener(this)
    binding.buttonLogout.setOnClickListener(this)
  }
  override fun onClick(view: View) {
    when (view.id) {
      R.id.button_logout -> {
        preferenceViewModel = obtainPreferenceViewModel(requireActivity())
        preferenceViewModel.clearPreferences()
        startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finish()
      }
      R.id.button_edit -> {
        val intent = Intent(activity, UpdateProfileActivity::class.java)
        startActivity(intent)
      }
    }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private fun getDetailProfile() {
    preferenceViewModel = obtainPreferenceViewModel(requireActivity())
    profileViewModel = obtainProfileViewModel(requireActivity())
    profileViewModel.getDetailProfile(preferenceViewModel.getToken().token.toString())
    profileViewModel.getResult().observe(requireActivity()) {
      setProfile(it)
    }
    profileViewModel.getErrorMessage().observe(requireActivity()) {
      Toast.makeText(activity, "lengkapi profile terlebih dahulu", Toast.LENGTH_SHORT).show()
      startActivity(Intent(activity, CreateProfileActivity::class.java))
    }
    profileViewModel.errorFromInternet().observe(requireActivity()) {
      Toast.makeText(activity, "error from internet", Toast.LENGTH_SHORT).show()
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
    if(activity != null) {
      binding.let {
        Glide.with(this)
          .load(Key.BASE_URL+image)
          .circleCrop()
          .into(it.imageProfile)
      }
    }
  }

  private fun obtainProfileViewModel(requireActivity: FragmentActivity): ProfileViewModel {
    val factory = ViewModelFactory.getInstance(requireActivity)
    return ViewModelProvider(requireActivity, factory).get(ProfileViewModel::class.java)
  }

  private fun obtainPreferenceViewModel(requireActivity: FragmentActivity): PreferenceViewModel {
    val factory = ViewModelFactory.getInstance(requireActivity)
    return ViewModelProvider(requireActivity, factory).get(PreferenceViewModel::class.java)
  }
}