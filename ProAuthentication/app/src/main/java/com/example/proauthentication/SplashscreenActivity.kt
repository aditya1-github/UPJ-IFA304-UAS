package com.example.proauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_splashscreen.*

class SplashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        spScreen.alpha = 0f
        spScreen.animate().setDuration(5000).alpha(1f).withEndAction {  // durasi animasi selama 5 detik
            val i = Intent(this, MainActivity::class.java)  // inisialisasi activity main
            startActivity(i)    // pindah ke activity main
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out) // animasi tampilan logo
            finish()
        }
    }
}