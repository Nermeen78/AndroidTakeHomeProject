package com.takehomeproject.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.core.view.isVisible
import com.example.takehomeproject.R
import com.example.takehomeproject.databinding.ActivityContentDetailsBinding
import com.squareup.picasso.Picasso
import com.takehomeproject.repository.model.ITunesContent

class ContentDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityContentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val content = intent.getSerializableExtra("Content") as ITunesContent
        if (content != null) {
            binding.txtName.text =
                if (content.wrapperType == "track") content.trackName else content.collectionName
            binding.txtArtistName.text = content.artistName
            if (content.wrapperType != "track") {
                binding.txtDescription.text = Html.fromHtml(content.description)

            } else {
                binding.txtDescription.text ="Collection Name:${content.collectionName}   , Track Count :${content.trackCount}"
            }
            Picasso.get().load(content.artworkUrl100).into(binding.imageArt1100)
            binding.btnPreview.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(content.previewUrl))
                startActivity(browserIntent)
            }
        }
    }
}