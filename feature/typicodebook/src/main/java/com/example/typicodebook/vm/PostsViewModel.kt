package com.example.typicodebook.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Post
import com.example.domain.usecase.base.BaseFetchPostsUseCase
import com.example.domain.usecase.base.BaseGetPostsFlowUseCase
import com.example.domain.usecase.base.BaseToggleFavoriteUseCase
import com.example.typicodebook.model.PostUi
import com.example.typicodebook.state.FavoriteButtonsUiState
import com.example.typicodebook.state.PostsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsFlowUseCase: BaseGetPostsFlowUseCase,
    private val fetchPostsUseCase: BaseFetchPostsUseCase,
    private val toggleFavoriteUseCase: BaseToggleFavoriteUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<PostsUiState>(PostsUiState.Success(emptyList()))
    val uiState: StateFlow<PostsUiState> = _uiState.asStateFlow()

    private val _uiButtonsState = MutableStateFlow(FavoriteButtonsUiState.ALL_POSTS)
    private val uiButtonsState: StateFlow<FavoriteButtonsUiState> = _uiButtonsState.asStateFlow()

    private var toggleJob: Job? = null
    private var fetchJob: Job? = null
    private var subscribeJob: Job? = null

    fun fetchPosts(userId: String) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                _uiState.value = PostsUiState.Fetching(isFetching = true)
                fetchPostsUseCase(userId)
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (exception: Exception) {
                _uiState.value = PostsUiState.Error(getErrorMessage(exception))
            }
        }
    }

    fun toggleFavorite(postId: String, isChecked: Boolean) {
        toggleJob?.cancel()
        toggleJob = viewModelScope.launch {
            try {
                toggleFavoriteUseCase(postId, isChecked)
            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (exception: Exception) {
                _uiState.value = PostsUiState.Error(getErrorMessage(exception))
            }
        }
    }

    fun subscribeToPosts(userId: String) {
        subscribeJob?.cancel()
        subscribeJob = viewModelScope.launch {
            getPostsFlowUseCase(userId)
                .combine(uiButtonsState) { posts, filterState ->
                    if (filterState == FavoriteButtonsUiState.FAV_POSTS_ONLY) {
                        posts.filter { it.favorite }
                    } else {
                        posts
                    }
                }
                .catch { exception ->
                    if (exception is CancellationException) {
                        throw exception
                    } else {
                        _uiState.value = PostsUiState.Error(getErrorMessage(exception))
                    }
                }
                .collect { list ->
                    val postsUi = list.map {
                        //todo: move to mapper
                        it.asPostUi()
                    }
                    _uiState.value = PostsUiState.Success(postsUi)
                }
        }
    }

    private fun getErrorMessage(exception: Throwable): String {
        //todo: improve
        return "Something went wrong"
    }

    fun userMessageShown() {
        _uiState.value = PostsUiState.Error(null)
    }

    fun filterPosts(favoritesOnly: Boolean) {
        when (favoritesOnly) {
            true -> _uiButtonsState.value = FavoriteButtonsUiState.FAV_POSTS_ONLY
            false -> _uiButtonsState.value = FavoriteButtonsUiState.ALL_POSTS
        }
    }
}

//todo: move to mapper
internal fun Post.asPostUi() = PostUi(userId, id, title, body, favorite)
