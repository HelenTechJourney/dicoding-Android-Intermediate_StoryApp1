package com.example.storyapp.ui.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.storyapp.databinding.ActivityDetailStoryBinding
import com.example.storyapp.remote.response.ListStoryItem
import com.example.storyapp.remote.response.Story
import com.example.storyapp.remote.response.UserPreference
import com.example.storyapp.ui.viewmodel.DataStoreViewModel
import com.example.storyapp.ui.viewmodel.DetailViewModel
import com.example.storyapp.ui.viewmodel.RepoViewModelFactory
import com.example.storyapp.ui.viewmodel.ViewModelFactory

@Suppress("DEPRECATION", "NAME_SHADOWING")
class DetailStoryActivity : AppCompatActivity() {
    private val viewModel: DetailViewModel by viewModels{
        RepoViewModelFactory(this)
    }
    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail Story"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val story = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_STORY, ListStoryItem::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_STORY)
        }

        story?.let{story->
            val pref = UserPreference.getInstance(dataStore)
            val dataStoreViewModel =
                ViewModelProvider(this, ViewModelFactory(pref))[DataStoreViewModel::class.java]
            dataStoreViewModel.getToken().observe(this) {token->
                viewModel.getDetail(token,story.id)
            }
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
        viewModel.detailUser.observe(this) { detailUser ->
            detailUser?.let{setDetailStory(it)}
        }
    }

    private fun setDetailStory(detailStory: Story) {
        binding.apply {
            tvDetailName.text = detailStory.name
            tvDetailDescription.text = detailStory.description
        }
        Glide.with(this)
            .load(detailStory.photoUrl)
            .into(binding.ivDetailPhoto)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}