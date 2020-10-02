package com.example.demo

import java.util.*

fun <T> Optional<T>.unwrap(): T? = orElse(null)