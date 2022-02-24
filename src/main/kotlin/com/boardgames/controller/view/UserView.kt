package com.boardgames.controller.view

import com.boardgames.domain.Author
import com.boardgames.domain.Role
import com.boardgames.domain.User
import java.time.LocalDateTime

data class UserView(
    val username: String,
    val name: String,
    val userId: String,
    val profile: String
) {
    companion object {
        fun from(user: User): UserView {
            return UserView(
                username = user.username,
                name = user.name,
                userId = user.userId,
                profile = user.profile
            )
        }
    }
}

data class AuthorView(
    val username: String,
    val name: String,
    val userId: String,
    val email: String?,
    val profile: String,
    val registeredAt: LocalDateTime,
    val role: Role,
) {
    companion object {
        fun from(author: Author): AuthorView {
            return AuthorView(
                username = author.username,
                name = author.name,
                userId = author.userId,
                email = author.email,
                profile = author.profile,
                registeredAt = author.registeredAt,
                role = author.role
            )
        }
    }
}
