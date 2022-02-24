package com.boardgames.exceptions.exceptions

class UnauthorizedException(
    serviceError: ServiceError,
    details: Map<String, Any> = emptyMap()
) : BaseException(serviceError, details)
