package edu.washington.mkl.quizdroid

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "quizName"
private const val ARG_PARAM2 = "quizSummary"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SummaryFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SummaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SummaryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var quizName: String? = null
    private var quizSummary: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            quizName = it.getString(ARG_PARAM1)
            quizSummary = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_summary, container, false)

        val tv_title = view.findViewById(R.id.textView_title) as TextView
        val tv_description = view.findViewById(R.id.textView_description) as TextView
        //val btn_start = findViewById(R.id.btn_submit) as Button

        tv_title.setText(quizName)
        tv_description.setText(quizSummary)

        val btn_next = view.findViewById(R.id.btn_start) as Button

        btn_next.setOnClickListener {
            onButtonPressed()
        }

        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed() {
        listener?.onSummaryButtonInteraction()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        fun onSummaryButtonInteraction()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SummaryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SummaryFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
