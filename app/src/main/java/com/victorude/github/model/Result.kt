package com.victorude.github.model

data class Result<out T>(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: T
)
