package com.takehomeproject.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.asLiveData
import com.takehomeproject.repository.api.ApiService
import com.takehomeproject.repository.db.dao.ITuneContentDAO
import com.takehomeproject.repository.model.ITunesContent
import com.takehomeproject.utils.Utilities
import kotlinx.coroutines.flow.Flow

class ITunesContentRepository(
    private val iTunesContentDAO: ITuneContentDAO,
    private val apiService: ApiService
) {

    private suspend fun getITunesContentsFromDb(): List<ITunesContent> =
        iTunesContentDAO.getContents()

    internal fun getITunesContentInfo(id: Int): Flow<ITunesContent> =
        iTunesContentDAO.getContentInfo(id)

    @WorkerThread
    suspend fun save(iTunesContent: ITunesContent) {
        iTunesContentDAO.save(iTunesContent)
    }

    fun deleteAll() {
        iTunesContentDAO.deleteAll()
    }

    @WorkerThread
    suspend fun getITunesContents(term: String, media: String): List<ITunesContent> {
        if (Utilities.isNetworkConnected) {
            deleteAll()
            return apiService.getITunesContent(term, media).results
        }
        return getITunesContentsFromDb()
    }


}