package com.galuszkat.helloapp.web

import com.galuszkat.helloapp.core.Owner
import javax.validation.constraints.NotBlank

class AccountRequest(
    @NotBlank
    private val firstName: String,
    @NotBlank
    private val lastName: String
) {

  fun toDomain(): Owner {
    return Owner(0, firstName, lastName)
  }
}