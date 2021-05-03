package com.felipenathan.guiabolso.controller

import com.felipenathan.guiabolso.service.TransactionService
import com.felipenathan.guiabolso.vo.TransactionVO
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class TransactionController(
    private val transactionService: TransactionService
) {

    @GetMapping("/{id}/transacoes/{year}/{month}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getTransactions(
        @PathVariable("id") id: Long,
        @PathVariable("year") year: Int,
        @PathVariable("month") month: Int
    ): ResponseEntity<MutableList<TransactionVO>> {
        return ResponseEntity(transactionService.findTransactions(id, year, month), HttpStatus.OK);
    }
}