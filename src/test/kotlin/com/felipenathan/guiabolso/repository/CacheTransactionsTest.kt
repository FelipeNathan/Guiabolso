package com.felipenathan.guiabolso.repository

import com.felipenathan.guiabolso.service.MockService
import com.felipenathan.guiabolso.vo.TransactionVO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.util.Assert.notNull
import kotlin.random.Random

@ExtendWith(SpringExtension::class)
@TestInstance(PER_CLASS)
class CacheTransactionsTest {

    @InjectMocks
    lateinit var cacheTransactions: CacheTransactionsImpl

    @BeforeEach
    fun setup() = cacheTransactions.clear()

    @Test
    fun `if cache not exists then return empty list`() {
        val transactions = cacheTransactions.get("1-2-3")
        notNull(transactions, "Transactions should not be null")
        assert(transactions.isEmpty())
    }

    @Test
    fun `with key 1-2-3 should return 3 transactions`() {
        val key = "1-2-3"
        val fakeTransactions = createFakeTransactions(3)
        cacheTransactions.put(key, fakeTransactions)

        val transactions = cacheTransactions.get(key)

        notNull(transactions, "Transactions should not be null")
        assert(transactions.isNotEmpty())
        assertEquals(3, transactions.size)
        assertEquals(fakeTransactions, transactions)
    }

    private fun createFakeTransactions(count: Int): MutableList<TransactionVO> {
        val date = MockService.generateRandomDate(2021, 3)
        val list = mutableListOf<TransactionVO>()
        for (i in 1..count) {
            list.add(
                TransactionVO(
                    MockService.generateRandomDescription(),
                    date.time,
                    Random.nextInt(-9_999_999, 9_999_999)
                )
            )
        }
        return list
    }
}