package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.MockkRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetNoteTest {
    private lateinit var getNotesMockk: GetNotes
    private lateinit var fakeRepository: NoteRepository

    @Before
    fun setUp() {
        fakeRepository = MockkRepository()
        getNotesMockk = GetNotes(fakeRepository)

        val notesToInsert =
            mutableListOf<com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note>()

        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note(
                    title = c.toString(),
                    content = c.toString(),
                    color = index,
                    timestamp = index.toLong()
                )
            )
        }

        notesToInsert.shuffled()

        runBlocking {
            notesToInsert.forEach { note ->
                fakeRepository.insertNote(note)
            }
        }
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getNotesOrderedAsc() = runBlocking {
        // When
        val notes = getNotesMockk(NoteOrder.Title(OrderType.Ascending)).first()
        // Then
        for(i in 0..notes.size - 2) {
            assert(notes[i].title < notes[i+1].title)
        }
    }
}