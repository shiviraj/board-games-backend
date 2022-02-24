package com.boardgames.service

import com.boardgames.uno.domain.Secret
import com.boardgames.uno.exceptions.error_code.BlogError
import com.boardgames.uno.exceptions.exceptions.DataNotFound
import com.boardgames.uno.repository.SecretRepository
import com.boardgames.uno.security.crypto.Crypto
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class SecretService(private val secretRepository: SecretRepository, private val crypto: Crypto) {
    fun getClientId(): Mono<Secret> {
        return getSecret(SecretKeys.GITHUB_CLIENT_ID)
    }

    fun getClientSecret(): Mono<Secret> {
        return getSecret(SecretKeys.GITHUB_CLIENT_SECRET)
    }

    private fun getSecret(secretKey: SecretKeys): Mono<Secret> {
        return secretRepository.findByKey(secretKey)
            .map {
                it.decryptValue(crypto.decrypt(it.value))
            }
            .switchIfEmpty(Mono.error(DataNotFound(BlogError.BLOG604)))
    }
}

enum class SecretKeys {
    GITHUB_CLIENT_ID,
    GITHUB_CLIENT_SECRET
}
