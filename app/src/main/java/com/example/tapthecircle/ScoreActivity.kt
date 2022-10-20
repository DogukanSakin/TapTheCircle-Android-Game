package com.example.tapthecircle

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.tapthecircle.databinding.ActivityScoreBinding


class ScoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScoreBinding;
    lateinit var sharedPreferences : SharedPreferences;
    var highScore: Int =0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityScoreBinding.inflate(layoutInflater);
        val view = binding.root;
        setContentView(view);
        sharedPreferences = this.getSharedPreferences("com.dogukansakin.datastorage", Context.MODE_PRIVATE);
        highScore = sharedPreferences.getInt("highScore",0);
        binding.highScoreText.text = highScore.toString();

    }
    fun playGame(view: View){
        finish();
        val intent = Intent(applicationContext,GameActivity::class.java);
        intent.putExtra("highScore",highScore);
        startActivity(intent);
    }
}