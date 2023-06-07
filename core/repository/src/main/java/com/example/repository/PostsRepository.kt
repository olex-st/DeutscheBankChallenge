package com.example.repository

import com.example.api.RemoteDataSource
import com.example.database.LocalDataSource
import com.example.domain.UserNotExistException
import com.example.domain.model.Comment
import com.example.domain.model.Post
import com.example.domain.model.User
import com.example.domain.repository.PostsBaseRepository
import com.example.repository.mapper.BaseMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val mapper: BaseMapper,
    private val localDataSource: LocalDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : PostsBaseRepository {

    override suspend fun login(userId: String): User {
        return withContext(dispatcher) {
            try {
                mapper.mapUserResponseToUser(remoteDataSource.login(userId))
            } catch (e: Exception) {
                throw UserNotExistException()
            }
        }
    }

    override suspend fun toggleFavorite(postId: String, isFavorite: Boolean) {
        return withContext(dispatcher) {
            localDataSource.updateFavoritePost(postId, isFavorite)
        }
    }

    override suspend fun getPostsFlow(userId: String): Flow<List<Post>> {
        return withContext(dispatcher) {
            localDataSource.getAllPostsFlow(userId).map {
                it.map { post ->
                    mapper.mapPostEntityToPost(post)
                }
            }
        }
    }

    override suspend fun fetchPosts(userId: String) {
        withContext(dispatcher) {
            val entityPosts = remoteDataSource.getPosts(userId)
                .map {
                    mapper.mapPostResponseToPostEntity(it)
                }
            localDataSource.insertPosts(entityPosts)
        }
    }

    override suspend fun getComments(postId: String): List<Comment> {
        return withContext(dispatcher) {
            remoteDataSource.getComments(postId).map {
                mapper.mapCommentResponseToComment(it)
            }
        }
    }

    override suspend fun getPost(postId: String): Post {
        return withContext(dispatcher) {
            mapper.mapPostEntityToPost(localDataSource.getPost(postId))
        }
    }
}
