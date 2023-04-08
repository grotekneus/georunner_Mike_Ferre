package com.example.georunner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.georunner.databinding.ActivityCreateAccountBinding

class CreateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateAccountBinding.inflate((layoutInflater))
        setContentView(binding.root)

    }
}