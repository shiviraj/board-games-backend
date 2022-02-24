package com.boardgames.security.authorization

import com.boardgames.uno.domain.Role

@Target(AnnotationTarget.FUNCTION)
annotation class Authorization(
    val allowedRole: Role = Role.DUMMY,
)
