package com.example.repository.fake

import com.example.database.LocalDataSource
import com.example.database.model.entity.PostEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestLocalDataSource : LocalDataSource {
    override suspend fun insertPosts(postEntities: List<PostEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateFavoritePost(postId: String, favorite: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun getPost(postId: String): PostEntity {
        return PostEntity(0,0,"","")
    }

    override fun getAllPostsFlow(userId: String): Flow<List<PostEntity>> {
        return flowOf(
            listOf(
            PostEntity(1,2,"",""),
            PostEntity(1,3,"","")
            )
        )
    }

}
