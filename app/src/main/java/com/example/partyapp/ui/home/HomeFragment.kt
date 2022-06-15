package com.example.partyapp.ui.home

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.partyapp.ViewPartyActivity
import com.example.partyapp.data.Party
import com.example.partyapp.data.User
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

        val pr = PartyRepository()
        pr.getParties()
        val listView: ListView = binding.availableParties
        val adapter = PartyListAdapter(this.requireActivity().applicationContext)
        listView.adapter = adapter
        val obs = Observer<ArrayList<Party>>() {
            Log.w("salutari", "hello!!!")
            adapter.setNewPartyList(it)
        }
        pr.getFinalParties().observe(viewLifecycleOwner, obs)
        listView.setOnItemClickListener{
            parent,view,position,id ->
            val clickedParty : Party = adapter.partyList[position]
            val intent = Intent(this.requireActivity().applicationContext, ViewPartyActivity::class.java)

            val partyData = ArrayList<String>()
            partyData.add(clickedParty.title)
            partyData.add(clickedParty.address)
            partyData.add(clickedParty.date)
            partyData.add(clickedParty.organizer.name)
            intent.putExtra("clickedparty", partyData)
            startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        Log.w("salutari", "SALUT FROM DESTROY")
        super.onDestroyView()
        _binding = null
    }

    private class PartyListAdapter(
        context: Context,
    ) : BaseAdapter() {
        var partyList: ArrayList<Party> = ArrayList<Party>()
        private val partiesContext: Context = context

        fun setNewPartyList(list: ArrayList<Party>) {
            partyList = list
            notifyDataSetChanged()
        }

        //      how many parties in the list
        override fun getCount(): Int {
            return partyList.size
        }

        override fun getItem(position: Int): Any {
            return partyList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        //       renders each party/ row
        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
            val textView = TextView(partiesContext)
            textView.text = partyList[position].title
            return textView
        }

    }
}
