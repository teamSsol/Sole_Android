package cmc.sole.android.Signup

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cmc.sole.android.*
import cmc.sole.android.CourseTag.placeCategories
import cmc.sole.android.CourseTag.transCategories
import cmc.sole.android.CourseTag.withCategories
import cmc.sole.android.Signup.Retrofit.*
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivitySignupNicknameBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.File


class SignupNicknameActivity: BaseActivity<ActivitySignupNicknameBinding>(ActivitySignupNicknameBinding::inflate),
    SignupNicknameView, SignupSocialView {

    lateinit var signupService: SignupService

    private var imageUri: Uri? = null

    var all = String()
    var service = String()
    var personal = String()
    var marketing = String()

    companion object{
        // 갤러리 권한 요청
        const val REQ_GALLERY = 1
    }

    private val imageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            imageUri = result.data?.data
            Log.d("EXAMPLE", "imageUri = ${imageUri.toString()}")
            imageUri?.let{
                Glide.with(this)
                    .load(imageUri).placeholder(R.drawable.ic_profile).fallback(R.drawable.ic_profile).fitCenter()
                    .apply(RequestOptions().circleCrop().override(130,130))
                    .into(binding.signupNicknameProfileIv)
            }
        }
    }

    override fun initAfterBinding() {
        all = intent.getStringExtra("all").toString()
        service = intent.getStringExtra("service").toString()
        personal = intent.getStringExtra("personal").toString()
        marketing = intent.getStringExtra("marketing").toString()

        nicknameListener()
        initClickListener()
    }

    private fun initSignupService(nickname: SignupNicknameRequest) {
        signupService = SignupService()
        signupService.setSignupNicknameView(this)
        signupService.setSignupSocialView(this)
        signupService.checkNickname(nickname)
    }

    private fun nicknameListener() {
        binding.signupNicknameEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun afterTextChanged(p0: Editable?) {
                if (binding.signupNicknameEt.length() in 1..10) {
                    initSignupService(SignupNicknameRequest(binding.signupNicknameEt.text.toString()))
                } else if (binding.signupNicknameEt.length() > 10) {
                    nicknameOption(0, "닉네임은 최대 10자까지 작성이 가능해요.")
                } else if (binding.signupNicknameEt.length() == 0) {
                    nicknameOption(-1, null)
                }
            }
        })
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
            saveNickname(binding.signupNicknameEt.text.toString())

            var file: File
            var requestFile: RequestBody
            var multipartFile: MultipartBody.Part

            if (imageUri == null) {
                requestFile = RequestBody.create(MediaType.parse("image/*"), "")
                multipartFile = MultipartBody.Part.createFormData("multipartFile", "", requestFile)
            } else {
                file = File(absolutelyPath(imageUri, this))
                requestFile = RequestBody.create(MediaType.parse("image/*"), file)
                multipartFile = MultipartBody.Part.createFormData("multipartFile", file.name, requestFile)
            }

            // UPDATE: 카테고리 선택하는 부분 추가해주기!
            // MEMO: 임의로 Dummy Data 넣기
            val placeCategoriesSet: MutableSet<String> = HashSet()
            placeCategoriesSet.add(placeCategories.TASTY_PLACE.toString())
            savePlaceCategories(placeCategoriesSet)

            val transCategoriesSet: MutableSet<String> = HashSet()
            transCategoriesSet.add(transCategories.WALK.toString())
            saveTransCategories(transCategoriesSet)

            val withCategoriesSet: MutableSet<String> = HashSet()
            withCategoriesSet.add(withCategories.COUPLE.toString())
            saveWithCategories(withCategoriesSet)

            var jsonBody = JSONObject()
            jsonBody.put("accessToken", getAccessToken())
            jsonBody.put("fcmToken", getFCMToken())
            jsonBody.put("infoAccepted", personal)
            jsonBody.put("marketingAccepted", marketing)
            jsonBody.put("nickname", getNickname())
            jsonBody.put("placeCategories", JSONArray(getPlaceCategories()!!.toTypedArray()))
            jsonBody.put("serviceAccepted", service)
            jsonBody.put("transCategories", JSONArray(getTransCategories()!!.toTypedArray()))
            jsonBody.put("withCategories", JSONArray(getWithCategories()!!.toTypedArray()))

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonBody.toString())
            var memberRequestDto = MultipartBody.Part.createFormData("memberRequestDto", "memberRequestDto", requestBody)

            signupService.socialSignup("kakao", memberRequestDto, multipartFile)
        }
    }

    // 절대경로 변환
    private fun absolutelyPath(path: Uri?, context : Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result = c?.getString(index!!)

        return result!!
    }

    private fun nicknameOption(option: Int, message: String?) {
        val on = ContextCompat.getColor(this@SignupNicknameActivity, R.color.main)
        val off = Color.parseColor("#D3D4D5")

        binding.signupNicknameResultTv.text = message

        when (option) {
            -1 -> {
                binding.signupNicknameResult.setImageResource(0)
                binding.signupNicknameNextBtn.setCardBackgroundColor(off)
                binding.signupNicknameNextBtn.isEnabled = false
            }
            0 -> {
                binding.signupNicknameResult.setImageResource(R.drawable.ic_signup_error)
                binding.signupNicknameResultTv.setTextColor(Color.parseColor("#FF717D"))
                binding.signupNicknameNextBtn.setCardBackgroundColor(off)
                binding.signupNicknameNextBtn.isEnabled = false
            }
            1 -> {
                binding.signupNicknameResult.setImageResource(R.drawable.ic_signup_check)
                binding.signupNicknameResultTv.setTextColor(Color.parseColor("#8BDEB5"))
                binding.signupNicknameNextBtn.setCardBackgroundColor(on)
                binding.signupNicknameNextBtn.isEnabled = true
            }
        }
    }

    override fun signupNicknameSuccessView(result: Boolean) {
        nicknameOption(1, "사용 가능한 닉네임입니다.")
    }

    override fun signupNicknameFailureView() {
        nicknameOption(0, "이 닉네임은 이미 다른 사람이 사용하고 있어요.")
    }

    override fun signupSocialSuccessView(result: SignupSocialResponse) {
        Log.d("SIGNUP-SERVICE", result.toString())
        changeActivity(SignupFinishActivity::class.java)
    }

    override fun signupSocialFailureView() {
        showToast("소셜 로그인 에러")
    }
}