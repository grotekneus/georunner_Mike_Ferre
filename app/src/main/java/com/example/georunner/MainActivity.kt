package com.example.georunner


import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.georunner.databinding.ActivityMainBinding
import android.content.Intent
//<<<<<<< HEAD
import android.os.Bundle
import com.example.georunner.room.User
import com.example.georunner.room.UserDatabase
import com.example.georunner.room.UserRoomRepository
//=======
//>>>>>>> 5ad0be549439a389e9ceca6b3658c96ca1ca6b24
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var userRoomRepository: UserRoomRepository
    //private lateinit var main: MainActivity


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)
        //main = activity as MainActivity

        GlobalScope.launch(Dispatchers.IO) {
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
            //GlobalScope.launch(Dispatchers.IO){

            //}
        }

        binding.sighnUpButton.setOnClickListener{
            createAccount()
        }

        binding.MikeButton.setOnClickListener {
            val intent = Intent(this,MikeActivity::class.java)
            startActivity(intent)
        }
    }
    private fun verifyPassword(){

        val userDao = userRoomRepository.userDao
        val username = binding.userNameTxt.text.toString()
        var password = binding.loginPassword.text.toString()
        GlobalScope.launch(Dispatchers.IO) {
            val user = userDao.getUserByUsername(username)
            if(user.password==password){
                login(user)
            }
            else{
                return@launch
            }
            // use the user object as needed
        }

        //Snackbar.make(binding.root, "login werkt", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        //val intent = Intent(this,HomeActivity::class.java)
        //startActivity(intent)
    }

    private fun login(user: User){
        val intent = Intent(this,MapActivity::class.java)
        intent.putExtra("USER_OBJECT", user)
        startActivity(intent)
    }

    private fun createAccount(){
        val intent = Intent(this,CreateAccountActivity::class.java)
        startActivity(intent)
    }
}

