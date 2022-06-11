package com.example.capstone_apps.helper

import android.Manifest

class Key {
  companion object {
    const val TIME = 1500L
    const val INVALID_EMAIL = "Invalid Email address"
    const val EMAIL_EMPTY = "email empty"
    const val MINiMUN_CHAR = "Minimum 8 Character Password"
    const val UPPERCASE_CHAR = "Must Contain 1 Upper-case Character"
    const val LOWERCASE_CHAR =  "Must Contain 1 Lower-case Character"
    const val SPECIAL_CHAR = "Must Contain 1 Special Character (@#\$%^&+=)"

    // regex
    const val UPPERCASE_REGEX = ".*[A-Z].*"
    const val LOWERCASE_REGEX = ".*[a-z].*"
    const val SPECIAL_REGEX = ".*[@#\$%^&+=].*"

    //login
    const val SUCCESS_LOGIN = "success login"
    const val FAILED_LOGIN = "failed login"

    //register
    const val SUCCESS_REGISTER = "success register"

    //username
    const val NAME_EMPTY = "name empty"

    //base url
    const val BASE_URL = "http://192.168.1.69:3000/"

    //factory
    const val UNKNOWN_MODEL_CLASS = "unknown model class "

    //preferences
    const val PREFS_NAME = "login_pref"
    const val TOKEN = "token"
    const val IS_LOGIN = "is_login"

    //put data
    const val ID = "id"

    //bundle profile
    const val USERNAME = "username"
    const val FULL_NAME = "fullname"
    const val GENDER = "gender"
    const val LOCATION = "location"
    const val EMAIL = "email"
    const val DATE = "date"

    //token
    const val token  = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoyLCJ1c2VybmFtZSI6InRlc3RpbmciLCJlbWFpbCI6InRlc3QxMkBnbWFpbC5jb20ifSwiaWF0IjoxNjUzNDgxODEwLCJleHAiOjE2NTM0ODU0MTB9.sPNxwfhirTNv0vZva9s_iVUNC4kEU_O681hZDsQI4kA"

    //format
    const val FILENAME_FORMAT = "dd-MMM-yyyy"

    //permission kamera
    const val REQUEST_CODE_PERMISSION = 10
    val REQUIRED_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    const val CAMERA_FAILED = "tidak mendaptkan permission"

    //title
    const val CREATE_PROFILE = "Lengkapi profile"
  }
}