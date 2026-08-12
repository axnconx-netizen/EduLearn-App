package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.service.FirebaseController
import com.example.service.GeminiAiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthScreenState {
    object Splash : AuthScreenState()
    object Login : AuthScreenState()
    data class StudentDashboard(val userIdentifier: String) : AuthScreenState()
    data class AdminPanel(val adminEmail: String) : AuthScreenState()
}

data class AiGeneratedState(
    val isLoading: Boolean = false,
    val category: String = "",
    val subject: String = "গণিত",
    val title: String = "",
    val content: String = "",
    val year: String? = null
)

class AuthViewModel : ViewModel() {

    private val _screenState = MutableStateFlow<AuthScreenState>(AuthScreenState.Splash)
    val screenState: StateFlow<AuthScreenState> = _screenState.asStateFlow()

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()

    private val _aiState = MutableStateFlow(AiGeneratedState())
    val aiState: StateFlow<AiGeneratedState> = _aiState.asStateFlow()

    private val _lastSessionToken = MutableStateFlow("")
    val lastSessionToken: StateFlow<String> = _lastSessionToken.asStateFlow()

    fun updatePhoneNumber(number: String) {
        _phoneNumber.value = number
    }

    fun onSplashFinished() {
        _screenState.value = AuthScreenState.Login
    }

    fun loginWithPhone(phone: String) {
        val rawPhone = phone.ifBlank { _phoneNumber.value }
        val digitsOnly = rawPhone.replace(Regex("[^0-9]"), "")
        val isAdmin = digitsOnly.endsWith("8436959141") || rawPhone.contains("8436959141")

        viewModelScope.launch {
            val token = FirebaseController.syncUserSession(
                phone = if (rawPhone.isNotBlank()) rawPhone else "+91 Student",
                userType = if (isAdmin) "ADMIN_SUPERUSER" else "STUDENT"
            )
            _lastSessionToken.value = token
        }

        // Instant screen state transition with zero UI lag
        if (isAdmin) {
            _screenState.value = AuthScreenState.AdminPanel(adminEmail = "ratankundu8654@gmail.com")
        } else {
            _screenState.value = AuthScreenState.StudentDashboard(
                userIdentifier = if (rawPhone.isNotBlank()) rawPhone else "+91 Student"
            )
        }
    }

    fun generateAiContent(
        category: String, 
        categoryTitle: String, 
        subject: String = "গণিত",
        year: String? = null
    ) {
        viewModelScope.launch {
            _aiState.value = AiGeneratedState(
                isLoading = true,
                category = category,
                subject = subject,
                title = categoryTitle,
                content = "",
                year = year
            )

            val generated = GeminiAiService.generateMadhyamikContent(category, subject, year)

            _aiState.value = _aiState.value.copy(
                isLoading = false,
                content = generated
            )
        }
    }

    fun closeAiModal() {
        _aiState.value = AiGeneratedState()
    }

    fun logout() {
        _phoneNumber.value = ""
        _screenState.value = AuthScreenState.Login
    }
}
