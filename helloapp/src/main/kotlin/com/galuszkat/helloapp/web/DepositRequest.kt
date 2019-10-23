package com.galuszkat.helloapp.web

import java.math.BigDecimal
import javax.validation.constraints.NotBlank

data class DepositRequest(
        @NotBlank
        val number: Long,
        @NotBlank
        val amount: BigDecimal
)