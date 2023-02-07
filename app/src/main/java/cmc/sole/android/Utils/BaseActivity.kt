package cmc.sole.android.Utils

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding>(private val inflate: (LayoutInflater) -> T) : AppCompatActivity() {
    private lateinit var binding: T

    private var imm: InputMethodManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        initAfterBinding()
    }

    protected abstract fun initAfterBinding()

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showLog(TAG: String, message: String) {
        Log.d(TAG, message)
    }

    fun changeActivity(activity: Class<*>) {
        startActivity(Intent(this, activity))
    }

    fun setFragment(id: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(id, fragment).commit()
    }
}