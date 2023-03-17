package cmc.sole.android.MyCourse.Write.Search

data class SearchNaverData(
    var title: String,
    var link: String,
    var description: String,
    var lastBuildDate: String,
    var total: Int,
    var start: Int,
    var display: Int,
    var items: List<SearchResultData>
)

data class SearchResultData(
    var title: String,
    var link: String,
    var category: String,
    var description: String,
    var telephone: String,
    var address: String,
    var roadAddress: String,
    var mapx: String,
    var mapy: String,
)