package com.bangkit.capstone.insightapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.databinding.ActivityLoginBinding
import com.bangkit.capstone.insightapp.view.activity.welcomescreen.WelcomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignClient = GoogleSignIn.getClient(this, gso)

        mAuth = FirebaseAuth.getInstance()

        binding.login.setOnClickListener {
            signIn()
        }

        binding.tamuLogin.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn() {
        val signInIntent = googleSignClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("Sign in Activity", "FirebaseAuthWithGoogle" + account.id)
                    fireBaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    Log.w("Sign in Activity", "Google Sign in failed")
                }
            } else {
                Log.w("Sign in Activity", exception.toString())
            }
        }
    }

    private fun fireBaseAuthWithGoogle(idToken: String) {
        binding.loadingbarLogin.visibility = View.VISIBLE
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("Sign in Activity", "SignInWithCredential:success")

//                    firebaseuUid = mAuth.currentUser?.uid.toString()
//                    refUser = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseuUid)
//                    val userHashMap = HashMap<String, Any>()
//                    userHashMap["uid"] = firebaseuUid
//                    userHashMap["profile_photo"] = mAuth.currentUser?.photoUrl.toString()
//                    userHashMap["username"] = mAuth.currentUser?.displayName.toString()
//                    userHashMap["email"] = mAuth.currentUser?.email.toString()
//                    userHashMap["status"] = "offline"
//                    userHashMap["search"] = mAuth.currentUser?.displayName.toString().toLowerCase(Locale.ROOT)
//                    userHashMap["bio"] = "Empty"
//
//                    refUser.updateChildren(userHashMap)
//                        .addOnCompleteListener { tasks ->
//                            if (tasks.isSuccessful) {
//                                Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
//                            } else {
//                                Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
//                            }
//                        }

                    binding.loadingbarLogin.visibility = View.GONE
                    val intent = Intent(this, WelcomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()

                } else {
                    binding.loadingbarLogin.visibility = View.GONE
                    Log.w("sign in activity", "SignInWithCredential:failure")
                }
            }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    companion object {
        private const val RC_SIGN_IN = 120
    }

}