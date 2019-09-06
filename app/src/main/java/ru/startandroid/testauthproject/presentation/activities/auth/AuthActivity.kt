package ru.startandroid.testauthproject.presentation.activities.auth


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
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
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.FacebookException
import org.json.JSONException
import com.facebook.GraphResponse
import org.json.JSONObject
import com.facebook.GraphRequest
import com.facebook.login.LoginResult
import com.facebook.FacebookCallback
import com.facebook.login.LoginManager
import java.util.Arrays.asList
import java.util.*


class AuthActivity : BaseActivity(), IAuthFlow.IAuthListener {

    private val callbackManager: CallbackManager = CallbackManager.Factory.create()
    private val RC_SIGN_IN: Int = 0
    private val fragmentManager: FragmentManager = supportFragmentManager
    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var googleAccount: GoogleSignInAccount? = null
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
//        FacebookSdk.sdkInitialize(getApplicationContext())
//        AppEventsLogger.activateApp(this)
        handleIntent()
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignOut()

    }

    override fun onStart() {
        super.onStart()
        googleAccount = GoogleSignIn.getLastSignedInAccount(this)
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
                Fblogin()
            }
            IAuthFlow.SocialAuthType.GOOGLE -> {
                googleSignIn()
            }
        }
    }

    override fun openScreen(typeScreen: IAuthFlow.NavigationType) {
        when (typeScreen) {
            IAuthFlow.NavigationType.SIGN_IN_SCREEN -> {
                getOpenScreen(SignInFragment.newInstance())
            }

            IAuthFlow.NavigationType.SIGN_UP_SCREEN -> {
                getOpenScreen(SingUpFragment.newInstance())
            }
            IAuthFlow.NavigationType.RECOVER_ACCOUNT_SCREEN -> {
                getOpenScreen(RecoverAccountFragment.newInstance())
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
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }


    private fun handleSignInResult(task: Task<GoogleSignInAccount>?) {
        try {
            if (task != null) {
                googleAccount = task.getResult(ApiException::class.java)
                updateUI(googleAccount)
            }
        } catch (e: ApiException) {
            Log.w("GoogleAuthException", "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }

    private fun googleSignIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun googleSignOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                makeToast("Successfully signed out")
            }
    }

    private fun getOpenScreen(fragment: Fragment) {
        fragmentManager.beginTransaction()
            .replace(R.id.auth_container, fragment)
            .addToBackStack("Auth")
            .commit()
    }

    private fun Fblogin() {
        // Set permissions
        LoginManager.getInstance()
            .logInWithReadPermissions(this, Arrays.asList("email", "public_profile"))

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    makeToast("Successfully logged in")
//                    GraphRequest.newMeRequest(
//                        loginResult.accessToken
//                    ) { json, response ->
//                        if (response.error != null) {
//                            // handle error
//                            println("ERROR")
//                        } else {
//                            println("Success")
//                            try {
//
//                                val jsonresult = (json).toString()
//                                println("JSON Result$jsonresult")
//
//                                val str_email = json.getString("email")
//                                val str_id = json.getString("id")
//                                val str_firstname = json.getString("first_name")
//                                val str_lastname = json.getString("last_name")
//
//                            } catch (e: JSONException) {
//                                e.printStackTrace()
//                            }
//
//                        }
//                    }.executeAsync()
                }

                override fun onCancel() {
                    Log.d("CANCELED", "On cancel")
                }

                override fun onError(error: FacebookException) {
                    Log.d("ERROR", error.toString())
                }
            })
    }
}