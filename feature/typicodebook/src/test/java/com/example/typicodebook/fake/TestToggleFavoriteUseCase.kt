package com.example.typicodebook.fake

import com.example.domain.usecase.base.BaseToggleFavoriteUseCase

class TestToggleFavoriteUseCase : BaseToggleFavoriteUseCase {
    override suspend operator fun invoke(postId: String, isChecked: Boolean) {
    }
}