package com.felipenathan.guiabolso

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@EnableWebMvc
@SpringBootApplication
class GuiabolsoApplication

fun main(args: Array<String>) {
    runApplication<GuiabolsoApplication>(*args)
}
