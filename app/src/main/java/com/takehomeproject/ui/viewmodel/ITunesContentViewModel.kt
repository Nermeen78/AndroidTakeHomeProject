package com.takehomeproject.ui.viewmodel

import androidx.lifecycle.*
import com.takehomeproject.MyApplication
import com.takehomeproject.repository.ITunesContentRepository
import com.takehomeproject.repository.model.ITunesContent
import com.takehomeproject.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ITunesContentViewModel(var iTunesContentRepository: ITunesContentRepository) : ViewModel() {

    fun save(content: ITunesContent) = viewModelScope.launch {
        iTunesContentRepository.save(content)
    }

    fun getITunesContents(term: String, media: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {

            emit(
                Resource.success(
                    data = iTunesContentRepository.getITunesContents(
                        term,
                        media
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun isContentExist(id: Int) = iTunesContentRepository.getITunesContentInfo(id).asLiveData()

    fun deleteAll() = viewModelScope.launch {
        iTunesContentRepository.deleteAll()
    }

}

class ITunesContentViewModelFactory(private val repository: ITunesContentRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ITunesContentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ITunesContentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}