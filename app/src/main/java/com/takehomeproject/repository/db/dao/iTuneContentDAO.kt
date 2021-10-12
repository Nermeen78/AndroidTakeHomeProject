package com.takehomeproject.repository.db.dao

import androidx.room.*
import com.takehomeproject.repository.model.ITunesContent
import kotlinx.coroutines.flow.Flow


@Dao
interface ITuneContentDAO {
    @Query("Select * from iTunes_content where trackId=:id or collectionId=:id")
    fun getContentInfo(id:Int):Flow<ITunesContent>

    @Query("Select * from iTunes_content")
    suspend fun getContents(): List<ITunesContent>

    @Insert(onConflict = OnConflictStrategy.REPLACE,entity = ITunesContent::class)
    suspend fun save(content: ITunesContent)

    @Query("DELETE FROM iTunes_content")
      fun deleteAll()

}