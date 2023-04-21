package com.example.vofaz.common.model

import java.util.UUID

object Database {

    val userAuth = hashSetOf<UserAuth>()

    var sessionAuth: UserAuth? = null

    init {
        userAuth.add(UserAuth(UUID.randomUUID().toString(), "Arthur Lima", "a@a.com", "123456789"))
        userAuth.add(UserAuth(UUID.randomUUID().toString(), "José Araujo", "b@b.com", "987654321"))
    }

}