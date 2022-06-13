package com.example.proauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proauthentication.models.Movie
import com.example.proauthentication.models.MovieResponse
import com.example.proauthentication.services.MovieApiInterface
import com.example.proauthentication.services.MovieApiService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    // region inisialasi variable class - Begin
    private lateinit var viewLogout : TextView
    private lateinit var otentikasi : FirebaseAuth
    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // region inisialasi variable local - dimapping dengan object di layout dan firebase
        otentikasi = FirebaseAuth.getInstance()
        viewLogout = findViewById(R.id.vLogout)
        // endregion

        // region inisialasi event tombol di klik
        viewLogout.setOnClickListener {
            otentikasi.signOut()
            Intent(this@HomeActivity, MainActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
        // endregion

        // region binding data ke list
        rv_movies_list.layoutManager = LinearLayoutManager(this)
        rv_movies_list.setHasFixedSize(true)
        getMovieData { movies : List<Movie> ->
            rv_movies_list.adapter = MovieAdapter(movies)
        }
        // endregion
    }

    // region fungsi mengambil data movie dari api open database movie
    private fun getMovieData(callback: (List<Movie>) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getMovieList().enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                return callback(response.body()!!.movies)
            }
        })
    }
    // endregion
}

