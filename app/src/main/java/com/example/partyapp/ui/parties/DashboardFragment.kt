package com.example.partyapp.ui.parties

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.partyapp.databinding.FragmentDashboardBinding
import kotlin.concurrent.timer

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        val button : Button = binding.buttonHelloWorld

        this.binding.buttonHelloWorld.setOnClickListener(){
            print("hello")

            ObjectAnimator.ofFloat(button, "translationX", 100f).apply {
                duration = 2000
                start()
            }
            this.binding.buttonHelloWorld.setOnClickListener(){
                ObjectAnimator.ofFloat(button, "translationY", 100f).apply {
                    duration = 2000
                    start()
                }
            }


        }


        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}