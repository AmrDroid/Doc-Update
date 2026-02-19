package com.impiricus.docupdate.domain.models

data class Physician(
    val id: String,
    val npi: String,
    val firstName: String,
    val lastName: String,
    val specialty: String,
    val state: String,
    val consentOptIn: Boolean,
    val preferredChannel: String
) {
    val fullName: String
        get() = "$firstName $lastName"
}
