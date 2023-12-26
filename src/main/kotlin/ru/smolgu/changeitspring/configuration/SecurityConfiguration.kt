package ru.smolgu.changeitspring.configuration

import ru.smolgu.changeitspring.utils.KCRoleConverter


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration(private val kcRoleConverter: KCRoleConverter) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val jwtAuthenticationConverter = JwtAuthenticationConverter().apply {
            setJwtGrantedAuthoritiesConverter(kcRoleConverter)
        }

        return http
            .csrf { it.disable() }
            .cors { }
            .authorizeHttpRequests { authorize ->
                authorize
                    .anyRequest().permitAll()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { jwt ->
                    jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)
                }
            }
            .build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedHeaders = listOf("*")
            allowedOriginPatterns = listOf("*")
            allowCredentials = true
            allowedMethods = listOf("*")
        }
        val source = UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
        return source
    }
}