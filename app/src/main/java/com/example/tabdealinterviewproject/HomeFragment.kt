package com.example.tabdealinterviewproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class HomeFragment: Fragment() {

    private lateinit var textView: TextView
    private lateinit var button: MaterialButton

    companion object {
        private const val ARG_BALANCE = "balance"

        fun newInstance(balance: Long): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putLong(ARG_BALANCE, balance)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        textView = rootView.findViewById(R.id.text_view)
        button = rootView.findViewById(R.id.button)

        val balance = arguments?.getLong(ARG_BALANCE) ?: 0
        textView.text = "$balance $"
        button.setOnClickListener {
            val fragment = AssetsFragment()

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return rootView
    }

}