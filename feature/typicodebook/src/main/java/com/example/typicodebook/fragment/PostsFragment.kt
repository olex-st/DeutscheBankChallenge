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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.typicodebook.R
import com.example.typicodebook.adapter.PostsAdapter
import com.example.typicodebook.databinding.FPostsBinding
import com.example.typicodebook.state.PostsUiState
import com.example.typicodebook.vm.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostsFragment : Fragment() {

    private val viewModel: PostsViewModel by viewModels()

    private var _binding: FPostsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private var userId: Int? = null
    private lateinit var postAdapter: PostsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            arguments?.let {
                userId = it.getInt(getString(R.string.key_navigation_user_id))
            }
        } else {
            userId = savedInstanceState.getInt(getString(R.string.key_bundle_user_id))
        }
        fetchData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        observeViewModel()
    }

    private fun fetchData() {
        viewModel.subscribeToPosts(userId.toString())
        viewModel.fetchPosts(userId.toString())
    }

    private fun setupUi() {
        binding.rbAll.setOnClickListener {
            viewModel.filterPosts(false)
        }
        binding.rbFav.setOnClickListener {
            viewModel.filterPosts(true)
        }
        postAdapter = PostsAdapter { id, type ->
            onClick(id, type)
        }

        binding.rvPosts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postAdapter
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is PostsUiState.Success -> {
                            hideProgress()
                            postAdapter.addItems(uiState.posts)
                        }

                        is PostsUiState.Fetching -> showProgress()
                        is PostsUiState.Error -> uiState.errorMessage?.let {
                            hideProgress()
                            Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_LONG).show()
                            viewModel.userMessageShown()
                        }
                    }
                }
            }
        }
    }

    private fun showProgress() {
        binding.includeProgress.lpiProgress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.includeProgress.lpiProgress.visibility = View.INVISIBLE
    }

    private fun navigateToCommentsFragment(postId: Int) {
        val action = PostsFragmentDirections.actionPostsFragmentToCommentsFragment(postId)
        findNavController().navigate(action)
    }

    private fun onClick(postId: Int, type: TypeClicked) {
        when (type) {
            TypeClicked.VIEW_CLICKED -> navigateToCommentsFragment(postId)
            TypeClicked.FAV_IS_CHECKED -> viewModel.toggleFavorite(postId.toString(), true)
            TypeClicked.FAV_IS_UNCHECKED -> viewModel.toggleFavorite(postId.toString(), false)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(getString(R.string.key_bundle_user_id), userId!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

enum class TypeClicked {
    VIEW_CLICKED, FAV_IS_CHECKED, FAV_IS_UNCHECKED
}