package com.example.repository.fake

import com.example.api.RemoteDataSource
import com.example.api.model.CommentResponse
import com.example.api.model.PostResponse
import com.example.api.model.UserResponse

class TestRemoteDataSource : RemoteDataSource {
    override suspend fun login(userId: String): UserResponse {
        val company = UserResponse.Company(
            "Yost and Sons",
            "Switchable contextually-based project",
            "aggregate real-time technologies"
        )
        val geo = UserResponse.Address.Geo("24.6463", "-168.8889")
        val address = UserResponse.Address(
            "Dayna Park",
            "Suite 449",
            "Bartholomebury",
            "76495-3109",
            geo
        )
        val userResponse = UserResponse(
            9,
            "Glenna Reichert",
            "Delphine",
            "Chaim_McDermott@dana.io",
            address,
            "(775)976-6794 x41206",
            "conrad.com",
            company
        )
        return userResponse
    }

    override suspend fun getPosts(userId: String): List<PostResponse> {
        return listOf(
            PostResponse(1, 2, "", ""),
            PostResponse(1, 2, "", "")
        )
    }

    override suspend fun getComments(postId: String): List<CommentResponse> {
        return listOf(
            CommentResponse(1, 2, "", "", ""),
            CommentResponse(1, 2, "", "", "")
        )
    }

}
