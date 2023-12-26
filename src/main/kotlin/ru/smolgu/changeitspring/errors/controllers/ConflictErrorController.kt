package ru.smolgu.changeitspring.errors.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.smolgu.changeitspring.dto.ErrorDto
import ru.smolgu.changeitspring.errors.common.ConflictError
import java.time.LocalDateTime

@RestControllerAdvice
class ConflictErrorController {


    @ExceptionHandler(ConflictError::class)
    fun onConflict(error: ConflictError): ResponseEntity<ErrorDto> = ResponseEntity<ErrorDto>(
        ErrorDto(message = error.message, timestamp = LocalDateTime.now()),
        HttpStatus.CONFLICT
    )

}