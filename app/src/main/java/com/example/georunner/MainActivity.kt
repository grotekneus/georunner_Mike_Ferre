package com.example.georunner



import androidx.appcompat.app.AppCompatActivity
import com.example.georunner.databinding.ActivityMainBinding
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.georunner.room.User
import com.example.georunner.room.UserRoomRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var userRoomRepository: UserRoomRepository

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)

        lifecycleScope.launch(Dispatchers.IO) {
            userRoomRepository = UserRoomRepository(applicationContext)
        }

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
            verifyPassword()
        }

        binding.sighnUpButton.setOnClickListener{
            createAccount()
        }
    }
    private fun verifyPassword(){

        val userDao = userRoomRepository.userDao
        val username = binding.userNameTxt.text.toString()
        var password = binding.loginPassword.text.toString()
        lifecycleScope.launch(Dispatchers.IO) {
            if(userDao.getUserByUsername(username) != null){
                val user = userDao.getUserByUsername(username)

                if(user.password==password){
                    login(user)
                }
                else{
                    return@launch
                }
            }

        }

    }

    private fun login(user: User){
        val intent = Intent(this,HomeActivity::class.java)
        intent.putExtra("USER_OBJECT", user)
        startActivity(intent)
    }

    private fun createAccount(){
        val intent = Intent(this,CreateAccountActivity::class.java)
        startActivity(intent)
    }

}

