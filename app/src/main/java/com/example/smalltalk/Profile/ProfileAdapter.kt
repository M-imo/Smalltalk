package com.example.smalltalk.Profile

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.smalltalk.R

class ProfileAdapter(
    private val dataSet: List<Profile>    // her kan du endre til ListOf<String> til Profile - endre oxo OnViewCreate

): RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    inner class ProfileViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val container : ConstraintLayout
        val nameText: TextView     // her endrer du fra TextView til nameText
        val emailText: TextView


        init {
            container = view.findViewById(R.id.cell_constraint)
            nameText = view.findViewById(R.id.cell_name_text)
            emailText = view.findViewById(R.id.cell_email_text)
            
        }

    }
    // når en celle blir opprettet så kjøres denne funksjonen automatisk - henter ut en view/celle fra XML
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
      val cellView = LayoutInflater.from(parent.context).inflate(R.layout.text_row_item, parent, false)

        return ProfileViewHolder(cellView)
    }

    // Setter dataen i cellen - kjøres hver gang en celle resiruleres
    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {

        val Profile = dataSet[position]  // her må du rename content til Profile / henter ut fra posisjonen den ligger på
        holder.nameText.text = Profile.name  // her må du rename fra content til Profile.name
       /* holder.textView.text = dataSet[position] */  // WTF is this?

        // Annen hver rute vil være farget i listen med navn
        if (position % 2 != 0) {          // finner par tall og oddetall - hvis den er null er den partall 
        holder.nameText.setBackgroundColor(Color.parseColor("#432152")) // endrer bakgrunnsfarge - kan oxo bare ha men denne setningen uten if()
    }       else{
        holder.nameText.setBackgroundColor(Color.parseColor("#FF33EE")) // du kan oxo bare skrive holder.textView.setBackgroundColor(Color.BLUE) isf alle tallene
        holder.emailText.text = Profile.email

        }


        }

    override fun getItemCount(): Int { // Forteller hvor stort innholdet du har
        return dataSet.size
    }
}
