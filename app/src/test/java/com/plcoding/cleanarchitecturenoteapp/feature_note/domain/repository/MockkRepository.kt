package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockkRepository : NoteRepository {

    private val listOfNotes: MutableList<Note> = mutableListOf()
    override fun getNotes(): Flow<List<Note>> = flow { emit(listOfNotes) }

    override suspend fun getNoteById(id: Int): Note? {
        return listOfNotes.find { it.id == id }
    }

    override suspend fun insertNote(note: Note) {
        listOfNotes.add(note)
    }

    override suspend fun deleteNote(note: Note) {
        listOfNotes.remove(note)
    }
}