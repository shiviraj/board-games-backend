package com.boardgames.controller

import com.boardgames.uno.domain.Secret
import com.boardgames.uno.service.OauthService
import com.boardgames.uno.service.SecretKeys
import com.boardgames.uno.testUtils.assertNextWith
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono

class OauthControllerTest {
    private val oauthService = mockk<OauthService>()
    private val oauthController = OauthController(oauthService)

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should get clientId`() {
        val secret = Secret(SecretKeys.GITHUB_CLIENT_ID, "clientId")
        every { oauthService.getClientId() } returns Mono.just(secret)
        assertNextWith(oauthController.getClientId()) {
            it shouldBe secret
            verify(exactly = 1) {
                oauthService.getClientId()
            }
        }
    }

    @Test
    fun `should sign in user by oauth`() {
//        val user = User(username = "username")
//        every { oauthService.signIn(any()) } returns Mono.just(Pair("loggedIn", user))
//        val code = CodeRequest("code")
//        assertNextWith(oauthController.signIn(code)) {
//            it shouldBe AuthenticationResponse("loggedIn", UserView.from(user))
//            verify(exactly = 1) {
//                oauthService.signIn(code)
//            }
//        }
    }
}
