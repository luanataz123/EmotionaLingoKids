package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PaperNote
import com.example.ui.theme.MintGreen
import com.example.ui.theme.TurboAmber
import com.example.ui.theme.TurboOrange
import com.example.ui.theme.ZenDeepPurple
import com.example.viewmodel.AppScreen
import com.example.viewmodel.MainViewModel

data class EmotionSticker(val name: String, val emoji: String)

@Composable
fun PaperReliefScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateTo(AppScreen.HOME)
    }

    val notes by viewModel.notes.collectAsState()
    var noteInput by remember { mutableStateOf("") }

    val stickers = remember {
        listOf(
            EmotionSticker("Preocupação", "⚡"),
            EmotionSticker("Acelerado", "🤯"),
            EmotionSticker("Lição / Tarefa", "🎒"),
            EmotionSticker("Chateado", "😢"),
            EmotionSticker("Bravo", "😤"),
            EmotionSticker("Ideia", "💡")
        )
    }
    var selectedSticker by remember { mutableStateOf(stickers[0]) }
    var showReliefCelebration by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("paper_relief_screen"),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp)
    ) {
        // Top Header
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                IconButton(
                    onClick = { viewModel.navigateTo(AppScreen.HOME) },
                    modifier = Modifier.testTag("btn_paper_back")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Descarrego no Papel 📝",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp
                    )
                    Text(
                        text = "Estratégia 1: Dê autorização para a mente tirar folga!",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Educational Insight Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2F1))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "🧠✨", fontSize = 32.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Quando tiramos as coisas da cabeça e colocamos no papel, o cérebro sabe que nada será esquecido. O modo 'fazer' relaxa!",
                        fontSize = 12.sp,
                        color = Color(0xFF004D40),
                        lineHeight = 16.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // New Note Box
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "O que tá pesando na sua cabeça agora?",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Sticker Picker
                    Text(
                        text = "Escolha um selo:",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(stickers) { sticker ->
                            val isSelected = selectedSticker == sticker
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) ZenDeepPurple else Color(0xFFF5F5F5),
                                modifier = Modifier
                                    .clickable { selectedSticker = sticker }
                                    .testTag("sticker_${sticker.name}")
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = sticker.emoji, fontSize = 16.sp)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = sticker.name,
                                        fontSize = 12.sp,
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = noteInput,
                        onValueChange = { noteInput = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_paper_note"),
                        placeholder = {
                            Text("Ex: Lição de matemática, briga com o colega, prova de amanhã...")
                        },
                        minLines = 3,
                        maxLines = 5,
                        shape = RoundedCornerShape(16.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Action 1: Shred / Release
                        Button(
                            onClick = {
                                if (noteInput.isNotBlank()) {
                                    viewModel.addPaperNote(
                                        text = noteInput,
                                        emotion = selectedSticker.name,
                                        emoji = selectedSticker.emoji,
                                        shredImmediately = true
                                    )
                                    noteInput = ""
                                    showReliefCelebration = true
                                }
                            },
                            enabled = noteInput.isNotBlank(),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("btn_shred_note"),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = TurboOrange)
                        ) {
                            Text("Tirar da Mente! 🚀", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }

                        // Action 2: Save in Safe Journal
                        Button(
                            onClick = {
                                if (noteInput.isNotBlank()) {
                                    viewModel.addPaperNote(
                                        text = noteInput,
                                        emotion = selectedSticker.name,
                                        emoji = selectedSticker.emoji,
                                        shredImmediately = false
                                    )
                                    noteInput = ""
                                }
                            },
                            enabled = noteInput.isNotBlank(),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("btn_save_note"),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00897B))
                        ) {
                            Text("Guardar no Diário 📓", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Celebration Banner when shredded
        item {
            AnimatedVisibility(
                visible = showReliefCelebration,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .clickable { showReliefCelebration = false },
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "🎉💨", fontSize = 32.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Ufa! Preocupação Descarregada!",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFF57F17),
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Sua mente recebeu autorização para relaxar agora. O motor esfriou!",
                                fontSize = 12.sp,
                                color = Color(0xFF5D4037)
                            )
                        }
                        IconButton(onClick = { showReliefCelebration = false }) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = "OK")
                        }
                    }
                }
            }
        }

        // Notes List Header
        item {
            Text(
                text = "Caderno de Desabafo & Tarefas (${notes.size}) 📝",
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        if (notes.isEmpty()) {
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "📝✨", fontSize = 36.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Nenhuma anotação pendente!",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Text(
                            text = "Sua cabeça está limpinha. Quando surgir uma preocupação, descarregue aqui!",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            items(notes) { note ->
                PaperNoteCard(
                    note = note,
                    onShred = { viewModel.shredNote(note.id) },
                    onDelete = { viewModel.deleteNote(note.id) }
                )
            }
        }
    }
}

@Composable
fun PaperNoteCard(
    note: PaperNote,
    onShred: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .testTag("note_item_${note.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (note.isShredded) Color(0xFFF5F5F5) else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (note.isShredded) 0.dp else 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(if (note.isShredded) Color(0xFFE0E0E0) else TurboAmber.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = note.emoji, fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = note.emotion,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (note.isShredded) Color.Gray else TurboOrange
                    )
                    if (note.isShredded) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = MintGreen.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "Aliviado ✨",
                                color = MintGreen,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = note.text,
                    fontSize = 14.sp,
                    textDecoration = if (note.isShredded) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (note.isShredded) Color.Gray else MaterialTheme.colorScheme.onSurface
                )
            }

            if (!note.isShredded) {
                IconButton(
                    onClick = onShred,
                    modifier = Modifier.testTag("btn_shred_${note.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteSweep,
                        contentDescription = "Dar folga da mente",
                        tint = TurboOrange
                    )
                }
            } else {
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.testTag("btn_delete_${note.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Apagar",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}
