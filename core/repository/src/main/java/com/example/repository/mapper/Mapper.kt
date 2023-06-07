package com.example.repository.mapper

import com.example.api.model.CommentResponse
import com.example.api.model.PostResponse
import com.example.api.model.UserResponse
import com.example.database.model.entity.PostEntity
import com.example.domain.model.Comment
import com.example.domain.model.Post
import com.example.domain.model.User
import javax.inject.Inject

class Mapper @Inject constructor() : BaseMapper {

    override fun mapUserResponseToUser(userResponse: UserResponse): User {
        return User(userResponse.id)
    }

    override fun mapPostResponseToPost(postResponse: PostResponse): Post {
        return Post(
            postResponse.userId,
            postResponse.id,
            postResponse.title ?: "",
            postResponse.body ?: ""
        )
    }

    override fun mapCommentResponseToComment(commentResponse: CommentResponse): Comment {
        return Comment(
            commentResponse.postId,
            commentResponse.id,
            commentResponse.name ?: "",
            commentResponse.email ?: "",
            commentResponse.body ?: ""
        )
    }

    override fun mapPostResponseToPostEntity(postResponse: PostResponse): PostEntity {
        return PostEntity(
            postResponse.userId,
            postResponse.id,
            postResponse.title ?: "",
            postResponse.body ?: ""
        )
    }

    override fun mapPostEntityToPost(postEntity: PostEntity): Post {
        return Post(
            postEntity.userId,
            postEntity.id,
            postEntity.title,
            postEntity.body
        ).apply { favorite = postEntity.favorite }
    }
}