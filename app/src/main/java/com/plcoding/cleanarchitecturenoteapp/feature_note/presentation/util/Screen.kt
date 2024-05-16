package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.util

import android.content.Context
import android.widget.Toast

sealed class Screen(val route: String) {
    object NotesScreen: Screen("notes_screen")
    object AddEditNoteScreen: Screen("add_edit_note_screen")
}

fun String.toastMessageLong(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}
fun String.toastMessageShort(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}
