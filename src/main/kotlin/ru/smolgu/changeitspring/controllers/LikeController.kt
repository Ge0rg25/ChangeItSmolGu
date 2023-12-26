package ru.smolgu.changeitspring.controllers

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.smolgu.changeitspring.services.LikeService
import java.util.UUID

@RestController
@RequestMapping("likes")
class LikeController(private val likeService: LikeService) {


    @PostMapping("{id}")
    fun like(@PathVariable id: UUID,  @AuthenticationPrincipal jwt: Jwt): Unit = likeService.like(id, jwt)

    @DeleteMapping("{id}")
    fun unlike(@PathVariable id: UUID,  @AuthenticationPrincipal jwt: Jwt): Unit = likeService.unlike(id, jwt)

    @GetMapping
    fun getUserLikes(@AuthenticationPrincipal jwt: Jwt) = likeService.getUserLikes(jwt)

}