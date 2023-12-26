package ru.smolgu.changeitspring.repositories


import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.smolgu.changeitspring.models.PetitionEntity
import java.util.*

@Repository
interface PetitionRepository: JpaRepository<PetitionEntity, UUID> {
    fun findByIdAndAuthorId(id: UUID, authorId: UUID): PetitionEntity?
}
