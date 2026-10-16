package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.QuizRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("EmotionaLingo Kids", appName)
  }

  @Test
  fun `verify modular chapters quiz tracks and story exist`() {
    assertTrue(QuizRepository.modules.isNotEmpty())
    val chapter1 = QuizRepository.getModule("modulo_1_motor_da_mente")
    assertNotNull(chapter1)
    assertEquals("O Motor da Nossa Cabeça", chapter1.title)
    assertEquals(5, chapter1.storyChapters.size)
    assertEquals(5, chapter1.circuits.size)
    assertTrue(QuizRepository.circuits.size >= 5)
    assertTrue(QuizRepository.allBadges.isNotEmpty())
  }
}
