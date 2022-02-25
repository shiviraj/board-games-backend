package com.boardgames.service

import com.boardgames.domain.*
import com.boardgames.gateway.GithubGateway
import com.boardgames.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService(
    val userRepository: UserRepository,
    val idGeneratorService: IdGeneratorService,
    val tokenService: TokenService,
    val githubGateway: GithubGateway
) {

    fun signInUserFromOauth(githubUser: Pair<GithubUser, AccessTokenResponse>): Mono<User> {
        return userRepository.findByUsername(githubUser.first.username)
            .switchIfEmpty(registerNewUser(githubUser))
    }

    private fun registerNewUser(githubUser: Pair<GithubUser, AccessTokenResponse>): Mono<User> {
        val user = githubUser.first
        return githubGateway.getUserEmail(githubUser.second)
            .doOnError {
                Mono.just(GithubUserEmail(email = ""))
            }
            .flatMap { githubUserEmail ->
                idGeneratorService.generateId(IdType.UserId)
                    .flatMap { userId ->
                        save(
                            User(
                                uniqueId = user.id.toString(),
                                name = user.name ?: user.username,
                                userId = userId,
                                email = githubUserEmail.email,
                                emailVerified = githubUserEmail.verified,
                                profile = user.profile,
                                location = user.location,
                                source = user.source,
                                username = user.username
                            )
                        )
                    }
            }
            .logOnSuccess("Successfully registered a new user", mapOf("user" to user.username))
            .logOnError("Failed to register a new user", mapOf("user" to user.username))
    }

    private fun save(user: User) = userRepository.save(user)
    fun extractUser(token: String): Mono<User> {
        return tokenService.extractToken(token)
            .flatMap {
                if (it.userType.isDummy()) {
                    Mono.just(User.createDummy(it.userId, it.name))
                } else {
                    userRepository.findByUserId(it.userId)
                }
            }.logOnSuccess("Successfully fetched user from token")
            .logOnError("Failed to fetch user from token")
    }

    fun createDummyUser(name: String): Mono<Pair<Token, User>> {
        return idGeneratorService.generateId(IdType.DummyUserId).flatMap { userId ->
            val user = User.createDummy(userId, name)
            tokenService.generateToken(user).map { Pair(it, user) }
        }
    }
}
