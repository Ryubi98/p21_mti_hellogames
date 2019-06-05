package com.ryubi.hellogames

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainFragment : Fragment() {

    fun startDetailFragment(id: Int) {
        val dataBundle = Bundle()
        dataBundle.putInt("game_id", id)
        val newFragment = DetailFragment()
        newFragment.arguments = dataBundle
        fragmentManager!!.beginTransaction()
            .replace(R.id.main_container, newFragment)
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val baseURL = "https://androidlessonsapi.herokuapp.com/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()
        val service: WSList = retrofit.create(WSList::class.java)

        val wsCallBack : Callback<List<Game>> = object : Callback<List<Game>> {
            override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                Log.w("TAG", "WebService call failed")
            }

            override fun onResponse(call: Call<List<Game>>, response: Response<List<Game>>) {
                if (response.code() == 200) {
                    val responseData = ArrayList(response.body()!!)

                    val nb1 = responseData.random()
                    responseData.remove(nb1)
                    val nb2 = responseData.random()
                    responseData.remove(nb2)
                    val nb3 = responseData.random()
                    responseData.remove(nb3)
                    val nb4 = responseData.random()
                    responseData.remove(nb4)

                    Glide.with(this@MainFragment)
                        .load(nb1.picture)
                        .into(game1)
                    Glide.with(this@MainFragment)
                        .load(nb2.picture)
                        .into(game2)
                    Glide.with(this@MainFragment)
                        .load(nb3.picture)
                        .into(game3)
                    Glide.with(this@MainFragment)
                        .load(nb4.picture)
                        .into(game4)

                    game1.setOnClickListener {
                        startDetailFragment(nb1.id)
                    }
                    game2.setOnClickListener {
                        startDetailFragment(nb2.id)
                    }
                    game3.setOnClickListener {
                        startDetailFragment(nb3.id)
                    }
                    game4.setOnClickListener {
                        startDetailFragment(nb4.id)
                    }
                }
                else {
                    Toast.makeText(activity, "Code : " + response.code(), Toast.LENGTH_SHORT).show()
                }
            }
        }

        service.getAllGames().enqueue(wsCallBack)
    }
}
