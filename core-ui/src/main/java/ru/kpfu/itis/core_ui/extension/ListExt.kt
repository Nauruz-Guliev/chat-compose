package ru.kpfu.itis.core_ui.extension

fun <T> List<T>.containsEach(vararg values: T): Boolean {
    return values.all { value -> this.contains(value) }
}