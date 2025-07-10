package cvetyshayasiren.roughdraft

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform