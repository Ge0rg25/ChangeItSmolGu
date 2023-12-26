package ru.smolgu.changeitspring.errors.common

class ConflictError(override val message: String): RuntimeException() {
}