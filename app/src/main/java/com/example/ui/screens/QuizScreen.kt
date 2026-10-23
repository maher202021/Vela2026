package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.HighlightOff
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.YemenDataProvider

@Composable
fun QuizScreen(
    onNavigateToMap: () -> Unit,
    modifier: Modifier = Modifier
) {
    val questions = remember { YemenDataProvider.quizQuestions }
    var currentIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var isAnswered by remember { mutableStateOf(false) }
    var isQuizCompleted by remember { mutableStateOf(false) }

    val currentQuestion = questions.getOrNull(currentIndex)

    fun handleOptionSelect(optionIndex: Int) {
        if (isAnswered) return
        selectedOptionIndex = optionIndex
        isAnswered = true
        if (optionIndex == currentQuestion?.correctIndex) {
            score++
        }
    }

    fun handleNext() {
        if (currentIndex < questions.size - 1) {
            currentIndex++
            selectedOptionIndex = null
            isAnswered = false
        } else {
            isQuizCompleted = true
        }
    }

    fun handleRestart() {
        currentIndex = 0
        score = 0
        selectedOptionIndex = null
        isAnswered = false
        isQuizCompleted = false
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = "اختبر معلوماتك في جغرافيا وخريطة اليمن",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Text(
            text = "تحدَّ نفسك وتعرّف على المحافظات والتضاريس والمعالم التاريخية",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (!isQuizCompleted && currentQuestion != null) {
            // Progress Bar & Question Counter
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "السؤال ${currentIndex + 1} من ${questions.size}",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "النقاط: $score",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { (currentIndex + 1) / questions.size.toFloat() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Question Card
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("quiz_question_card")
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = currentQuestion.question,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 24.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Options List
                    currentQuestion.options.forEachIndexed { optIndex, optionText ->
                        val isSelected = selectedOptionIndex == optIndex
                        val isCorrect = optIndex == currentQuestion.correctIndex

                        val (containerColor, borderColor, textColor) = when {
                            !isAnswered -> Triple(
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                                Color.Transparent,
                                MaterialTheme.colorScheme.onSurface
                            )
                            isCorrect -> Triple(
                                Color(0xFF10B981).copy(alpha = 0.15f),
                                Color(0xFF10B981),
                                Color(0xFF047857)
                            )
                            isSelected -> Triple(
                                Color(0xFFEF4444).copy(alpha = 0.15f),
                                Color(0xFFEF4444),
                                Color(0xFFB91C1C)
                            )
                            else -> Triple(
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                                Color.Transparent,
                                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                            )
                        }

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .border(1.5.dp, borderColor, RoundedCornerShape(12.dp))
                                .clickable(enabled = !isAnswered) { handleOptionSelect(optIndex) }
                                .testTag("quiz_opt_$optIndex"),
                            shape = RoundedCornerShape(12.dp),
                            color = containerColor
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .background(
                                                color = if (isSelected || (isAnswered && isCorrect)) borderColor else MaterialTheme.colorScheme.surfaceVariant,
                                                shape = CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "${optIndex + 1}",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected || (isAnswered && isCorrect)) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = optionText,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = if (isSelected || (isAnswered && isCorrect)) FontWeight.Bold else FontWeight.Normal,
                                        color = textColor
                                    )
                                }

                                if (isAnswered) {
                                    if (isCorrect) {
                                        Icon(
                                            Icons.Default.CheckCircle,
                                            contentDescription = "إجابة صحيحة",
                                            tint = Color(0xFF10B981)
                                        )
                                    } else if (isSelected) {
                                        Icon(
                                            Icons.Default.HighlightOff,
                                            contentDescription = "إجابة غير صحيحة",
                                            tint = Color(0xFFEF4444)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Explanation Card after answering
                    AnimatedVisibility(visible = isAnswered) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 14.dp)
                                .background(
                                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f),
                                    RoundedCornerShape(12.dp)
                                )
                                .padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.HelpOutline,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "توضيح ومعلومة جغرافية:",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = currentQuestion.explanation,
                                style = MaterialTheme.typography.bodySmall,
                                lineHeight = 19.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (isAnswered) {
                        Button(
                            onClick = { handleNext() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("quiz_next_btn")
                        ) {
                            Text(if (currentIndex < questions.size - 1) "السؤال التالي" else "عرض النتيجة النهائية")
                        }
                    }
                }
            }
        } else {
            // Quiz Complete Summary Card
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .testTag("quiz_completion_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Stars,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "اكتمل الاختبار الجغرافي!",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    val percentage = (score * 100) / questions.size
                    val feedbackText = when {
                        percentage >= 85 -> "ممتاز! معرفتك بجغرافيا وتاريخ اليمن مذهلة وعميقة! 🇾🇪"
                        percentage >= 60 -> "أحسنت! لديك معرفة جيدة جداً بخريطة ومحافظات اليمن. 👍"
                        else -> "بداية طيبة! يمكنك استكشاف الخريطة ودليل المحافظات لمعرفة المزيد. 🗺️"
                    }

                    Text(
                        text = "نتيجتك: $score من ${questions.size} ($percentage%)",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = feedbackText,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { handleRestart() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("restart_quiz_btn")
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("إعادة الاختبار")
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = onNavigateToMap,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("العودة إلى الخريطة التفاعلية")
                    }
                }
            }
        }
    }
}
