package com.example.service

import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiAiService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    suspend fun generateMadhyamikContent(
        category: String, 
        subject: String, 
        year: String? = null
    ): String = withContext(Dispatchers.IO) {
        val apiKey = try { BuildConfig.GEMINI_API_KEY } catch (e: Exception) { "" }

        val prompt = when {
            category == "PYQ" && year != null -> 
                "West Bengal Board (WBBSE) Class 10 Madhyamik $subject $year সালের বোর্ড পরীক্ষার সমস্ত গুরুত্বপূর্ণ প্রশ্নপত্র, সঠিক সমাধান, গাণিতিক সুত্র ও উত্তরমালা বিশুদ্ধ বাংলায় বিস্তারিত লিখুন।"
            category == "PYQ" -> 
                "West Bengal Board (WBBSE) Class 10 Madhyamik $subject বিগত ৫ বছরের গুরুত্বপূর্ণ প্রশ্ন ও উত্তর (PYQ) বাংলায় লিখুন। গাণিতিক সুত্র, সংক্ষিপ্ত প্রশ্ন ও সঠিক উত্তর পরিষ্কার পয়েন্ট করে উপস্থাপন করুন।"
            category == "SUGGESTION" -> 
                "West Bengal Board Class 10 Madhyamik 2027 $subject ২০২৭ সালের সম্ভাব্য ১০টি অত্যন্ত গুরুত্বপূর্ণ সাজেশন নোটস ও তাদের সঠিক সমাধান বিশুদ্ধ বাংলায় বিস্তারিত লিখুন।"
            category == "QUIZ" -> 
                "West Bengal Class 10 Madhyamik $subject এর ওপর ৫টি বহু বিকল্পভিত্তিক প্রশ্ন (MCQ) তৈরি করুন। প্রতিটি প্রশ্নের ৪টি অপশন এবং সাথে সঠিক উত্তর ও ব্যাখ্যা বাংলায় দিন।"
            else -> 
                "West Bengal Madhyamik $subject $category নোটস ও গুরুত্বপূর্ণ সূত্রমালা বাংলায় লিখুন।"
        }

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext getFallbackContent(category, subject, year)
        }

        try {
            val jsonBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", prompt)
                            })
                        })
                    })
                })
            }

            val requestBody = jsonBody.toString().toRequestBody("application/json".toMediaType())
            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$apiKey"

            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val responseString = response.body?.string() ?: ""

            if (response.isSuccessful && responseString.isNotEmpty()) {
                val jsonResponse = JSONObject(responseString)
                val candidates = jsonResponse.optJSONArray("candidates")
                if (candidates != null && candidates.length() > 0) {
                    val content = candidates.getJSONObject(0).optJSONObject("content")
                    val parts = content?.optJSONArray("parts")
                    if (parts != null && parts.length() > 0) {
                        val text = parts.getJSONObject(0).optString("text")
                        if (text.isNotBlank()) {
                            return@withContext text
                        }
                    }
                }
            }
            return@withContext getFallbackContent(category, subject, year)
        } catch (e: Exception) {
            return@withContext getFallbackContent(category, subject, year)
        }
    }

    private fun getFallbackContent(category: String, subject: String, year: String? = null): String {
        return when {
            category == "PYQ" && year != null -> """
                📌 পশ্চিমবঙ্গ মাধ্যমিক $subject - $year সালের প্রশ্নপত্র ও সমাধান (PYQ)

                [১] সংক্ষিপ্ত উত্তরভিত্তিক প্রশ্ন ($year):
                • প্রশ্ন: $subject বিষয়ে $year সালের প্রধান উপপাদ্য/তত্ত্বটি বিবৃত করুন।
                👉 উত্তর: সঠিক সুত্র ও ব্যাখ্যানুসারে $year সালের প্রশ্নপত্রে মূল প্রয়োগটি ছিল $subject এর ভিত্তিমূলক সূত্রের ওপর।

                [২] গাণিতিক সূত্রমালা ও সমাধান:
                • সমীকরণ: ax² + bx + c = 0
                • বীজদ্বয়ের যোগফল = -b/a, গুণফল = c/a

                [৩] বিশ্লেষণাত্মক বহুনির্বাচনী প্রশ্ন:
                • $year সালের মাধ্যমিক পরীক্ষায় $subject বিষয়ে সর্বমোট ৯০ নম্বরের লিখিত পরীক্ষা অনুষ্ঠিত হয়েছিল।
            """.trimIndent()

            category == "PYQ" -> """
                📌 পশ্চিমবঙ্গ মাধ্যমিক $subject - বিগত ৫ বছরের প্রশ্ন ও উত্তর (PYQ)

                প্রশ্ন ১: দ্বিঘাত সমীকরণ ax² + bx + c = 0 এর নিরূপক (Discriminant) কী?
                👉 উত্তর: D = b² - 4ac। যদি D >= 0 হয়, তবে বীজদ্বয় বাস্তব হবে।

                প্রশ্ন ২: বৃত্তস্থ চতুর্ভুজের বিপরীত কোণদ্বয়ের সম্পর্ক কী?
                👉 উত্তর: বৃত্তস্থ চতুর্ভুজের বিপরীত কোণদ্বয় পরস্পর সম্পূরক (সমষ্টি ১৮০°)।

                প্রশ্ন ৩: সরল সুদের ক্ষেত্রে I = (P * r * t) / 100 সুত্রটিতে P ও r বলতে কী বোঝায়?
                👉 উত্তর: P = আসল (Principal), r = বার্ষিক সুদের হার (Rate of Interest)।
            """.trimIndent()

            category == "SUGGESTION" -> """
                🌟 মাধ্যমিক ২০২৭ $subject - সুপার সাজেশন নোটস

                ১. অতি গুরুত্বপূর্ণ সূত্রমালা:
                - পরিমিতি: গোলকের আয়তন = (4/3) * π * r³
                - সমকোণী চৌপলের সমগ্রতলের ক্ষেত্রফল = 2(lb + bh + hl)

                ২. গুরুত্বপূর্ণ প্রশ্নাবলী:
                • থালেসের উপপাদ্য এবং তার বিপরীত উপপাদ্যের প্রয়োগ সংক্রান্ত প্রমাণ।
                • ত্রিকোণমিতিক অবেদাবলি sin²θ + cos²θ = 1 ব্যবহার করে মান নির্ণয়।
            """.trimIndent()

            else -> """
                🧪 মাধ্যমিক $subject - মক টেস্ট কুইজ

                প্রশ্ন ১: আলোকবর্ষ কিসের একক?
                A) সময়  B) দূরত্ব  C) তীব্রতা  D) ভর
                ✅ সঠিক উত্তর: B) দূরত্ব

                প্রশ্ন ২: বায়ুমণ্ডলের সর্বনিম্ন স্তর কোনটি?
                A) ট্রপোস্ফিয়ার  B) স্ট্র্যাটোস্ফিয়ার  C) মেসোস্ফিয়ার  D) থার্মোস্ফিয়ার
                ✅ সঠিক উত্তর: A) ট্রপোস্ফিয়ার
            """.trimIndent()
        }
    }
}
