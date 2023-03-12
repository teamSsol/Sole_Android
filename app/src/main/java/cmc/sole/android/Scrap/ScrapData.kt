package cmc.sole.android.Scrap

data class ScrapFolderData(
    var title: String?,
    var viewType: Int = 0
)

const val defaultFolder = 1
const val addFolder = 2