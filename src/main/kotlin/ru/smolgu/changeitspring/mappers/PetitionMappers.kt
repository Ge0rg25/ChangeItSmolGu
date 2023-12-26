package ru.smolgu.changeitspring.mappers

import ru.smolgu.changeitspring.dto.CreatePetitionRq
import ru.smolgu.changeitspring.dto.PetitionDtoRs
import ru.smolgu.changeitspring.models.PetitionEntity
import java.util.UUID


fun CreatePetitionRq.toEntity(authorId: String) = PetitionEntity(
    title = title,
    description = description,
    photoId = photoId,
    authorId = UUID.fromString(authorId)
)


fun PetitionEntity.toDto() = PetitionDtoRs(
    id = id,
    title = title,
    description = description,
    photoId = photoId,
    authorId = authorId,
    voices = voices.size
)