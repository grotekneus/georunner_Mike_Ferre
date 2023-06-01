package com.example.georunner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.georunner.databinding.ActivityCreateAccountBinding
import com.example.georunner.room.User
import com.example.georunner.room.UserRoomRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CreateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAccountBinding
    private lateinit var userRoomRepository: UserRoomRepository

    //val userRoomRepository = UserRoomRepository(applicationContext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateAccountBinding.inflate((layoutInflater))
        setContentView(binding.root)

        lifecycleScope.launch(Dispatchers.IO) {
            userRoomRepository = UserRoomRepository(applicationContext)
        }

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
                val user=createAccount()
                val intent = Intent(this,HomeActivity::class.java)
                intent.putExtra("USER_OBJECT", user)
                startActivity(intent)
            }
        }

    }

    private fun createAccount(): User {
        val userName=binding.newUsernameTxt.text.toString()
        val password=binding.enterPasswordTxt.text.toString()

        val user=User(password,userName,0)

        lifecycleScope.launch(Dispatchers.IO){
            userRoomRepository.userDao.insert(user)
        }
        return user

    }

}