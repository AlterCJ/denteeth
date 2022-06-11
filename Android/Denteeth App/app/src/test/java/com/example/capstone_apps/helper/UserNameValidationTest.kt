package com.example.capstone_apps.helper

import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class UserNameValidationTest {
  @Test
  fun `when username is empty`() {
    val name = ""
    Assert.assertEquals("name empty", UserNameValidation.validUsername(name))
  }

  @Test
  fun `when username not empty`() {
    val name = "testing"
    Assert.assertEquals("", UserNameValidation.validUsername(name))
  }

  @Test
  fun `when name not empty`() {
    val name = "dimas"
    Assert.assertEquals("", UserNameValidation.validFullName(name))

  }

  @Test
  fun `when name empty`() {
    val name = ""
    Assert.assertEquals("name empty", UserNameValidation.validFullName(name))
  }
}