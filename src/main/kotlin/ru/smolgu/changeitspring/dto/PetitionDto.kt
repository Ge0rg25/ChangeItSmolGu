package ru.smolgu.changeitspring.dto

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length
import java.util.UUID

data class CreatePetitionRq(
    @NotBlank
    val title: String,

    @Length(min = 10, max = 100)
    @NotBlank
    val description: String,
    val photoId: UUID
)


data class PetitionDtoRs(
    val id: UUID,
    val title: String,
    val description: String,
    val photoId: UUID,
    val authorId: UUID,
    val voices: Int
)

