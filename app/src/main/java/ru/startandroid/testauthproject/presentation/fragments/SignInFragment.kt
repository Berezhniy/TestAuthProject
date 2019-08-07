package ru.startandroid.testauthproject.presentation.fragments

import android.content.Context
import android.os.Bundle
import ru.startandroid.testauthproject.R
import ru.startandroid.testauthproject.presentation.activities.auth.flow.AuthFlowErrorModel
import ru.startandroid.testauthproject.presentation.activities.auth.flow.IAuthFlow
import ru.startandroid.testauthproject.presentation.base.BaseAuthFragment

class SignInFragment : BaseAuthFragment(), IAuthFlow.IAuthCallback {

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

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun getLayout(): Int  = R.layout.fragment_sign_in

    override fun showError(error: AuthFlowErrorModel) {
        //ToDo show error for validation inputs
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SignInFragment().apply {
                arguments = Bundle().apply {
                   //ToDo put bundle
                }
            }
    }

//    fun IAuthFlow.NavigationType() {
//        if (listener != null) listener.socialAuth(IAuthFlow.NavigationType.SIGN_IN_SCREEN, this)
//    }

}
