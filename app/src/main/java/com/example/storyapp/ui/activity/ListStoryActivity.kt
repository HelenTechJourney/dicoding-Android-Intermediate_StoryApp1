package com.example.storyapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityListStoryBinding
import com.example.storyapp.remote.response.ListStoryItem
import com.example.storyapp.remote.response.UserPreference
import com.example.storyapp.ui.adapter.ListStoryAdapter
import com.example.storyapp.ui.viewmodel.DataStoreViewModel
import com.example.storyapp.ui.viewmodel.ListStoryViewModel
import com.example.storyapp.ui.viewmodel.RepoViewModelFactory
import com.example.storyapp.ui.viewmodel.ViewModelFactory

class ListStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListStoryBinding
    private lateinit var token: String
    private var isFinished = false
    private val viewModel: ListStoryViewModel by viewModels{
        RepoViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityListStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }

        supportActionBar?.title = "Story App"

        val pref = UserPreference.getInstance(dataStore)
        val dataStoreViewModel =
            ViewModelProvider(this, ViewModelFactory(pref))[DataStoreViewModel::class.java]
        dataStoreViewModel.getToken().observe(this) {
            token = it
            viewModel.getAllStories(token)
        }
        viewModel.listUser.observe(this) { listUser ->
            listUser?.let{setUserData(it)}
        }
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.items_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setUserData(items: List<ListStoryItem>) {

        val adapter = ListStoryAdapter(items)
        binding.rvStory.adapter = adapter
        binding.rvStory.visibility = View.VISIBLE

        adapter.setOnItemClickCallback(object : ListStoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListStoryItem) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(story: ListStoryItem) {
        val intent = Intent(this, DetailStoryActivity::class.java)
        intent.putExtra(DetailStoryActivity.EXTRA_STORY, story)
        startActivity(intent)
    }

    private fun showAlertDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val alert = dialogBuilder.create()
        dialogBuilder
            .setTitle(getString(R.string.logout))
            .setMessage(getString(R.string.you_sure))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                logout()
            }
            .setNegativeButton(getString(R.string.no)) { _, _ ->
                alert.cancel()
            }
            .show()
    }

    private fun logout() {
        val pref = UserPreference.getInstance(dataStore)
        val loginViewModel =
            ViewModelProvider(this, ViewModelFactory(pref))[DataStoreViewModel::class.java]
        loginViewModel.apply {
            saveLoginState(false)
            saveToken("")
            saveName("")
        }
        isFinished = true
        startActivity(Intent(this, WelcomeActivity::class.java))
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout-> {
                showAlertDialog()
                return true
            }
            R.id.lang-> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}