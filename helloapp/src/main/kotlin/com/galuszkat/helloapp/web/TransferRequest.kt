package com.galuszkat.helloapp.web

import java.math.BigDecimal
import javax.validation.constraints.NotBlank

data class TransferRequest (
        @NotBlank
        val amount: BigDecimal,
        @NotBlank
        val sourceNumber: Long,
        @NotBlank
        val destinationNumber: Long
)