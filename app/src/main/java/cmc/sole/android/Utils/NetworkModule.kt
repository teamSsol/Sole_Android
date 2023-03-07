package com.example.geeksasaeng.Utils

import cmc.sole.android.Utils.AuthorizationTokenInterceptor
import com.sole.android.ApplicationClass
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class NetworkModule {
    companion object {
        // retrofit 변수를 외부에서 업데이트할 수 없도록 방지
        private var retrofit: Retrofit? = null

        // retrofit 객체를 가져오는 함수
        // 새로 만들지 않아도 된다!!
        fun getInstance(): Retrofit? {
            if (retrofit == null) {
                synchronized(this) {
                    val client: OkHttpClient = OkHttpClient.Builder().build()

                    retrofit = Retrofit.Builder()
                        .baseUrl(ApplicationClass.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .client(client)
                        .build()
                }
            }

            return retrofit
        }
    }
}
