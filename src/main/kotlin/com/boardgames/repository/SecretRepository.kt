package com.boardgames.repository

import com.boardgames.domain.Secret
import com.boardgames.service.SecretKeys
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface SecretRepository : ReactiveCrudRepository<Secret, String> {
    fun findByKey(key: SecretKeys): Mono<Secret>
}
