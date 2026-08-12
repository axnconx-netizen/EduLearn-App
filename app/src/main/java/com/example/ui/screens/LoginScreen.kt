package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.PrimaryIndigo
import com.example.ui.theme.SecondaryTeal
import kotlinx.coroutines.delay

data class OnboardingHighlight(
    val title: String,
    val titleBn: String,
    val subtitle: String,
    val icon: ImageVector,
    val accentColor: Color,
    val bgGradient: List<Color>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    phoneNumber: String,
    onPhoneChanged: (String) -> Unit,
    onPhoneSubmit: (String) -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(true) }
    var currentSlideIndex by remember { mutableIntStateOf(0) }

    val highlights = listOf(
        OnboardingHighlight(
            title = "AI Auto Solver",
            titleBn = "AI সমস্যা সমাধানকারী",
            subtitle = "তাৎক্ষণিক WBBSE সমাধান ও গাণিতিক সূত্রের বিশদ ব্যাখ্যা",
            icon = Icons.Default.AutoAwesome,
            accentColor = Color(0xFF00F0FF),
            bgGradient = listOf(Color(0xFF1E1B4B), Color(0xFF0F172A))
        ),
        OnboardingHighlight(
            title = "5 Years PYQs with Board Solutions",
            titleBn = "৫ বছরের বোর্ড প্রশ্ন সমাধান",
            subtitle = "২০২২ থেকে ২০২৬ সালের মাধ্যমিক পরীক্ষার প্রশ্নপত্র ও উত্তরমালা",
            icon = Icons.Default.HistoryEdu,
            accentColor = Color(0xFF10B981),
            bgGradient = listOf(Color(0xFF064E3B), Color(0xFF0F172A))
        ),
        OnboardingHighlight(
            title = "Live Quiz Tracker",
            titleBn = "লাইভ কুইজ ট্র্যাকার",
            subtitle = "তাৎক্ষণিক AI মক কুইজ পরীক্ষা ও পারফরম্যান্স পর্যবেক্ষণ",
            icon = Icons.Default.Quiz,
            accentColor = Color(0xFFF59E0B),
            bgGradient = listOf(Color(0xFF451A03), Color(0xFF0F172A))
        )
    )

    // Auto-cycling slider timer
    LaunchedEffect(Unit) {
        while (true) {
            delay(3500)
            currentSlideIndex = (currentSlideIndex + 1) % highlights.size
        }
    }

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                // Centered Book Brand Logo Header
                AsyncImage(
                    model = "https://i.postimg.cc/rF2Fwv1J/860f51ee-337c-4760-b635-433b23570672.png",
                    contentDescription = "EduLearn Logo",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "EduLearn",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "মাধ্যমিক শিক্ষা ও সাজেশন পোর্টাল",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // REQUIREMENT 1: ONBOARDING PREVIEW SLIDER (Key Highlights Carousel)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            currentSlideIndex = (currentSlideIndex + 1) % highlights.size
                        },
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.5.dp, highlights[currentSlideIndex].accentColor),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = highlights[currentSlideIndex].bgGradient
                                )
                            )
                            .padding(20.dp)
                    ) {
                        AnimatedContent(
                            targetState = currentSlideIndex,
                            transitionSpec = { fadeIn() togetherWith fadeOut() },
                            label = "OnboardingSlide"
                        ) { index ->
                            val item = highlights[index]
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(20.dp),
                                        color = item.accentColor.copy(alpha = 0.2f),
                                        border = BorderStroke(1.dp, item.accentColor)
                                    ) {
                                        Text(
                                            text = item.title,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = item.accentColor,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }

                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(item.accentColor.copy(alpha = 0.25f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = item.icon,
                                            contentDescription = null,
                                            tint = item.accentColor,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = item.titleBn,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = item.subtitle,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFFCBD5E1),
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
                }

                // Slider Page Indicator Dots
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    highlights.indices.forEach { idx ->
                        Box(
                            modifier = Modifier
                                .size(if (idx == currentSlideIndex) 10.dp else 6.dp)
                                .clip(CircleShape)
                                .background(
                                    if (idx == currentSlideIndex)
                                        highlights[currentSlideIndex].accentColor
                                    else
                                        Color.Gray.copy(alpha = 0.4f)
                                )
                                .clickable { currentSlideIndex = idx }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // CLEAN STUDENT LOOK: Textfield for "+ Mobile Number"
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = onPhoneChanged,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("+ Mobile Number") },
                    placeholder = { Text("Enter 10-digit mobile number") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.PhoneAndroid,
                            contentDescription = null,
                            tint = PrimaryIndigo
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { onPhoneSubmit(phoneNumber) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo)
                ) {
                    Text(
                        text = "Continue",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(
                    onClick = { showBottomSheet = true }
                ) {
                    Text(
                        text = "Continue with 084369 59141",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryIndigo
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Native Bottom Sheet Overlay triggered on entry: "Continue with 084369 59141"
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                    containerColor = MaterialTheme.colorScheme.surface,
                    dragHandle = { BottomSheetDefaults.DragHandle() }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Quick Sign-In",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            IconButton(onClick = { showBottomSheet = false }) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = "Close"
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Continue with 084369 59141",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = PrimaryIndigo,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "Verified mobile credential detected on device",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
                        )

                        // Phone account Card
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onPhoneChanged("084369 59141")
                                    showBottomSheet = false
                                    onPhoneSubmit("084369 59141")
                                },
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            tonalElevation = 2.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(PrimaryIndigo),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PhoneAndroid,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "+91 084369 59141",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Tap to automatically proceed",
                                        fontSize = 12.sp,
                                        color = SecondaryTeal
                                    )
                                }

                                Text(
                                    text = "SELECT",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = PrimaryIndigo
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                onPhoneChanged("084369 59141")
                                showBottomSheet = false
                                onPhoneSubmit("084369 59141")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo)
                        ) {
                            Text(
                                text = "Continue as 084369 59141",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedButton(
                            onClick = {
                                showBottomSheet = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Use another number",
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}
