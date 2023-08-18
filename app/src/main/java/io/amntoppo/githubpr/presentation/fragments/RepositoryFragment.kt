package io.amntoppo.githubpr.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.amntoppo.githubpr.R
import io.amntoppo.githubpr.databinding.FragmentRepositoryBinding
import io.amntoppo.githubpr.domain.model.Repository
import io.amntoppo.githubpr.presentation.adapters.RepositoryAdapter
import io.amntoppo.githubpr.presentation.listeners.RepositoryOnClickListener
import io.amntoppo.githubpr.presentation.viewmodels.MainViewModel
import io.amntoppo.githubpr.utils.Resource

class RepositoryFragment: Fragment(R.layout.fragment_repository), RepositoryOnClickListener {

    lateinit var binding: FragmentRepositoryBinding
    val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepositoryBinding.inflate(inflater, container, false)

        val repositoryAdapter = RepositoryAdapter(this)
        binding.apply {
            repositoryRecyclerView.apply {
                adapter = repositoryAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            viewModel.getAllRepository().observe(viewLifecycleOwner) { result ->
                binding.apply {
                    repositoryAdapter.submitList(result.data)
                    progressBar.isVisible =
                        result is Resource.Loading && result.data.isNullOrEmpty()
                    textViewError.isVisible =
                        result is Resource.Error && result.data.isNullOrEmpty()
                    textViewError.text = result.error?.localizedMessage
                }
            }

            return binding.root
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onRepositoryClick(modelItem: Repository) {
        val bundle = Bundle()
        bundle.putString("repository_name", modelItem.name)
        val navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController.navigate(R.id.action_navigation_repository_to_navigation_pullrequest, bundle)
    }
}