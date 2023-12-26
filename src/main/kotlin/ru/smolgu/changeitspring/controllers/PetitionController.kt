package ru.smolgu.changeitspring.controllers

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.smolgu.changeitspring.dto.CreatePetitionRq
import ru.smolgu.changeitspring.services.PetitionService
import java.util.*

@RestController
@RequestMapping("petitions")
@Validated
class PetitionController(private val petitionService: PetitionService) {

    @PostMapping
    fun create(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestBody dto: CreatePetitionRq
    ): Unit =
        petitionService.create(jwt, dto)

    @DeleteMapping("{id}")
    fun delete(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable @NotNull id: UUID
    ) = petitionService.delete(id, jwt)

    @GetMapping
    fun getAll(
        @RequestParam
        page: Int?,
        @RequestParam("page-size")
        pageSize: Int?
    ) = petitionService.getAll(page, pageSize)

}