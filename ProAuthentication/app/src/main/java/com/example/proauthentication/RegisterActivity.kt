package com.example.proauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    // region inisialasi variable class - Begin
    private lateinit var btnLogin : Button
    private lateinit var btnSimpan : Button
    private lateinit var txtEmail : EditText
    private lateinit var txtPassword : EditText
    private lateinit var otentikasi : FirebaseAuth
    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // region inisialasi variable local - dimapping dengan object di layout dan firebase
        otentikasi = FirebaseAuth.getInstance()
        btnSimpan = findViewById(R.id.bSimpan)
        btnLogin = findViewById(R.id.bLogin)
        txtEmail = findViewById(R.id.etEmailReg)
        txtPassword = findViewById(R.id.etPasswordReg)
        // endregion

        // region inisialasi event tombol di klik
        btnSimpan.setOnClickListener {
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

            registerUser(email, password)
        }
        btnLogin.setOnClickListener {
            Intent(this@RegisterActivity, MainActivity::class.java).also {
                startActivity(it)
            }
        }
        // endregion
    }

    // region fungsi yang dibutuhkan
    // region fungsi tombol register user
    private fun registerUser(email: String, password: String){
        otentikasi.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful){
                Intent(this@RegisterActivity, HomeActivity::class.java).also{
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }else{
                Toast.makeText(this, it.exception?.message, Toast.LENGTH_LONG).show()
            }
        }
    }
    // endregion

    // region fungsi pengecekan jika sudah login akan diarahkan ke halaman home
    override fun onStart() {
        super.onStart()
        if (otentikasi.currentUser != null){
            Intent(this@RegisterActivity, HomeActivity::class.java).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
    // endregion

    // endregion
}