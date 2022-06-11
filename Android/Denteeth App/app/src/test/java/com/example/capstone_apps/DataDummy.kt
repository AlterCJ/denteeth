package com.example.capstone_apps

import com.example.capstone_apps.data.remote.response.*

object DataDummy {
  fun generateDummyLoginEntity(): ResponseLogin {
    val error = false
    val message = "success login"
    val token = "adsadasdasdasddasdasdad"
    return ResponseLogin(error, message, token)
  }

  fun generateDummyRegisterEntity(): ResponseRegister {
    val error = false
    val message = "user created"
    return ResponseRegister(error, message)
  }

  fun generateDummyArticle(): ResponseArticle {
    val error = false
    val message = "ok"
    val listData = ArrayList<DataItem>()
    for (i in 0..10) {
      val data = DataItem(
        "testing.jpg",
        "2022-02-22T22:22:22Z",
        "testing data dummy",
        i,
        "testing",
        "test",
        "2022-02-22T22:22:22Z"
      )
      listData.add(data)
    }
    return ResponseArticle(listData,error, message)
  }

  fun generateDummyProfile(): ResponseProfile {
    val error = false
    val message = "ok"
    val data = Data(
      "testing.jpg",
      "2022-02-22T22:22:22Z",
      "2022-02-22T22:22:22Z",
      "pria",
      "surabaya",
      1,
      "testing",
      "test@gmail.com",
      "testing",
      "2022-02-22T22:22:22Z"
    )
    return  ResponseProfile(data, error, message)
  }
}