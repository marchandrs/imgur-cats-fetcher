package com.stefanini.imgurcatfetcher.model

data class AdConfig(
    val highRiskFlags: List<Any>,
    val safeFlags: List<String>,
    val showAdLevel: Int,
    val showsAds: Boolean,
    val unsafeFlags: List<String>,
    val wallUnsafeFlags: List<String>
)