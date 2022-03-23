package com.example.smalltalk

import android.widget.Toast
import androidx.core.graphics.Insets.add
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModel
import com.example.smalltalk.database.User
import com.example.smalltalk.database.UserDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// i viewModel skal kun sjekke hvis passord og brukernavn er rikitg det skal ikke ha noen tilknytting/ting som inngår i selevet viewet
//Denne klassen burde hete InnloggingViewModel

class InnloggingFragmentViewModel : ViewModel() { // må skrive dette for å få by viewModels() gul i InnloggingFragment

    private lateinit var userDAO: UserDAO


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