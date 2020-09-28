package com.reem.android.finalmovieapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

import com.reem.android.finalmovieapp.R
import com.reem.android.finalmovieapp.data.models.remote.*
import com.reem.android.finalmovieapp.data.models.ui.Movie
import com.reem.android.finalmovieapp.ui.movie.FavoritesFragment
import com.reem.android.finalmovieapp.ui.movie.popular.PopularFragment
import com.reem.android.finalmovieapp.ui.movie.TopRatedFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

   private fun showMovieDetails(movie: Movie) {
       val intent = Intent(this, MovieDetailsActivity::class.java)
       intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
       intent.putExtra(MOVIE_POSTER, movie.posterPath)
       intent.putExtra(MOVIE_TITLE, movie.title)
       intent.putExtra(MOVIE_RATING, movie.rating)
       intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
       intent.putExtra(MOVIE_OVERVIEW, movie.overview)
       startActivity(intent)
   }


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val favoritesfragment = FavoritesFragment()
        val popularfragment = PopularFragment()
        val topratedfragment = TopRatedFragment()

        setCurrentFragment(popularfragment)


        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.top_rated -> setCurrentFragment(topratedfragment)
                R.id.popular -> setCurrentFragment(popularfragment)
                R.id.favorite -> setCurrentFragment(favoritesfragment)

            }
            true
        }




    }

    private fun setCurrentFragment(fragment:  Fragment) = supportFragmentManager.beginTransaction().apply{
        replace(R.id.fragment_container, fragment)
        commit()
    }

    }


