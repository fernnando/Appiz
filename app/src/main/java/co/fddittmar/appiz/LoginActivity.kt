package co.fddittmar.appiz

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    var mAuth = FirebaseAuth.getInstance()
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    val RC_SIGN_IN = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(mAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // println("Teste")
        // println(mAuth.currentUser)

//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build()
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        btnSignIn.setOnClickListener({
            println("Button clicked")
            googleSignIn()
        })

        btnSignUp.setOnClickListener({
            tilConfirmPassword.visibility = View.VISIBLE
            btnSignIn.visibility = View.GONE
            btnSignUp.visibility = View.GONE
            btnCreateAccount.visibility = View.VISIBLE
        })

        btnCreateAccount.setOnClickListener {
            if(etLogin.text.isBlank() || etPassword.text.isBlank() || etConfirmPassword.text.isBlank() || (etPassword.text.toString() != etConfirmPassword.text.toString()))
                Toast.makeText(this, "Please check all fields", Toast.LENGTH_LONG).show()
            else
                createNewUser(etLogin.text.toString(), etPassword.text.toString())

        }


        btnSignIn.setOnClickListener {
            mAuth.signInWithEmailAndPassword(etLogin.text.toString(), etPassword.text.toString())
                    .addOnCompleteListener(this){ task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            //val user = mAuth.currentUser
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                            //updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this, "Invalid credentials.", Toast.LENGTH_LONG).show()
                            //updateUI(null)
                        }
                    }
        }



        /*mAuth.createUserWithEmailAndPassword("fernnando@email.teste", "senha12345")
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //val user = mAuth.currentUser
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        //updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        println(task.exception)
                        //updateUI(null)
                    }

                    // ...
                }*/
    }

    private fun googleSignIn() {
        val signInIntent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun createNewUser(username: String, password: String){
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //val user = mAuth.currentUser
                        Toast.makeText(this, "Account successfully created!", Toast.LENGTH_LONG).show()
                        tilConfirmPassword.visibility = View.GONE
                        btnSignIn.visibility = View.VISIBLE
                        btnSignUp.visibility = View.VISIBLE
                        btnCreateAccount.visibility = View.GONE
                        //updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, "Please check all fields", Toast.LENGTH_LONG).show()
                        println(task.exception)
                        //updateUI(null)
                    }

                    // ...
                }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            //updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            println(e.statusCode)
        }

    }


}
