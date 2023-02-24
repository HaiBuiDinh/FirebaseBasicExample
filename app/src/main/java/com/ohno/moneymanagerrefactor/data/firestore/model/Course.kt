package com.ohno.moneymanagerrefactor.data.firestore.model

data class Course(
    var name: String? = null,
    var duration: Long = 0L,
    var description: String? = null
) {
    companion object {
        const val FIELD_NAME = "name"
        const val FIELD_DURATION = "duration"
        const val FIELD_DESCRIPTION = "description"
    }
}