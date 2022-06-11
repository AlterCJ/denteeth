package com.example.capstone_apps.helper

import org.junit.Assert
import org.junit.Test

class PasswordValidationTest {
  @Test
  fun `when password last then eight`(){
    val passwordText = "Testing@1234"
    Assert.assertEquals("", PasswordValidation.validationPassword(passwordText))
  }

  @Test
  fun `when password uppercase character`() {
    val passwordText = "Testing@1234"
    Assert.assertEquals("", PasswordValidation.validationPassword(passwordText))
  }

  @Test
  fun `when password lowercase character`() {
    val passwordText = "Testing@1234"
    Assert.assertEquals("", PasswordValidation.validationPassword(passwordText))
  }

  @Test
  fun `when password dont have spesial char`() {
    val passwordText = "Testing@1234"
    Assert.assertEquals("", PasswordValidation.validationPassword(passwordText))
  }
}