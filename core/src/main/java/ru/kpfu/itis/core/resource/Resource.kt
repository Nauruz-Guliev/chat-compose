package ru.kpfu.itis.core.resource

import android.content.Context
import androidx.annotation.AnyRes
import androidx.annotation.StringRes

sealed class Resource(
    @AnyRes open val id: Int
) {
    /**
     * Утилитный класс для хранения, получения и передачи строк.
     */
    class String(
        @StringRes override var id: Int,
        private vararg val arg: Int
    ) : Resource(id) {
        fun getValue(context: Context): kotlin.String {
            val arguments = mutableListOf<kotlin.String>()
            arg.forEach {
                kotlin.runCatching {
                    arguments.add(context.resources.getString(it))
                }
            }
            return context.resources.getString(id, arguments)
        }
    }
}