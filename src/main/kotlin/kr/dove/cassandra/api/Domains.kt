package kr.dove.cassandra.api

data class Log(
    val serverId: Long,
    val timestamp: Long,

    val origin: String,
    val userId: String,
    val endpoint: String,
)