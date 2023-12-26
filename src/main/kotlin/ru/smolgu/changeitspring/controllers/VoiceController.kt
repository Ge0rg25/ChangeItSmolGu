package ru.smolgu.changeitspring.controllers

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.smolgu.changeitspring.dto.PetitionDtoRs
import ru.smolgu.changeitspring.services.VoiceService
import java.util.UUID

@RestController
@RequestMapping("voices")
class VoiceController(private val voiceService: VoiceService) {

    @GetMapping
    fun getUserVoices(@AuthenticationPrincipal jwt: Jwt): List<PetitionDtoRs> = voiceService.getUserVoices(jwt)

    @PostMapping("{id}")
    fun voice(@PathVariable id: UUID, @AuthenticationPrincipal jwt: Jwt) {
        voiceService.voice(id, jwt)
    }


    @DeleteMapping("{id}")
    fun unvoice(@PathVariable id: UUID, @AuthenticationPrincipal jwt: Jwt) {
        voiceService.unvoice(id, jwt)
    }
}