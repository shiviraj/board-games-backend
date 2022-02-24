package com.boardgames.exceptions.exceptions

import com.boardgames.uno.exceptions.exceptions.BaseException
import com.boardgames.uno.exceptions.exceptions.ServiceError

class UnauthorizedException(
    serviceError: ServiceError,
    details: Map<String, Any> = emptyMap()
) : BaseException(serviceError, details)
