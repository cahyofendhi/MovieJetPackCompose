package com.bcr.moviejetpackcompose.core.model

import android.os.Parcelable
import android.util.Log
import androidx.compose.runtime.MutableState
import com.bcr.moviejetpackcompose.utils.dateFormat
import com.bcr.moviejetpackcompose.utils.getMovieImage
import com.google.gson.annotations.SerializedName

enum class GroupType(val type: String) {
	movie("movie"),
	tv("tv"),
}

enum class CategoryType(val type: String) {
	upcoming("upcoming"),
	popular("popular"),
	toprate("top_rated"),
	latest("latest"),
	onAir("on_the_air")
}


data class MovieResponse(

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	val results: List<Movie>? = null,

	@field:SerializedName("total_results")
	val totalResults: Int? = null
)

data class Movie(

	@field:SerializedName("number_of_episodes")
	val numberOfEpisodes: Int = 0,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("number_of_seasons")
	val numberOfSeasons: Int? = null,

	@field:SerializedName("languages")
	val languages: List<String?>? = null,

	@field:SerializedName("created_by")
	val createdBy: List<Any?>? = null,

	@field:SerializedName("origin_country")
	val originCountry: List<String?>? = null,

	@field:SerializedName("original_name")
	val originalName: String? = null,

	@field:SerializedName("episode_run_time")
	val episodeRunTime: List<Int?>? = null,

	@field:SerializedName("next_episode_to_air")
	val nextEpisodeToAir: Any? = null,

	@field:SerializedName("in_production")
	val inProduction: Boolean? = null,

	@field:SerializedName("last_air_date")
	val lastAirDate: String? = null,

	@field:SerializedName("original_language")
	val originalLanguage: String? = null,

	@field:SerializedName("imdb_id")
	val imdbId: String? = null,

	@field:SerializedName("video")
	val video: Boolean? = null,

	@field:SerializedName("title")
	val title: String? = "",

	@field:SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@field:SerializedName("revenue")
	val revenue: Int? = null,

	@field:SerializedName("genres")
	val genres: List<GenresItem?>? = null,

	@field:SerializedName("popularity")
	val popularity: Double? = null,

	@field:SerializedName("production_countries")
	val productionCountries: List<ProductionCountriesItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("vote_count")
	val voteCount: Int? = null,

	@field:SerializedName("budget")
	val budget: Int? = null,

	@field:SerializedName("overview")
	val overview: String? = "",

	@field:SerializedName("original_title")
	val originalTitle: String? = "",

	@field:SerializedName("runtime")
	val runtime: Int? = 0,

	@field:SerializedName("poster_path")
	val posterPath: String? = null,

	@field:SerializedName("spoken_languages")
	val spokenLanguages: List<SpokenLanguagesItem?>? = null,

	@field:SerializedName("production_companies")
	val productionCompanies: List<ProductionCompaniesItem?>? = null,

	@field:SerializedName("release_date")
	val releaseDate: String? = null,

	@field:SerializedName("first_air_date")
	val firstAirDate: String? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Double? = 0.0,

	@field:SerializedName("belongs_to_collection")
	val belongsToCollection: Any? = null,

	@field:SerializedName("tagline")
	val tagline: String? = null,

	@field:SerializedName("adult")
	val adult: Boolean? = null,

	@field:SerializedName("homepage")
	val homepage: String? = null,

	@field:SerializedName("status")
	val status: String? = "",

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("genre_ids")
	val genreIds: List<Int?>? = null,

) {

	fun getTitleMovie(): String {
		return when {
			originalTitle != null && originalTitle.isNotEmpty() -> {
				originalTitle
			}
			name != null -> {
				name
			}
			else -> {
				""
			}
		}
	}

	fun getImageBackdrop(): String {
		return getMovieImage(backdropPath)
	}

	fun getImagePoster(): String {
		return getMovieImage(posterPath)
	}

	fun genreList(): String {
		val _genreMap: Map<Int, String> = mapOf(
			28 to "Action",
			12 to "Adventure",
			16 to "Animation",
			35 to "Comedy",
			80 to "Crime",
			99 to "Documentary",
			18 to "Drama",
			10751 to "Family",
			10762 to "Kids",
			10759 to "Action & Adventure",
			14 to "Fantasy",
			36 to "History",
			27 to "Horror",
			10402 to "Music",
			9648 to "Mystery",
			10749 to "Romance",
			878 to "Science Fiction",
			10770 to "TV Movie",
			53 to "Thriller",
			10752 to "War",
			37 to "Western",
			10763 to "",
			10764 to "Reality",
			10765 to "Sci-Fi & Fantasy",
			10766 to "Soap",
			10767 to "Talk",
			10768 to "War & Politics",
		)
		val maps: ArrayList<String> = ArrayList()
		genreIds?.let {
			it.forEach { i ->
				maps.add("${_genreMap[i]}")
			}
		}
		return maps.joinToString(separator = ", ")
	}

	fun getDateMovie(): String {
		if (getShowReleaseDate().isNotEmpty()) {
			return getShowReleaseDate()
		} else {
			return getShowFirstAirdate()
		}
 	}

	fun getShowReleaseDate(): String {
		return if (releaseDate != null) {
			dateFormat(releaseDate)
		} else {
			""
		}
	}

	fun getShowFirstAirdate(): String {
		return if (firstAirDate != null) {
			dateFormat(firstAirDate)
		} else {
			""
		}
	}

	fun allGenre(): List<String> {
		val maps: ArrayList<String> = ArrayList()
		genres?.let {
			it.forEach { i ->
				i?.let { j ->
					maps.add("${j.name}")
				}
			}
		}
		return maps
	}

}



data class GenresItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class SpokenLanguagesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("iso_639_1")
	val iso6391: String? = null,

	@field:SerializedName("english_name")
	val englishName: String? = null
)

data class ProductionCountriesItem(

	@field:SerializedName("iso_3166_1")
	val iso31661: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)

data class ProductionCompaniesItem(

	@field:SerializedName("logo_path")
	val logoPath: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("origin_country")
	val originCountry: String? = null
)
