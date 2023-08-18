package io.amntoppo.githubpr.presentation.adapters


import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.amntoppo.githubpr.databinding.ItemPullRequestBinding
import io.amntoppo.githubpr.domain.model.PullRequest
import io.amntoppo.githubpr.utils.getFormattedDate

class PullRequestAdapter: ListAdapter<PullRequest, PullRequestAdapter.PullRequestViewHolder>(PullRequestComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestViewHolder {
        val binding = ItemPullRequestBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return PullRequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        val currentItem = getItem(position)
        if(currentItem != null) {
            holder.bind(currentItem)
        }
    }
    class PullRequestViewHolder(private val binding: ItemPullRequestBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: PullRequest) {
            binding.apply {
                prTitle.text = currentItem.title
                prUsername.text = currentItem.user.login
                prCreatedOn.text = getFormattedDate(currentItem.created_at)
                if(!currentItem.closed_at.isNullOrEmpty()) {
                    prClosedon.isVisible = true
                    prClosedon.text = getFormattedDate(currentItem.closed_at)
                }
                Glide.with(itemView)
                    .load(currentItem.user.avatar_url)
                    .into(prImage)
            }
        }
    }


    class PullRequestComparator: DiffUtil.ItemCallback<PullRequest>() {
        override fun areItemsTheSame(newItem: PullRequest, oldItem: PullRequest) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PullRequest, newItem: PullRequest) =
            oldItem == newItem
    }
}