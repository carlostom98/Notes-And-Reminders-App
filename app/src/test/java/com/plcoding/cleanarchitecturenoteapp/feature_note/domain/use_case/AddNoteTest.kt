package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.MockkRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

class AddNoteTest {

    private lateinit var repository: NoteRepository
    private lateinit var addNoteUseCase: AddNote


    @Before
    fun setUp() {
        repository = MockkRepository()
        addNoteUseCase = AddNote(repository)
    }

    @After
    fun tearDown() {

    }
}