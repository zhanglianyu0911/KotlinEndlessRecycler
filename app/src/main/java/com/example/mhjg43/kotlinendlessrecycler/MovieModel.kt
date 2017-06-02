package com.example.mhjg43.kotlinendlessrecycler

import java.io.Serializable

/**
 * Created by mhjg43 on 6/2/2017.
 */
class MovieModel (internal var type: String): Serializable {

    internal var title: String? = null
    internal var rating: String? = null
}