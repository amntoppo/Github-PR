package io.amntoppo.githubpr.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.amntoppo.githubpr.R
import io.amntoppo.githubpr.data.remote.PullRequestApi
import io.amntoppo.githubpr.data.remote.RepositoryApi
import io.amntoppo.githubpr.databinding.ActivityMainBinding
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
        viewModel.getAllPullRequest()

            viewModel.closedPRData.observe(this) { pull ->
            pull.forEach {
                Log.e("pull", it.number.toString())
            }
        }
        setContentView(binding.root)
    }
}