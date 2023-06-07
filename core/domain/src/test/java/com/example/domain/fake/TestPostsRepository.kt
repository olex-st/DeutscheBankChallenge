package com.example.domain.fake

import com.example.domain.model.Comment
import com.example.domain.model.Post
import com.example.domain.model.User
import com.example.domain.repository.PostsBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestPostsRepository : PostsBaseRepository {

    override suspend fun login(userId: String): User {
        return User(9)
    }

    override suspend fun toggleFavorite(postId: String, isFavorite: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun getPostsFlow(userId: String): Flow<List<Post>> = flowOf(listOf(
        Post(1, 10, "test", "test").apply { favorite = true },
        Post(1, 11, "test", "test").apply { favorite = false }
    ))

    override suspend fun fetchPosts(userId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getComments(postId: String): List<Comment> {
        return listOf(
            Comment(2, 7, "comm1", "comm1@text.com", "comm1"),
            Comment(2, 8, "comm1", "comm1@text.xom", "comm1")
        )
    }

    override suspend fun getPost(postId: String): Post {
        return Post(1, 2, "postText1", "postText2")
    }
}
