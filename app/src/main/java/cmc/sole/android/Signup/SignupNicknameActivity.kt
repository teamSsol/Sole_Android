package cmc.sole.android.Signup

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
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
import androidx.lifecycle.ViewModelProvider
import cmc.sole.android.R
import cmc.sole.android.Signup.Retrofit.*
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivitySignupNicknameBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import okhttp3.*
import retrofit2.http.Multipart
import java.io.File


class SignupNicknameActivity: BaseActivity<ActivitySignupNicknameBinding>(ActivitySignupNicknameBinding::inflate),
    SignupNicknameView, SignupSocialView {

    private var signupVM = SignupViewModel()
    lateinit var signupService: SignupService

    private var imageUriString = String()
    // private lateinit var imageUri: Uri

    lateinit var accessToken: String

    companion object{
        // 갤러리 권한 요청
        const val REQ_GALLERY = 1
    }

    private val imageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val imageUri = result.data?.data
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
        signupVM = ViewModelProvider(this)[SignupViewModel::class.java]

        accessToken = intent.getStringExtra("accessToken").toString()

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
            var profileImg: MultipartBody.Part? = null

//            Log.d("sendImg- editProfile",imageUri.toString())
//            if(imageUri.toString() != getProfileImgUrl()){ // 프로필 이미지가 변경할게 있다면,
//                Log.d("sendImg- editProfile?",imageUri.path.toString())
//                val file = File(absolutelyPath(imageUri))
//                val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
//                profileImg = MultipartBody.Part.createFormData("profileImg", file.name, requestFile) //폼데이터
//            }else{
//                val file = File(imageUri.toString())
//                val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
//                profileImg = MultipartBody.Part.createFormData("profileImg", file.name, requestFile) //폼데이터
//            }
//
//            //나머지 dormitoryId랑 nickname
//            val mapData = HashMap<String, RequestBody>()
//            val infoAccepted: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), "true")
//            val marketingAccepted: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), "true")
//            val serviceAccepted: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), "true")
//            mapData.put("infoAccepted", infoAccepted)
//            mapData.put("marketingAccepted", marketingAccepted)
//            mapData.put("serviceAccepted", serviceAccepted)

            val requestTokenMap: HashMap<String, RequestBody> = HashMap()
            val nameBody4 = RequestBody.create(MediaType.parse("text/plain"), accessToken)
            requestTokenMap["accessToken"] = nameBody4

            var imageUri = Uri.parse(imageUriString)

            val file = absolutelyPath(imageUri)?.let { it1 -> File(it1) }
            Log.d("SIGNUP-SERVICE", "FILE = $file")
            val requestFile = file?.let { it1 ->
                RequestBody.create(MediaType.parse("image/*"),
                    it1
                )
            }
            if (file != null) {
                profileImg = requestFile?.let { it1 ->
                    MultipartBody.Part.createFormData("profileImg", file.name,
                        it1
                    )
                }
            } //폼데이터

//            if (imageUri.toString() != getProfileImgUrl()){ // 프로필 이미지가 변경할게 있다면,
//                val file = File(absolutelyPath(imageUri))
//                val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
//                profileImg = MultipartBody.Part.createFormData("profileImg", file.name, requestFile) //폼데이터
//            } else {
//                val file = File(imageUri.toString())
//                val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
//                profileImg = MultipartBody.Part.createFormData("profileImg", file.name, requestFile) //폼데이터
//            }

            var memberRequestDto = MultipartBody.Part.createFormData("memberRequestDto", "{\"infoAccepted\": true,\"marketingAccepted\": true,\"nickname\": \"luna\",\"serviceAccepted\": true}")
            var oauthRequest = MultipartBody.Part.createFormData("oauthRequest", "{\"accessToken\": \"$accessToken")

            Log.d("SIGNUP-SERVICE", "memberRequestDto = $memberRequestDto / OauthRequest = $oauthRequest")
            Log.d("SIGNUP-SERVICE", "memberRequestDto = ${memberRequestDto.body()}")

            // Uri 타입의 파일경로를 가지는 RequestBody 객체 생성
            var fileBody = RequestBody.create(MediaType.parse("image/jpeg"), imageUriString);
            // RequestBody로 Multipart.Part 객체 생성
            var filePart = MultipartBody.Part.createFormData("photo", "photo.jpg", fileBody);

            if (binding.signupNicknameEt.length() in 1..10) {
                signupService.socialLogin("kakao", memberRequestDto, // filePart,
                    profileImg,
                    // requestTokenMap
                    oauthRequest
                )
            }
        }
    }

    // 절대경로 변환
    private fun absolutelyPath(path: Uri): String? {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = contentResolver.query(path, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()
        var result = index?.let { c?.getString(it) }
        return result
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
        changeActivity(SignupFinishActivity::class.java)
    }

    override fun signupSocialFailureView() {
        showToast("소셜 로그인 에러")
        changeActivity(SignupFinishActivity::class.java)
    }
}