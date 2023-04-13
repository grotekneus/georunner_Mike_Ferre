package com.example.georunner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.georunner.databinding.ActivityCreateAccountBinding
import com.example.georunner.room.User
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateAccountBinding.inflate((layoutInflater))
        setContentView(binding.root)

        binding.createAccountButton.setOnClickListener{
            if(binding.newUsernameTxt.text.toString()==""){
                Snackbar.make(binding.root, "please enter an username", Snackbar.LENGTH_LONG).setAction("Action", null).show()
                return@setOnClickListener
            }
            else if(binding.enterPasswordTxt.text.toString()==""){
                Snackbar.make(binding.root, "please enter a password", Snackbar.LENGTH_LONG).setAction("Action", null).show()
                return@setOnClickListener
            }
            else if(binding.confirmPasswordTxt.text.toString() != binding.enterPasswordTxt.text.toString() ){
                Snackbar.make(binding.root, "this password does not match previous password", Snackbar.LENGTH_LONG).setAction("Action", null).show()
                return@setOnClickListener
            }

            else {
                createAccount()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }

    }

    private fun createAccount(){
        val userName=binding.newUsernameTxt.text.toString()
        val password=binding.enterPasswordTxt.text.toString()

        val user=User(password, userName)

        GlobalScope.launch(Dispatchers.IO){
            //userRoomRepository.userDao.insert(user)
        }


    }
}