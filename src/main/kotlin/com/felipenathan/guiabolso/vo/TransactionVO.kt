package com.felipenathan.guiabolso.vo

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class TransactionVO(

    @JsonProperty("descricao")
    var description: String? = null,

    @JsonProperty("data")
    var date: Long? = null,

    @JsonProperty("valor")
    var value: Int? = 0

) : Serializable