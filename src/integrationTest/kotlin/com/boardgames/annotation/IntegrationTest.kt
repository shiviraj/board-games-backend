package com.boardgames.annotation

import com.boardgames.BoardGamesApplication
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ActiveProfiles("test")
@SpringBootTest(
    classes = [BoardGamesApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ExtendWith(SpringExtension::class)
annotation class IntegrationTest
