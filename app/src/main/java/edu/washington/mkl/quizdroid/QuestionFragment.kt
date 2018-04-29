package edu.washington.mkl.quizdroid

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "q1"
private const val ARG_PARAM2 = "q2"
private const val ARG_PARAM3 = "q3"
private const val ARG_PARAM4 = "q4"


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [QuestionFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [QuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class QuestionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var question: String? = null
    private var q1: String? = null
    private var q2: String? = null
    private var q3: String? = null
    private var q4: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var selected:Int = -1
    private var btn_submit:Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            question = it.getString("question")
            q1 = it.getString(ARG_PARAM1)
            q2 = it.getString(ARG_PARAM2)
            q3 = it.getString(ARG_PARAM3)
            q4 = it.getString(ARG_PARAM4)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_question, container, false)

        val tv_title = view.findViewById(R.id.textView_question) as TextView
        btn_submit = view.findViewById(R.id.btn_submit) as Button
        val layout = view.findViewById(R.id.linearLayout) as LinearLayout

        val rg_answers = RadioGroup(view.context)
        rg_answers.orientation = RadioGroup.VERTICAL

        tv_title.setText(question)

        val answers = arrayOf(q1.toString(), q2.toString(), q3.toString(), q4.toString())

        for (option in answers) {
            val radioButton = RadioButton(view.context)
            radioButton.text = option
            rg_answers.addView(radioButton)
        }

        rg_answers.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            selected  = rg_answers.indexOfChild(view.findViewById(rg_answers.getCheckedRadioButtonId()))
            updateButton()
        })

        layout.addView(rg_answers)

        btn_submit!!.setOnClickListener { view ->
            onButtonPressed()
        }

        updateButton()

        return view
    }

    fun updateButton() {
        btn_submit!!.visibility = if(selected != -1) View.VISIBLE else View.INVISIBLE
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed() {
        listener?.onQuestionButtonInteraction(selected)
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
        fun onQuestionButtonInteraction(selected:Int)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuestionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String, param3:String, param4:String, param5:String) =
                QuestionFragment().apply {
                    arguments = Bundle().apply {
                        putString("question", param1)
                        putString(ARG_PARAM1, param2)
                        putString(ARG_PARAM2, param3)
                        putString(ARG_PARAM3, param4)
                        putString(ARG_PARAM4, param5)
                    }
                }
    }
}
