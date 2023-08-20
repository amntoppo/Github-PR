package io.amntoppo.githubpr.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.amntoppo.githubpr.R
import io.amntoppo.githubpr.databinding.FragmentPullRequestBinding
import io.amntoppo.githubpr.presentation.adapters.PullRequestAdapter
import io.amntoppo.githubpr.presentation.viewmodels.MainViewModel
import io.amntoppo.githubpr.utils.REPOSITORY_NAME
import io.amntoppo.githubpr.utils.Resource
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PullRequestFragment: Fragment(R.layout.fragment_pull_request) {

    lateinit var binding: FragmentPullRequestBinding
    val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPullRequestBinding.inflate(inflater, container, false)

        val bundle: Bundle? = this.arguments
        val repositoryName = bundle?.getString(REPOSITORY_NAME)

        val pullRequestAdapter = PullRequestAdapter()
        binding.apply {
            pullRequestRecycler.apply {
                adapter = pullRequestAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            progressBar.visibility = View.VISIBLE
        }
        if(!repositoryName.isNullOrEmpty()) {
            lifecycleScope.launch {
                viewModel.getPullRequest(repositoryName)
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.pullRequestStateFlow.collect { result ->
                        binding.apply {
                            pullRequestAdapter.submitList(result.data)
                            progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                            textViewError.isVisible = result is Resource.Error || result !is Resource.Loading && result.data.isNullOrEmpty()
                            textViewError.text = result.error?.localizedMessage ?: context?.getString(R.string.empty_pull_request)
                        }
                    }
                }
            }
        } else {
            viewModel.allPullRequestData.observe(viewLifecycleOwner) { result ->
                binding.apply {
                    pullRequestAdapter.submitList(result.data)
                    progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                    textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                    textViewError.text = result.error?.localizedMessage
                }
            }
        }

        return binding.root
    }

}