package com.example.presentation


import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.domain.repository.TokenRepository
import com.example.domain.usecase.GetTokensUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import com.example.common.model.Result
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class GetTokenUseCaseTest  {

    @Inject
    lateinit var repository: TokenRepository

    @Inject
    lateinit var useCase: GetTokensUseCase

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun testGetCurrenciesUseCase() = runBlocking {
        val result = useCase()
        assertTrue(result is Result.Success)
        assertTrue((result as Result.Success).data.isNotEmpty())
    }
}