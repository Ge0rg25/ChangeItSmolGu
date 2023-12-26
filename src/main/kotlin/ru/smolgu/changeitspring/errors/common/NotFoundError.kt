package ru.smolgu.changeitspring.errors.common

class NotFoundError(override val message: String) : RuntimeException()