package com.example.smalltalk.Chat

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smalltalk.ChatObject
import com.example.smalltalk.Profile.ProfileFragment
import com.example.smalltalk.R
import com.example.smalltalk.database.AppDatabase
import java.util.*


class ChatlistFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    //private lateinit var adapter: ChatAdapter
    private lateinit var adapter: ChatAdapterSimple
    private lateinit var sendButton: TextView
    private lateinit var messageInput: EditText
    private lateinit var loader: ProgressBar


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
        sendButton = view.findViewById(R.id.chat_send_button)
        messageInput = view.findViewById(R.id.chat_message_input)
        loader = view.findViewById(R.id.chat_loader)
        loader.isVisible = false

        viewModel.userDAO =  AppDatabase.getInstance((requireContext())).userDAO() //Henter ut databasen

        recyclerView = view.findViewById(R.id.chat_recycler_view)
        layoutManager = LinearLayoutManager(activity)



        val chatList = listOf(
            ChatObject(
                "Hei Jonas, du tar vel strømregningen min siden jeg er del av folket nå?",
                "Erna",
                Date()    // <- endre til timestamp??
            ),
            ChatObject("Erna! Du skylder oss allerede. Prøv NAV 😎", "Jonas", Date()),
            ChatObject("😡😡😡", "Erna", Date())

        )



       /* adapter = ChatAdapterSimple()*/

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
               /* findNavController().navigate(ChatlistFragmentDirections.actionChatlistFragmentToProfileFragment())*/ // du må sende med en string i det siste parametret bruker som er innlogget
                activity?.supportFragmentManager?.commit {
                    setReorderingAllowed(true)
                    replace<ProfileFragment>(R.id.fragmentContainerView) // bruker replace isf add
                }
            }

            sendButton.setOnClickListener{
                val messageText = messageInput.text.toString()
                val newList = adapter.dataSet.toMutableList()

                newList.add(
                    ChatObject(
                        viewModel.loggedInUser.value?.id?: "",
                        messageText,
                        viewModel.loggedInUser.value?.username?: "",
                        Date().time

                    )
                )

                adapter.updateData(newList)
                messageInput.text.clear()
                hideKeyboard(requireActivity())

                if (messageText.isNotEmpty()) {
                    viewModel.sendMessages(
                        Volly.newRequestQueue(activity),
                        messageText
                    )
                }

            }

            bindObserves()

        }

        fun bindObserve() {
            viewModel.showLoader.observe(viewLifecycleOwner) { showLoader ->
                loader.isVisible = showLoader
            }
            viewModel.apiSuccess.observe(viewLifecycleOwner) { success ->
                if (success) {
                    fetchMessageAndUpdateData()
                } else {
                    // TODO: Show error
                    // TODO: Fjerne lokal melding
                }
            }

            viewModel.messageList.observe(viewLifecycleOwner) { newMessageList ->
                adapter.updateData(newMessageList)
                recyclerView.scrollToPosition(adapter.itemCount - 1)
            }

            viewModel.showError.observe(viewLifecycleOwner) { errorText ->
                requireActivity().runOnUiThread {
                    Toast.makeText(activity, errorText, Toast.LENGTH_LONG).show()
                }
            }
        }
          fun fetchMessagesAndUpdateData() {
              viewModel.fetchMessages(Volley.newRequestQueue(activity))
          }

        fun hideKeyboard(activity: Activity) {
              val imm: InputMethodeManager =
                  activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = activity.currentFocus
            if (view == null) {
                view = View(activity)

                imm.hideSoftInputFromWindow(view.windowToken, 0)
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