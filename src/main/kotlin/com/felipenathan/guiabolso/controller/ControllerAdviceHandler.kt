package com.felipenathan.guiabolso.controller

import com.felipenathan.guiabolso.exception.GuiabolsoException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException

@RestControllerAdvice
class ControllerAdviceHandler {

    @ExceptionHandler(NoHandlerFoundException::class)
    fun noHandlerFoundException(ex: NoHandlerFoundException): ResponseEntity<Any> {
        val message = "A página que está tentando acessar não existe"
        LOG.error("$message: ${ex.message}")
        return ResponseEntity(message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentExceptionHandler(ex: IllegalArgumentException): ResponseEntity<Any> {
        val message = "Parâmetro incorreto na requisição"
        LOG.error("$message: ${ex.message}")
        return ResponseEntity(message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(GuiabolsoException::class)
    fun guiabolsoExceptionHandler(ex: GuiabolsoException): ResponseEntity<Any> {
        val message = "Parâmetro incorreto na requisição: ${ex.message}"
        LOG.error(message)
        return ResponseEntity(message, HttpStatus.BAD_REQUEST)
    }

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(ControllerAdviceHandler::class.java)
    }
}