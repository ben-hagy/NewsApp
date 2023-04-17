package com.example.newsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.models.Article

/**
 * "Database" annotation tells Room that this is our database abstract class for Room db implementation
 * Requires "entities" which refers to the entity type (the primary data response object, in this case
 * our "Article" class), and version, which should start at 1.
 *
 * The implementation of the database class should always be abstract, as Room will actually use the
 * class and create the database objects itself.
 *
 * The class needs a function to retrieve your Dao.
 *
 * In short, the companion object creates an instance of our database, which is null at first, then in our
 * invoke function, we only create an instance of the database if it hasn't been created already. Once the
 * database is created, we set "instance" to refer to the database, and our database can use our
 * getArticleDao() function to access our database functions.
 */


@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Converters::class) // this annotation tells Room that we're using type converters and where that class is located
abstract class ArticleDatabase: RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    // this object is used to create our database
    companion object {
        @Volatile // Volatile annotation allows other threads to immediately see when a thread changes this instance
        // instance of our ArticleDatabase is initially null; below code will actually create it
        private var instance: ArticleDatabase? = null
        // the lock is used to synchronize setting that instance to ensure there's only 1 instance of the db at once
        private val LOCK = Any()

        /** operator fun will run whenever we initialize the database object
         * synchronized block with the LOCK object makes sure that only one thread is accessing this
         * at any given time, to really make sure we're only creating one instance of our db
         *
         * if the database instance is null, check in a synchronized block to see if it's still null
         * and hasn't been created elsewhere; if so, create the database and set the value of our
         * instance to the created database
         */
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext, // pass application context
                ArticleDatabase::class.java, // the root class for the database builder
                "article_db.db") // title of the database file
                    .build()
    }
}