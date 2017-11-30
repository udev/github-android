package com.victorude.github.model

data class Result<T>(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: T
)
