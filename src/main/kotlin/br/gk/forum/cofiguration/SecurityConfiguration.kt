package br.gk.forum.cofiguration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfiguration(
    private val authProvider: AuthenticationProvider,
    private val jwtAuthFilter: JwtAuthenticationFilter
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf {
                disable()
            }
            cors {
                disable()
            }
            authorizeHttpRequests {
                authorize(HttpMethod.OPTIONS, "/**", permitAll)

                authorize("/api/v1/swagger/**", permitAll)
                authorize("/swagger**/**", permitAll)

                authorize("/error", permitAll)
                authorize("/auth/**", permitAll)

                authorize(HttpMethod.POST, "/user", permitAll)
                authorize("/user/**", hasRole("ADMIN"))

                authorize(matches = anyRequest, access = authenticated)
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            formLogin {
                disable()
            }
            httpBasic {
                disable()
            }
            headers {
                frameOptions {
                    disable()
                }
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(filter = jwtAuthFilter)
        }
        http.authenticationProvider(authProvider)
        return http.build()
    }
}