package com.felipenathan.guiabolso.service

import com.felipenathan.guiabolso.exception.InvalidMonthException
import com.felipenathan.guiabolso.exception.InvalidTransactionIdException
import com.felipenathan.guiabolso.exception.InvalidYearException
import com.felipenathan.guiabolso.repository.CacheTransactions
import com.felipenathan.guiabolso.repository.TransactionRepositoryImpl
import com.felipenathan.guiabolso.vo.TransactionVO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.random.Random

@ExtendWith(SpringExtension::class)
@TestInstance(PER_CLASS)
class TransactionServiceTest {

    @InjectMocks
    lateinit var transactionService: TransactionServiceImpl

    @Mock
    lateinit var transactionRepository: TransactionRepositoryImpl

    @Mock
    lateinit var cache: CacheTransactions

    @Test
    fun `when id greater than 100_000 then throw InvalidTransactionIdException`() {
        assertThrows<InvalidTransactionIdException> { transactionService.findTransactions(100_101, 2010, 1) }
    }

    @Test
    fun `when id lower than 1_000 then throw InvalidTransactionIdException`() {
        assertThrows<InvalidTransactionIdException> { transactionService.findTransactions(999, 2010, 1) }
    }

    @Test
    fun `when negative year then throw InvalidYearException`() {
        assertThrows<InvalidYearException> { transactionService.findTransactions(1_000, -10, 1) }
    }

    @Test
    fun `when month greater than 12 then throw InvalidMonthException`() {
        assertThrows<InvalidMonthException> { transactionService.findTransactions(1_000, 2010, 13) }
    }

    @Test
    fun `when month lower than 1 then throw InvalidMonthException`() {
        assertThrows<InvalidMonthException> { transactionService.findTransactions(1_000, 2010, 0) }
    }

    @Test
    fun `when transaction already loaded then should not call repository`() {
        val fakeTransactions = createFakeTransactions()
        `when`(cache.get("$ID-$YEAR-$MONTH")).thenReturn(fakeTransactions)

        val transactions = transactionService.findTransactions(ID, YEAR, MONTH)

        verify(transactionRepository, never()).findTransactions(ID, YEAR, MONTH)
        assert(transactions.isNotEmpty())
        assertEquals(fakeTransactions, transactions)
    }

    @Test
    fun `when transaction is new then should call repository`() {
        val empty = mutableListOf<TransactionVO>()
        `when`(cache.get("$ID-$YEAR-$MONTH")).thenReturn(empty)

        transactionService.findTransactions(ID, YEAR, MONTH)

        verify(transactionRepository).findTransactions(ID, YEAR, MONTH)
    }

    private fun createFakeTransactions(): MutableList<TransactionVO> {
        val date = MockService.generateRandomDate(YEAR, MONTH)
        return mutableListOf(
            TransactionVO(
                MockService.generateRandomDescription(),
                date.time,
                Random.nextInt(-9_999_999, 9_999_999)
            )
        )
    }

    companion object {
        const val ID: Long = 1_000L
        const val YEAR: Int = 2021
        const val MONTH: Int = 5
    }
}