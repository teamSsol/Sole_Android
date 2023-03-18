package cmc.sole.android.Follow.Retrofit

import cmc.sole.android.Follow.FollowCourseResult
import cmc.sole.android.Follow.FollowUserDataResult

interface FollowCourseView {
    fun followCourseSuccessView(followCourse: ArrayList<FollowCourseResult>)
    fun followCourseFailureView()
}

interface FollowListView {
    fun followListSuccessView(followerResult: ArrayList<FollowUserDataResult>)
    fun followListFailureView()
}

interface FollowUnfollowView {
    fun followUnfollowSuccessView()
    fun followUnfollowFailureView()
}