package cmc.sole.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun initAfterBinding() {
        setFragment(R.id.main_fl, HomeFragment())
        initBottomNavi()
    }

    private fun initBottomNavi() {
        binding.mainBottomNavi.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    setFragment(R.id.main_fl, HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.bottom_map -> {
                    setFragment(R.id.main_fl, MapFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.bottom_profile -> {
                    setFragment(R.id.main_fl, ProfileFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.bottom_heart -> {
                    setFragment(R.id.main_fl, HeartFragment())
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}