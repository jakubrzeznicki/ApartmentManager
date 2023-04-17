package com.jakubrzeznicki.apartmentmanager.createpin

import com.jakubrzeznicki.apartmentmanager.createpin.viewmodel.CreatePinViewModel
import com.jakubrzeznicki.apartmentmanager.data.apartmentpin.ApartmentPinDataSource
import io.mockk.MockKAnnotations
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by jrzeznicki on 17/04/2023.
 */
class CreatePinViewModelTest {

    private val repository: ApartmentPinDataSource = mockk()
    private lateinit var viewModel: CreatePinViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = CreatePinViewModel(repository)
    }

    @Test
    fun `generateRandomDigits should return code, consisted of 6 digits`() {
        /* Given */
        val expectedSize = 6
        /* When */
        val code = viewModel.generateRandomDigits()
        /* Then */
        Assert.assertEquals(expectedSize, code)
    }

    @Test
    fun `generateCode should return valid code, consisted of digits from parameter`() {
        /* Given */
        val digits = listOf(1, 4, 6, 7, 3, 1)
        val expectedCode = digits.joinToString(SEPARATOR)
        /* When */
        val code = viewModel.generateCode(digits)
        /* Then */
        Assert.assertEquals(expectedCode, code)
    }

    @Test
    fun `generateCode should return valid code, consisted of generated random digits`() {
        /* Given */
        val digits = listOf(1, 2, 1, 1, 1, 5)
        val wrongCode = digits.joinToString(SEPARATOR)
        /* When */
        val code = viewModel.generateCode(digits)
        /* Then */
        Assert.assertTrue(wrongCode != code)
        Assert.assertTrue(code.split(SEPARATOR).distinct().size > 3)
    }

    private companion object {
        const val SEPARATOR = ""
    }
}