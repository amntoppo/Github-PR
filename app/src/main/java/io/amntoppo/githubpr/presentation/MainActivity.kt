package io.amntoppo.githubpr.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.amntoppo.githubpr.R
import io.amntoppo.githubpr.data.remote.PullRequestApi
import io.amntoppo.githubpr.data.remote.RepositoryApi
import io.amntoppo.githubpr.databinding.ActivityMainBinding
import io.amntoppo.githubpr.domain.model.PullRequest
import io.amntoppo.githubpr.presentation.adapters.PullRequestAdapter
import io.amntoppo.githubpr.presentation.viewmodels.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val pullRequestAdapter = PullRequestAdapter()
        binding.apply {
            pullRequestRecycler.apply {
                adapter = pullRequestAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
            progressBar.visibility = VISIBLE
        }
        viewModel.getAllPullRequest().observe(this) {
            if(it != null) {
                binding.progressBar.visibility = GONE
                pullRequestAdapter.submitList(it)
            }
        }
//        viewModel.closedPRData.observe(this) { pull ->
////                pullRequestAdapter.submitList(pull)
//
//            pull.forEach {
//                Log.e("pull", it.number.toString())
//               pullRequestAdapter.submitList(listOf(it))
//            }
//        }
        setContentView(binding.root)
    }
}