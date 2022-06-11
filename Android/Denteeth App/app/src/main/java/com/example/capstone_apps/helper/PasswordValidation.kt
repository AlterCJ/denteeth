package com.example.capstone_apps.helper

object PasswordValidation {
  fun validationPassword(passwordText: String): String {
    if(passwordText.length < 8) {
      return Key.MINiMUN_CHAR
    }
    if(!passwordText.matches(Key.UPPERCASE_REGEX.toRegex())) {
      return Key.UPPERCASE_CHAR
    }
    if(!passwordText.matches(Key.LOWERCASE_REGEX.toRegex())) {
      return Key.LOWERCASE_CHAR
    }
    if(!passwordText.matches(Key.SPECIAL_REGEX.toRegex())) {
      return Key.SPECIAL_CHAR
    }

    return ""
  }
}