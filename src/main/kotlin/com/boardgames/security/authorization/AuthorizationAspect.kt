package com.boardgames.security.authorization

import com.boardgames.uno.domain.User
import com.boardgames.uno.exceptions.error_code.BlogError
import com.boardgames.uno.exceptions.exceptions.UnauthorizedException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class AuthorizationAspect {

    @Around("@annotation(authorization)")
    fun performAction(proceedingJoinPoint: ProceedingJoinPoint, authorization: Authorization): Any {
        val args = proceedingJoinPoint.args.toList()
        if (args.isEmpty()) throw getUnauthorizedError()
        val user = args.last() as User
        if (user.role.isJunior(authorization.allowedRole)) {
            throw getUnauthorizedError()
        }
        return proceedingJoinPoint.proceed()
    }

    private fun getUnauthorizedError(): UnauthorizedException {
        return UnauthorizedException(BlogError.BLOG606)
    }
}


