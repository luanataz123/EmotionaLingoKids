package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MintGreen
import com.example.ui.theme.TurboAmber
import com.example.ui.theme.ZenDeepPurple
import com.example.ui.theme.ZenLavender
import com.example.viewmodel.AppScreen
import com.example.viewmodel.BreathPhase
import com.example.viewmodel.MainViewModel

@Composable
fun MeditationScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.stopBreathing()
        viewModel.navigateTo(AppScreen.HOME)
    }

    val isRunning by viewModel.isBreathingActive.collectAsState()
    val phase by viewModel.currentBreathPhase.collectAsState()
    val secondsLeft by viewModel.phaseSecondsLeft.collectAsState()
    val cyclesDone by viewModel.breathCyclesDone.collectAsState()
    val soundEnabled by viewModel.soundEnabled.collectAsState()

    val targetScale = when (phase) {
        BreathPhase.INHALE -> 1.35f
        BreathPhase.HOLD -> 1.35f
        BreathPhase.EXHALE -> 0.85f
        BreathPhase.REST -> 0.95f
    }

    val animatedScale by animateFloatAsState(
        targetValue = if (isRunning) targetScale else 1f,
        animationSpec = tween(
            durationMillis = if (isRunning) phase.seconds * 1000 else 400,
            easing = FastOutSlowInEasing
        ),
        label = "breath_scale"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("meditation_screen"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
                    onClick = {
                        viewModel.stopBreathing()
                        viewModel.navigateTo(AppScreen.HOME)
                    },
                    modifier = Modifier.testTag("btn_meditation_back")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Viagem Interior 🚀🧘",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp
                    )
                    Text(
                        text = "Estratégia 2: Uma pausa para se conectar com você!",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(
                    onClick = { viewModel.toggleSound() },
                    modifier = Modifier.testTag("btn_toggle_sound")
                ) {
                    Icon(
                        imageVector = if (soundEnabled) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
                        contentDescription = "Som",
                        tint = ZenDeepPurple
                    )
                }
            }
        }

        // Animated Breathing Bubble
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(290.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF3F1D78),
                                Color(0xFF1E0E3B),
                                Color(0xFF0F071D)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Outermost ring
                Box(
                    modifier = Modifier
                        .size(240.dp)
                        .scale(animatedScale)
                        .border(
                            width = 2.dp,
                            color = Color(0xFFB39DDB).copy(alpha = 0.35f),
                            shape = CircleShape
                        )
                )

                // Middle glowing bubble
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .scale(animatedScale)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFF7E57C2).copy(alpha = 0.8f),
                                    Color(0xFF512DA8).copy(alpha = 0.5f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                // Core content
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = if (isRunning) phase.emoji else "🧘",
                        fontSize = 38.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = if (isRunning) phase.label else "Toque em Iniciar Viagem",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )

                    if (isRunning) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "$secondsLeft s",
                            color = TurboAmber,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // Controls
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        if (isRunning) viewModel.stopBreathing() else viewModel.startBreathing()
                    },
                    modifier = Modifier
                        .height(54.dp)
                        .fillMaxWidth(0.85f)
                        .testTag("btn_start_stop_breathing"),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isRunning) Color(0xFFE53935) else ZenDeepPurple
                    )
                ) {
                    Icon(
                        imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isRunning) "Pausar" else "Iniciar",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isRunning) "Pausar Viagem" else "Iniciar Viagem 'Ser e Estar' 🚀",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
            ) {
                Text(
                    text = "Ciclos Concluídos: $cyclesDone 🧘",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // Mindful Guide Cards for 8-10 year olds
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Como fazer a sua viagem interior: 🌟",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = ZenDeepPurple
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    MeditationStepRow(
                        num = "1",
                        text = "Sente-se confortavelmente e solte os ombros longe das orelhas."
                    )
                    MeditationStepRow(
                        num = "2",
                        text = "Acompanhe o círculo: inspire quando ele cresce, solte o ar quando ele diminui."
                    )
                    MeditationStepRow(
                        num = "3",
                        text = "Imagine que o seu motorzinho 220V está esfriando e ganhando um carinho."
                    )
                    MeditationStepRow(
                        num = "4",
                        text = "Você não tem que fazer nada agora. Só ser e curtir esse momento!"
                    )
                }
            }
        }
    }
}

@Composable
fun MeditationStepRow(num: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(ZenLavender.copy(alpha = 0.25f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = num,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = ZenDeepPurple
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
