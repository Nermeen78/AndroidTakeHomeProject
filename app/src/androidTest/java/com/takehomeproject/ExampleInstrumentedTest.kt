package com.takehomeproject

import android.content.Context
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.takehomeproject.repository.db.ItunesContentRoomDatabase
import com.takehomeproject.repository.db.dao.ITuneContentDAO
import com.takehomeproject.repository.model.ITunesContent
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var iTuneContentDAO: ITuneContentDAO
    private lateinit var db: ItunesContentRoomDatabase

    @Before
    fun createDb() {
        val context: Context =InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, ItunesContentRoomDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        iTuneContentDAO = db.itunesContentDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertContent() = runBlocking {
        val content = ITunesContent(
            1,
            artistId = 10,
            artistName = "TEST",
            trackName = "TEST Track",
            trackId = 110
        )
        iTuneContentDAO.save(content)
        val contentFromDb = iTuneContentDAO.getContents().first()
        assertEquals(contentFromDb.trackName, content.trackName)
    }

    @Test
    @Throws(Exception::class)
    fun getAllWords() = runBlocking {
        val content1 = ITunesContent(
            1,
            artistId = 10,
            artistName = "TEST1",
            trackName = "TEST1 Track",
            trackId = 1
        )
        iTuneContentDAO.save(content1)
        val content2 = ITunesContent(
            2,
            artistId = 10,
            artistName = "TEST2",
            trackName = "TEST2 Track",
            trackId = 2
        )
        iTuneContentDAO.save(content2)


        val contentsFromDb = iTuneContentDAO.getContents()
        assertEquals(contentsFromDb[0].trackName, content1.trackName)
        assertEquals(contentsFromDb[1].trackName, content2.trackName)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() = runBlocking {
        val content1 = ITunesContent(
            1,
            artistId = 10,
            artistName = "TEST1",
            trackName = "TEST1 Track",
            trackId = 1
        )
        iTuneContentDAO.save(content1)
        val content2 = ITunesContent(
            2,
            artistId = 10,
            artistName = "TEST2",
            trackName = "TEST2 Track",
            trackId = 2
        )
        iTuneContentDAO.save(content1)
        iTuneContentDAO.deleteAll()
        val contentsFromDb = iTuneContentDAO.getContents()
        assertTrue(contentsFromDb.isEmpty())
    }
}