package com.example.tapthecircle

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tapthecircle.databinding.ActivityGameBinding
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding;
    private var score=0;
    private val defaultTime:Long=45000;//45 second
    var randomPositionX : Float= 0F;
    var randomPositionY : Float= 0F;
    val screenWidthSize= Resources.getSystem().getDisplayMetrics().widthPixels;
    val screenHeightSize =  Resources.getSystem().getDisplayMetrics().heightPixels;
    var highScore:Int=0;
    var isGameOver:Boolean=false;
    lateinit var sharedPreferences : SharedPreferences;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(layoutInflater);
        val view = binding.root;
        val intent = intent;
        highScore = intent.getIntExtra("highScore",0);
        setContentView(view);
        sharedPreferences = this.getSharedPreferences("com.dogukansakin.datastorage", Context.MODE_PRIVATE);
        object : CountDownTimer(defaultTime,1000){
            override fun onTick(p0: Long) {
                binding.timeText.text = "Time: "+(p0/1000);
                changeCirclePosition();
            }

            override fun onFinish() {
                isGameOver=true;
                binding.timeText.text = "Time: 0";
                val alert = AlertDialog.Builder(this@GameActivity);
                alert.setTitle("Game Over");
                alert.setMessage("Restart The Game?");
                alert.setPositiveButton("Yes"){ dialog,which->
                    val intent = intent;
                    finish();
                    startActivity(intent);

                }
                alert.setNegativeButton("No"){ dialog,which->
                    val intent = Intent(applicationContext,ScoreActivity::class.java);
                    startActivity(intent);

                }
                alert.show();
                if(score>highScore){
                    sharedPreferences.edit().putInt("highScore",score).apply();
                }
            }

        }.start()

    }
    fun increaseScore(view: View){
        if(!isGameOver){
            score += 1;
            binding.scoreText.text="Score: "+score;
            changeCirclePosition();
        }


    }
    fun changeCirclePosition(){

        randomPositionX = Random.nextInt(-(screenWidthSize/3),screenWidthSize/3).toFloat();
        binding.circleView.translationX = randomPositionX;
        randomPositionY = Random.nextInt(-(screenHeightSize/3), screenHeightSize/3).toFloat();
        binding.circleView.translationY = randomPositionY;
    }

}