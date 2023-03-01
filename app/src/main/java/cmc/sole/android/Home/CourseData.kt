package cmc.sole.android.Home

data class PopularCourse(
    var image: String,
    var title: String
)

data class DefaultCourse(
    var image: String,
    var title: String,
    var scrap: Boolean,
    var location: String,
    var time: String,
    var distance: String,
    var tag: ArrayList<String>
)