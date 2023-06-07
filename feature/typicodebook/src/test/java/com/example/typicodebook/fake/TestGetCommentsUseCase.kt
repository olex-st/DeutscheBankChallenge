package com.example.typicodebook.fake

import com.example.domain.model.Comment
import com.example.domain.model.Post
import com.example.domain.model.Text
import com.example.domain.usecase.base.BaseGetCommentsUseCase

class TestGetCommentsUseCase(
) : BaseGetCommentsUseCase {
    override suspend operator fun invoke(postId: String): List<Text> {
        return listOf<Text>(
            Post(1, 2, "postText1", "postText2"),
            Comment(2, 7, "comm1", "comm1@text.com", "comm1"),
            Comment(2, 8, "comm1", "comm1@text.xom", "comm1")
        )

    }
}