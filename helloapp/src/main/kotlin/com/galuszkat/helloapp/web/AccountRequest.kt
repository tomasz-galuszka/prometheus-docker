package com.galuszkat.helloapp.web

import com.galuszkat.helloapp.core.Owner
import javax.validation.constraints.NotBlank

data class AccountRequest(
    @NotBlank
    val firstName: String,
    @NotBlank
    val lastName: String
) {

  fun toDomain(): Owner {
    return Owner(0, firstName, lastName)
  }

  override fun toString(): String {
    return "AccountRequest(firstName='$firstName', lastName='$lastName')"
  }


}