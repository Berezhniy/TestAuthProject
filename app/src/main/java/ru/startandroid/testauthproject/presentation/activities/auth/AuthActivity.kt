package ru.startandroid.testauthproject.presentation.activities.auth


import android.app.SharedElementCallback
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import ru.startandroid.testauthproject.R
import ru.startandroid.testauthproject.presentation.activities.auth.flow.AuthFlowModel
import ru.startandroid.testauthproject.presentation.activities.auth.flow.IAuthFlow
import ru.startandroid.testauthproject.presentation.base.BaseActivity
import ru.startandroid.testauthproject.presentation.fragments.RecoverAccountFragment
import ru.startandroid.testauthproject.presentation.fragments.SignInFragment
import ru.startandroid.testauthproject.presentation.fragments.SingUpFragment
import ru.startandroid.testauthproject.utils.extention.ApplicationConstants
import ru.startandroid.testauthproject.utils.extention.hideKeyboard
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.android.synthetic.main.fragment_sign_in.*
import javax.security.auth.callback.Callback


class AuthActivity : BaseActivity(), IAuthFlow.IAuthListener {

    val fragmentManager:FragmentManager = supportFragmentManager
    companion object {
        @JvmStatic
        fun newInstanceWithClearStack(context: Context, typeScreen: IAuthFlow.NavigationType): Intent {
            return Intent(context, AuthActivity::class.java).apply {
                this.putExtra(ApplicationConstants.AUTH_TYPE_SCREEN, typeScreen)
                this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }
    }

//    override fun injectDependency(component: ViewModelComponent) {
//
//    }
//    override fun onStart() {
//        super.onStart()
////    val account = GoogleSignIn.getLastSignedInAccount(this)
////    updateUI(account)
//    }

//    private fun updateUI(account: GoogleSignInAccount?) {
//
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        handleIntent()
    }

    override fun authRequest(flowModel: AuthFlowModel, callback: IAuthFlow.IAuthCallback) {
        when (flowModel.type) {
            IAuthFlow.AuthType.SIGN_IN -> {

            }

            IAuthFlow.AuthType.SIGN_UP -> {

            }

            IAuthFlow.AuthType.RECOVER_ACCOUNT -> {

            }
        }
    }

    override fun socialAuth(type: IAuthFlow.SocialAuthType, callback: IAuthFlow.IAuthCallback) {
        when (type) {
            IAuthFlow.SocialAuthType.FACEBOOK -> {

            }
            IAuthFlow.SocialAuthType.GOOGLE -> {

            }
        }
    }

    override fun openScreen(typeScreen: IAuthFlow.NavigationType) {
        when (typeScreen) {
            IAuthFlow.NavigationType.SIGN_IN_SCREEN -> {
                fragmentManager.beginTransaction()
                    .replace(R.id.auth_container, SignInFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
//                replaceFragment(R.id.auth_container, SignInFragment.newInstance())
            }

            IAuthFlow.NavigationType.SIGN_UP_SCREEN -> {
                fragmentManager.beginTransaction()
                    .replace(R.id.auth_container, SingUpFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
//                replaceFragment(R.id.auth_container, SingUpFragment.newInstance())

            }
            IAuthFlow.NavigationType.RECOVER_ACCOUNT_SCREEN -> {
                fragmentManager.beginTransaction()
                    .replace(R.id.auth_container, RecoverAccountFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
//                replaceFragment(R.id.auth_container, RecoverAccountFragment.newInstance())
            }
        }
    }

    private fun handleIntent(){
        openScreen(IAuthFlow.NavigationType.SIGN_IN_SCREEN)
        hideKeyboard()
    }
//    var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//        .requestEmail()
//        .build()

}