package com.example.repository.mapper

import com.example.api.model.CommentResponse
import com.example.api.model.PostResponse
import com.example.api.model.UserResponse
import com.example.database.model.entity.PostEntity
import com.example.domain.model.Comment
import com.example.domain.model.Post
import com.example.domain.model.User

interface BaseMapper {

    fun mapUserResponseToUser(userResponse: UserResponse): User

    fun mapPostResponseToPost(postResponse: PostResponse): Post

    fun mapCommentResponseToComment(commentResponse: CommentResponse): Comment

    fun mapPostResponseToPostEntity(postResponse: PostResponse): PostEntity

    fun mapPostEntityToPost(postEntity: PostEntity): Post
}
