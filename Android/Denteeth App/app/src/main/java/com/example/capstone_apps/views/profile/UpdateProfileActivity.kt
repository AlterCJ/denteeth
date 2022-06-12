package com.example.capstone_apps.views.profile

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.capstone_apps.R
import com.example.capstone_apps.data.remote.response.ResponseProfile
import com.example.capstone_apps.databinding.ActivityUpdateProfileBinding
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

class UpdateProfileActivity : AppCompatActivity() {
  private lateinit var binding: ActivityUpdateProfileBinding
  private lateinit var preferenceViewModel: PreferenceViewModel
  private lateinit var profileViewModel: ProfileViewModel
  private lateinit var uploadImageViewModel: UploadImageViewModel
  private var imageFile: String? = null
  private var getFile: File? = null
  private lateinit var imageEmpty: String

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if(requestCode == Key.REQUEST_CODE_PERMISSION) {
      if(!allPermissionGranted()) {
        makeToast(Key.CAMERA_FAILED)
        finish()
      }
    }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
    setContentView(binding.root)
    supportActionBar?.hide()

    setFieldsEdit()

    setPermission()

    binding.buttonSave.setOnClickListener { submitFormUpdateProfile() }
    binding.imageButton.setOnClickListener { uploadFromGallery() }
  }

  private fun uploadFromGallery() {
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
      val myFile = uriToFile(selectedImg, this@UpdateProfileActivity)
      getFile = myFile
      uploadImage(selectedImg)
    }
  }

  private fun uploadImage(selectedImg: Uri) {
    uploadImageViewModel = obtainUploadImageViewModel(this)
    preferenceViewModel = obtainPreferenceViewModel(this)
    if(getFile != null) {
      val file = reduceFileImage(getFile as File)
      val uploadImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
      val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
        "image", file.name, uploadImageFile
      )
      uploadImageViewModel.uploadImage(preferenceViewModel.getToken().token.toString(),imageMultiPart)
      uploadImageViewModel.getResult().observe(this) {
        makeToast("success upload image")
        imageFile = it.data.src
        binding.imageView2.setImageURI(selectedImg)
      }
      uploadImageViewModel.getError().observe(this) {
        makeToast(it)
      }
      uploadImageViewModel.getLoading().observe(this) {}
    }else{
      makeToast("silahkan masukan gambar terlebih dahulu")
    }
  }


  private fun submitFormUpdateProfile() {
    preferenceViewModel = obtainPreferenceViewModel(this)
    profileViewModel = obtainProfileViewModel(this)
    if(imageFile != null) {
      profileViewModel.updateProfile(
        preferenceViewModel.getToken().token.toString(),
        imageFile!!,
        binding.usernameEditText.text.toString(),
        binding.dateEditText.text.toString(),
        binding.locationEditText.text.toString(),
        binding.genderEditText.text.toString()
      )
      profileViewModel.getResult().observe(this) {
        makeToast(it.message)
      }
      profileViewModel.getErrorMessage().observe(this) {
        makeToast(it)
      }
    }else{
      profileViewModel.updateProfile(
        preferenceViewModel.getToken().token.toString(),
        imageEmpty,
        binding.nameEditText.text.toString(),
        binding.dateEditText.text.toString(),
        binding.locationEditText.text.toString(),
        binding.genderEditText.text.toString()
      )
      profileViewModel.getResult().observe(this) {
        makeToast(it.message)
      }
      profileViewModel.getErrorMessage().observe(this) {
        makeToast(it)
      }
    }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private fun setFieldsEdit() {
    preferenceViewModel = obtainPreferenceViewModel(this)
    profileViewModel = obtainProfileViewModel(this)
    profileViewModel.getDetailProfile(preferenceViewModel.getToken().token.toString())
    profileViewModel.getResult().observe(this) {
      setProfile(it)
    }
    profileViewModel.getErrorMessage().observe(this) {
      makeToast(it)
    }
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private fun setProfile(result: ResponseProfile) {
    binding.usernameEditText.setText(result.data.username)
    binding.nameEditText.setText(result.data.fullname)
    binding.emailEditText.setText(result.data.email)
    binding.dateEditText.setText(result.data.data)
    binding.locationEditText.setText(result.data.location)
    binding.genderEditText.setText(result.data.gender)
    setImage(result.data.imageprofile)
    imageEmpty = result.data.imageprofile
  }

  private fun setImage(image: String) {
    binding.let {
      Glide.with(this)
        .load(Key.BASE_URL+image)
        .circleCrop()
        .into(it.imageView2)
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