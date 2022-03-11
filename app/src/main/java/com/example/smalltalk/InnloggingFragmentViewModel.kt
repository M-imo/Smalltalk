package com.example.smalltalk

import androidx.lifecycle.ViewModel
import com.example.smalltalk.database.User
import com.example.smalltalk.database.UserDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// i viewModel skal kun sjekke hvis passord og brukernavn er rikitg det skal ikke ha noen tilknytting/ting som inngår i selevet viewet
class InnloggingFragmentViewModel : ViewModel() { // må skrive dette for å få by viewModels() gul 

    // legg merke til at funksjonene ikke er privat da er den tilgjenglig overalt
    fun logInUser(userDAO: UserDAO, user: User, callback: () -> Unit) {
        // Start en ny tråd
        CoroutineScope(Dispatchers.IO).launch {
            //Fjerne alle brukere og legge til den vi logget inn med
            userDAO.deleteAllUsers()
            userDAO.addUser(user)
            callback()
        }.start()
    }
}