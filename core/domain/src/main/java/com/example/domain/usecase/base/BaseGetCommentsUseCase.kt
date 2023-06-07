package com.example.domain.usecase.base

import com.example.domain.model.Text
//TODO: improve inheritance
interface BaseGetCommentsUseCase {

    suspend operator fun invoke(postId: String): List<Text>
}
