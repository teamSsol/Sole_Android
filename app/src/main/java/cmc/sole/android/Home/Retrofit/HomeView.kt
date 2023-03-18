package cmc.sole.android.Home.Retrofit

import cmc.sole.android.Home.*

interface HomePopularCourseView {
    fun homePopularCourseSuccessView(homePopularResponse: HomePopularResponse)
    fun homePopularCourseFailureView()
}

interface HomeDefaultCourseView {
    fun homeDefaultCourseSuccessView(homeDefaultResponse: HomeDefaultResponse)
    fun homeDefaultCourseFailureView()
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

// MEMO: 알림
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

// MEMO: 회원탈퇴
interface MyPageMemberQuitView {
    fun myPageMemberQuitSuccessView()
    fun myPageMemberQuitFailureView()
}