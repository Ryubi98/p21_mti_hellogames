package com.ryubi.hellogames

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val baseURL = "https://androidlessonsapi.herokuapp.com/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()
        val service: WSGame = retrofit.create(WSGame::class.java)

        val wsCallBack : Callback<GameDetail> = object : Callback<GameDetail> {
            override fun onFailure(call: Call<GameDetail>, t: Throwable) {
                Log.w("TAG", "WebService call failed")
            }

            override fun onResponse(call: Call<GameDetail>, response: Response<GameDetail>) {
                if (response.code() == 200) {
                    val responseData = response.body()!!

                    name.text = responseData.name
                    type.text = responseData.type
                    nb_players.text = responseData.players.toString()
                    year.text = responseData.year.toString()
                    details.text = responseData.description_en

                    Glide.with(this@DetailFragment)
                        .load(responseData.picture)
                        .into(game)

                    more.setOnClickListener {
                        val implicitIntent = Intent(Intent.ACTION_VIEW)
                        implicitIntent.data = Uri.parse(responseData.url)
                        startActivity(implicitIntent)
                    }
                }
                else {
                    Toast.makeText(activity, "Code : " + response.code(), Toast.LENGTH_SHORT).show()
                }
            }
        }

        service.getGameDetails(this.arguments!!.getInt("game_id")).enqueue(wsCallBack)
    }
}
