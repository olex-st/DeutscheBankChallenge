package com.example.typicodebook.fake

import com.example.domain.usecase.base.BaseFetchPostsUseCase

class TestFetchPostsUseCase : BaseFetchPostsUseCase {
    override suspend operator fun invoke(userId: String) {
    }
}