package com.felipenathan.guiabolso.repository

import com.felipenathan.guiabolso.vo.TransactionVO

interface CacheTransactions {
    fun get(key: String): MutableList<TransactionVO>
    fun put(key: String, newTransactions: MutableList<TransactionVO>): MutableList<TransactionVO>?
    fun clear()
}