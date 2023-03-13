package sequenia.test.ru.data.nw.model

import com.google.gson.annotations.SerializedName

data class MovieNW(
    @SerializedName("films")
    val films: List<FilmNW?>?
) {
    data class FilmNW(
        @SerializedName("description")
        val description: String?,
        @SerializedName("genres")
        val genres: List<String?>?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("image_url")
        val imageUrl: String?,
        @SerializedName("localized_name")
        val localizedName: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("rating")
        val rating: String?,
        @SerializedName("year")
        val year: Int?
    )
}