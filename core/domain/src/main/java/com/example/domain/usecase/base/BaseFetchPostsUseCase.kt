package com.example.domain.usecase.base
//TODO: improve inheritance
interface BaseFetchPostsUseCase {

    suspend operator fun invoke(userId: String)
}
