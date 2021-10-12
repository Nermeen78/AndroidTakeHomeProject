package com.takehomeproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.takehomeproject.databinding.ContentRowBinding
import com.squareup.picasso.Picasso
import com.takehomeproject.repository.model.ITunesContent


class ITunesContentsAdapter(
    private val contents: ArrayList<ITunesContent>,
    val listener: OnContentClickListener
) :
    RecyclerView.Adapter<ITunesContentsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ContentRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(content: ITunesContent) {

            binding.txtName.text =
                if (content.wrapperType != "track") content.collectionName else  content.trackName
            binding.txtKind.text =
                if (content.wrapperType == "track") content.kind else content.wrapperType

            Picasso.get().load(content.artworkUrl100).into(binding.imageArt160)
            binding.root.setOnClickListener {
                listener.onContentClick(content)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ContentRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(contents[position])
    }

    override fun getItemCount(): Int {
        return contents.size
    }

    fun addContents(newContents: List<ITunesContent>) {
        contents.apply {
            clear()
            addAll(newContents)
        }

    }

    interface OnContentClickListener {
        fun onContentClick(content: ITunesContent)
    }
}