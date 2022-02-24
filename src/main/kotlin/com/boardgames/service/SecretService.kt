package com.boardgames.service

import com.boardgames.domain.Secret
import com.boardgames.exceptions.error_code.BlogError
import com.boardgames.exceptions.exceptions.DataNotFound
import com.boardgames.repository.SecretRepository
import com.boardgames.security.crypto.Crypto
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
