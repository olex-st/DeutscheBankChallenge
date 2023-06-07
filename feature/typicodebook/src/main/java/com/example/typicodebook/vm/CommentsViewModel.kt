package com.example.typicodebook.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Comment
import com.example.domain.model.Post
import com.example.domain.usecase.base.BaseGetCommentsUseCase
import com.example.domain.usecase.base.BaseToggleFavoriteUseCase
import com.example.typicodebook.model.CommentUi
import com.example.typicodebook.state.CommentsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val getCommentsUseCase: BaseGetCommentsUseCase,
    private val toggleFavoriteUseCase: BaseToggleFavoriteUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<CommentsUiState>(CommentsUiState.Success(emptyList()))
    val uiState: StateFlow<CommentsUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null
    private var toggleJob: Job? = null

    fun fetchComments(postId: String) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                _uiState.value = CommentsUiState.Fetching(isFetching = true)
                val comments = getCommentsUseCase(postId)
                val commentsUi = comments.map {
                    when (it) {
                        is Comment -> it.asCommentUi()
                        is Post -> it.asPostUi()
                    }
                }
                _uiState.value = CommentsUiState.Success(commentsUi)
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (exception: Exception) {
                _uiState.value = CommentsUiState.Error(getErrorMessage(exception))
            }
        }
    }

    fun toggleFavorite(postId: String, isChecked: Boolean) {
        toggleJob?.cancel()
        toggleJob = viewModelScope.launch {
            try {
                _uiState.value = CommentsUiState.Fetching(isFetching = true)
                toggleFavoriteUseCase(postId, isChecked)
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (exception: Exception) {
                _uiState.value = CommentsUiState.Error(getErrorMessage(exception))
            }
        }
    }

    private fun getErrorMessage(exception: Throwable): String {
        //todo: improve
        return "Something went wrong"
    }

    fun userMessageShown() {
        _uiState.value = CommentsUiState.Error(null)
    }
}

//todo: to mapper?
private fun Comment.asCommentUi() = CommentUi(postId, id, name, email, body)
