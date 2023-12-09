package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


private const val ARG_TOILETTES = "param1"

class ToiletListFragment : Fragment(){
    private var toilets: ArrayList<Toilet> = arrayListOf()
    private lateinit var bookAdapter: ToiletAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            toilets = it.getSerializable(ARG_TOILETTES) as ArrayList<Toilet>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        recyclerView = view.findViewById(R.id.toilet_list_rv)
        bookAdapter = ToiletAdapter(toilets)
        recyclerView.adapter = bookAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(toilets: ArrayList<Toilet>) =
            ToiletListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_TOILETTES, toilets)
                }
            }
    }
}