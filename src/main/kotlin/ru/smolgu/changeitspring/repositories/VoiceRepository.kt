package ru.smolgu.changeitspring.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.smolgu.changeitspring.models.VoiceEntity
import java.util.*

interface VoiceRepository : JpaRepository<VoiceEntity, UUID> {

    fun findAllByUserId(userId: UUID): List<VoiceEntity>
}