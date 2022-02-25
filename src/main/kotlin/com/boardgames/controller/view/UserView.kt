package com.boardgames.controller.view

import com.boardgames.domain.User

data class UserView(
    val username: String,
    val name: String,
    val userId: String,
    val profile: String,
) {
    companion object {
        fun from(user: User): UserView {
            return UserView(
                username = user.username,
                name = user.name,
                userId = user.userId,
                profile = user.profile,
            )
        }
    }
}
