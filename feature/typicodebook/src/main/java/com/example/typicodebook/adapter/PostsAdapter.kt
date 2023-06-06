package com.example.typicodebook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.typicodebook.databinding.IPostBinding
import com.example.typicodebook.fragment.TypeClicked
import com.example.typicodebook.model.PostUi

class PostsAdapter(private val onClick: (Int, TypeClicked) -> Unit) :
    RecyclerView.Adapter<PostsAdapter.PostVH>() {

    private val posts = mutableListOf<PostUi>()

    fun addItems(postUiList: List<PostUi>) {
        posts.clear()
        posts.addAll(postUiList)
        notifyDataSetChanged()
    }

    class PostVH(private val itemBinding: IPostBinding, val onClick: (Int, TypeClicked) -> Unit) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private var postId: Int? = null

        init {
            itemBinding.tbFav.setOnCheckedChangeListener { _, isChecked ->
                postId?.let { postId ->
                    if (isChecked) {
                        onClick(postId, TypeClicked.FAV_IS_CHECKED)
                    } else {
                        onClick(postId, TypeClicked.FAV_IS_UNCHECKED)
                    }
                }
            }

            itemBinding.root.setOnClickListener {
                postId?.let { postId ->
                    onClick(postId, TypeClicked.VIEW_CLICKED)
                }
            }
        }

        fun bind(post: PostUi) {
            postId = post.id
            itemBinding.mtvTitle.text = post.title
            itemBinding.mtvBody.text = post.body
            itemBinding.tbFav.isChecked = post.isChecked
        }

        companion object {
            fun from(parent: ViewGroup, onClick: (Int, TypeClicked) -> Unit): PostVH {
                val view = IPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return PostVH(view, onClick)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostVH {
        return PostVH.from(parent, onClick)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostVH, position: Int) {
        val property = posts[position]
        holder.bind(property)
    }
}