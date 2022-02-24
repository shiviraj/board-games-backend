package com.boardgames.service

import com.boardgames.uno.domain.Token
import com.boardgames.uno.domain.User
import com.boardgames.uno.repository.TokenRepository
import com.boardgames.uno.security.crypto.Crypto
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import org.springframework.security.core.userdetails.User as User1

@Service
class TokenService(
    val tokenRepository: TokenRepository,
    val crypto: Crypto
) : UserDetailsService {
    fun generateToken(user: User): Mono<Token> {
        return generateToken(Token.from(user))
    }

    fun generateToken(token: Token): Mono<Token> {
        return save(token.updateValue(crypto.encrypt(token.toString())))
            .logOnSuccess("Successfully generate token")
            .logOnError("failed to generate token")
    }

    fun logoutUser(token: String): Mono<Token> {
        return tokenRepository.deleteByValue(token)
    }

    override fun loadUserByUsername(token: String?): UserDetails? {
        if (token.isNullOrEmpty()) return null
        return User1("username", "password", emptyList())
    }

    fun extractToken(token: String) = tokenRepository.findByValue(token).map {
        it.updateValue(crypto.decrypt(it.getValue()))
    }.logOnSuccess("Successfully get token")
        .logOnError("Failed to get token")

    private fun save(token: Token) = tokenRepository.save(token)
}

