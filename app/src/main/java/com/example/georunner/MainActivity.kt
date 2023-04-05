package com.example.georunner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.georunner.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
/*
        setContentView(binding.root)
        binding.loginButton.setOnClickListener { view ->
            Snackbar.make(view, "clicked a button", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }
*/
        binding.loginButton.setOnClickListener { view ->
            Snackbar.make(view, "Nice, clicked a button", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }


    }
}

/*
class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnLogin.setText("lol")
        binding.btnLogin.setOnClickListener(this::login)

        println("MainActivity--onCreate")
    }



    override fun onSaveInstanceState(outState: Bundle) {
        println("MainActivity--OnSaveInstanceState (1arg)")
        outState.run {
            putString("name", binding.txtUsername.text.toString())
            // Do we want to save the password? Think about it...
        }
        super.onSaveInstanceState(outState)
    }

    // Watch out with this method with two arguments, it will only work with Lollipop and higher
    // See https://stackoverflow.com/questions/30549722/onsaveinstancestate-is-not-getting-called-after-screen-rotation/30549787
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        println("MainActivity--OnSaveInstanceState (2args)")
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        println("MainActivity--onRestoreInstanceState (2args)")
        super.onRestoreInstanceState(savedInstanceState, persistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        println("MainActivity--onRestoreInstanceState (1arg)")
        savedInstanceState.run {
            binding.txtUsername.setText(getString("name"))
        }
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        println("MainActivity--onStart")
    }

    override fun onResume() {
        super.onResume()
        println("MainActivity--onResume")
    }

    override fun onPause() {
        super.onPause()
        println("MainActivity--onPause")
    }

    override fun onStop() {
        super.onStop()
        println("MainActivity--onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("MainActivity--onDestroy")
    }

    private fun login(view: View) {
        if(binding.txtLogin.text.toString().isEmpty()) {
            msg("Your username is empty!", view)
            return
        }
        if(!binding.txtPassword.text.contentEquals("supersecret")) {
            msg("Invalid password!", view)
            return
        }

        msg("And we're in!", view)
        val intent = Intent(this, WelcomeActivity::class.java)
        // 1. the "easy but stupid" way
        // intent.putExtra("username", binding.txtUsername.text.toString())
        // 2. the "better" way, using a model
        intent.putExtra("user", createUser())
        startActivity(intent)
    }

    private fun createUser(): User {
        // push all layout values into your model here
        return User(binding.txtUsername.text.toString())
    }

    private fun msg(text: String, view: View) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()

    }
}
 */