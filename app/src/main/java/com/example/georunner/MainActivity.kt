package com.example.georunner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.georunner.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.loginButton.setOnClickListener {
                //var textMessage = "nice job dude"
                if(binding.userNameTxt.text.toString()==""){
                    Snackbar.make(binding.root, "please enter an username", Snackbar.LENGTH_LONG).setAction("Action", null).show()
                    return@setOnClickListener
                }
                else if(binding.loginPassword.text.toString()==""){
                    Snackbar.make(binding.root, "please enter a password", Snackbar.LENGTH_LONG).setAction("Action", null).show()
                    return@setOnClickListener
                }
            login()
        }

        binding.sighnUpButton.setOnClickListener{
            createAccount()
        }
    }
    private fun login(){
        Snackbar.make(binding.root, "login werkt", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
    }

    private fun createAccount(){
        val intent = Intent(this,CreateAccountActivity::class.java)
        startActivity(intent)
    }
}
