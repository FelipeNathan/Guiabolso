package com.felipenathan.guiabolso.repository

import com.felipenathan.guiabolso.vo.TransactionVO
import org.springframework.stereotype.Repository

@Repository
class CacheTransactionsImpl : CacheTransactions {

    override fun get(key: String): MutableList<TransactionVO> = transactions.getOrDefault(key, mutableListOf())

    override fun put(key: String, newTransactions: MutableList<TransactionVO>) = transactions.put(key, newTransactions)
    
    override fun clear() = transactions.clear()

    companion object {
        private val transactions = mutableMapOf<String, MutableList<TransactionVO>>()
    }
}