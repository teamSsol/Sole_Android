package cmc.sole.android.Scrap.Retrofit

import cmc.sole.android.Home.DefaultCourse

interface ScrapFolderView {
    fun scrapFolderSuccessView(scrapFolderDataResult: ArrayList<ScrapFolderDataResult>)
    fun scrapFolderFailureView()
}

interface ScrapDefaultFolderView {
    fun scrapDefaultFolderSuccessView(scrapDefaultFolderList: ArrayList<ScrapCourseResult>)
    fun scrapDefaultFolderFailureView()
}

interface ScrapFolderAddView {
    fun scrapFolderAddSuccessView(scrapFolderAddResult: ScrapFolderAddResult)
    fun scrapFolderAddFailureView()
}

interface ScrapDefaultFolderCourseDeleteView {
    fun scrapDefaultFolderCourseDeleteSuccessView()
    fun scrapDefaultFolderCourseDeleteFailureView()
}

interface ScrapFolderDeleteView {
    fun scrapFolderDeleteSuccessView()
    fun scrapFolderDeleteFailureView()
}

interface ScrapFolderNameUpdateView {
    fun scrapFolderNameUpdateSuccessView()
    fun scrapFolderNameUpdateFailureView()
}

interface ScrapCourseView {
    fun scrapCourseSuccessView(scrapCourseResult: ArrayList<ScrapCourseResult>)
    fun scrapCourseFailureView()
}

interface ScrapCourseDeleteView {
    fun scrapCourseDeleteSuccessView()
    fun scrapCourseDeleteFailureView()
}

interface ScrapCourseMoveView {
    fun scrapCourseMoveSuccessView(scrapCourseMoveResult: ScrapCourseMoveResult)
    fun scrapCourseMoveFailureView()
}