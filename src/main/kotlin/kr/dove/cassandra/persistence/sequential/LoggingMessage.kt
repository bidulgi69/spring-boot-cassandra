package kr.dove.cassandra.persistence.sequential

import kr.dove.cassandra.persistence.Times
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

//  Time Sequential Data
@Table(value = "logging_messages")
data class LoggingMessage(
    @PrimaryKey val key: LogKey,
    val origin: String,
    val userId: String,
    val endpoint: String,
): Times()
