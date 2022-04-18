package com.example.smalltalk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.smalltalk.database.AppDatabase
import com.example.smalltalk.database.User
import com.example.smalltalk.database.UserDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InnloggingFragment : Fragment() {

    private lateinit var brukernavn: EditText // denne kan ikke være med i viewModel fordi den inngår i viewet
    private lateinit var passord: EditText   // denne kan ikke være med i viewModel fordi den inngår i viewet
    private lateinit var logInButton: AppCompatButton
    private lateinit var glemtpassord: TextView  // denne kan ikke være med i viewModel fordi den inngår i viewet
    private lateinit var userDAO: UserDAO
    private val viewModel: InnloggingFragmentViewModel by viewModels() // for å få denne gul må du skrive i InnloggingFragmentviewModel class InnloggingFragmentViewModel : viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_innlogging, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        brukernavn = view.findViewById(R.id.editText_Brukernavn)
        passord = view.findViewById(R.id.editText_Passord)
        logInButton = view.findViewById(R.id.button_LoggInn)
        glemtpassord = view.findViewById(R.id.textView_glemtPassord)

        val database = AppDatabase.getInstance(requireContext())
        userDAO = database.userDAO()

        bindButtons()
    }

    private fun bindButtons() {

        logInButton.setOnClickListener {

            // Sjekke om passord og brukernavn er "riktig"
            if (brukernavn.text.toString() == "" && passord.text.toString() == "") {

                // Opprette et brukerobjekt (dette skal senere komme fra API)
                // ID på bruker blir autogenerert i entiteten
                val user = User("Erna", "Erna Solberg") // Dette bør vises i App Inspection -> DB

                // Kalle funksjon med en callback
                viewModel.logInUser(userDAO,user) { // beskrivelse her !!!!!!!!!!
                    findNavController().navigate(InnloggingFragmentDirections.actionInnloggingFragmentToChatlistFragment()) // id 'androidx.navigation.safeargs' i build.gradle (plugin)

                }
            } else {
                Toast.makeText(context, "Feil brukernavn eller passord", Toast.LENGTH_LONG).show()
            }
        }
    }

   /* private fun logInUser(user: User, callback: () -> Unit) {
        // Start en ny tråd
        CoroutineScope(Dispatchers.IO).launch {
            //Fjerne alle brukere og legge til den vi logget inn med
            userDAO.deleteAllUsers()
            userDAO.addUser(user)
            callback()
        }.start() // Dette er flyttet til InnloggingFragmentViewModel så kan slette denne nå
    }*/
}


