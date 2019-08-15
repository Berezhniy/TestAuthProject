package ru.startandroid.testauthproject.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_sing_up.view.*
import ru.startandroid.testauthproject.R
import ru.startandroid.testauthproject.presentation.activities.auth.flow.AuthFlowErrorModel
import ru.startandroid.testauthproject.presentation.activities.auth.flow.IAuthFlow
import ru.startandroid.testauthproject.presentation.base.BaseAuthFragment

class SingUpFragment : BaseAuthFragment(), IAuthFlow.IAuthCallback {

    private var listener: IAuthFlow.IAuthListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //ToDo get extras from bundle
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IAuthFlow.IAuthListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }


    override fun viewLogic(view: View) {
        view.tv_sign_in.setOnClickListener { listener!!.openScreen(IAuthFlow.NavigationType.SIGN_IN_SCREEN) }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun getLayout(): Int = R.layout.fragment_sing_up

    override fun showError(error: AuthFlowErrorModel) {
        //ToDo show error for validation inputs
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SingUpFragment().apply {
                arguments = Bundle().apply {
                    //todo put bundle
                }
            }
    }
}
