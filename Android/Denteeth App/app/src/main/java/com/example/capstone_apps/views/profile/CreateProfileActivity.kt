package com.example.capstone_apps.views.profile

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.capstone_apps.R
import com.example.capstone_apps.databinding.ActivityCreateProfileBinding
import com.example.capstone_apps.helper.Key
import com.example.capstone_apps.helper.reduceFileImage
import com.example.capstone_apps.helper.uriToFile
import com.example.capstone_apps.viewmodels.PreferenceViewModel
import com.example.capstone_apps.viewmodels.ProfileViewModel
import com.example.capstone_apps.viewmodels.UploadImageViewModel
import com.example.capstone_apps.viewmodels.ViewModelFactory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class CreateProfileActivity : AppCompatActivity() {
  private lateinit var binding: ActivityCreateProfileBinding
  private lateinit var preferencesViewModel: PreferenceViewModel
  private lateinit var profileViewModel: ProfileViewModel
  private lateinit var uploadImageViewModel: UploadImageViewModel
  private var imageFile: String? = null
  private var getFile: File? = null
  private var imageEmpty: String? = ""

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if(requestCode == Key.REQUEST_CODE_PERMISSION) {
      if(!allPermissionGranted()) {
        makeToast(Key.CAMERA_FAILED)
        finish()
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityCreateProfileBinding.inflate(layoutInflater)
    setContentView(binding.root)
    supportActionBar?.title = Key.CREATE_PROFILE

    setPermission()

    binding.imageButton.setOnClickListener { getImageFormGallery() }
    binding.buttonSave.setOnClickListener { createProfileUser() }
  }

  private fun getImageFormGallery() {
    val intent = Intent()
    intent.action = Intent.ACTION_GET_CONTENT
    intent.type = "image/*"
    val chooser = Intent.createChooser(intent, "choose a picture")
    launcherIntentGallery.launch(chooser)
  }

  private val launcherIntentGallery = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
  ){ result ->
    if(result.resultCode == RESULT_OK) {
      val selectedImg: Uri = result.data?.data as Uri
      val myFile = uriToFile(selectedImg, this@CreateProfileActivity)
      getFile = myFile
      uploadsImage(selectedImg)
    }
  }

  private fun uploadsImage(selectedImg: Uri){
    uploadImageViewModel = obtainUploadImageViewModel(this)
    preferencesViewModel = obtainPreferenceViewModel(this)
    if(getFile != null) {
      val file = reduceFileImage(getFile as File)
      val uploadImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
      val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
        "image", file.name, uploadImageFile
      )
      uploadImageViewModel.uploadImage(preferencesViewModel.getToken().token.toString(), imageMultiPart)
      uploadImageViewModel.getResult().observe(this) {
        makeToast("success upload image")
        imageFile = it.data.src
        binding.imageView2.setImageURI(selectedImg)
      }
      uploadImageViewModel.getError().observe(this) {
        makeToast(it)
      }
      uploadImageViewModel.getLoading().observe(this){}
    }else{
      makeToast("silahkan masukan gambar terlebih dahulu")
    }
  }

  private fun createProfileUser(){
    profileViewModel = obtainProfileViewModel(this)
    preferencesViewModel = obtainPreferenceViewModel(this)
    if(imageFile != null) {
      profileViewModel.createProfile(
        preferencesViewModel.getToken().token.toString(),
        imageFile!!,
        binding.nameEditText.text.toString(),
        binding.dateEditText.text.toString(),
        binding.locationEditText.text.toString(),
        binding.genderEditText.text.toString()
      )
      profileViewModel.getResult().observe(this){
        makeToast("success create profile")
      }
      profileViewModel.getErrorMessage().observe(this) {
        makeToast("failed create profile")
      }
      profileViewModel.getLoading().observe(this){}
    }else{
      profileViewModel.createProfile(
        preferencesViewModel.getToken().token.toString(),
        "",
        binding.nameEditText.text.toString(),
        binding.dateEditText.text.toString(),
        binding.locationEditText.text.toString(),
        binding.genderEditText.text.toString()
      )
      profileViewModel.getResult().observe(this){
        makeToast("success create profile")
      }
      profileViewModel.getErrorMessage().observe(this) {
        makeToast("failed create profile")
      }
      profileViewModel.getLoading().observe(this){}
    }
  }


  private fun obtainUploadImageViewModel(activity: AppCompatActivity): UploadImageViewModel {
    val factory = ViewModelFactory.getInstance(activity)
    return ViewModelProvider(activity, factory).get(UploadImageViewModel::class.java)
  }

  private fun obtainPreferenceViewModel(activity: AppCompatActivity): PreferenceViewModel {
    val factory = ViewModelFactory.getInstance(activity)
    return ViewModelProvider(activity, factory).get(PreferenceViewModel::class.java)
  }

  private fun obtainProfileViewModel(activity: AppCompatActivity): ProfileViewModel {
    val factory = ViewModelFactory.getInstance(activity)
    return ViewModelProvider(activity, factory).get(ProfileViewModel::class.java)
  }

  private fun allPermissionGranted() =
    Key.REQUIRED_PERMISSION.all {
      ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

  private fun setPermission() {
    if(!allPermissionGranted()) {
      ActivityCompat.requestPermissions(
        this, Key.REQUIRED_PERMISSION, Key.REQUEST_CODE_PERMISSION
      )
    }
  }

  private fun makeToast(message: String) {
    Toast.makeText(this,
      message,
      Toast.LENGTH_SHORT).show()
  }
}