package com.example.chatbot.ui.theme

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch


class ChatViewModel : ViewModel() {

    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }
    val generativeModel =
        GenerativeModel(
            // Specify a Gemini model appropriate for your use case
            modelName = "gemini-1.5-flash",
            // Access your API key as a Build Configuration variable (see "Set up your API key" above)
            apiKey = Constants.apikey
        )

    fun sendMessage(question: String) {
        viewModelScope.launch{
            val chat = generativeModel.startChat()
            messageList.add(MessageModel(question,"user"))
            val response = chat.sendMessage(question)
            messageList.add(MessageModel(response.text.toString(),"model"))
        }

    }
}

