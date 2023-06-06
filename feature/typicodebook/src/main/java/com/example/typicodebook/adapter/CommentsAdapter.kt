package com.example.typicodebook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.typicodebook.databinding.ICommentBinding
import com.example.typicodebook.databinding.IPostBinding
import com.example.typicodebook.fragment.TypeClicked
import com.example.typicodebook.model.TextUi
import com.example.typicodebook.model.CommentUi
import com.example.typicodebook.model.PostUi

enum class ViewHolderType {
    POST,
    COMMENT
}

class CommentsAdapter(private val onClick: (Int, TypeClicked) -> Unit) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    private val uis = mutableListOf<TextUi>()

    fun addItems(textUis: List<TextUi>) {
        uis.clear()
        uis.addAll(textUis)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ViewHolderType.POST.ordinal -> PostVH.from(parent, onClick)
            ViewHolderType.COMMENT.ordinal -> CommentVH.from(parent)
            else -> throw ClassCastException("Unknown view type $viewType")
        }
    }

    override fun getItemCount(): Int {
        return uis.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val item = uis[position]) {
            is PostUi -> (holder as PostVH).bind(item)
            is CommentUi -> (holder as CommentVH).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (uis[position]) {
            is PostUi -> ViewHolderType.POST.ordinal
            is CommentUi -> ViewHolderType.COMMENT.ordinal
        }
    }

    sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class PostVH(private val itemBinding: IPostBinding, val onClick: (Int, TypeClicked) -> Unit) : ViewHolder(itemBinding.root) {


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

    class CommentVH(private val itemBinding: ICommentBinding) : ViewHolder(itemBinding.root) {
        fun bind(comment: CommentUi) {
            itemBinding.mtvUsername.text = comment.name
            itemBinding.mtvComment.text = comment.body
        }

        companion object {
            fun from(parent: ViewGroup): CommentVH {
                val view = ICommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return CommentVH(view)
            }
        }
    }
}