package com.felipenathan.guiabolso.repository

import com.felipenathan.guiabolso.service.MockService
import com.felipenathan.guiabolso.vo.TransactionVO
import org.springframework.stereotype.Repository

@Repository
class TransactionRepositoryImpl : TransactionRepository {

    override fun findTransactions(id: Long, year: Int, month: Int): MutableList<TransactionVO> {
        val transactions = mutableListOf<TransactionVO>()
        val multiplier = id.toString().substring(0, 1).toInt() * month

        for (i in 1..multiplier) {
            val date = MockService.generateRandomDate(year, month)
            transactions.add(
                TransactionVO(
                    MockService.generateRandomDescription(),
                    date.time,
                    MockService.generateRandomValue(id, month, i)
                )
            )
        }

        return transactions
    }
}