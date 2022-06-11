package com.example.capstone_apps.helper

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateFormatter {
  fun formatDate(currentDateString: String, targetTImeZone: String): String {
    val instant = Instant.parse(currentDateString)
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy | HH:mm")
      .withZone(ZoneId.of(targetTImeZone))
    return formatter.format(instant)
  }
}