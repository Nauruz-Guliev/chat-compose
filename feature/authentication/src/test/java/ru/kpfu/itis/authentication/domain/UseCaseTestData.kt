package ru.kpfu.itis.authentication.domain

import ru.kpfu.itis.authentication.domain.model.User

const val EMAIL = "sasha@gmail.com"
const val NAME = "Aleksandr"
const val PASSWORD = "12345qwe"

val user = User(
    name = NAME,
    password = PASSWORD,
    email = EMAIL
)