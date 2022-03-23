package com.example.smalltalk

import androidx.lifecycle.ViewModel
import com.example.smalltalk.database.User
import com.example.smalltalk.database.UserDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Denne klassen burde hete ChatlistViewModel

class ChatlistFragmentViewModel: ViewModel() { // Arver fra ViewModel

    lateinit var userDAO: UserDAO
    lateinit var loggedInUser: User

    // Hente lagret bruker fra database
     fun getLoggedInUser(callback: () -> Unit) {

        // Oppretter en tråd og kjører denne (MÅ gjøres når man kommuniserer med database)
        CoroutineScope(Dispatchers.IO).launch {
            // Hente innlogget bruker og sette variablen
            loggedInUser = userDAO.getLoggedInUser()

            // Kalle på callback
            callback()
        }
    }
}