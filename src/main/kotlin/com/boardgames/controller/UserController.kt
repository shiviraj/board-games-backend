package com.boardgames.controller

import com.boardgames.controller.view.AuthenticationResponse
import com.boardgames.controller.view.UserView
import com.boardgames.domain.User
import com.boardgames.service.UserService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/users")
class UserController(
    val userService: UserService
) {
    @GetMapping("/authorize")
    fun validateUser(user: User): Mono<UserView> {
        return Mono.just(UserView.from(user))
    }

    @PostMapping
    fun createUser(@RequestParam name: String): Mono<AuthenticationResponse> {
        return userService.createDummyUser(name).map {
            AuthenticationResponse.from(it.first, it.second)
        }
    }
}

