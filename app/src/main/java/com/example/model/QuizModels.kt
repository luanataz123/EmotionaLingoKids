package com.example.model

data class StoryChapter(
    val id: Int,
    val moduleId: String = "modulo_1_motor_da_mente",
    val title: String,
    val emoji: String,
    val summary: String,
    val content: String,
    val secretTip: String,
    val accentColorHex: Long
)

data class QuizQuestion(
    val id: Int,
    val circuitId: Int,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val funTip: String
)

data class QuizCircuit(
    val id: Int,
    val moduleId: String = "modulo_1_motor_da_mente",
    val title: String,
    val subtitle: String,
    val emoji: String,
    val colorHex: Long,
    val questions: List<QuizQuestion>
) {
    val uniqueKey: String get() = "${moduleId}_${id}"
}

data class EmotionModule(
    val id: String,
    val chapterNumber: Int,
    val title: String,
    val subtitle: String,
    val emoji: String,
    val summary: String,
    val colorHex: Long,
    val storyChapters: List<StoryChapter>,
    val circuits: List<QuizCircuit>,
    val hasInteractiveTools: Boolean = false,
    val isComingSoon: Boolean = false
)

data class Badge(
    val id: String,
    val title: String,
    val description: String,
    val emoji: String,
    val isUnlocked: Boolean = false
)

data class PaperNote(
    val id: String,
    val text: String,
    val emotion: String,
    val emoji: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isShredded: Boolean = false
)

data class UserStats(
    val totalScore: Int = 0,
    val selectedModuleId: String = "modulo_1_motor_da_mente",
    val completedCircuitKeys: Set<String> = emptySet(),
    val starsPerCircuit: Map<String, Int> = emptyMap(),
    val notesCreatedCount: Int = 0,
    val breathsCompletedCount: Int = 0,
    val avatarEmoji: String = "🧠🏎️",
    val avatarName: String = "Piloto Zen",
    val unlockedBadgeIds: Set<String> = emptySet()
)
