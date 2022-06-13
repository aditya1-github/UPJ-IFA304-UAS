package com.example.proauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    // region inisialasi variable class - Begin
    private lateinit var btnRegister : Button
    private lateinit var btnLogin : Button
    private lateinit var txtEmail : EditText
    private lateinit var txtPassword : EditText
    private lateinit var otentikasi : FirebaseAuth
    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // region inisialasi variable local method
        otentikasi = FirebaseAuth.getInstance()
        txtEmail = findViewById(R.id.etEmail)
        txtPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.bLogin)
        // endregion

        // region inisialasi event tombol saat diklik
        // region event tombol login
        btnLogin.setOnClickListener {
            val email = txtEmail.text.toString().trim()
            val password = txtPassword.text.toString().trim()

            // region validasi email dan password
            if (email.isEmpty()){
                txtEmail.error = "Email Tidak Boleh Kosong"
                txtEmail.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty() || password.length < 6){
                txtPassword.error = "Password tidak boleh kosong dan minimal 6 karakter"
                txtPassword.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                txtEmail.error = "Format Penulisan email salah"
                txtEmail.requestFocus()
                return@setOnClickListener
            }
            // endregion

            loginUser(email, password) // memanggil fungsi loginUser dibawah
        }
        // endregion

        //region event tombol register
        btnRegister = findViewById(R.id.bRegister) // mapping variable dengan object layout
        btnRegister.setOnClickListener {
            Intent(this@MainActivity, RegisterActivity::class.java).also { // inisialiasi activity register
                startActivity(it) // pindah halaman ke register
            }
        }
        // endregion
        // endregion
    }

    // region fungsi yang dibutuhkan
    // region fungsi login user
    private fun loginUser(email: String, password: String){
        otentikasi.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful){
                Intent(this@MainActivity, HomeActivity::class.java).also{
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }else{
                Toast.makeText(this, it.exception?.message, Toast.LENGTH_LONG).show()
            }
        }
    }
    // endregion

    // region fungsi pengecekan jika user sudah login maka diarahkan ke activity Home
    override fun onStart() {
        super.onStart()
        if (otentikasi.currentUser != null) {
            Intent(this@MainActivity, HomeActivity::class.java).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
    // endregion
    // endregion
}
