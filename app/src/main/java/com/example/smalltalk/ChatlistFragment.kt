package com.example.smalltalk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smalltalk.database.AppDatabase
import com.example.smalltalk.database.User
import com.example.smalltalk.database.UserDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Appendable
import java.util.*


class ChatlistFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    //private lateinit var adapter: ChatAdapter
    private lateinit var adapter: ChatAdapterSimple

    lateinit var profileIcon: ImageView
    private val viewModel: ChatlistFragmentViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chatlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileIcon = view.findViewById(R.id.chat_profile_icon)

        val chatList = listOf(
            ChatObject(
                "Hei Jonas, du tar vel strømregningen min siden jeg er del av folket nå?",
                "Erna",
                Date()
            ),
            ChatObject("Erna! Du skylder oss allerede. Prøv NAV 😎", "Jonas", Date()),
            ChatObject("😡😡😡", "Erna", Date())

        )

        recyclerView = view.findViewById(R.id.chat_recycler_view)
        layoutManager = LinearLayoutManager(activity)

        viewModel.userDAO =  AppDatabase.getInstance((requireContext())).userDAO() //Henter ut databasen

        //kaller på variabelen viewmodel som er et objekt av klassen ChatlistFragmentViewModel
        viewModel.getLoggedInUser {
            // ! Dette er inne i callback og kjøres først når callback blir kalt fra funksjonen

            // Opprette adapter og sende inn listen som dataSet
            adapter = ChatAdapterSimple(
                chatList,
                "Erna"
            )

            // Koble sammen recyclerViewet med layoutmanager og adapter
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter

            // Scrolle til bunn av recyclerView så siste meldinger vises
            recyclerView.scrollToPosition(chatList.size - 1);

            profileIcon.setOnClickListener {
                // TODO: Navigere til profilsiden
                activity?.supportFragmentManager?.commit {
                    setReorderingAllowed(true)
                    replace<ProfileFragment>(R.id.fragmentContainerView)
                }
            }
        }

    }

   /* // Hente lagret bruker fra database
    private fun getLoggedInUser(callback: () -> Unit) {

        // Oppretter en tråd og kjører denne (MÅ gjøres når man kommuniserer med database)
        CoroutineScope(Dispatchers.IO).launch {
            // Hente innlogget bruker og sette variablen
            loggedInUser = userDAO.getLoggedInUser()

            // Kalle på callback
            callback()
        }
    }*/ // Dette ligger/kopiert over til ChatlistFragmentViewModel derfor er det kommentert ut her
}