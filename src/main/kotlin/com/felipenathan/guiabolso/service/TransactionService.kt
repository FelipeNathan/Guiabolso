package com.felipenathan.guiabolso.service

import com.felipenathan.guiabolso.vo.TransactionVO

interface TransactionService {
    fun findTransactions(id: Long, year: Int, month: Int): MutableList<TransactionVO>
}