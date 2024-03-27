package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.cleanarchitecturenoteapp.constants.ConstantsUtils
import com.plcoding.cleanarchitecturenoteapp.di.AppModule
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.NotesScreen
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.util.Screen
import com.plcoding.cleanarchitecturenoteapp.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {

    @get:Rule(order = 0)
    val hilt = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalAnimationApi::class)
    @Before
    fun setUp() {
        hilt.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            CleanArchitectureNoteAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ) {
                    composable(route = Screen.NotesScreen.route) {
                        NotesScreen(navController = navController)
                    }
                    composable(
                        route = Screen.AddEditNoteScreen.route +
                                "?noteId={noteId}&noteColor={noteColor}",
                        arguments = listOf(
                            navArgument(
                                name = "noteId"
                            ) {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(
                                name = "noteColor"
                            ) {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                        )
                    ) {
                        val color = it.arguments?.getInt("noteColor") ?: -1
                        AddEditNoteScreen(
                            navController = navController,
                            noteColor = color
                        )
                    }
                }
            }
        }
    }

    @Test
    fun saveNewNote_EditAfterwards() {
        // When
        composeRule.onNodeWithContentDescription("Add note").performClick()

        composeRule.onNodeWithTag(ConstantsUtils.TITLE_TEXT_FIELD)
            .performTextInput("test-title")
        composeRule.onNodeWithTag(ConstantsUtils.CONTENT_TEXT_FIELD)
            .performTextInput("test-content")

        composeRule.onNodeWithContentDescription("Save note").performClick()

        // Then
        composeRule.onNodeWithText("test-title").assertIsDisplayed()
        composeRule.onNodeWithText("test-title").performClick()

        composeRule.onNodeWithTag(ConstantsUtils.TITLE_TEXT_FIELD).assertTextEquals("test-title")
        composeRule.onNodeWithTag(ConstantsUtils.CONTENT_TEXT_FIELD).assertTextEquals("test-content")

        composeRule.onNodeWithTag(ConstantsUtils.CONTENT_TEXT_FIELD).performTextInput("-second")

        composeRule.onNodeWithContentDescription("Save note").performClick()
        composeRule.onNodeWithText("test-content-second").assertIsDisplayed()

    }

}