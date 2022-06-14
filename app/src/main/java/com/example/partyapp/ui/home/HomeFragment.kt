package com.example.partyapp.ui.home

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.partyapp.R
import com.example.partyapp.data.Party
import com.example.partyapp.databinding.FragmentHomeBinding
import com.example.partyapp.repositories.FirebaseSetup
import com.example.partyapp.repositories.PartyRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
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

//        listView.adapter = PartyListAdapter(this.requireActivity())
        var partiesList: ArrayList<Party?> = ArrayList()
        val db: FirebaseSetup = FirebaseSetup()
        val reference = db.database.reference.child("parties")

        reference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                partiesList.clear()
                for(ps: DataSnapshot in snapshot.children){
                    val p: Party? = ps.getValue(Party::class.java)
                    partiesList.add(p)
                }
            }


            override fun onCancelled(dbError: DatabaseError) {
                Log.w(ContentValues.TAG, "loadParties:onCancelled", dbError.toException())
            }
        })
        return root


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    public class PartyListAdapter(context: Context) : BaseAdapter() {
//        public val partiesContext: Context = context
//        private lateinit var partyList: ArrayList<Party>
//        //      how many parties in the list
//
//        fun setParties(parties:ArrayList<Party>){
//            partyList = parties
//        }
//
//        override fun getCount(): Int {
//            return partyList.size
//        }
//
//        override fun getItem(position: Int): Any {
//            return partyList[position]
//        }
//
//        override fun getItemId(position: Int): Long {
//            return position.toLong()
//        }
//
//        //       renders each party/ row
////        @SuppressLint("ViewHolder")
//        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
//            var retView: View = LayoutInflater.from(partiesContext).
//            inflate(R.layout.fragment_home, viewGroup, false)
//
//            val p: Party = getItem(position) as Party
//            var partyTitleView = retView.findViewById<TextView>(R.id.text_view_item_title)
//            var partyDateView = retView.findViewById<TextView>(R.id.text_view_item_date)
//            var partyOrgView = retView.findViewById<TextView>(R.id.text_view_item_org)
//
//            partyTitleView.text = p.title
//            partyDateView.text = p.date
//            partyOrgView.text = p.organizer.username
//
//            return retView
//        }
//
//    }
}