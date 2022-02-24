package com.boardgames.service

import com.boardgames.controller.CodeRequest
import com.boardgames.domain.Secret
import com.boardgames.domain.Token
import com.boardgames.domain.User
import com.boardgames.gateway.GithubGateway
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class OauthService(
    val userService: UserService,
    val secretService: SecretService,
    val githubGateway: GithubGateway,
    val tokenService: TokenService
) {
    fun getClientId(): Mono<Secret> {
        return secretService.getClientId()
            .logOnSuccess("Successfully get client Id")
            .logOnError("Failed to get client id")
    }

    fun signIn(code: CodeRequest): Mono<Pair<Token, User>> {
        return githubGateway.getAccessTokens(code.code).flatMap { accessTokenResponse ->
            githubGateway.getUserProfile(accessTokenResponse)
                .map { Pair(it, accessTokenResponse) }
        }.flatMap {
            userService.signInUserFromOauth(it)
        }.flatMap { user ->
            tokenService.generateToken(user).map {
                Pair(it, user)
            }
        }
    }
}
