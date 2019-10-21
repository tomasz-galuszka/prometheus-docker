package com.galuszkat.helloapp.core

import javax.persistence.*

@Entity
data class Owner (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, length = 80)
    val firstName: String,

    @Column(nullable = false, length = 80)
    val lastName: String
)