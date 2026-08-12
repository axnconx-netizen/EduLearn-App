package com.example.service

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

data class UserSessionLog(
    val phoneNumber: String,
    val userType: String,
    val sessionToken: String,
    val timestamp: Long,
    val formattedTime: String
)

object FirebaseController {

    private var isFirebaseAvailable = false
    private val localSessionLogs = mutableListOf<UserSessionLog>()

    fun init(context: Context) {
        try {
            if (FirebaseApp.getApps(context).isEmpty()) {
                FirebaseApp.initializeApp(context)
            }
            isFirebaseAvailable = true
            Log.d("FirebaseController", "Firebase Core Initialized Successfully")
        } catch (e: Exception) {
            Log.w("FirebaseController", "Firebase Core Init Warning: ${e.message}")
            isFirebaseAvailable = false
        }
    }

    suspend fun syncUserSession(phone: String, userType: String): String = withContext(Dispatchers.IO) {
        val sessionToken = "auth_tok_" + UUID.randomUUID().toString().take(10)
        val timestamp = System.currentTimeMillis()
        val timeString = java.text.SimpleDateFormat("dd MMM, hh:mm a", java.util.Locale.getDefault()).format(java.util.Date(timestamp))

        val log = UserSessionLog(
            phoneNumber = phone,
            userType = userType,
            sessionToken = sessionToken,
            timestamp = timestamp,
            formattedTime = timeString
        )

        synchronized(localSessionLogs) {
            localSessionLogs.removeAll { it.phoneNumber == phone }
            localSessionLogs.add(0, log)
        }

        if (isFirebaseAvailable) {
            try {
                val db = FirebaseFirestore.getInstance()
                val userData = hashMapOf(
                    "phoneNumber" to phone,
                    "userType" to userType,
                    "sessionToken" to sessionToken,
                    "lastLoginTimestamp" to timestamp,
                    "formattedTime" to timeString,
                    "status" to "ACTIVE_ONLINE"
                )

                db.collection("active_user_sessions")
                    .document(phone.ifBlank { "guest_user" })
                    .set(userData)
            } catch (e: Exception) {
                Log.e("FirebaseController", "Firestore sync warning: ${e.message}")
            }
        }
        return@withContext sessionToken
    }

    fun getLiveSessionLogs(): List<UserSessionLog> {
        synchronized(localSessionLogs) {
            return localSessionLogs.toList()
        }
    }
}
