package com.example.api

import com.example.api.model.CommentResponse
import com.example.api.model.PostResponse
import com.example.api.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostsRemoteDataSource : RemoteDataSource {

    @GET("/users/{userId}")
    override suspend fun login(
        @Path(value = "userId") userId: String
    )
            : UserResponse

    @GET("/posts")
    override suspend fun getPosts(
        @Query(value = "userId") userId: String
    )
            : List<PostResponse>

    @GET("/comments")
    override suspend fun getComments(
        @Query(value = "postId") postId: String
    )
            : List<CommentResponse>
}

