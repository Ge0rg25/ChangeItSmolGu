package ru.smolgu.changeitspring.models

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "petitions")
class PetitionEntity(

    var title: String,

    var description: String,

    val photoId: UUID,

    val authorId: UUID

) {

    @Id
    val id: UUID = UUID.randomUUID()

    @OneToMany(mappedBy = "petition")
    val likes : MutableList<LikeEntity> = mutableListOf()

    @OneToMany(mappedBy = "petition")
    val voices: MutableList<VoiceEntity> = mutableListOf()

}