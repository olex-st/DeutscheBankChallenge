package com.example.domain.usecase.base

interface BaseToggleFavoriteUseCase {
    //TODO: improve inheritance
    suspend operator fun invoke(postId: String, isChecked: Boolean)
}
