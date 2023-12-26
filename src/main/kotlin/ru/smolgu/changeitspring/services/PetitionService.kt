package ru.smolgu.changeitspring.services

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.smolgu.changeitspring.dto.CreatePetitionRq
import ru.smolgu.changeitspring.dto.PetitionDtoRs
import ru.smolgu.changeitspring.errors.common.NotFoundError
import ru.smolgu.changeitspring.mappers.toDto
import ru.smolgu.changeitspring.mappers.toEntity
import ru.smolgu.changeitspring.repositories.PetitionRepository
import java.util.*

private const val DEFAULT_PAGE = 0
private const val DEFAULT_PAGE_SIZE = 10

@Service
class PetitionService(private val petitionRepository: PetitionRepository) {


    @Transactional
    fun create(jwt: Jwt, dto: CreatePetitionRq): Unit {
        petitionRepository.save(
            dto.toEntity(jwt.subject)
        )
    }

    @Transactional
    fun delete(id: UUID, jwt: Jwt) {
        if (SecurityContextHolder.getContext().authentication.authorities.any { it.authority == "ROLE_admin" }) {
            println("user is admin")
            petitionRepository.findByIdOrNull(id)?.let {
                petitionRepository.delete(it)
                println("petition deleted by admin")
            } ?: throw NotFoundError("Petition not found")
            return
        }

        petitionRepository.findByIdAndAuthorId(id, UUID.fromString(jwt.subject))?.let {
            petitionRepository.delete(it)
            println("petition deleted by user")
        } ?: throw NotFoundError("Petition not found")
    }


    fun getAll(
        page: Int?,
        size: Int?
    ): Page<PetitionDtoRs> =
        petitionRepository.findAll(
            PageRequest.of(
                page ?: DEFAULT_PAGE,
                size ?: DEFAULT_PAGE_SIZE
            )
        ).map { it.toDto() }
}