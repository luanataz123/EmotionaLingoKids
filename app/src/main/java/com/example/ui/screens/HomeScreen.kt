package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SportsScore
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.QuizRepository
import com.example.model.EmotionModule
import com.example.ui.theme.MintGreen
import com.example.ui.theme.TurboAmber
import com.example.ui.theme.TurboOrange
import com.example.ui.theme.ZenDeepPurple
import com.example.ui.theme.ZenLavender
import com.example.viewmodel.AppScreen
import com.example.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val stats by viewModel.userStats.collectAsState()
    val heatPercentage by viewModel.engineHeatPercentage.collectAsState()
    val activeModule = viewModel.activeModule

    val animatedHeat by animateFloatAsState(
        targetValue = heatPercentage / 100f,
        animationSpec = tween(durationMillis = 800),
        label = "heat"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen_scroll"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // App Header Brand
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "EmotionaLingo Kids 🌟",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp,
                        color = ZenDeepPurple
                    )
                    Text(
                        text = "Aprenda sobre seus sentimentos brincando!",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = ZenDeepPurple.copy(alpha = 0.12f),
                    modifier = Modifier.clickable { viewModel.navigateTo(AppScreen.BADGES) }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stats.avatarEmoji, fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${stats.totalScore} XP",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = ZenDeepPurple
                        )
                    }
                }
            }
        }

        // Active Chapter Hero Card
        item {
            val moduleColor = Color(activeModule.colorHex)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(moduleColor, moduleColor.copy(alpha = 0.85f))
                        )
                    )
            ) {
                Column {
                    if (activeModule.id == "modulo_1_motor_da_mente") {
                        Image(
                            painter = painterResource(id = R.drawable.img_hero_banner),
                            contentDescription = "O Motor da Cabeça - Banner",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color.White.copy(alpha = 0.25f)
                            ) {
                                Text(
                                    text = "CAPÍTULO ${activeModule.chapterNumber}",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            // Switch chapter button
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color.White.copy(alpha = 0.2f),
                                modifier = Modifier.clickable { viewModel.navigateTo(AppScreen.MODULES_LIST) }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Map,
                                        contentDescription = "Ver Capítulos",
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Ver Todos (${QuizRepository.modules.size})",
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "${activeModule.title} ${activeModule.emoji}",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )

                        Text(
                            text = activeModule.subtitle,
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = activeModule.summary,
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )
                    }
                }
            }
        }

        // Engine Temperature / RPM Meter ("Termômetro da Mente")
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Speed,
                                contentDescription = "Velocímetro",
                                tint = if (heatPercentage > 60) TurboOrange else MintGreen,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Termômetro da Mente",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (heatPercentage > 60) Color(0xFFFFEBEE) else Color(0xFFE8F8F0)
                        ) {
                            Text(
                                text = if (heatPercentage > 60) "Acelerado $heatPercentage%" else "Zen $heatPercentage%",
                                color = if (heatPercentage > 60) TurboOrange else MintGreen,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LinearProgressIndicator(
                        progress = { animatedHeat },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        color = if (heatPercentage > 60) TurboOrange else MintGreen,
                        trackColor = Color(0xFFE0E0E0)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    val statusMsg = when {
                        heatPercentage > 75 -> "🔥 Modo 220V ativado! Jogue os quizzes ou faça uma pausa para esfriar o motor."
                        heatPercentage > 40 -> "🏎️ Equilibrando o motor! Você está aprendendo a dar folga para os pensamentos."
                        else -> "🧘✨ Modo 'Ser e Estar' no comando! Sua mente está calma, focada e presente."
                    }

                    Text(
                        text = statusMsg,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // Horizontal Chapters Carousel
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 18.dp, end = 18.dp, top = 14.dp, bottom = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Capítulos do EmotionaLingo 🗺️",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "Ver todos",
                    color = ZenDeepPurple,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.clickable { viewModel.navigateTo(AppScreen.MODULES_LIST) }
                )
            }

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(QuizRepository.modules) { module ->
                    val isSelected = stats.selectedModuleId == module.id
                    val mColor = Color(module.colorHex)

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) mColor.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface,
                        border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, mColor) else null,
                        shadowElevation = 2.dp,
                        modifier = Modifier
                            .width(160.dp)
                            .clickable { viewModel.selectModule(module.id) }
                            .testTag("carousel_module_${module.id}")
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = module.emoji, fontSize = 24.sp)
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = mColor.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        text = "Cap. ${module.chapterNumber}",
                                        color = mColor,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp,
                                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = module.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                maxLines = 1
                            )
                            Text(
                                text = "${module.circuits.size} Pistas Quiz",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Section Title
        item {
            Text(
                text = "Atividades do Capítulo Atual 🚀",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 18.dp, top = 20.dp, bottom = 8.dp)
            )
        }

        // Feature 1: Quiz Challenge for active module
        item {
            val totalInActive = activeModule.circuits.size
            val doneInActive = activeModule.circuits.count { stats.completedCircuitKeys.contains(it.uniqueKey) }

            MenuActionCard(
                title = "Quiz: ${activeModule.title} 🏆",
                subtitle = "Jogue as $totalInActive pistas de perguntas e desafios deste capítulo!",
                tag = "$doneInActive/$totalInActive Concluídas",
                color = Color(activeModule.colorHex),
                iconEmoji = activeModule.emoji,
                testTag = "action_start_quiz",
                onClick = { viewModel.navigateTo(AppScreen.QUIZ_LIST) }
            )
        }

        // Feature 2: Storybook for active module
        item {
            MenuActionCard(
                title = "Histórias do Capítulo 📖✨",
                subtitle = "Leia as ${activeModule.storyChapters.size} histórias ilustradas com dicas secretas!",
                tag = "${activeModule.storyChapters.size} Histórias",
                color = ZenLavender,
                iconEmoji = "📚",
                testTag = "action_open_story",
                onClick = { viewModel.navigateTo(AppScreen.STORY) }
            )
        }

        // Feature 3: Descarregue no Papel
        item {
            MenuActionCard(
                title = "Estratégia 1: Descarrego no Papel 📝",
                subtitle = "Tire preocupações e lições da cabeça para o cérebro relaxar de verdade!",
                tag = "${stats.notesCreatedCount} Notas Aliviadas",
                color = Color(0xFF00897B),
                iconEmoji = "📝",
                testTag = "action_open_paper",
                onClick = { viewModel.navigateTo(AppScreen.PAPER_RELIEF) }
            )
        }

        // Feature 4: Viagem para Dentro (Respiração)
        item {
            MenuActionCard(
                title = "Estratégia 2: Viagem Interior 🚀🧘",
                subtitle = "Respiração e meditação 'Ser e Estar' guiada para desacelerar o motor!",
                tag = "${stats.breathsCompletedCount} Ciclos Feitos",
                color = ZenDeepPurple,
                iconEmoji = "✨",
                testTag = "action_open_meditation",
                onClick = { viewModel.navigateTo(AppScreen.MEDITATION) }
            )
        }
    }
}

@Composable
fun MenuActionCard(
    title: String,
    subtitle: String,
    tag: String,
    color: Color,
    iconEmoji: String,
    testTag: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable(onClick = onClick)
            .testTag(testTag),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = iconEmoji, fontSize = 26.sp)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = color.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = tag,
                        color = color,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 16.sp
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Abrir",
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

