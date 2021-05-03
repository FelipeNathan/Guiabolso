package com.felipenathan.guiabolso.service

import com.felipenathan.guiabolso.exception.InvalidMonthException
import com.felipenathan.guiabolso.exception.InvalidTransactionIdException
import com.felipenathan.guiabolso.exception.InvalidYearException
import com.felipenathan.guiabolso.repository.CacheTransactions
import com.felipenathan.guiabolso.repository.TransactionRepository
import com.felipenathan.guiabolso.vo.TransactionVO
import org.springframework.stereotype.Service

@Service
class TransactionServiceImpl(
    private val transactionRepository: TransactionRepository,
    private val cacheTransactions: CacheTransactions
) : TransactionService {

    override fun findTransactions(id: Long, year: Int, month: Int): MutableList<TransactionVO> {
        validateParameters(id, year, month)

        val cacheKey = "$id-$year-$month"
        var transactions = cacheTransactions.get(cacheKey)

        if (transactions.isEmpty()) {
            transactions = transactionRepository.findTransactions(id, year, month);
            cacheTransactions.put(cacheKey, transactions)
        }

        return transactions
    }

    private fun validateParameters(id: Long, year: Int, month: Int) {
        if (id !in 1_000L..100_000L)
            throw InvalidTransactionIdException()

        if (year < 0)
            throw InvalidYearException()

        if (month !in 1..12)
            throw InvalidMonthException()
    }

}