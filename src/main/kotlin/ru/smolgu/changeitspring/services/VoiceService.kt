package ru.smolgu.changeitspring.services

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.smolgu.changeitspring.errors.common.ConflictError
import ru.smolgu.changeitspring.errors.common.NotFoundError
import ru.smolgu.changeitspring.mappers.toDto
import ru.smolgu.changeitspring.models.VoiceEntity
import ru.smolgu.changeitspring.repositories.PetitionRepository
import ru.smolgu.changeitspring.repositories.VoiceRepository
import java.util.UUID

@Service
class VoiceService(private val petitionRepository: PetitionRepository, private val voiceRepository: VoiceRepository) {


    @Transactional
    fun voice(id: UUID, jwt: Jwt) {
        petitionRepository.findByIdOrNull(id)?.let {
            it.voices.find { voice -> voice.userId == UUID.fromString(jwt.subject) }
                ?.let { throw ConflictError("User has already voted for this post") }
            it.voices.add(
                voiceRepository.save(
                    VoiceEntity(UUID.fromString(jwt.subject), it)
                )
            )
        } ?: throw NotFoundError("Petition not found")
    }


    @Transactional
    fun unvoice(id: UUID, jwt: Jwt) {
        petitionRepository.findByIdOrNull(id)?.let {
            it.voices.find { voice -> voice.userId == UUID.fromString(jwt.subject) }?.let { voice ->
                voiceRepository.delete(voice)
            } ?: throw NotFoundError("The user did not voice this post")
        } ?: throw NotFoundError("Petition not found")
    }


    fun getUserVoices(jwt: Jwt) = voiceRepository.findAllByUserId(UUID.fromString(jwt.subject)).map { it.petition.toDto() }
}