package com.example.mhjg43.kotlinendlessrecycler

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by mhjg43 on 6/2/2017.
 */
class VerticalLineDecorator (space: Int): RecyclerView.ItemDecoration(){
    private var space = 2

    init {
        this.space = space
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        if (parent?.getChildAdapterPosition(view) == 0){
            outRect?.top = space
        }

        outRect?.bottom = space
    }
}