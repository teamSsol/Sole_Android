package cmc.sole.android.Scrap.Retrofit

interface ScrapFolderView {
    fun scrapFolderSuccessView(scrapFolderDataResult: ArrayList<ScrapFolderDataResult>)
    fun scrapFolderFailureView()
}

interface ScrapFolderAddView {
    fun scrapFolderAddSuccessView(scrapFolderAddResult: ScrapFolderAddResult)
    fun scrapFolderAddFailureView()
}

interface ScrapFolderDeleteView {
    fun scrapFolderDeleteSuccessView()
    fun scrapFolderDeleteFailureView()
}

interface ScrapCourseView {
    fun scrapCourseSuccessView(scrapCourseResult: ArrayList<ScrapCourseResult>)
    fun scrapCourseFailureView()
}

interface ScrapCourseDeleteView {
    fun scrapCourseDeleteSuccessView()
    fun scrapCourseDeleteFailureView()
}