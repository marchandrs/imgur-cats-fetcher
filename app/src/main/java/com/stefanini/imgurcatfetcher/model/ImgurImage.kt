package com.stefanini.imgurcatfetcher.model

data class ImgurImage(
    val data: List<Data>,
    val status: Int,
    val success: Boolean
)