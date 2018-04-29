package edu.washington.mkl.quizdroid

import android.content.Context
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
private const val ARG_PARAM1 = "question"
private const val ARG_PARAM2 = "answer"
private const val ARG_PARAM3 = "userChoice"
private const val ARG_PARAM4 = "correct"
private const val ARG_PARAM5 = "total"
private const val ARG_PARAM6 = "finished"


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AnswerFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AnswerFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AnswerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var question: String? = null
    private var answer: String? = null
    private var userChoice: String? = null
    private var correct: Int? = null
    private var total: Int? = null
    private var finished: Boolean? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            question = it.getString(ARG_PARAM1)
            answer = it.getString(ARG_PARAM2)
            userChoice = it.getString(ARG_PARAM3)
            correct = it.getInt(ARG_PARAM4)
            total = it.getInt(ARG_PARAM5)
            finished = it.getBoolean(ARG_PARAM6)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_answer, container, false)

        val tv_user = view.findViewById(R.id.textView_userChoice) as TextView
        val tv_answer = view.findViewById(R.id.textView_answer) as TextView
        val tv_title = view.findViewById(R.id.textView_title) as TextView
        val tv_progress = view.findViewById(R.id.textView_progress) as TextView
        val btn_next = view.findViewById(R.id.btn_next) as Button

        tv_user.setText(userChoice)
        tv_answer.setText(answer)
        tv_title.setText(question)
        tv_progress.setText(getString(R.string.progress_message, correct, total))

        btn_next.setOnClickListener {
            onButtonPressed()
        }

        btn_next.setText(if(finished == true) "Finish" else "Next")

        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed() {
        listener?.onAnswerButtonInteraction()
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
        // TODO: Update argument type and name
        fun onAnswerButtonInteraction()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AnswerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String, param4:Int, param5:Int, param6:Boolean) =
                AnswerFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                        putString(ARG_PARAM3, param3)
                        putInt(ARG_PARAM4, param4)
                        putInt(ARG_PARAM5, param5)
                        putBoolean(ARG_PARAM6, param6)
                    }
                }
    }
}
