package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.InvalidNoteException
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.MockkRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.InvalidClassException

class AddNoteTest {

    private lateinit var repository: NoteRepository
    private lateinit var addNoteUseCase: AddNote

    private val fakeDB = mutableListOf<Note>()
    @Before
    fun setUp() {
        MockKAnnotations.init()
        repository = mockk()

        coEvery { repository.insertNote(any()) } answers {
            fakeDB.add(firstArg())
        }

        addNoteUseCase = AddNote(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test(expected = InvalidNoteException::class)
    fun `test InvalidNoteException is throw when title is blank`() = runBlocking {
        // When
        val note = Note("","Some Content", 0L, 0)
        addNoteUseCase(note)
    }
    @Test(expected = InvalidNoteException::class)
    fun `test InvalidNoteException is throw when content is blank`() = runBlocking {
        // When
        val note = Note("Some title","", 0L, 0)
        addNoteUseCase(note)
    }
    @Test
    fun `test all arguments are valid, repository insert called successfully`() = runBlocking {
        // When
        val note = Note("Some Title ","Some Content", 0L, 0)
        addNoteUseCase(note)
        // Then
        coVerify (exactly = 1) { repository.insertNote(note) }
    }
}

