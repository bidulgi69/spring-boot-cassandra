package kr.dove.cassandra.service

import kr.dove.cassandra.api.Log
import kr.dove.cassandra.api.LoggingService
import kr.dove.cassandra.persistence.sequential.LogKey
import kr.dove.cassandra.persistence.sequential.LoggingMessage
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.lang.Long.max
import java.util.*
import kotlin.math.min

@RestController
class LoggingServiceImpl(
    private val template: ReactiveCassandraTemplate,
) : LoggingService {

    override fun post(log: Log): Mono<LoggingMessage> {
        return template
            .insert(
                LoggingMessage(
                LogKey(
                    log.serverId,
                    setBoundary(log.timestamp),
                    log.timestamp
                ),
                log.origin,
                log.userId,
                log.endpoint
            )
            )
    }

    override fun logging(serverId: Long, from: Long, to: Long): Flux<LoggingMessage> {
        val ranges: Pair<Long, Long> = Pair(
            min(from, to),
            max(from, to)
        )
        return template
            .select("""
                select * from logging_messages 
                where server_id=$serverId and time_boundary=${setBoundary(ranges.first)} 
                and timestamp > ${ranges.first} and timestamp < ${ranges.second} 
            """.trimIndent(),
                LoggingMessage::class.java
        )
    }

    private fun setBoundary(t: Long): Long {
        val c = Calendar.getInstance()
        c.time = Date(t)

        c.add(Calendar.HOUR, 1)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
        return c.time.time
    }
}