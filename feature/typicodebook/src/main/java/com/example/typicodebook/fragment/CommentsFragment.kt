package com.example.typicodebook.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.typicodebook.R
import com.example.typicodebook.adapter.CommentsAdapter
import com.example.typicodebook.databinding.FCommentsBinding
import com.example.typicodebook.state.CommentsUiState
import com.example.typicodebook.vm.CommentsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommentsFragment : Fragment() {

    private var _binding: FCommentsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: CommentsViewModel by viewModels()
    private var postId: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            arguments?.let {
                postId = it.getInt(getString(R.string.key_navigation_post_id))
            }
        } else {
            postId = savedInstanceState.getInt(getString(R.string.key_bundle_post_id))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FCommentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val commentsAdapter = CommentsAdapter { id, type ->
            onClick(id, type)
        }

        binding.rvPosts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = commentsAdapter
        }

        viewModel.fetchComments(postId.toString())

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is CommentsUiState.Success -> {
                            hideProgress()
                            commentsAdapter.addItems(uiState.comments)
                        }

                        is CommentsUiState.Fetching -> showProgress()
                        is CommentsUiState.Error -> uiState.errorMessage?.let {
                            hideProgress()
                            Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_LONG).show()
                            viewModel.userMessageShown()
                        }
                    }
                }
            }
        }
    }

    private fun onClick(postId: Int, type: TypeClicked) {
        when (type) {
            TypeClicked.VIEW_CLICKED -> {}
            TypeClicked.FAV_IS_CHECKED -> viewModel.toggleFavorite(postId.toString(), true)
            TypeClicked.FAV_IS_UNCHECKED -> viewModel.toggleFavorite(postId.toString(), false)
        }
    }

    private fun showProgress() {
        binding.includeProgress.lpiProgress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.includeProgress.lpiProgress.visibility = View.INVISIBLE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(getString(R.string.key_bundle_post_id), postId!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}