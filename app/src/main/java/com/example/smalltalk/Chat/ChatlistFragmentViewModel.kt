package com.example.smalltalk.Chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smalltalk.ChatObject
import com.example.smalltalk.database.User
import com.example.smalltalk.database.UserDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Method
import java.util.*

// Denne klassen burde hete ChatlistViewModel

class ChatlistFragmentViewModel: ViewModel() { // Arver fra ViewModel

    lateinit var userDAO: UserDAO
    val loggedInUser: /*User*/ MutableLiveData<User> = MutableLiveData()
    val baseUrl = "https://us-central1-smalltalk-3bfb8.cloudfunctions.net/api/"

        //Hva er dette ???
        val showLoader: MutableLiveData<Boolean> = MutableLiveData()
        val apiSuccess: MutableLiveData<Boolean> = MutableLiveData()
        var showError: MutableLiveData<String> = MutableLiveData()
        val messageList: MutableLiveData<List<ChatObject>> = MutableLiveData()


    // Hente lagret bruker fra database
     fun getLoggedInUser(callback: () -> Unit) {

        // Oppretter en tråd og kjører denne (MÅ gjøres når man kommuniserer med database)
        CoroutineScope(Dispatchers.IO).launch {
            // Hente innlogget bruker og sette variablen
            val user = userDAO.getLoggedInUser()
            loggedInUser.postValue(user)

            // Kalle på callback
            callback()
        }
    }

    fun fetchMessages (requestQueue: RequestQueue) {
        showLoader.postValue(true)

        loggedInUser.value?.id?.let { userId->
            val url = baseUrl + "messages?userId=${userId}"

            val request = StringRequest (
                Request.Method.GET,
            url,
                { json ->
                    val messages = Klaxon().parseArray<ChatObject>(json) ?: listOf()

                    showLoader.postValue(false)
                    messageList.postValue(messages)
                },
                { error ->
                    print ("")

                    showLoader.postValue(false)
                    showError.postValue("Could not get message")
                }

            )
            requestQueue.add(request)
        }
    }

    fun sendMessages(
        requesteQueue: RequestQueue,
        massageText: String
    ) {
        loggedInUser.value?.id.let { userId ->
            val url = baseUrl + "sendMessage"

            val postRequest: StringRequest = object : StringReqest(
                Method.POST,
                url,
                {json ->
                    apiSuccess.postValue(false)
                    showError.postValue("Could not send message")
                }
            ) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()

                    params["message"] = messageText
                    params["userId"] = userId

                    return params
                }
            }

            requestQueue.add(postRequest)
        }

        }
    }
