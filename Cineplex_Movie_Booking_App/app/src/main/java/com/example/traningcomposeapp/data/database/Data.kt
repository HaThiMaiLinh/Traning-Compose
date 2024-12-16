package com.example.traningcomposeapp.data.database

import com.example.traningcomposeapp.R
import com.example.traningcomposeapp.data.entities.CinemaEntity
import com.example.traningcomposeapp.data.entities.MovieEntity

object Data {

    val pager = listOf(
        Pager(
            image = R.drawable.aladdin_poster,
            title = "Get the best seat to your next cinematic treat",
            subtitle = "SubTitle 1"
        ),
        Pager(
            image = R.drawable.dochanh_poster,
            title = "Never miss a blockbuster with us",
            subtitle = "SubTitle 2"
        ),
        Pager(
            image = R.drawable.pele__poster,
            title = "Take a seat, the movie’s about to begin",
            subtitle = "SubTitle 3"
        )
    )

    val cinemas = listOf(
        CinemaEntity(
            name = "CGV Vincom Ba Trieu",
            distance = "2.5 km",
            address = "Tầng 5, Vincom Center, 191 Bà Triệu, Hai Bà Trưng, Hà Nội",
            logo = R.drawable.pvrcinema
        ),
        CinemaEntity(
            name = "Lotte Cinema Đống Đa",
            distance = "3.0 km",
            address = "Tầng 5, Lotte Center Hanoi, 54 Liễu Giai, Ba Đình, Hà Nội",
            logo = R.drawable.uscinema
        ),
        CinemaEntity(
            name = "BHD Star Cineplex",
            distance = "1.8 km",
            address = "Tầng 4, Aeon Mall Long Biên, Hà Nội",
            logo = R.drawable.inoxcinema
        )
    )

    val movies = listOf(
        MovieEntity(
            title = "Avengers: Endgame",
            overview = "The Avengers assemble once more to undo the damage caused by Thanos.",
            releaseDate = "2024-12-6",
            posterPath = "https://upload.wikimedia.org/wikipedia/en/0/0d/Avengers_Endgame_poster.jpg",
            backdropPath = "https://upload.wikimedia.org/wikipedia/en/0/0d/Avengers_Endgame_poster.jpg",
            voteAverage = 8.4
        ),
        MovieEntity(
            title = "Spider-Man: No Way Home",
            overview = "Spider-Man seeks help from Doctor Strange to erase the memory of his secret identity.",
            releaseDate = "2024-12-11",
            posterPath = "https://upload.wikimedia.org/wikipedia/vi/thumb/7/71/%C3%81p_ph%C3%ADch_phim_Ng%C6%B0%E1%BB%9Di_Nh%E1%BB%87n_kh%C3%B4ng_c%C3%B2n_nh%C3%A0.jpg/345px-%C3%81p_ph%C3%ADch_phim_Ng%C6%B0%E1%BB%9Di_Nh%E1%BB%87n_kh%C3%B4ng_c%C3%B2n_nh%C3%A0.jpg",
            backdropPath = "https://upload.wikimedia.org/wikipedia/vi/thumb/7/71/%C3%81p_ph%C3%ADch_phim_Ng%C6%B0%E1%BB%9Di_Nh%E1%BB%87n_kh%C3%B4ng_c%C3%B2n_nh%C3%A0.jpg/345px-%C3%81p_ph%C3%ADch_phim_Ng%C6%B0%E1%BB%9Di_Nh%E1%BB%87n_kh%C3%B4ng_c%C3%B2n_nh%C3%A0.jpg",
            voteAverage = 8.1
        ),
        MovieEntity(
            title = "The Dark Knight",
            overview = "Batman faces off against the Joker, a criminal mastermind who wants to plunge Gotham City into anarchy.",
            releaseDate = "2024-12-10",
            posterPath = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cc/The_Dark_Knight_-_PR_-_Barcelona.jpg/1199px-The_Dark_Knight_-_PR_-_Barcelona.jpg",
            backdropPath = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cc/The_Dark_Knight_-_PR_-_Barcelona.jpg/1199px-The_Dark_Knight_-_PR_-_Barcelona.jpg",
            voteAverage = 9.0
        ),
        MovieEntity(
            title = "Inception",
            overview = "A skilled thief is given a chance to have his criminal record erased if he can successfully perform an inception.",
            releaseDate = "2024-12-7",
            posterPath = "https://upload.wikimedia.org/wikipedia/vi/1/18/Inception_OST.jpg?20140811040326",
            backdropPath = "https://upload.wikimedia.org/wikipedia/vi/1/18/Inception_OST.jpg?20140811040326",
            voteAverage = 8.8
        ),
        MovieEntity(
            title = "Titanic",
            overview = "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.",
            releaseDate = "2024-12-7",
            posterPath = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/Titanic.jpg/884px-Titanic.jpg",
            backdropPath = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/92/Titanic.jpg/884px-Titanic.jpg",
            voteAverage = 7.8
        ),
        MovieEntity(
            title = "The Matrix",
            overview = "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
            releaseDate = "2024-12-8",
            posterPath = "https://upload.wikimedia.org/wikipedia/en/c/c1/The_Matrix_Poster.jpg",
            backdropPath = "https://upload.wikimedia.org/wikipedia/en/c/c1/The_Matrix_Poster.jpg",
            voteAverage = 8.7
        ),
        MovieEntity(
            title = "The Lion King",
            overview = "Lion prince Simba and his father are targeted by his bitter uncle, who wants to ascend the throne himself.",
            releaseDate = "2024-12-9",
            posterPath = "https://upload.wikimedia.org/wikipedia/en/3/3d/The_Lion_King_poster.jpg",
            backdropPath = "https://upload.wikimedia.org/wikipedia/en/3/3d/The_Lion_King_poster.jpg",
            voteAverage = 8.5
        ),
        MovieEntity(
            title = "Jurassic Park",
            overview = "During a preview tour, a theme park suffers a major power breakdown that allows its cloned dinosaur exhibits to run amok.",
            releaseDate = "2024-12-9",
            posterPath = "https://upload.wikimedia.org/wikipedia/en/e/e7/Jurassic_Park_poster.jpg",
            backdropPath = "https://upload.wikimedia.org/wikipedia/en/e/e7/Jurassic_Park_poster.jpg",
            voteAverage = 8.1
        ),
        MovieEntity(
            title = "Shawshank Redemption",
            overview = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
            releaseDate = "2024-12-10",
            posterPath = "https://upload.wikimedia.org/wikipedia/en/8/81/ShawshankRedemptionMoviePoster.jpg",
            backdropPath = "https://upload.wikimedia.org/wikipedia/en/8/81/ShawshankRedemptionMoviePoster.jpg",
            voteAverage = 9.3
        ),
        MovieEntity(
            title = "Forrest Gump",
            overview = "The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal, and other historical events unfold from the perspective of an Alabama man with an extraordinary life story.",
            releaseDate = "2024-12-11",
            posterPath = "https://upload.wikimedia.org/wikipedia/en/6/67/Forrest_Gump_poster.jpg",
            backdropPath = "https://upload.wikimedia.org/wikipedia/en/6/67/Forrest_Gump_poster.jpg",
            voteAverage = 8.8
        ),
    )
}
