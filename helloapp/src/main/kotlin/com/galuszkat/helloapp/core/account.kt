package com.galuszkat.helloapp.core

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Account(

    @Id
    @Column(length = 10)
    val number: Long = 0,

    @OneToOne(cascade = [CascadeType.ALL], optional = false)
    val owner: Owner,

    @Column(nullable = false, precision = 10, scale = 2)
    val amount: BigDecimal = BigDecimal.ZERO,

    val createdOn: LocalDateTime = LocalDateTime.now()

)