package com.example.smalltalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.smalltalk.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Sette at appen skal starte på login-skjermen (fagmentet)
        // Skal senere sjekke om brukeren er logget inn og isåfall gå rett til chat-fragment
        checkLoggedInUser { isLoggedIn ->
            if (isLoggedIn) {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<ChatlistFragment>(R.id.fragmentContainerView)
                }
            } else {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<InnloggingFragment>(R.id.fragmentContainerView)

            }

        }
    }
}
    private fun checkLoggedInUser (callback:(Boolean) -> Unit) { // Hva skjer her ?

        //Starter en ny tråd
        CoroutineScope(Dispatchers.IO).launch {
            //Kaller callback med en boolean som sier om brukeren finnes eller ikke
            val isUserInDatabase = AppDatabase.getInstance(this@MainActivity).userDAO().getLoggedInUser() != null
            callback(isUserInDatabase)
        }

    }
}