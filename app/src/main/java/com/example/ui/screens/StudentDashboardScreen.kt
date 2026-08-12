package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.PrimaryIndigo
import com.example.ui.theme.SecondaryTeal
import com.example.viewmodel.AiGeneratedState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDashboardScreen(
    userIdentifier: String,
    aiState: AiGeneratedState,
    onGenerateAiContent: (category: String, title: String, subject: String, year: String?) -> Unit,
    onCloseAiModal: () -> Unit,
    onLogout: () -> Unit
) {
    var selectedBottomNav by remember { mutableIntStateOf(0) } // 0: নোটস, 1: কুইজ, 2: নোটিশ, 3: সংরক্ষিত
    var selectedSubject by remember { mutableStateOf("গণিত") }
    var showPyqYearsDialog by remember { mutableStateOf(false) }

    val subjects = listOf("গণিত", "ভৌত বিজ্ঞান", "জীবন বিজ্ঞান", "ইতিহাস", "ভূগোল", "বাংলা", "ইংরেজি")

    val pyqYears = listOf(
        "২০২৬" to "২০২৬ সালের প্রশ্নপত্র",
        "২০২৫" to "২০২৫ সালের প্রশ্নপত্র",
        "২০২৪" to "২০২৪ সালের প্রশ্নপত্র",
        "২০২৩" to "২০২৩ সালের প্রশ্নপত্র",
        "২০২২" to "২০২২ সালের প্রশ্নপত্র"
    )

    // Unified Rich Charcoal Black Palette for Perfect High-Contrast
    val charcoalBg = Color(0xFF0D0D11)
    val charcoalSurface = Color(0xFF14141C)
    val obsidianCard = Color(0xFF181822)

    val neonCyan = Color(0xFF00F0FF)
    val neonEmerald = Color(0xFF10B981)
    val neonAmber = Color(0xFFF59E0B)
    val neonBlue = Color(0xFF3B82F6)
    val neonRed = Color(0xFFFF4D4D)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = charcoalBg,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = PrimaryIndigo.copy(alpha = 0.25f),
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.School,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "মাধ্যমিক ড্যাশবোর্ড",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 19.sp,
                                color = Color.White
                            )
                            Text(
                                text = "শিক্ষার্থী: $userIdentifier",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFFCBD5E1)
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "লগ আউট",
                            tint = neonRed
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = charcoalSurface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = charcoalSurface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = selectedBottomNav == 0,
                    onClick = { selectedBottomNav = 0 },
                    icon = { Icon(Icons.Default.MenuBook, contentDescription = "নোটস") },
                    label = { Text("নোটস", fontWeight = FontWeight.Bold, fontSize = 13.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryIndigo,
                        selectedTextColor = PrimaryIndigo,
                        unselectedIconColor = Color(0xFF94A3B8),
                        unselectedTextColor = Color(0xFF94A3B8)
                    )
                )
                NavigationBarItem(
                    selected = selectedBottomNav == 1,
                    onClick = {
                        selectedBottomNav = 1
                        onGenerateAiContent("QUIZ", "মক টেস্ট কুইজ", selectedSubject, null)
                    },
                    icon = { Icon(Icons.Default.Quiz, contentDescription = "কুইজ") },
                    label = { Text("কুইজ", fontWeight = FontWeight.Bold, fontSize = 13.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryIndigo,
                        selectedTextColor = PrimaryIndigo,
                        unselectedIconColor = Color(0xFF94A3B8),
                        unselectedTextColor = Color(0xFF94A3B8)
                    )
                )
                NavigationBarItem(
                    selected = selectedBottomNav == 2,
                    onClick = { selectedBottomNav = 2 },
                    icon = { Icon(Icons.Default.Notifications, contentDescription = "নোটিশ") },
                    label = { Text("নোটিশ", fontWeight = FontWeight.Bold, fontSize = 13.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryIndigo,
                        selectedTextColor = PrimaryIndigo,
                        unselectedIconColor = Color(0xFF94A3B8),
                        unselectedTextColor = Color(0xFF94A3B8)
                    )
                )
                NavigationBarItem(
                    selected = selectedBottomNav == 3,
                    onClick = { selectedBottomNav = 3 },
                    icon = { Icon(Icons.Default.Bookmark, contentDescription = "সংরক্ষিত") },
                    label = { Text("সংরক্ষিত", fontWeight = FontWeight.Bold, fontSize = 13.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryIndigo,
                        selectedTextColor = PrimaryIndigo,
                        unselectedIconColor = Color(0xFF94A3B8),
                        unselectedTextColor = Color(0xFF94A3B8)
                    )
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(charcoalBg)
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 18.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item { Spacer(modifier = Modifier.height(6.dp)) }

                // "Structured Content" Slider Bar (কাঠামোগত কন্টেন্ট)
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1B4B)),
                        border = BorderStroke(1.5.dp, PrimaryIndigo)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "কাঠামোগত কন্টেন্ট (Structured Content)",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Surface(
                                    shape = RoundedCornerShape(20.dp),
                                    color = PrimaryIndigo
                                ) {
                                    Text(
                                        text = "WBBSE 2027",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "ওয়েস্ট বেঙ্গল বোর্ডের নতুন সিলেবাস ভিত্তিক AI চালিত নোটস ও সাজেশন",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFFE2E8F0)
                            )
                        }
                    }
                }

                // PRE-LOADED 4-COLOR ICONS WITH OBSIDIAN GREY & NEON BORDERS
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Red Frame - নোটস
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    onGenerateAiContent("SUGGESTION", "২০২৭ সাজেশন নোটস", selectedSubject, null)
                                },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = obsidianCard),
                            border = BorderStroke(2.dp, neonRed)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(CircleShape)
                                        .background(neonRed.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Description,
                                        contentDescription = "নোটস",
                                        tint = neonRed,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "নোটস",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                        }

                        // Green Frame - ভিডিও
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = obsidianCard),
                            border = BorderStroke(2.dp, neonEmerald)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(CircleShape)
                                        .background(neonEmerald.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayCircle,
                                        contentDescription = "ভিডিও",
                                        tint = neonEmerald,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "ভিডিও",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                        }

                        // Orange Frame - অডিও
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = obsidianCard),
                            border = BorderStroke(2.dp, neonAmber)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(CircleShape)
                                        .background(neonAmber.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Headphones,
                                        contentDescription = "অডিও",
                                        tint = neonAmber,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "অডিও",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                        }

                        // Blue Frame - লেআউট
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = obsidianCard),
                            border = BorderStroke(2.dp, neonBlue)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(CircleShape)
                                        .background(neonBlue.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.GridView,
                                        contentDescription = "লেআউট",
                                        tint = neonBlue,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "লেআউট",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                // Subject Filter Horizontal List
                item {
                    Column {
                        Text(
                            text = "বিষয় নির্বাচন করুন:",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(subjects) { subject ->
                                FilterChip(
                                    selected = selectedSubject == subject,
                                    onClick = { selectedSubject = subject },
                                    label = {
                                        Text(
                                            text = subject,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                    },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = PrimaryIndigo,
                                        selectedLabelColor = Color.White,
                                        containerColor = Color(0xFF1E1E2A),
                                        labelColor = Color(0xFFE2E8F0)
                                    ),
                                    border = FilterChipDefaults.filterChipBorder(
                                        enabled = true,
                                        selected = selectedSubject == subject,
                                        borderColor = if (selectedSubject == subject) PrimaryIndigo else Color(0xFF334155)
                                    )
                                )
                            }
                        }
                    }
                }

                // Section Header: AI Smart Learning Module
                item {
                    Text(
                        text = "AI স্মার্ট লার্নিং মডিউল ($selectedSubject)",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }

                // 1) AI CARD 1: 'বিগত ৫ বছরের প্রশ্ন (PYQ)' -> Triggers Year Selector Screen/Dialog Instantly
                item {
                    Card(
                        onClick = {
                            showPyqYearsDialog = true
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF141A22)),
                        border = BorderStroke(2.dp, neonCyan)
                    ) {
                        Row(
                            modifier = Modifier.padding(22.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(neonCyan.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.HistoryEdu,
                                    contentDescription = null,
                                    tint = neonCyan,
                                    modifier = Modifier.size(28.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(18.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "বিগত ৫ বছরের প্রশ্ন (PYQ)",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "$selectedSubject বিষয়ের বিগত ৫ বছরের সালভিত্তিক প্রশ্নপত্র",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFFCBD5E1)
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = neonCyan
                            ) {
                                Text(
                                    text = "সাল নির্বাচন",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF0F172A),
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                                )
                            }
                        }
                    }
                }

                // 2) AI CARD 2: '২০২৭ সাজেশন নোটস'
                item {
                    Card(
                        onClick = {
                            onGenerateAiContent("SUGGESTION", "২০২৭ সাজেশন নোটস", selectedSubject, null)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF121F1A)),
                        border = BorderStroke(2.dp, neonEmerald)
                    ) {
                        Row(
                            modifier = Modifier.padding(22.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(neonEmerald.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = neonEmerald,
                                    modifier = Modifier.size(28.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(18.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "২০২৭ সাজেশন নোটস",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "মাধ্যমিক ২০২৭ পরীক্ষার জন্য অতি গুরুত্বপূর্ণ স্পেশাল সাজেশন",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFFCBD5E1)
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = neonEmerald
                            ) {
                                Text(
                                    text = "AI জেনারেট",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                                )
                            }
                        }
                    }
                }

                // 3) AI CARD 3: 'মক টেস্ট কুইজ'
                item {
                    Card(
                        onClick = {
                            onGenerateAiContent("QUIZ", "মক টেস্ট কুইজ", selectedSubject, null)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF221A12)),
                        border = BorderStroke(2.dp, neonAmber)
                    ) {
                        Row(
                            modifier = Modifier.padding(22.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(neonAmber.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Quiz,
                                    contentDescription = null,
                                    tint = neonAmber,
                                    modifier = Modifier.size(28.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(18.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "মক টেস্ট কুইজ",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "AI দ্বারা তাৎক্ষণিক তৈরি MCQ মক কুইজ পরীক্ষা",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFFCBD5E1)
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = neonAmber
                            ) {
                                Text(
                                    text = "AI কুইজ",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF0F172A),
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                                )
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(28.dp)) }
            }

            // MODAL SUB-SCREEN: MADHYAMIK PYQ 5-YEAR SELECTOR
            if (showPyqYearsDialog) {
                ModalBottomSheet(
                    onDismissRequest = { showPyqYearsDialog = false },
                    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                    containerColor = Color(0xFF14141C),
                    dragHandle = { BottomSheetDefaults.DragHandle() }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 22.dp, vertical = 14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.HistoryEdu,
                                    contentDescription = null,
                                    tint = neonCyan,
                                    modifier = Modifier.size(26.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "বিগত ৫ বছরের প্রশ্নপত্র ($selectedSubject)",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            }
                            IconButton(onClick = { showPyqYearsDialog = false }) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = "বন্ধ করুন",
                                    tint = Color.White
                                )
                            }
                        }

                        Text(
                            text = "যেকোনো নির্দিষ্ট বছরের প্রশ্নপত্র ও সমাধান জেনারেট করতে ট্যাপ করুন:",
                            fontSize = 13.sp,
                            color = Color(0xFFCBD5E1),
                            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                        )

                        // 5 Distinct Year Cards
                        pyqYears.forEach { (yearCode, yearTitle) ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                                    .clickable {
                                        showPyqYearsDialog = false
                                        onGenerateAiContent("PYQ", yearTitle, selectedSubject, yearCode)
                                    },
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = obsidianCard),
                                border = BorderStroke(1.5.dp, neonCyan.copy(alpha = 0.8f))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(38.dp)
                                                .clip(CircleShape)
                                                .background(neonCyan.copy(alpha = 0.2f)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = yearCode,
                                                fontWeight = FontWeight.ExtraBold,
                                                fontSize = 12.sp,
                                                color = neonCyan
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(14.dp))
                                        Text(
                                            text = yearTitle,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = Color.White
                                        )
                                    }

                                    Icon(
                                        imageVector = Icons.Default.ArrowForwardIos,
                                        contentDescription = null,
                                        tint = neonCyan,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            // AI Content Generation Modal / Bottom Sheet - Targeted Year Content Output
            if (aiState.isLoading || aiState.content.isNotBlank()) {
                ModalBottomSheet(
                    onDismissRequest = onCloseAiModal,
                    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false),
                    containerColor = Color(0xFF121218),
                    dragHandle = { BottomSheetDefaults.DragHandle() }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(22.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = neonCyan
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "${aiState.title} - ${aiState.subject}",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            }
                            IconButton(onClick = onCloseAiModal) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = "বন্ধ করুন",
                                    tint = Color.White
                                )
                            }
                        }

                        Divider(
                            modifier = Modifier.padding(vertical = 14.dp),
                            color = Color(0xFF334155)
                        )

                        if (aiState.isLoading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    CircularProgressIndicator(color = neonCyan)
                                    Spacer(modifier = Modifier.height(18.dp))
                                    Text(
                                        text = "Gemini AI দ্বারা ${aiState.title} জেনারেট হচ্ছে...",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 440.dp)
                            ) {
                                item {
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(14.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2A)),
                                        border = BorderStroke(1.dp, Color(0xFF383854))
                                    ) {
                                        Text(
                                            text = aiState.content,
                                            fontSize = 16.sp,
                                            lineHeight = 26.sp,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(18.dp),
                                            color = Color.White
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(18.dp))

                            Button(
                                onClick = onCloseAiModal,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo)
                            ) {
                                Text(
                                    text = "সম্পন্ন (Close)",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
