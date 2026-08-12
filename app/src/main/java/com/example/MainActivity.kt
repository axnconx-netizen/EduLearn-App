package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.service.FirebaseController
import com.example.ui.screens.AdminPanelScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.StudentDashboardScreen
import com.example.ui.theme.EduLearnTheme
import com.example.viewmodel.AuthScreenState
import com.example.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseController.init(this)
        enableEdgeToEdge()
        setContent {
            EduLearnTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EduLearnMainApp()
                }
            }
        }
    }
}

@Composable
fun EduLearnMainApp(authViewModel: AuthViewModel = viewModel()) {
    val screenState by authViewModel.screenState.collectAsState()
    val phoneNumber by authViewModel.phoneNumber.collectAsState()
    val aiState by authViewModel.aiState.collectAsState()

    when (val state = screenState) {
        is AuthScreenState.Splash -> {
            SplashScreen(
                onTimeout = { authViewModel.onSplashFinished() }
            )
        }

        is AuthScreenState.Login -> {
            LoginScreen(
                phoneNumber = phoneNumber,
                onPhoneChanged = { authViewModel.updatePhoneNumber(it) },
                onPhoneSubmit = { phone -> authViewModel.loginWithPhone(phone) }
            )
        }

        is AuthScreenState.AdminPanel -> {
            AdminPanelScreen(
                adminEmail = state.adminEmail,
                onLogout = { authViewModel.logout() }
            )
        }

        is AuthScreenState.StudentDashboard -> {
            StudentDashboardScreen(
                userIdentifier = state.userIdentifier,
                aiState = aiState,
                onGenerateAiContent = { category, title, subject, year ->
                    authViewModel.generateAiContent(category, title, subject, year)
                },
                onCloseAiModal = { authViewModel.closeAiModal() },
                onLogout = { authViewModel.logout() }
            )
        }
    }
}
