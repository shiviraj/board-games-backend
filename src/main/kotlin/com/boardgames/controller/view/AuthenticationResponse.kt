package com.boardgames.controller.view

import com.boardgames.domain.Token
import com.boardgames.domain.User

class AuthenticationResponse(val token: String, val user: UserView) {
    companion object {
        fun from(token: Token, user: User): AuthenticationResponse {
            return AuthenticationResponse(token.getValue(), UserView.from(user))
        }
    }
}

