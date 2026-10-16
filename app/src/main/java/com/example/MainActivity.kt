package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.SportsScore
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material.icons.outlined.SportsScore
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MeditationScreen
import com.example.ui.screens.ModulesScreen
import com.example.ui.screens.PaperReliefScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.StoryScreen
import com.example.ui.screens.TrophyScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.ZenDeepPurple
import com.example.viewmodel.AppScreen
import com.example.viewmodel.MainViewModel

data class NavItem(
    val screen: AppScreen,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppContainer(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppContainer(viewModel: MainViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()

    val navItems = listOf(
        NavItem(AppScreen.HOME, "Início", Icons.Filled.Home, Icons.Outlined.Home, "nav_home"),
        NavItem(AppScreen.MODULES_LIST, "Capítulos", Icons.Filled.Map, Icons.Outlined.Map, "nav_modules"),
        NavItem(AppScreen.QUIZ_LIST, "Quiz", Icons.Filled.SportsScore, Icons.Outlined.SportsScore, "nav_quiz"),
        NavItem(AppScreen.STORY, "Histórias", Icons.Filled.MenuBook, Icons.Outlined.MenuBook, "nav_story"),
        NavItem(AppScreen.PAPER_RELIEF, "Papel 📝", Icons.Filled.NoteAlt, Icons.Outlined.NoteAlt, "nav_paper"),
        NavItem(AppScreen.BADGES, "Troféus", Icons.Filled.EmojiEvents, Icons.Outlined.EmojiEvents, "nav_badges")
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            // Only show bottom navigation on non-active quiz question
            if (currentScreen != AppScreen.QUIZ_ACTIVE) {
                NavigationBar(
                    modifier = Modifier.testTag("main_navigation_bar")
                ) {
                    navItems.forEach { item ->
                        val isSelected = when (item.screen) {
                            AppScreen.HOME -> currentScreen == AppScreen.HOME
                            AppScreen.MODULES_LIST -> currentScreen == AppScreen.MODULES_LIST
                            AppScreen.QUIZ_LIST -> currentScreen == AppScreen.QUIZ_LIST || currentScreen == AppScreen.QUIZ_ACTIVE
                            AppScreen.STORY -> currentScreen == AppScreen.STORY
                            AppScreen.PAPER_RELIEF -> currentScreen == AppScreen.PAPER_RELIEF
                            AppScreen.MEDITATION -> currentScreen == AppScreen.MEDITATION
                            AppScreen.BADGES -> currentScreen == AppScreen.BADGES
                            AppScreen.QUIZ_ACTIVE -> false
                        }

                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { viewModel.navigateTo(item.screen) },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            },
                            label = {
                                Text(
                                    text = item.title,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = ZenDeepPurple.copy(alpha = 0.15f)
                            ),
                            modifier = Modifier.testTag(item.testTag)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                AppScreen.HOME -> HomeScreen(viewModel = viewModel)
                AppScreen.MODULES_LIST -> ModulesScreen(viewModel = viewModel)
                AppScreen.QUIZ_LIST, AppScreen.QUIZ_ACTIVE -> QuizScreen(viewModel = viewModel)
                AppScreen.STORY -> StoryScreen(viewModel = viewModel)
                AppScreen.PAPER_RELIEF -> PaperReliefScreen(viewModel = viewModel)
                AppScreen.MEDITATION -> MeditationScreen(viewModel = viewModel)
                AppScreen.BADGES -> TrophyScreen(viewModel = viewModel)
            }
        }
    }
}
