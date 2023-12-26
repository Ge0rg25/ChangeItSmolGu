package ru.smolgu.changeitspring.services

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.smolgu.changeitspring.errors.common.ConflictError
import ru.smolgu.changeitspring.errors.common.NotFoundError
import ru.smolgu.changeitspring.mappers.toDto
import ru.smolgu.changeitspring.models.LikeEntity
import ru.smolgu.changeitspring.repositories.LikeRepository
import ru.smolgu.changeitspring.repositories.PetitionRepository
import java.util.*

@Service
class LikeService(
    private val petitionRepository: PetitionRepository,
    private val likeRepository: LikeRepository
) {

    @Transactional
    fun like(id: UUID, jwt: Jwt) {
        petitionRepository.findByIdOrNull(id)?.let {
            it.likes.find { like -> (like.userId == UUID.fromString(jwt.subject) && like.petition == it)  }?.let {
                throw ConflictError("User already liked this post")
            }
            it.likes.add(
                likeRepository.save(
                    LikeEntity(UUID.fromString(jwt.subject), it)
                )
            )
        } ?: throw NotFoundError("Petition not found")
    }


    @Transactional
    fun unlike(id: UUID, jwt: Jwt) {
        petitionRepository.findByIdOrNull(id)?.let {
            it.likes.find { like -> like.userId == UUID.fromString(jwt.subject) }?.let { like ->
                likeRepository.delete(like)
            } ?: throw NotFoundError("The user did not like this post")
        } ?: throw NotFoundError("Petition not found")
    }

    fun getUserLikes(jwt: Jwt) = likeRepository.findAllByUserId(UUID.fromString(jwt.subject)).map { it.petition.toDto() }
}