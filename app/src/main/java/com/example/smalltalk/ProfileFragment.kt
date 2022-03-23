package com.example.smalltalk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ProfileFragment : Fragment() {

    lateinit var recycleView: RecyclerView //
    lateinit var layoutManager: LinearLayoutManager // sier hvordan innholdet i listen vises
    lateinit var adapter: ProfileAdapter//hvordan cellene skal se ut - må lage selv - lage en adapter klasse


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)

        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val profiles = listOf(
            Profile(
                "Erna Solberg",
            500,
            100000,
            1,
            "erna-smera@høyre.no",
            "+47 2222 5555",
                null,
            R.drawable.ernasolbergbilde
            ),
            Profile(
                "Jonas Gahr Støre",
                500,
                100000,
                1,
                "jonas-amazonas@ap.no",
                "+47 5555 3333",
                null,
            R.drawable.jonasamazonasbilde
            )
        )

        recycleView = view.findViewById(R.id.recycle_view_2)

        layoutManager = LinearLayoutManager(activity)
        adapter = ProfileAdapter(profiles)

        recycleView.layoutManager = layoutManager
        recycleView.adapter = adapter //kobler sammen *veldig vikitg å huske å ha med

    }


}

