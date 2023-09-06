package cmc.sole.android.Home.Retrofit

import cmc.sole.android.Home.*

interface HomeGetCurrentGPSView {
    fun homeGetCurrentGPSSuccessView(currentGPS: String)
    fun homeGetCurrentGPSFailureView()
}

interface HomeUpdateCurrentGPSView {
    fun homeUpdateCurrentGPSSuccessView(homeCurrentGPSResult: HomeCurrentGPSResult)
    fun homeUpdateCurrentGPSFailureView()
}

interface HomeCategoriesUpdateView {
    fun homeCategoriesUpdateSuccessView()
    fun homeCategoriesUpdateFailureView()
}

interface HomeGetCategoriesView {
    fun homeGetCategoriesUpdateSuccessView(data: HomeCategoriesResult)
    fun homeGetCategoriesUpdateFailureView()
}

interface HomePopularCourseView {
    fun homePopularCourseSuccessView(homePopularResponse: HomePopularResponse)
    fun homePopularCourseFailureView()
}

interface HomeDefaultCourseView {
    fun homeDefaultCourseSuccessView(homeDefaultResponse: HomeDefaultResponse)
    fun homeDefaultCourseFailureView()
}

interface HomeFilterCourseView {
    fun homeFilterCourseSuccessView(homeDefaultResponse: HomeDefaultResponse)
    fun homeFilterCourseFailureView()
}

interface HomeCourseDetailView {
    fun homeCourseDetailSuccessView(homeCourseDetailResult: HomeCourseDetailResult)
    fun homeCourseDetailFailureView()
}

interface HomeScrapAddAndCancelView {
    fun homeScrapAddAndCancelSuccessView()
    fun homeScrapAddAndCancelFailureView()
}

// MEMO: 마이페이지
interface MyPageInfoView {
    fun myPageInfoSuccessView(myPageInfoResult: MyPageInfoResult)
    fun myPageInfoFailureView()
}

interface MyPageInfoUpdateView {
    fun myPageInfoUpdateSuccessView(myPageInfoResult: MyPageInfoResult)
    fun myPageInfoUpdateFailureView()
}

// MEMO: 마이페이지 알림
interface MyPageNotificationView {
    fun myPageNotificationSuccessView(myPageNotificationResult: MyPageNotificationResult)
    fun myPageNotificationFailureView()
}

interface MyPageNotificationUpdateView {
    fun myPageNotificationUpdateSuccessView(myPageNotificationUpdateResult: MyPageNotificationResult)
    fun myPageNotificationUpdateFailureView()
}

interface MyPageNotificationHistoryView {
    fun myPageNotificationHistorySuccessView(myPageNotificationHistoryResult: ArrayList<MyPageNotificationHistoryResult?>)
    fun myPageNotificationHistoryFailureView()
}

// MEMO: 마이페이지 공지사항
interface MyPageNoticeView {
    fun myPageNoticeSuccessView(myPageNoticeResult: ArrayList<MyPageNoticeResult>)
    fun myPageNoticeFailureView()
}

interface MyPageNoticeAddView {
    fun myPageNoticeAddSuccessView(myPageNoticeResult: MyPageNoticeResult)
    fun myPageNoticeAddFailureView()
}

interface MyPageNoticeUpdateView {
    fun myPageNoticeUpdateSuccessView(myPageNoticeResult: MyPageNoticeResult)
    fun myPageNoticeUpdateFailureView()
}

// MEMO: 회원탈퇴
interface MyPageMemberQuitView {
    fun myPageMemberQuitSuccessView()
    fun myPageMemberQuitFailureView()
}

// MEMO: 스크랩 등록/취소
interface ScrapOnOffView {
    fun scrapOnOffSuccessView()
    fun scrapOnOffFailureView()
}