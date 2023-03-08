package com.ohno.moneymanagerrefactor.data.firestore.model

data class Record(
    var category: Int? = null,
    var money: Float? = 0f,
    var remark: String? = null,
    var time: Long? = 0L,
) {
    companion object {
        const val CATEGORY = "category"
        const val MONEY = "money"
        const val REMARK = "remark"
        const val TIME = "time"
    }
}
