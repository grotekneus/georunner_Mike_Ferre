package com.example.georunner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.georunner.databinding.ActivityCreateAccountBinding
import com.example.georunner.room.User
import com.example.georunner.room.UserRoomRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CreateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAccountBinding
    private lateinit var userRoomRepository: UserRoomRepository

    //val userRoomRepository = UserRoomRepository(applicationContext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateAccountBinding.inflate((layoutInflater))
        setContentView(binding.root)
        //val userDatabase = UserDatabase.getDatabase(applicationContext)
        //val userRoomRepository = UserRoomRepository(applicationContext)

        GlobalScope.launch(Dispatchers.IO) {
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
                createAccount()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }

    }

    private fun createAccount(){
        val userName=binding.newUsernameTxt.text.toString()
        val password=binding.enterPasswordTxt.text.toString()

        val user=User(password,userName,0)

        GlobalScope.launch(Dispatchers.IO){
            userRoomRepository.userDao.insert(user)
        }
        //Snackbar.make(binding.root, "WEEE fucking did it", Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }


    /*
     private fun insertDataToDatabase() {
        val firstName = addFirstName_et.text.toString()
        val lastName = addLastName_et.text.toString()
        val age = addAge_et.text

        if(inputCheck(firstName, lastName, age)){
            // Create User Object
            val user = User(0, firstName, lastName, Integer.parseInt(age.toString()))
            // Add Data to Database
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            // Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }
     */
}