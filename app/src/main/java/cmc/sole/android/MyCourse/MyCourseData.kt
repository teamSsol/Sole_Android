package cmc.sole.android.MyCourse

data class TagButton(
    var index: Int?,
    var title: String,
    var isChecked: Boolean
)

data class PlaceInfoData(
    var title: String?,
    var address: String?,
    var roadAddress: String?,
    var mapx: String?,
    var mapy: String?,
    var imgList: ArrayList<String>?
)