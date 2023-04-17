package com.example.newsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Entity tag is for Room to use to create a table in our local database.
 * We also added the "id" variable to the data class, for Room to use as its own unique identifier in
 * the local database. The PrimaryKey annotation tells Room to use the "id" variable as its primary
 * key, and the "autoGenerate = true" parameter tells Room to handle incrementing this variable.
 */


@Entity(
    tableName = "articles"
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String,
    val urlToImage: String?
) : Serializable {
//     override function in case I keep getting errors with the webview? for now changed id of Source to "null"
    override fun hashCode(): Int {
        var result = id.hashCode()
        if(url.isNullOrEmpty()){
            result = 31 * result + url.hashCode()
        }
        return result
    }
}
