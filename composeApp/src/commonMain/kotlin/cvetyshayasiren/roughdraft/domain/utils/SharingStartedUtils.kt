package cvetyshayasiren.roughdraft.domain.utils

import kotlinx.coroutines.flow.SharingStarted

fun SharingStarted.Companion.custom(): SharingStarted =
    WhileSubscribed(stopTimeoutMillis = 5000, replayExpirationMillis = 5000)