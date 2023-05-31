package cmc.sole.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cmc.sole.android.Follow.FollowFragment
import cmc.sole.android.Home.HomeFragment
import cmc.sole.android.MyCourse.MyCourseFragment
import cmc.sole.android.Scrap.ScrapFragment

import cmc.sole.android.databinding.ActivityMainBinding
import cmc.sole.android.databinding.ActivitySignupAgreeMarketingBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        saveFirstLogin(false)
        setFragment(R.id.main_fl, HomeFragment())
        initBottomNavi()

        setContentView(binding.root)
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

    fun setFragment(frameId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(frameId, fragment).commit()
    }
}