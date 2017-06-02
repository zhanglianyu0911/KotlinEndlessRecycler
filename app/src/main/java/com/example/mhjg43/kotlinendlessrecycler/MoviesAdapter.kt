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

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by mhjg43 on 6/2/2017.
 */
class MoviesAdapter (internal var movies: ArrayList<MovieModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val TYPE_MOVIE = 0
    val TYPE_LOAD = 1

    internal var loadMoreListener: OnloadMoreListener? = null
    internal var isLoading = false
    internal var isMoreDataAvailable = true

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        if (position>= itemCount-1 && isMoreDataAvailable&& !isLoading && loadMoreListener !=null){
            isLoading = true
            loadMoreListener?.onLoadMore()
        }

        if(getItemViewType(position) == TYPE_MOVIE){
            (holder as MovieHolder).bindData(movies[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)

        if(viewType == TYPE_MOVIE){
            return MovieHolder(inflater.inflate(R.layout.row_movie,parent,false))
        } else{
            return LoadHolder(inflater.inflate(R.layout.row_load,parent,false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(movies[position].type.equals("movie")){
            return TYPE_MOVIE
        }else{
            return TYPE_LOAD
        }
    }
    internal class MovieHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var tvTitle: TextView
        var tvRating: TextView

        init {
            tvTitle = itemView.findViewById(R.id.title) as TextView
            tvRating = itemView.findViewById(R.id.rating) as TextView
        }

        fun bindData(movieModel: MovieModel){
            tvTitle.setText(movieModel.title)
            tvRating.text = "Rating" + movieModel.rating
        }
    }

    internal class LoadHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return movies.size
    }

    interface OnloadMoreListener{
        fun onLoadMore()
    }


    fun setMoreDataAvailable(moreDataAvailable: Boolean){
        isMoreDataAvailable = moreDataAvailable
    }
    /* notifyDataSetChanged is final method so we can't override it
     call adapter.notifyDataChanged(); after update the list
     */
    fun notifyDataChanged(){
        notifyDataSetChanged()
        isLoading = false
    }

    fun setLoadMoreListener(loadMoreListener: OnloadMoreListener){
        this.loadMoreListener = loadMoreListener
    }

}