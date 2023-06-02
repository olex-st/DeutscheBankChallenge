package com.example.deutschebankchallenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.deutschebankchallenge.databinding.APostsListBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: APostsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = APostsListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}