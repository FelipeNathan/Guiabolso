package com.felipenathan.guiabolso.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.felipenathan.guiabolso.exception.InvalidMonthException
import com.felipenathan.guiabolso.exception.InvalidTransactionIdException
import com.felipenathan.guiabolso.exception.InvalidYearException
import com.felipenathan.guiabolso.vo.TransactionVO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `when invalid url for transaction then throw bad request`() {

        val message = "Parâmetro incorreto na requisição"
        mockMvc.perform(get("/teste/transacoes/2000/1"))
            .andExpect(status().isBadRequest)
            .andExpect(content().string(message))
    }

    @Test
    fun `when transaction id out of range then throw bad request`() {

        val message = "Parâmetro incorreto na requisição: ${InvalidTransactionIdException().message}"
        mockMvc.perform(get("/999/transacoes/2000/1"))
            .andExpect(status().isBadRequest)
            .andExpect(content().string(message))
    }

    @Test
    fun `when month out of range then throw bad request`() {

        val message = "Parâmetro incorreto na requisição: ${InvalidMonthException().message}"
        mockMvc.perform(get("/1000/transacoes/2000/13"))
            .andExpect(status().isBadRequest)
            .andExpect(content().string(message))
    }

    @Test
    fun `when negative year then throw bad request`() {

        val message = "Parâmetro incorreto na requisição: ${InvalidYearException().message}"
        mockMvc.perform(get("/1000/transacoes/-1000/12"))
            .andExpect(status().isBadRequest)
            .andExpect(content().string(message))
    }

    @Test
    fun `when unknown url then throw bad request`() {

        val message = "A página que está tentando acessar não existe"

        mockMvc.perform(get("/teste/"))
            .andExpect(status().isNotFound)
            .andExpect(content().string(message))
    }

    @Test
    fun `when 1000-2010-3 then return 3 transaction`() {

        val result: MvcResult = mockMvc.perform(get("/1000/transacoes/2010/3"))
            .andExpect(status().isOk)
            .andReturn()

        val transactions = getResultBody(result.response.contentAsString)

        assertEquals(3, transactions.size)
    }

    @Test
    fun `when 2050-2021-10 then return 20 transaction`() {

        val result: MvcResult = mockMvc.perform(get("/2050/transacoes/2021/10"))
            .andExpect(status().isOk)
            .andReturn()

        val transactions = getResultBody(result.response.contentAsString)

        assertEquals(20, transactions.size)
    }

    @Test
    fun `when 9999-2021-12 then return 108 transaction`() {

        val result: MvcResult = mockMvc.perform(get("/9999/transacoes/2021/12"))
            .andExpect(status().isOk)
            .andReturn()

        val transactions = getResultBody(result.response.contentAsString)

        assertEquals(108, transactions.size)
    }

    @Test
    fun `when 100000-2021-1 then return 108 transaction`() {

        val result: MvcResult = mockMvc.perform(get("/100000/transacoes/2021/1"))
            .andExpect(status().isOk)
            .andReturn()

        val transactions = getResultBody(result.response.contentAsString)

        assertEquals(1, transactions.size)
    }

    private fun getResultBody(body: String): MutableList<TransactionVO> {
        val objectMapper = ObjectMapper()

        return objectMapper.readValue(
            body,
            objectMapper.typeFactory.constructCollectionType(MutableList::class.java, TransactionVO::class.java)
        )
    }
}