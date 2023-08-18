package io.amntoppo.githubpr.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.amntoppo.githubpr.R
import io.amntoppo.githubpr.databinding.FragmentPullRequestBinding
import io.amntoppo.githubpr.presentation.adapters.PullRequestAdapter
import io.amntoppo.githubpr.presentation.viewmodels.MainViewModel
import io.amntoppo.githubpr.utils.Resource

@AndroidEntryPoint
class PullRequestFragment: Fragment(R.layout.fragment_pull_request) {

    lateinit var binding: FragmentPullRequestBinding
    val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPullRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pullRequestAdapter = PullRequestAdapter()
        binding.apply {
            pullRequestRecycler.apply {
                adapter = pullRequestAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            progressBar.visibility = View.VISIBLE
        }
        viewModel.getAllPullRequest().observe(this) {
            if(it != null) {
                when(it) {
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        pullRequestAdapter.submitList(it.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.textViewError.text = getString(R.string.error_found)
                    }
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }

            }
        }
    }
}