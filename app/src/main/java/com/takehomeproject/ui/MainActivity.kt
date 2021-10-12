package com.takehomeproject.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.takehomeproject.R
import com.example.takehomeproject.databinding.ActivityMainBinding
import com.takehomeproject.MyApplication
import com.takehomeproject.repository.model.ITunesContent
import com.takehomeproject.ui.adapter.ITunesContentsAdapter
import com.takehomeproject.ui.viewmodel.ITunesContentViewModel
import com.takehomeproject.ui.viewmodel.ITunesContentViewModelFactory
import com.takehomeproject.utils.Status
import com.takehomeproject.utils.Utilities

class MainActivity : AppCompatActivity(), ITunesContentsAdapter.OnContentClickListener {
    private val viewModel: ITunesContentViewModel by viewModels {
        ITunesContentViewModelFactory((application as MyApplication).applicationComponent.iTunesContentRepository)
    }
    private lateinit var adapter: ITunesContentsAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupConnectionChanges()
    }

    private fun setupConnectionChanges() {
        //Check Internet Connectivity
        if (Utilities.isNetworkConnected)
            showControlInOnlineMode()
        else
            hideControlsInOfflineMode()
        Utilities.networkNotifyListeners.add(object : Utilities.InterfaceNetworkNotify {
            override fun networkChange(off: Boolean, on: Boolean) {
                if (on) {
                    runOnUiThread {
                        showControlInOnlineMode()
                    }
                } else {
                    runOnUiThread {
                        hideControlsInOfflineMode()
                    }
                }
            }

        })
    }

    private fun hideControlsInOfflineMode() {
        binding.layoutSearch.isVisible = false
        binding.txtNoInternetConnection.visibility = View.VISIBLE
        search(
            "", ""
        )
    }

    private fun showControlInOnlineMode() {
        binding.txtNoInternetConnection.visibility = View.GONE
        binding.layoutSearch.isVisible = true
        if (!binding.textInputSearch.editText?.text.isNullOrEmpty())
            search(
                binding.textInputSearch.editText?.text.toString(),
                binding.spinnerListOfMediaTypes.selectedItem.toString()
            )
    }


    private fun setupUI() {
        binding.spinnerListOfMediaTypes.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.MediaTypes)
        )
        binding.btnSearch.setOnClickListener {
            search(
                binding.textInputSearch.editText?.text.toString(),
                binding.spinnerListOfMediaTypes.selectedItem.toString()
            )
        }
        binding.textInputSearch.editText?.addTextChangedListener {
            binding.btnSearch.isEnabled = !it.isNullOrEmpty()
        }
        binding.recyclerViewContents.layoutManager = LinearLayoutManager(this)
        adapter = ITunesContentsAdapter(arrayListOf(), this)
        binding.recyclerViewContents.adapter = adapter
    }

    private fun search(term: String, media: String) {
        viewModel.getITunesContents(term, media).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.recyclerViewContents.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { contents -> showList(contents as List<ITunesContent>) }
                    }
                    Status.ERROR -> {
                        binding.recyclerViewContents.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerViewContents.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun showList(contents: List<ITunesContent>) {
        adapter.apply {
            addContents(contents)
            notifyDataSetChanged()
        }
    }


    override fun onContentClick(content: ITunesContent) {
        viewModel.isContentExist(content.trackId ?: content.collectionId!!).observe(this, {
            if (it==null)
                viewModel.save(content)
        })

        val intent = Intent(this, ContentDetails::class.java).apply {
            putExtra("Content", content)
        }
        startActivity(intent)
    }
}