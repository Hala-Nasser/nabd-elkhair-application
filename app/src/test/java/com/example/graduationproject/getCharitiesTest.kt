package com.example.graduationproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.graduationproject.api.donorApi.charities.CharitiesJson
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


@RunWith(MockitoJUnitRunner::class)
class getCharitiesTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TestViewModel

    private lateinit var apiHelper: ApiRequests

    @Mock
    private lateinit var apiCharitiesObserver: Observer<Resource<CharitiesJson>?>

    private lateinit var mockWebServer: MockWebServer


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel = TestViewModel()
        viewModel.getCharityDetail().observeForever(apiCharitiesObserver)

        mockWebServer = MockWebServer()
        mockWebServer.start()
        apiHelper = RetrofitInstance.create()
    }

    @Test
    fun `fetch details and check response Code 200 returned`(){
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("success_response.json").content)
        mockWebServer.enqueue(response)
        // Act
        val  actualResponse = apiHelper.getCharities().execute()
        // Assert
        assertEquals(response.toString().contains("200"),actualResponse.code().toString().contains("200"))
    }

    @After
    fun tearDown() {
        viewModel.getCharityDetail().removeObserver(apiCharitiesObserver)
        mockWebServer.shutdown()
    }


}