package com.example.comicsearch.database

import android.content.Context
import androidx.room.*
import com.example.comicsearch.api.Movie
import com.example.comicsearch.regularMoviesNumber


@Database(entities = [Movie::class], exportSchema = false, version = 1)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var instance: MovieDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(
            LOCK
        ) {
            instance
                ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            MovieDatabase::class.java, "movie.db"
        )
            .build()
    }
}

@Dao
interface MovieDao {
    @Query("Select * from movie limit :limit offset :offset")
    suspend fun listMoviesFromDB(limit: Int = regularMoviesNumber, offset: Int): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("Select * from movie where name LIKE :query limit :limit offset :offset")
    suspend fun searchMovieFromDB(query: String, limit: Int = regularMoviesNumber, offset: Int): List<Movie>

    @Query("Select * from movie where id =:id")
    suspend fun getMovie(id: Int): Movie
}