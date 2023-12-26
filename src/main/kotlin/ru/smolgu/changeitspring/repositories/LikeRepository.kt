package ru.smolgu.changeitspring.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.smolgu.changeitspring.models.LikeEntity
import java.util.*

@Repository
interface LikeRepository : JpaRepository<LikeEntity, UUID> {

    fun findAllByUserId(userId: UUID): List<LikeEntity>

}