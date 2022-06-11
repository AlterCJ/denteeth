package com.example.capstone_apps.helper

import android.util.Patterns

object EmailValidation {
  fun validEmail(emailText: String): String {
    if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
      return Key.INVALID_EMAIL
    }
    return ""
  }
}