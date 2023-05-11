package com.example.georunner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        // Find the button in the layout
        val button = view.findViewById<Button>(R.id.btnSearch)
        // Inflate the layout for this fragment
        button.setOnClickListener {
            val newfragment = InfoFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.search_container, newfragment)
                .addToBackStack(null)
                .commit()
        }
        return view
    }
}