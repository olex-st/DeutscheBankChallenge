package com.example.typicodebook.fake

import com.example.domain.model.User
import com.example.domain.usecase.base.BaseLoginUseCase

class TestLoginUseCaseCorrectUser : BaseLoginUseCase {
    override suspend operator fun invoke(userId: String): User {
        return User(9)
    }
}