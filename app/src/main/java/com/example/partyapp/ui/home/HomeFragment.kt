package com.example.partyapp.ui.home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.partyapp.data.Party
import com.example.partyapp.databinding.FragmentHomeBinding
import com.example.partyapp.repositories.PartyRepository
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.awaitCancellation

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listView: ListView = binding.availableParties

        listView.adapter = PartyListAdapter(this.requireActivity())

        return root


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private class PartyListAdapter(
        context: Context,
        val pr: PartyRepository = PartyRepository(),
        val partyList: ArrayList<Party> = pr.getParties(object: PartyRepository.PartyListCallback{
            override fun onCallback(parties: ArrayList<Party>): ArrayList<Party> {
                return parties
            }
        })
    ): BaseAdapter() {

        private val partiesContext: Context = context

        //      how many parties in the list
        override fun getCount(): Int {
            return partyList.size
        }

        override fun getItem(position: Int): Any {
            return "nush ce face asta dar trb sa stea aici"
        }

        override fun getItemId(position: Int): Long {
           return position.toLong()
        }

//       renders each party/ row
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val textView = TextView(partiesContext)
            textView.text = partyList[0].title
            return textView
        }

    }
}