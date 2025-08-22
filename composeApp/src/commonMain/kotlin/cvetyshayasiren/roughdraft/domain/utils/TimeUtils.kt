package cvetyshayasiren.roughdraft.domain.utils

import kotlin.time.Duration.Companion.seconds

fun Double.toPrettyMinSec() = if(isFinite()) seconds.toComponents { _, minutes, seconds, _ ->
    "$minutes:" + "$seconds".padStart(2, '0') } else ""