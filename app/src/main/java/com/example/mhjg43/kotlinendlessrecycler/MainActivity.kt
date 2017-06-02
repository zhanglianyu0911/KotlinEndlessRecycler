package com.example.mhjg43.kotlinendlessrecycler
/*
                    .-=====-.
                    | .""". |
                    | |   | |
                    | |   | |
                    | '---' |
                    |       |
                    |       |
 .-================-'       '-================-.
j|  _                                          |
g|.'o\                                    __   |
s| '-.'.                                .'o.`  |
 '-==='.'.=========-.       .-========.'.-'===-'
        '.`'._    .===,     |     _.-' /
          '._ '-./  ,//\   _| _.-'  _.'
             '-.| ,//   \-'  `   .-'
                `// _`--;    ;.-'
                  `\._ ;|    |
                     \`-'  . |
                     |_.-'-._|
                     \  _'_  /
                     |; -:- |
                     || -.- \
                     |;     .\
                     / `'\'`\-;
                    ;    '. `_/
                    |,`-._;  .`|
                    `;\  `.-'-;
                     | \   \  |
                     |  `\  \ |
                     |   )  | |
                     |  /  /` /
                     | |  /|  |
                     | | / | /
                     | / |/ /|
                     | \ / / |
                     |  /o | |
                     |  |_/  |
                     |       |
                     |       |
                     |       |
                     |       |
                     |       |
                     |       |
                     |       |
                     '-=====-'
                  God Bless No Bug
*/

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.example.mhjg43.kotlinendlessrecycler.network.MoviesApi
import com.example.mhjg43.kotlinendlessrecycler.network.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    internal var recyclerView : RecyclerView ? = null
    private var movies: ArrayList<MovieModel> ?= null
    internal var adapter: MoviesAdapter ?= null
    internal var api : MoviesApi ?= null
    internal var TAG = "MainActivity - "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        movies = ArrayList<MovieModel>()

        adapter = MoviesAdapter(movies!!)
        adapter?.setLoadMoreListener(object : MoviesAdapter.OnloadMoreListener{
            override fun onLoadMore() {
                recyclerView?.post{
                    val index = movies?.size?.minus(1)

                    loadMore(index!!)
                }
            }
        })

        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(applicationContext)
        //recyclerView?.addItemDecoration(VerticalLineDecorator(10))
        recyclerView?.adapter = adapter

        api = ServiceGenerator.createService<MoviesApi>(MoviesApi::class.java)
        load(0)
    }

    private fun load(index: Int) {
        val call = api?.getMovies(index)

        call?.enqueue(object : Callback<List<MovieModel>>{
            override fun onResponse(call: Call<List<MovieModel>>, response: Response<List<MovieModel>>) {
                if (response.isSuccessful) {
                    movies?.addAll(response.body())
                    adapter?.notifyDataChanged()
                } else {
                    Log.e(TAG, " Response Error1 " + response.code().toString() + response.isSuccessful)
                }
            }

            override fun onFailure(call: Call<List<MovieModel>>?, t: Throwable?) {
                Log.e(TAG, " Response Error " + t?.message)
            }


        })
    }

    private fun loadMore(index: Int){
        //add loading progress view
        movies?.add(MovieModel("load"))
        adapter?.notifyItemInserted(movies?.size?.minus(1)!!)

        val call = api?.getMovies(index)
        call?.enqueue(object : Callback<List<MovieModel>> {
            override fun onResponse(call: Call<List<MovieModel>>, response: Response<List<MovieModel>>) {
                if (response.isSuccessful) {

                    //remove loading view
                    movies?.removeAt(movies?.size?.minus(1)!!)

                    val result = response.body()
                    if (result.size > 0) {
                        //add loaded data
                        movies?.addAll(result)
                    } else {//result size 0 means there is no more data available at server
                        adapter?.setMoreDataAvailable(false)
                        //telling adapter to stop calling load more as no more server data available
                        Toast.makeText(applicationContext, "No More Data Available", Toast.LENGTH_LONG).show()
                    }
                    adapter?.notifyDataChanged()
                    //should call the custom method adapter.notifyDataChanged here to get the correct loading status
                } else {
                    Log.e(TAG, " Load More Response Error " + response.code().toString())
                }
            }

            override fun onFailure(call: Call<List<MovieModel>>, t: Throwable) {
                Log.e(TAG, " Load More Response Error " + t.message)
            }
        })
    }
}
