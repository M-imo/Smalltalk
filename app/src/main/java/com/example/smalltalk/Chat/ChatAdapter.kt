package com.example.smalltalk.Chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smalltalk.ChatObject
import com.example.smalltalk.R
import java.text.SimpleDateFormat
import java.util.*



// Tar imot brukernavnet til nåværende innlogget bruker og bruker dette til å sjekke om jeg har sendt meldingen eller ikke
class ChatAdapter(
    private val dataSet: List<ChatObject>,
    private val loggedInUsername: String
) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    lateinit var context: Context

    // Dette er litt "avansert"
    // Har laget to separate ViewHolders for å skille på venstre og høyre chate-boble
    // Disse to arver igjen av en egen som definerer variablene, dette er fordi da kan jeg sende
    //inn den som de arver av istedenfor å ha if-sjekker gjennom alle funksjoner
    // Godt eksempel på god bruk av arv

    open class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        open lateinit var senderNameText: TextView
        open lateinit var profileImage: ImageView
        open lateinit var messageText: TextView
        open lateinit var dateText: TextView
    }

    inner class LeftChatViewHolder(view: View) : ChatViewHolder(view) {
        override var senderNameText: TextView = view.findViewById(R.id.left_chat_bubble_name)
        override var profileImage: ImageView = view.findViewById(R.id.left_chat_bubble_image)
        override var messageText: TextView = view.findViewById(R.id.left_chat_bubble_message_text)
        override var dateText: TextView = view.findViewById(R.id.left_chat_bubble_date_text)
    }

    inner class RightChatViewHolder(view: View) : ChatViewHolder(view) {
        override var senderNameText: TextView = view.findViewById(R.id.right_chat_bubble_name)
        override var profileImage: ImageView = view.findViewById(R.id.right_chat_bubble_image)
        override var messageText: TextView = view.findViewById(R.id.right_chat_bubble_message_text)
        override var dateText: TextView = view.findViewById(R.id.right_chat_bubble_date_text)
    }

    // Dette er en innebygget funksjon i Adapter som man kan override
    // Den gjør at man kan skille på hva slags view som skal vises basert på posisjonen i dataSet
    override fun getItemViewType(position: Int): Int {
        return if (isChatFromLoggedInUser(position)) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        context = parent.context

        // Sjekker her om hva slags type view jeg skal vise, og laste inn riktig XML basert på dette
        val cellView = if (viewType == 0) {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.right_chat_cell_item, parent, false)
        } else {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.left_chat_cell_item, parent, false)
        }

        val params: ViewGroup.LayoutParams = cellView.layoutParams
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        cellView.layoutParams = params

        // Oppretter riktig ViewHolder-type basert på hva som skal vises
        return if (viewType == 0) {
            RightChatViewHolder(cellView)
        } else {
            LeftChatViewHolder(cellView)
        }
    }

    // Her trenger jeg ikke bry meg om typen (venstre/høyre) fordi jeg tar inn foreldre-ViewHolder
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatObject = dataSet[position]

        holder.senderNameText.text = chatObject.username
        holder.messageText.text = chatObject.message

        val simpleDate = SimpleDateFormat("hh:mm dd/MM/yyyy", Locale.getDefault())
        holder.dateText.text = simpleDate.format(chatObject.time)
    }

    override fun getItemCount() = dataSet.size

    // Sjekker om chat er fra innlogget bruker (skal isåfall vises på høyre side)
    private fun isChatFromLoggedInUser(position: Int): Boolean {
        return dataSet[position].username == loggedInUsername
    }
}