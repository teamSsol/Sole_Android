package cmc.sole.android.Signup

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivitySignupNicknameBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File


class SignupNicknameActivity: BaseActivity<ActivitySignupNicknameBinding>(ActivitySignupNicknameBinding::inflate) {

    companion object{
        // 갤러리 권한 요청
        const val REQ_GALLERY = 1
    }

    private val imageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val imageUri = result.data?.data
            imageUri?.let{
                Glide.with(this)
                    .load(imageUri).placeholder(R.drawable.ic_profile).fallback(R.drawable.ic_profile).fitCenter()
                    .apply(RequestOptions().circleCrop().override(130,130))
                    .into(binding.signupNicknameProfileIv)
            }
        }
    }

    override fun initAfterBinding() {
        nicknameListener()
        initClickListener()
    }

    private fun nicknameListener() {
        binding.signupNicknameEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                /*
                if (binding.signupNicknameEt.selectionEnd > 9) {
                    nicknameOption(0, "닉네임은 최대 10자까지 작성이 가능해요.")
                } else if (binding.signupNicknameEt.selectionEnd <= 9) {
                    nicknameOption(1, null)
                }
                */
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun afterTextChanged(p0: Editable?) {
                if (binding.signupNicknameEt.length() in 1..10) {
                    // UPDATE: API 연결해서 다른 사용자가 사용 중인 닉네임인지 연결
                    // MEMO: if (만약 사용 중인 닉네임이 아니라면)
                    nicknameOption(1, "사용 가능한 닉네임입니다.")
                    // MEMO: else if (만약 사용 중인 닉네임이 맞다면)
                    // nicknameOption(0, "이 닉네임은 이미 다른 사람이 사용하고 있어요.")
                } else if (binding.signupNicknameEt.length() > 10) {
                    nicknameOption(0, "닉네임은 최대 10자까지 작성이 가능해요.")
                } else if (binding.signupNicknameEt.length() == 0) {
                    nicknameOption(-1, null)
                }
            }
        })

        /*
        binding.signupNicknameEt.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (binding.signupNicknameEt.length() in 1..10) {
                    nicknameOption(1, null)
                }
            }
        }
        */
    }

    private fun initClickListener() {
        binding.signupNicknameProfilePlusCv.setOnClickListener {
            val writePermission = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
            val readPermission = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)

            //권한 확인
            if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) {
                // 권한 요청
                ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE), REQ_GALLERY)
            } else {
                // 권한이 있는 경우 갤러리 실행
                val intent = Intent(Intent.ACTION_PICK)
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                imageResult.launch(intent)
            }
        }

        binding.signupNicknameNextBtn.setOnClickListener {
            if (binding.signupNicknameEt.length() in 1..10)
                changeActivity(SignupFinishActivity::class.java)
        }
    }

    private fun nicknameOption(option: Int, message: String?) {
        val on = ContextCompat.getColor(this@SignupNicknameActivity, R.color.main)
        val onText = ContextCompat.getColor(this@SignupNicknameActivity, R.color.black)
        val off = Color.parseColor("#D3D4D5")
        val offText = Color.parseColor("#FFFFFF")

        binding.signupNicknameResultTv.text = message

        when (option) {
            -1 -> {
                binding.signupNicknameResult.setImageResource(0)
                binding.signupNicknameNextBtn.setCardBackgroundColor(off)
                binding.signupNicknameNextTv.setTextColor(offText)
                binding.signupNicknameNextBtn.isEnabled = false
            }
            0 -> {
                binding.signupNicknameResult.setImageResource(R.drawable.ic_signup_error)
                binding.signupNicknameResultTv.setTextColor(Color.parseColor("#FF717D"))
                binding.signupNicknameNextBtn.setCardBackgroundColor(off)
                binding.signupNicknameNextTv.setTextColor(offText)
                binding.signupNicknameNextBtn.isEnabled = false
            }
            1 -> {
                binding.signupNicknameResult.setImageResource(R.drawable.ic_signup_check)
                binding.signupNicknameResultTv.setTextColor(Color.parseColor("#8BDEB5"))
                binding.signupNicknameNextBtn.setCardBackgroundColor(on)
                binding.signupNicknameNextTv.setTextColor(onText)
                binding.signupNicknameNextBtn.isEnabled = true
            }
        }
    }
}