package com.boardgames.controller

import com.boardgames.uno.controller.view.AuthenticationResponse
import com.boardgames.uno.controller.view.AuthorView
import com.boardgames.uno.domain.Secret
import com.boardgames.uno.service.OauthService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/oauth")
class OauthController(
    val oauthService: OauthService
) {

    @GetMapping("/client-id")
    fun getClientId(): Mono<Secret> {
        return oauthService.getClientId()
    }

    @PostMapping("/sign-in/code")
    fun signIn(@RequestBody code: CodeRequest): Mono<AuthenticationResponse> {
        return oauthService.signIn(code)
            .map {
                AuthenticationResponse(it.first.getValue(), AuthorView.from(it.second))
            }
    }
}


data class CodeRequest(val code: String)
