package com.takehomeproject.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "iTunes_content")
data class ITunesContent(
    @PrimaryKey (autoGenerate = true) val id:Long,
    val artistId: Int?=null,
    val artistName: String?=null,
    val artistViewUrl: String?=null,
    val artworkUrl100: String?=null,
    val artworkUrl60: String?=null,
    val collectionCensoredName: String?=null,
    val collectionExplicitness: String?=null,
    val collectionId: Int?=null,
    val collectionName: String?=null,
    val collectionPrice: Double?=null,
    val collectionViewUrl: String?=null,
    val country: String?=null,
    val currency: String?=null,
    val discCount: Int?=null,
    val discNumber: Int?=null,
    val kind: String?=null,
    val previewUrl: String?=null,
    val primaryGenreName: String?=null,
    val trackCensoredName: String?=null,
    val trackCount: Int?=null,
    val trackExplicitness: String?=null,
    val trackId: Int?=null,
    val trackName: String?=null,
    val trackNumber: Int?=null,
    val trackPrice: Double?=null,
    val trackTimeMillis: Int?=null,
    val trackViewUrl: String?=null,
    val wrapperType: String?=null,
    val description: String?=null
) : Serializable