package ru.startandroid.testauthproject.presentation.activities.auth


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import ru.startandroid.testauthproject.R
import ru.startandroid.testauthproject.presentation.activities.auth.flow.AuthFlowModel
import ru.startandroid.testauthproject.presentation.activities.auth.flow.IAuthFlow
import ru.startandroid.testauthproject.presentation.base.BaseActivity
import ru.startandroid.testauthproject.presentation.fragments.RecoverAccountFragment
import ru.startandroid.testauthproject.presentation.fragments.SignInFragment
import ru.startandroid.testauthproject.presentation.fragments.SingUpFragment
import ru.startandroid.testauthproject.utils.extention.ApplicationConstants
import ru.startandroid.testauthproject.utils.extention.hideKeyboard


class AuthActivity : BaseActivity(), IAuthFlow.IAuthListener {

    private val RC_SIGN_IN: Int = 0
    private val fragmentManager: FragmentManager = supportFragmentManager
    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var googleAccount: GoogleSignInAccount
    private lateinit var task: Task<GoogleSignInAccount>

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
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onStart() {
        super.onStart()
        googleAccount = GoogleSignIn.getLastSignedInAccount(this)!!
        updateUI(googleAccount)
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
                googleSignIn()
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

    private fun handleIntent() {
        openScreen(IAuthFlow.NavigationType.SIGN_IN_SCREEN)
        hideKeyboard()
    }

    private fun makeToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private fun updateUI(googleAcc: GoogleSignInAccount?) {
        if (googleAcc != null)
            makeToast("Successfully logged in")
        else
            makeToast("Not logged in")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }


    private fun handleSignInResult(task: Task<GoogleSignInAccount>?) {
        try {
            googleAccount = task!!.getResult(ApiException::class.java)!!
            updateUI(googleAccount)
        } catch (e: ApiException) {
            Log.w("GoogleAuthException", "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }

    private fun googleSignIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
}