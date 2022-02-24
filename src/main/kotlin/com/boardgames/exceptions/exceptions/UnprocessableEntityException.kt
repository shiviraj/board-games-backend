package com.boardgames.exceptions.exceptions

class UnprocessableEntityException(
    serviceError: ServiceError,
    details: Map<String, Any> = emptyMap()
) : BaseException(serviceError, details)
