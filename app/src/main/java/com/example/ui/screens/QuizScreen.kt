package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.QuizRepository
import com.example.model.EmotionModule
import com.example.model.QuizCircuit
import com.example.ui.theme.CoralRed
import com.example.ui.theme.MintGreen
import com.example.ui.theme.TurboAmber
import com.example.ui.theme.TurboOrange
import com.example.ui.theme.ZenDeepPurple
import com.example.ui.theme.ZenLavender
import com.example.viewmodel.AppScreen
import com.example.viewmodel.MainViewModel

@Composable
fun QuizScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val quizState by viewModel.quizState.collectAsState()
    val stats by viewModel.userStats.collectAsState()
    val activeModule = viewModel.activeModule

    BackHandler {
        if (quizState.circuit != null && !quizState.isCompleted) {
            viewModel.quitQuiz()
        } else {
            viewModel.navigateTo(AppScreen.HOME)
        }
    }

    if (quizState.circuit == null || quizState.isCompleted) {
        if (quizState.isCompleted) {
            QuizCompletionView(
                circuit = quizState.circuit!!,
                correctCount = quizState.correctAnswersCount,
                stars = quizState.earnedStars,
                totalQuestions = quizState.circuit!!.questions.size,
                onReplay = { viewModel.startCircuit(quizState.circuit!!) },
                onBackToTracks = { viewModel.quitQuiz() }
            )
        } else {
            QuizCircuitListView(
                activeModule = activeModule,
                allModules = QuizRepository.modules,
                completedCircuitKeys = stats.completedCircuitKeys,
                starsMap = stats.starsPerCircuit,
                onSelectModule = { moduleId -> viewModel.selectModule(moduleId) },
                onCircuitSelect = { circuit -> viewModel.startCircuit(circuit) },
                onBack = { viewModel.navigateTo(AppScreen.HOME) },
                modifier = modifier
            )
        }
    } else {
        ActiveQuestionView(
            circuit = quizState.circuit!!,
            questionIndex = quizState.currentQuestionIndex,
            selectedOption = quizState.selectedOptionIndex,
            isRevealed = quizState.isAnswerRevealed,
            onOptionSelected = { idx -> viewModel.selectOption(idx) },
            onNext = { viewModel.nextQuestion() },
            onQuit = { viewModel.quitQuiz() },
            modifier = modifier
        )
    }
}

@Composable
fun QuizCircuitListView(
    activeModule: EmotionModule,
    allModules: List<EmotionModule>,
    completedCircuitKeys: Set<String>,
    starsMap: Map<String, Int>,
    onSelectModule: (String) -> Unit,
    onCircuitSelect: (QuizCircuit) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val moduleColor = Color(activeModule.colorHex)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("circuit_list_screen"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp)
    ) {
        // Header
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("btn_back_home")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Quizzes das Emoções 🏆",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp
                    )
                    Text(
                        text = "Escolha o capítulo e vença os desafios!",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Chapter Selector Chips
        item {
            Text(
                text = "Capítulo Atual:",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp)
            ) {
                items(allModules) { module ->
                    val isSelected = module.id == activeModule.id
                    val mColor = Color(module.colorHex)

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) mColor else MaterialTheme.colorScheme.surface,
                        border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E0E0)),
                        shadowElevation = if (isSelected) 2.dp else 0.dp,
                        modifier = Modifier
                            .clickable { onSelectModule(module.id) }
                            .testTag("chip_module_${module.id}")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = module.emoji, fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Cap. ${module.chapterNumber}",
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // Active Chapter Header Card
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = moduleColor.copy(alpha = 0.12f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = activeModule.emoji, fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Capítulo ${activeModule.chapterNumber}: ${activeModule.title}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = moduleColor
                        )
                        Text(
                            text = "${activeModule.circuits.size} Pistas de Desafios Disponíveis",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Circuits list for this chapter
        items(activeModule.circuits) { circuit ->
            val isCompleted = completedCircuitKeys.contains(circuit.uniqueKey)
            val stars = starsMap[circuit.uniqueKey] ?: 0
            val circuitColor = Color(circuit.colorHex)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { onCircuitSelect(circuit) }
                    .testTag("circuit_card_${circuit.id}"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(circuitColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = circuit.emoji, fontSize = 26.sp)
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Pista ${circuit.id}",
                                color = circuitColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                            if (isCompleted) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Concluído",
                                    tint = MintGreen,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = circuit.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )

                        Text(
                            text = circuit.subtitle,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        // Star display
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            for (i in 1..3) {
                                Icon(
                                    imageVector = if (i <= stars) Icons.Default.Star else Icons.Default.StarBorder,
                                    contentDescription = "Estrela $i",
                                    tint = if (i <= stars) TurboAmber else Color(0xFFBDBDBD),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Jogar",
                        tint = circuitColor
                    )
                }
            }
        }
    }
}

@Composable
fun ActiveQuestionView(
    circuit: QuizCircuit,
    questionIndex: Int,
    selectedOption: Int?,
    isRevealed: Boolean,
    onOptionSelected: (Int) -> Unit,
    onNext: () -> Unit,
    onQuit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val question = circuit.questions[questionIndex]
    val total = circuit.questions.size
    val progress = (questionIndex + 1).toFloat() / total.toFloat()
    val circuitColor = Color(circuit.colorHex)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("active_question_screen"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp)
    ) {
        // Top Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onQuit, modifier = Modifier.testTag("btn_quit_quiz")) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Sair do Quiz"
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = circuit.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = circuitColor
                    )
                    Text(
                        text = "Pergunta ${questionIndex + 1} de $total",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(circuitColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = circuit.emoji, fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = circuitColor,
                trackColor = Color(0xFFEEEEEE)
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        // Question Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "DESAFIO 🧠",
                        color = TurboOrange,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = question.question,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        lineHeight = 24.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Options
        item {
            question.options.forEachIndexed { index, optionText ->
                val isSelected = selectedOption == index
                val isCorrectAnswer = index == question.correctIndex

                val borderColor = when {
                    !isRevealed -> if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFFE0E0E0)
                    isCorrectAnswer -> MintGreen
                    isSelected -> CoralRed
                    else -> Color(0xFFE0E0E0)
                }

                val bgColor = when {
                    !isRevealed -> if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surface
                    isCorrectAnswer -> MintGreen.copy(alpha = 0.12f)
                    isSelected -> CoralRed.copy(alpha = 0.12f)
                    else -> MaterialTheme.colorScheme.surface
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(2.dp, borderColor, RoundedCornerShape(16.dp))
                        .clickable(enabled = !isRevealed) { onOptionSelected(index) }
                        .testTag("option_button_$index"),
                    color = bgColor
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val letter = ('A' + index).toString()
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(borderColor.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = letter,
                                fontWeight = FontWeight.Bold,
                                color = if (isRevealed && isCorrectAnswer) MintGreen else MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Text(
                            text = optionText,
                            fontSize = 15.sp,
                            fontWeight = if (isSelected || (isRevealed && isCorrectAnswer)) FontWeight.Bold else FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )

                        if (isRevealed) {
                            if (isCorrectAnswer) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Correto",
                                    tint = MintGreen,
                                    modifier = Modifier.size(24.dp)
                                )
                            } else if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Incorreto",
                                    tint = CoralRed,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Explanation & Next Button (when revealed)
        item {
            AnimatedVisibility(
                visible = isRevealed,
                enter = fadeIn() + slideInVertically()
            ) {
                Column {
                    val wasCorrect = selectedOption == question.correctIndex

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (wasCorrect) Color(0xFFE8F5E9) else Color(0xFFFFF3E0)
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (wasCorrect) "🎉 Mandou Muito Bem!" else "💡 Quase lá! Veja a dica:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = if (wasCorrect) Color(0xFF2E7D32) else Color(0xFFE65100)
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = question.explanation,
                                fontSize = 13.sp,
                                lineHeight = 18.sp,
                                color = Color(0xFF263238)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(verticalAlignment = Alignment.Top) {
                                Icon(
                                    imageVector = Icons.Default.Lightbulb,
                                    contentDescription = "Dica",
                                    tint = TurboAmber,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = question.funTip,
                                    fontSize = 12.sp,
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                    color = Color(0xFF37474F)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = onNext,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("btn_next_question"),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = circuitColor)
                    ) {
                        Text(
                            text = if (questionIndex + 1 < total) "Próxima Pergunta ➡️" else "Ver Meu Troféu! 🏆",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuizCompletionView(
    circuit: QuizCircuit,
    correctCount: Int,
    stars: Int,
    totalQuestions: Int,
    onReplay: () -> Unit,
    onBackToTracks: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .testTag("quiz_completion_screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(TurboAmber.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = "Troféu",
                tint = TurboAmber,
                modifier = Modifier.size(56.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Pista Concluída! 🏁",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp
        )

        Text(
            text = circuit.title,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Stars
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 1..3) {
                Icon(
                    imageVector = if (i <= stars) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = "Estrela",
                    tint = if (i <= stars) TurboAmber else Color(0xFFBDBDBD),
                    modifier = Modifier
                        .size(42.dp)
                        .padding(horizontal = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Você acertou $correctCount de $totalQuestions perguntas!",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        val praise = when (stars) {
            3 -> "🌟 Perfeito! Você dominou os desafios das emoções deste circuito!"
            2 -> "👏 Muito bem! Você pegou o jeito de entender e cuidar da sua mente!"
            else -> "💪 Bom treino! Que tal jogar de novo para fixar todas as dicas secretas?"
        }

        Text(
            text = praise,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 13.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = onBackToTracks,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("btn_return_tracks"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ZenDeepPurple)
        ) {
            Text("Ver Todas as Pistas 🏎️", fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onReplay,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("btn_replay_circuit"),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Jogar Novamente")
            Spacer(modifier = Modifier.width(6.dp))
            Text("Jogar Essa Pista de Novo", fontWeight = FontWeight.Bold)
        }
    }
}
