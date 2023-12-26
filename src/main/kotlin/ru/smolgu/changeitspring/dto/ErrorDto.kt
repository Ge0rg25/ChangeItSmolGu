package ru.smolgu.changeitspring.dto

import java.time.LocalDateTime

data class ErrorDto(
    val message: String,
    val timestamp: LocalDateTime
)