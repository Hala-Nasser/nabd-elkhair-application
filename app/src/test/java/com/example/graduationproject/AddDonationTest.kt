package com.example.graduationproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.graduationproject.api.donorApi.addDonation.AddDonationJson
import com.example.graduationproject.api.donorApi.login.LoginJson
import com.example.graduationproject.network.ApiRequests
import com.example.graduationproject.network.RetrofitInstance
import com.example.graduationproject.test.Resource
import com.example.graduationproject.test.TestViewModel
import junit.framework.Assert.assertEquals
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
import java.net.HttpURLConnection
import java.text.SimpleDateFormat
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class AddDonationTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TestViewModel

    private lateinit var apiHelper: ApiRequests

    @Mock
    private lateinit var apiAddDonationObserver: Observer<Resource<AddDonationJson>?>

    private lateinit var mockWebServer: MockWebServer


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel = TestViewModel()
        viewModel.getDonationDetail().observeForever(apiAddDonationObserver)

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
        var body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("charity_id", "1")
            .addFormDataPart("donation_type_id", "1")
            .addFormDataPart("donor_phone", "0595848690")
            .addFormDataPart("donor_district", "الوسطى")
            .addFormDataPart("donor_city", "المغازي")
            .addFormDataPart("donor_address", "الطريق الاولى")
            .addFormDataPart("date_time", "$date 4:00م")
            .build()

        val  actualResponse = apiHelper.addDonation(body, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiYmMwNDM3YTM4NzdiNWY1ODg4ZjI2YjEwODc1NTcyNDQ4ZGMyODc3NmUxNThlZmUxOTUzM2M5NjAzNDIwY2QyMzZlODg1MjdiNDMyMjQ4YjciLCJpYXQiOjE2NTQ2ODk1MjYuNzg0MzU3LCJuYmYiOjE2NTQ2ODk1MjYuNzg0MzY1LCJleHAiOjE2ODYyMjU1MjYuNzE2NjUsInN1YiI6IjEiLCJzY29wZXMiOlsiZG9ub3IiXX0.f8GrYIX22p6ExG6Y_2nXhtmVMIsqkughPtjQ8eM_QJ_xUiH8UaNqWh_niTA6HNrbBS72p__nGdWTukjUDUAp4U8FcMnb82BhpXKkRdVGHT9wiZ4nnCQjKs4IYri50eiHH6v3MMweDxbfQ-eYT7kPjSDPjhwyiw4-vyBjAzsvAHl4cfK6dQobcXhbAyTNNaHg8-CxNnKoOQct9yRO5gfPmPI-VIW2ssgTGhh-o7jPJdRwMNqQVOYZNZQZGmwVVEz2PrPaMUct2jty_FDnrBLaHV50Bmrxi4g9IlUbjB_37tmqomUHG6J1QFgec1W_cGQhj-P33sij8O4HUUKJkIJ9NGPGDsbWzzCO_pgMJIVHC-EDTKpywd0WLM4ImOlQf-gj1ElSQX7v_bKE_f80mcQsfO4h9POhw1jBgtdZxPtBL1Wc-lGSsiU8_mLgYeAzK5Mui4vmZEgqaIYY0JdGpAkVpTitNm59JgKHadybIdD_QoDqGAKgLSUHcax2UpqNnsOBl5R5cdDQAaWdwja3Ey0VG9qKm4ck7fCZhq1IHQlkOnXVK7VZOZEzezt4aCvTX545w5Kqx428-COHkNL4fOZrkgE_Z3sIJpNHKsAh6PNd2ImQNQHEV32BSbsAUBs9ql0FRVTTIj__geqGVF9WEYsMwtbUNZa1vp0mLfyjZMUqCI8").execute()
        // Assert
        assertEquals(response.toString().contains("200"),actualResponse.code().toString().contains("200"))
    }

    @After
    fun tearDown() {
        viewModel.getDonationDetail().removeObserver(apiAddDonationObserver)
        mockWebServer.shutdown()
    }


}