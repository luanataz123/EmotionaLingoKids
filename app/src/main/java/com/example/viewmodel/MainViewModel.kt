package com.example.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.QuizRepository
import com.example.model.Badge
import com.example.model.EmotionModule
import com.example.model.PaperNote
import com.example.model.QuizCircuit
import com.example.model.QuizQuestion
import com.example.model.StoryChapter
import com.example.model.UserStats
import com.example.util.SoundHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

enum class AppScreen {
    HOME,
    MODULES_LIST,
    QUIZ_LIST,
    QUIZ_ACTIVE,
    STORY,
    PAPER_RELIEF,
    MEDITATION,
    BADGES
}

enum class BreathPhase(val label: String, val seconds: Int, val emoji: String) {
    INHALE("Inspira a calma... 🌸", 4, "🌬️"),
    HOLD("Segura a paz... ✨", 4, "✨"),
    EXHALE("Solta a correria... 💨", 4, "🍃"),
    REST("Sinta o agora... 🧘", 2, "🌟")
}

data class QuizSessionState(
    val circuit: QuizCircuit? = null,
    val currentQuestionIndex: Int = 0,
    val selectedOptionIndex: Int? = null,
    val isAnswerRevealed: Boolean = false,
    val correctAnswersCount: Int = 0,
    val isCompleted: Boolean = false,
    val earnedStars: Int = 0
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("emotionalingo_kids_prefs", Context.MODE_PRIVATE)

    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _userStats = MutableStateFlow(UserStats())
    val userStats: StateFlow<UserStats> = _userStats.asStateFlow()

    private val _quizState = MutableStateFlow(QuizSessionState())
    val quizState: StateFlow<QuizSessionState> = _quizState.asStateFlow()

    private val _notes = MutableStateFlow<List<PaperNote>>(emptyList())
    val notes: StateFlow<List<PaperNote>> = _notes.asStateFlow()

    // Active module being viewed or played
    val activeModule: EmotionModule
        get() = QuizRepository.getModule(_userStats.value.selectedModuleId)

    // Meditation State
    private val _isBreathingActive = MutableStateFlow(false)
    val isBreathingActive: StateFlow<Boolean> = _isBreathingActive.asStateFlow()

    private val _currentBreathPhase = MutableStateFlow(BreathPhase.INHALE)
    val currentBreathPhase: StateFlow<BreathPhase> = _currentBreathPhase.asStateFlow()

    private val _phaseSecondsLeft = MutableStateFlow(4)
    val phaseSecondsLeft: StateFlow<Int> = _phaseSecondsLeft.asStateFlow()

    private val _breathCyclesDone = MutableStateFlow(0)
    val breathCyclesDone: StateFlow<Int> = _breathCyclesDone.asStateFlow()

    // Sound toggle
    private val _soundEnabled = MutableStateFlow(true)
    val soundEnabled: StateFlow<Boolean> = _soundEnabled.asStateFlow()

    // Engine Zen Status (0: Ultra Zen, 100: 220V Super Acelerado)
    private val _engineHeatPercentage = MutableStateFlow(85)
    val engineHeatPercentage: StateFlow<Int> = _engineHeatPercentage.asStateFlow()

    private var breathJob: Job? = null

    init {
        loadPersistedData()
        updateEngineHeat()
    }

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun selectModule(moduleId: String) {
        _userStats.update { it.copy(selectedModuleId = moduleId) }
        saveUserData()
    }

    fun toggleSound() {
        _soundEnabled.value = !_soundEnabled.value
    }

    fun selectAvatar(emoji: String, name: String) {
        _userStats.update { it.copy(avatarEmoji = emoji, avatarName = name) }
        saveUserData()
    }

    // --- QUIZ ACTIONS ---
    fun startCircuit(circuit: QuizCircuit) {
        // Also ensure current active module is synchronized with circuit
        _userStats.update { it.copy(selectedModuleId = circuit.moduleId) }
        _quizState.value = QuizSessionState(
            circuit = circuit,
            currentQuestionIndex = 0,
            selectedOptionIndex = null,
            isAnswerRevealed = false,
            correctAnswersCount = 0,
            isCompleted = false,
            earnedStars = 0
        )
        _currentScreen.value = AppScreen.QUIZ_ACTIVE
    }

    fun selectOption(index: Int) {
        val current = _quizState.value
        if (current.isAnswerRevealed || current.circuit == null) return

        val question = current.circuit.questions.getOrNull(current.currentQuestionIndex) ?: return
        val isCorrect = index == question.correctIndex

        if (_soundEnabled.value) {
            if (isCorrect) SoundHelper.playCorrect() else SoundHelper.playWrong()
            SoundHelper.vibrate(getApplication(), if (isCorrect) 70 else 120)
        }

        val newCorrectCount = if (isCorrect) current.correctAnswersCount + 1 else current.correctAnswersCount

        _quizState.update {
            it.copy(
                selectedOptionIndex = index,
                isAnswerRevealed = true,
                correctAnswersCount = newCorrectCount
            )
        }
    }

    fun nextQuestion() {
        val current = _quizState.value
        val circuit = current.circuit ?: return

        if (current.currentQuestionIndex + 1 < circuit.questions.size) {
            _quizState.update {
                it.copy(
                    currentQuestionIndex = it.currentQuestionIndex + 1,
                    selectedOptionIndex = null,
                    isAnswerRevealed = false
                )
            }
        } else {
            // Finish circuit
            val totalQuestions = circuit.questions.size
            val correct = current.correctAnswersCount
            val stars = when {
                correct == totalQuestions -> 3
                correct >= 1 -> 2
                else -> 1
            }

            val pointsEarned = correct * 50 + stars * 25
            if (_soundEnabled.value) {
                SoundHelper.playVictory()
                SoundHelper.vibrate(getApplication(), 150)
            }

            _quizState.update {
                it.copy(
                    isCompleted = true,
                    earnedStars = stars
                )
            }

            val circuitKey = circuit.uniqueKey

            _userStats.update { prev ->
                val newCompleted = prev.completedCircuitKeys + circuitKey
                val prevStars = prev.starsPerCircuit[circuitKey] ?: 0
                val bestStars = maxOf(prevStars, stars)
                val newMap = prev.starsPerCircuit + (circuitKey to bestStars)
                prev.copy(
                    totalScore = prev.totalScore + pointsEarned,
                    completedCircuitKeys = newCompleted,
                    starsPerCircuit = newMap
                )
            }

            checkBadges()
            updateEngineHeat()
            saveUserData()
        }
    }

    fun quitQuiz() {
        _currentScreen.value = AppScreen.QUIZ_LIST
    }

    // --- PAPER RELIEF ACTIONS ---
    fun addPaperNote(text: String, emotion: String, emoji: String, shredImmediately: Boolean) {
        if (text.isBlank()) return

        val note = PaperNote(
            id = UUID.randomUUID().toString(),
            text = text.trim(),
            emotion = emotion,
            emoji = emoji,
            isShredded = shredImmediately
        )

        _notes.update { listOf(note) + it }
        _userStats.update { it.copy(notesCreatedCount = it.notesCreatedCount + 1) }

        if (_soundEnabled.value) {
            SoundHelper.playCorrect()
            SoundHelper.vibrate(getApplication(), 90)
        }

        checkBadges()
        updateEngineHeat()
        saveUserData()
    }

    fun shredNote(noteId: String) {
        _notes.update { list ->
            list.map { if (it.id == noteId) it.copy(isShredded = true) else it }
        }
        if (_soundEnabled.value) {
            SoundHelper.playBreatheOut()
            SoundHelper.vibrate(getApplication(), 60)
        }
        updateEngineHeat()
        saveUserData()
    }

    fun deleteNote(noteId: String) {
        _notes.update { list -> list.filterNot { it.id == noteId } }
        saveUserData()
    }

    // --- MEDITATION ACTIONS ---
    fun startBreathing() {
        if (_isBreathingActive.value) return
        _isBreathingActive.value = true
        _breathCyclesDone.value = 0
        runBreathingLoop()
    }

    fun stopBreathing() {
        breathJob?.cancel()
        _isBreathingActive.value = false
        _currentBreathPhase.value = BreathPhase.INHALE
        _phaseSecondsLeft.value = 4
    }

    private fun runBreathingLoop() {
        breathJob?.cancel()
        breathJob = viewModelScope.launch {
            val phases = listOf(BreathPhase.INHALE, BreathPhase.HOLD, BreathPhase.EXHALE, BreathPhase.REST)
            while (_isBreathingActive.value) {
                for (phase in phases) {
                    _currentBreathPhase.value = phase
                    _phaseSecondsLeft.value = phase.seconds

                    if (_soundEnabled.value) {
                        if (phase == BreathPhase.INHALE) SoundHelper.playBreatheIn()
                        if (phase == BreathPhase.EXHALE) SoundHelper.playBreatheOut()
                    }

                    for (sec in phase.seconds downTo 1) {
                        _phaseSecondsLeft.value = sec
                        delay(1000)
                    }
                }
                _breathCyclesDone.value += 1
                _userStats.update { it.copy(breathsCompletedCount = it.breathsCompletedCount + 1) }
                checkBadges()
                updateEngineHeat()
                saveUserData()
            }
        }
    }

    private fun updateEngineHeat() {
        val completedCircuits = _userStats.value.completedCircuitKeys.size
        val notesCount = _userStats.value.notesCreatedCount
        val breaths = _userStats.value.breathsCompletedCount

        // 85% starting heat, decreases with learning and mindfulness
        val cooldown = (completedCircuits * 10) + (notesCount * 5) + (breaths * 8)
        val newHeat = (85 - cooldown).coerceIn(10, 100)
        _engineHeatPercentage.value = newHeat
    }

    private fun checkBadges() {
        val stats = _userStats.value
        val unlocked = stats.unlockedBadgeIds.toMutableSet()

        if (stats.completedCircuitKeys.isNotEmpty()) {
            unlocked.add("first_step")
        }
        if (stats.completedCircuitKeys.contains("modulo_1_motor_da_mente_1") &&
            stats.completedCircuitKeys.contains("modulo_1_motor_da_mente_2")
        ) {
            unlocked.add("calm_pilot")
        }
        if (stats.completedCircuitKeys.contains("modulo_1_motor_da_mente_3")) {
            unlocked.add("screen_wise")
        }
        if (stats.completedCircuitKeys.contains("modulo_1_motor_da_mente_4")) {
            unlocked.add("zen_driver")
        }
        if (stats.notesCreatedCount >= 1) {
            unlocked.add("paper_pro")
        }
        if (stats.breathsCompletedCount >= 1) {
            unlocked.add("space_traveler")
        }
        // Check if explored other modules
        val distinctModulesCompleted = stats.completedCircuitKeys.map { it.substringBeforeLast('_') }.toSet()
        if (distinctModulesCompleted.size >= 2) {
            unlocked.add("emotion_detective")
        }
        if (stats.completedCircuitKeys.size >= QuizRepository.circuits.size) {
            unlocked.add("grand_champion")
        }

        if (unlocked.size > stats.unlockedBadgeIds.size) {
            _userStats.update { it.copy(unlockedBadgeIds = unlocked) }
        }
    }

    private fun saveUserData() {
        val stats = _userStats.value
        val editor = prefs.edit()
        editor.putInt("score", stats.totalScore)
        editor.putString("selected_module_id", stats.selectedModuleId)
        editor.putString("avatar_emoji", stats.avatarEmoji)
        editor.putString("avatar_name", stats.avatarName)
        editor.putInt("notes_count", stats.notesCreatedCount)
        editor.putInt("breaths_count", stats.breathsCompletedCount)

        val completedJson = JSONArray(stats.completedCircuitKeys).toString()
        editor.putString("completed_circuits_keys", completedJson)

        val badgesJson = JSONArray(stats.unlockedBadgeIds).toString()
        editor.putString("unlocked_badges", badgesJson)

        val starsObj = JSONObject()
        stats.starsPerCircuit.forEach { (k, v) -> starsObj.put(k, v) }
        editor.putString("stars_map_keys", starsObj.toString())

        // Save notes
        val notesArray = JSONArray()
        _notes.value.take(20).forEach { note ->
            val n = JSONObject()
            n.put("id", note.id)
            n.put("text", note.text)
            n.put("emotion", note.emotion)
            n.put("emoji", note.emoji)
            n.put("timestamp", note.timestamp)
            n.put("isShredded", note.isShredded)
            notesArray.put(n)
        }
        editor.putString("notes_list", notesArray.toString())

        editor.apply()
    }

    private fun loadPersistedData() {
        val score = prefs.getInt("score", 0)
        val selectedModuleId = prefs.getString("selected_module_id", "modulo_1_motor_da_mente") ?: "modulo_1_motor_da_mente"
        val avatarEmoji = prefs.getString("avatar_emoji", "🧠🏎️") ?: "🧠🏎️"
        val avatarName = prefs.getString("avatar_name", "Piloto Zen") ?: "Piloto Zen"
        val notesCount = prefs.getInt("notes_count", 0)
        val breathsCount = prefs.getInt("breaths_count", 0)

        val completedSet = mutableSetOf<String>()
        prefs.getString("completed_circuits_keys", null)?.let { str ->
            try {
                val arr = JSONArray(str)
                for (i in 0 until arr.length()) completedSet.add(arr.getString(i))
            } catch (_: Exception) {}
        }
        // Fallback for legacy integer circuit keys
        if (completedSet.isEmpty()) {
            prefs.getString("completed_circuits", null)?.let { str ->
                try {
                    val arr = JSONArray(str)
                    for (i in 0 until arr.length()) completedSet.add("modulo_1_motor_da_mente_${arr.getInt(i)}")
                } catch (_: Exception) {}
            }
        }

        val badgeSet = mutableSetOf<String>()
        prefs.getString("unlocked_badges", null)?.let { str ->
            try {
                val arr = JSONArray(str)
                for (i in 0 until arr.length()) badgeSet.add(arr.getString(i))
            } catch (_: Exception) {}
        }

        val starsMap = mutableMapOf<String, Int>()
        prefs.getString("stars_map_keys", null)?.let { str ->
            try {
                val obj = JSONObject(str)
                obj.keys().forEach { key ->
                    starsMap[key] = obj.getInt(key)
                }
            } catch (_: Exception) {}
        }
        if (starsMap.isEmpty()) {
            prefs.getString("stars_map", null)?.let { str ->
                try {
                    val obj = JSONObject(str)
                    obj.keys().forEach { key ->
                        starsMap["modulo_1_motor_da_mente_$key"] = obj.getInt(key)
                    }
                } catch (_: Exception) {}
            }
        }

        _userStats.value = UserStats(
            totalScore = score,
            selectedModuleId = selectedModuleId,
            completedCircuitKeys = completedSet,
            starsPerCircuit = starsMap,
            notesCreatedCount = notesCount,
            breathsCompletedCount = breathsCount,
            avatarEmoji = avatarEmoji,
            avatarName = avatarName,
            unlockedBadgeIds = badgeSet
        )

        val loadedNotes = mutableListOf<PaperNote>()
        prefs.getString("notes_list", null)?.let { str ->
            try {
                val arr = JSONArray(str)
                for (i in 0 until arr.length()) {
                    val obj = arr.getJSONObject(i)
                    loadedNotes.add(
                        PaperNote(
                            id = obj.getString("id"),
                            text = obj.getString("text"),
                            emotion = obj.getString("emotion"),
                            emoji = obj.getString("emoji"),
                            timestamp = obj.optLong("timestamp", System.currentTimeMillis()),
                            isShredded = obj.optBoolean("isShredded", false)
                        )
                    )
                }
            } catch (_: Exception) {}
        }
        _notes.value = loadedNotes
    }

    override fun onCleared() {
        super.onCleared()
        breathJob?.cancel()
    }
}
