package ru.smolgu.changeitspring.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "signatures")
class SignatureEntity(
    val userId: UUID,
    @ManyToOne
    val petition: PetitionEntity
) {
    @Id
    val id: UUID = UUID.randomUUID()
}