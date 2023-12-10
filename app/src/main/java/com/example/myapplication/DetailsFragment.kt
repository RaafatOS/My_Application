package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var toilet:Toilet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            toilet = it.getSerializable(ARG_PARAM1) as Toilet
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)

        val img = view.findViewById<ShapeableImageView>(R.id.det_image)
        Glide.with(img.context)
            .load(toilet.ImageURL)
            .into(img)
        view.findViewById<TextView>(R.id.det_commune).text = toilet.Commune
        view.findViewById<TextView>(R.id.det_codeP).text = toilet.Code_Postal
        if(toilet.OpeningHours == null){
            view.findViewById<TextView>(R.id.det_opening).text = "Non renseigné"
        }
        else view.findViewById<TextView>(R.id.det_opening).text = toilet.OpeningHours
        view.findViewById<TextView>(R.id.det_coordinates).text = "[" + toilet.PointGeo.lon.toString() + " ; " + toilet.PointGeo.lat.toString() + "]"
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment detailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(toilet: Toilet) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, toilet)
                }
            }
    }
}