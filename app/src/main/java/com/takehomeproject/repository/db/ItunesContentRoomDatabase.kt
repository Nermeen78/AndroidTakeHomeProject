package com.takehomeproject.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.takehomeproject.repository.db.dao.ITuneContentDAO
import com.takehomeproject.repository.model.ITunesContent

@Database(entities = [ITunesContent::class], version = 1, exportSchema = false)
abstract class ItunesContentRoomDatabase : RoomDatabase() {

    abstract fun itunesContentDAO(): ITuneContentDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ItunesContentRoomDatabase? = null

        fun getDatabase(context: Context): ItunesContentRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItunesContentRoomDatabase::class.java,
                    "itunes_contents_db"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}