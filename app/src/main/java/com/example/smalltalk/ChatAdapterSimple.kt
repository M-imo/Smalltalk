package com.example.smalltalk

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

    // Tar imot brukernavnet til nåværende innlogget bruker og bruker dette til å sjekke om jeg har sendt meldingen eller ikke
    class ChatAdapterSimple(
        private var dataSet: List<ChatObject>,
        private val loggedInUsername: String
    ) : RecyclerView.Adapter<ChatAdapterSimple.ChatViewHolder>() {

        inner class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val senderNameText: TextView = view.findViewById(R.id.simple_chat_bubble_name)
            val leftProfileImage: ImageView = view.findViewById(R.id.simple_chat_bubble_image_left)
            val rightProfileImage: ImageView = view.findViewById(R.id.simple_chat_bubble_image_right)
            val messageText: TextView = view.findViewById(R.id.simple_chat_bubble_message_text)
            val dateText: TextView = view.findViewById(R.id.simple_chat_bubble_date_text)
        }

        //Oppretter viewHolders fra XML
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {

            val cellView = LayoutInflater.from(parent.context)
                .inflate(R.layout.simple_chat_cell_item, parent, false)

        //Setter størrelse på cellen
            val params: ViewGroup.LayoutParams = cellView.layoutParams
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            cellView.layoutParams = params


            return ChatViewHolder(cellView)
        }

        override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
            //Henter ut chatobjektet på riktig posisjon
            val chatObject = dataSet[position]
            //Sjekker om denne spesifikke meldingen er for innlogget bruker
            val loggedInUser = isChatFromLoggedInUser(position)

            //Setter informasjon i cellen
            holder.senderNameText.text = chatObject.username
            holder.messageText.text = chatObject.message

            // Gjør så dato ser litt finere ut
            val simpleDate = SimpleDateFormat("hh:mm dd/MM/yyyy", Locale.getDefault())
            holder.dateText.text = simpleDate.format(chatObject.time)

            //Bestemmer hvilket bilde som skal vises basert på om man er logget inn
            holder.leftProfileImage.isVisible = !loggedInUser
            holder.rightProfileImage.isVisible = loggedInUser

            //Endrer farge og posisjon for chat-boblen basert på om man er logget inn
            if (loggedInUser) {
                holder.rightProfileImage.setImageResource(R.drawable.ernasolbergbilde)
                holder.senderNameText.gravity = Gravity.END
                holder.dateText.gravity = Gravity.END
                holder.messageText.setBackgroundResource(R.drawable.right_bubble_background)
            } else {
                holder.leftProfileImage.setImageResource(R.drawable.jonasamazonasbilde)
                holder.senderNameText.gravity = Gravity.START
                holder.dateText.gravity = Gravity.START
                holder.messageText.setBackgroundResource(R.drawable.left_bubble_background)
            }
        }

        override fun getItemCount() = dataSet.size

        // Sjekker om chat er fra innlogget bruker (skal isåfall vises på høyre side)
        private fun isChatFromLoggedInUser(position: Int): Boolean {
            return dataSet[position].username == loggedInUsername
        }

        //Funksjon som gjør det mulig å oppdatere listen i recycleViewet
        fun updateData (newList: List<ChatObject>) {
            dataSet = newList
            notifyDataSetChanged()
        }
    }
