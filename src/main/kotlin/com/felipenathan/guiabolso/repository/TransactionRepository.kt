package com.felipenathan.guiabolso.repository

import com.felipenathan.guiabolso.vo.TransactionVO

interface TransactionRepository {
    fun findTransactions(id: Long, year: Int, month: Int): MutableList<TransactionVO>
}