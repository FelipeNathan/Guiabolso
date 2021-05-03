package com.felipenathan.guiabolso.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@TestInstance(PER_CLASS)
class TransactionRepositoryTest {

    @InjectMocks
    lateinit var transactionRepositoryImpl: TransactionRepositoryImpl

    @Test
    fun `when id start with 1 and month is 3 should return 3 transactions`() {

        val transactions = transactionRepositoryImpl.findTransactions(1000, 2021, 3)
        assertEquals(3, transactions.size)
    }

    @Test
    fun `when id start with 2 and month is 10 should return 20 transactions`() {

        val transactions = transactionRepositoryImpl.findTransactions(2001, 2021, 10)
        assertEquals(20, transactions.size)
    }

    @Test
    fun `when id start with 9 and month is 12 should return 108 transactions`() {

        val transactions = transactionRepositoryImpl.findTransactions(9999, 2021, 12)
        assertEquals(108, transactions.size)
    }

    @Test
    fun `when id start with 1 and month is 1 should return 1 transaction`() {

        val transactions = transactionRepositoryImpl.findTransactions(1_000, 2021, 1)
        assertEquals(1, transactions.size)
    }
}