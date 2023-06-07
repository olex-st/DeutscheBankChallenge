package com.example.domain.usecase.base

import com.example.domain.model.User
//TODO: improve inheritance
interface BaseLoginUseCase {

    suspend operator fun invoke(userId: String): User
}
