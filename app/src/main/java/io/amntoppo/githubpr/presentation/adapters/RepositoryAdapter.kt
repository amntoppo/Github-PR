package io.amntoppo.githubpr.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.amntoppo.githubpr.databinding.ItemRepositoryBinding
import io.amntoppo.githubpr.domain.model.Repository
import io.amntoppo.githubpr.presentation.listeners.RepositoryOnClickListener

class RepositoryAdapter(private val onClickListener: RepositoryOnClickListener) :
    ListAdapter<Repository, RepositoryAdapter.RepositoryViewHolder>(RepositoryComparator()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoryViewHolder {
        val binding = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder(binding)
    }
    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val currentItem = getItem(position)
        if(currentItem != null) {
            holder.bind(currentItem, onClickListener)
        }
    }
    class RepositoryViewHolder(private val binding: ItemRepositoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(repository: Repository, onClickListener: RepositoryOnClickListener) {
            binding.apply {
                repositoryTitle.text = repository.name
                repositoryDescription.text = repository.description
                forkTextView.text = repository.forks.toString()
                starTextView.text = repository.stargazers_count.toString()
                if(repository.language != null) {
                    languageImageView.visibility = View.VISIBLE
                    languageTextView.visibility = View.VISIBLE
                    languageTextView.text = repository.language
                }
                else {
                    languageImageView.visibility = View.INVISIBLE
                    languageTextView.visibility = View.INVISIBLE
                }
                root.setOnClickListener {
                    onClickListener.onRepositoryClick(repository)
                }
            }
        }
    }
    class RepositoryComparator : DiffUtil.ItemCallback<Repository>() {
        override fun equals(other: Any?): Boolean {
            return super.equals(other)
        }

        override fun areItemsTheSame(oldItem: Repository, newItem: Repository) =
            oldItem.name == newItem.name


        override fun areContentsTheSame(oldItem: Repository, newItem: Repository) =
            oldItem == newItem

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }
}