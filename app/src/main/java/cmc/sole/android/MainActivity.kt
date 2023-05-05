package cmc.sole.android

import cmc.sole.android.Follow.FollowFragment
import cmc.sole.android.Home.HomeFragment
import cmc.sole.android.MyCourse.MyCourseFragment
import cmc.sole.android.Scrap.ScrapFragment
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun initAfterBinding() {
        saveFirstLogin(false)
        setFragment(R.id.main_fl, HomeFragment())
        initBottomNavi()
    }

    private fun initBottomNavi() {
        binding.mainBottomNavi.itemIconTintList = null
        binding.mainBottomNavi.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    setFragment(R.id.main_fl, HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.bottom_my_course -> {
                    setFragment(R.id.main_fl, MyCourseFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.bottom_follow -> {
                    setFragment(R.id.main_fl, FollowFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.bottom_scrap -> {
                    setFragment(R.id.main_fl, ScrapFragment())
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}