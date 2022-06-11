package com.example.capstone_apps.helper

object UserNameValidation {
  fun validUsername(name: String): String {
    if(name.isEmpty()) {
      return Key.NAME_EMPTY
    }
    return ""
  }

  fun validFullName(name: String): String {
    if(name.isEmpty()) {
      return Key.NAME_EMPTY
    }
    return ""
  }
}