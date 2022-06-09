package com.example.graduationproject

import android.app.Activity
import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.graduationproject.api.charityApi.campaign.CampaignJson
import com.example.graduationproject.classes.FileUtil
import com.example.graduationproject.network.ApiRequests
import com.example.graduationproject.network.RetrofitInstance
import com.example.graduationproject.test.Resource
import com.example.graduationproject.test.TestViewModel
import com.vansuita.pickimage.bean.PickResult
import junit.framework.Assert.assertEquals
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.io.File
import java.net.HttpURLConnection
import java.text.SimpleDateFormat
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class AddCampaignTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TestViewModel

    private lateinit var apiHelper: ApiRequests

    @Mock
    private lateinit var apiAddCampaignObserver: Observer<Resource<CampaignJson>?>

    private lateinit var mockWebServer: MockWebServer


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel = TestViewModel()
        viewModel.getCampaignDetail().observeForever(apiAddCampaignObserver)

        mockWebServer = MockWebServer()
        mockWebServer.start()
        apiHelper = RetrofitInstance.create()
    }

    @Test
    fun `fetch details and check response Code 200 returned`(){
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("success_response.json").content)
        mockWebServer.enqueue(response)
        // Act
        val sdf = SimpleDateFormat("d MMMM", Locale("ar", "SA"))
        val date = sdf.format(Date(2022, 6, 8))
        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("name", "حملة اطعام 100 عائلة متعففة")
            .addFormDataPart("description", "تهدف هذه الحملة الى توفير الطعام ل 100 عائلة متعففة في قطاع غزة خلال شهر رمضان")
            .addFormDataPart("expiry_date", date)
            .addFormDataPart("expiry_time", "10:00م")
            .addFormDataPart("donation_type_id", "2")
            .addFormDataPart(
                "image", R.drawable.logo.toString(),

            )
            .build()

        val  actualResponse = apiHelper.addCampaign("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiODY5ODhkN2Y1OTZiMTU0NTIxMWE3MGJhYzhlYTgzZDgyNjViNjdlN2MyNThkYjU0ZDNjZjM1MmFkY2RlMTJiZWE4MjNjNmRkNTNmMDkwMWUiLCJpYXQiOjE2NTQ2OTAyMDYuMjA3MDQ1LCJuYmYiOjE2NTQ2OTAyMDYuMjA3MDUxLCJleHAiOjE2ODYyMjYyMDYuMTc4NDQ0LCJzdWIiOiIxIiwic2NvcGVzIjpbImNoYXJpdHkiXX0.HMnVzqjTVm-fOgUsV4vBRkVwpQT3DJ62LFu4QNLctOBB2q9qRA8eXkIILzyATsypAueTpTk-9UnZT1_037AIyiJ4qiGUgXmpyGm9Yc9o357Z9jg0fE-rwZuE8ulkWCvXOioJDtcjJD9wn1E5D8CIFZJPscXhj5Ep_JTyKwBHTNf7OGUIn3LfS5Uga5ZOl1296ZCepXO_zMAIjX1keJmnJSc6kSaZ_nYh-mpFrCpGofwBC__RvpT_3Z_k4a4ZPQsUyPAX8G0YCyalpdeDRUTGaKSazlQvcUiOf27wf7i2L5s90YWRJ4mYwiGozTlrbAd6JJCWp7xeSkH3NXzu8HHXzCuqARdU_PLDSi5VN2OAR4lsRuVEIxBKIe4UbiqrAjwBwU-ophLF9VvsVkEhx-6-7pNYx-jLtfrSy9tuNnfg2jaqetemWY7PvMZ3ftf2p8dx47EQIXlCRBJb7LYcvOE4TVkoqSZhVKjmvBi2Wmz7i9LzRLhWY1pz7VQ2bSgeJ5wynF8aC-FnAHpcLo77G154dseQVYhtdtuxhXFM9CLgF3k6Em0HmQS2tr0-ZnsJQiM7gRQh7mFDzis_vShIa46husW1_Bma6HbC2BfVNsZcRW3Ep11gvZ2eE8qB_wuAnt1xhEZIimgpAD3Oke1fXfhSETQGAYC_W9bBZ5PjRBXUXbc", body).execute()
        // Assert
        assertEquals(response.toString().contains("200"),actualResponse.code().toString().contains("200"))
    }

    @After
    fun tearDown() {
        viewModel.getCampaignDetail().removeObserver(apiAddCampaignObserver)
        mockWebServer.shutdown()
    }


}