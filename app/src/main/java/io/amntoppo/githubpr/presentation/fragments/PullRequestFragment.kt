package io.amntoppo.githubpr.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
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

        val bundle: Bundle? = this.arguments
        val repositoryName = bundle?.getString("repository_name")

        val pullRequestAdapter = PullRequestAdapter()
        binding.apply {
            pullRequestRecycler.apply {
                adapter = pullRequestAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            progressBar.visibility = View.VISIBLE
        }
        if(!repositoryName.isNullOrEmpty()) {
            repositoryName.let {
                viewModel.getClosedPullRequests(it).observe(viewLifecycleOwner) { result ->
                    binding.apply {
                        pullRequestAdapter.submitList(result.data)
                        progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                        textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                        textViewError.text = result.error?.localizedMessage
                    }
                }
            }
        } else {
            viewModel.getAllPullRequest().observe(viewLifecycleOwner) { result ->
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}