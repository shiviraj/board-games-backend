package com.boardgames.exceptions.exceptions

class ForbiddenException(
    serviceError: ServiceError,
    details: Map<String, Any> = emptyMap()
) : BaseException(serviceError, details)
