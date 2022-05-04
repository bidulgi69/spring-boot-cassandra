package kr.dove.cassandra.api

import kr.dove.cassandra.persistence.sequential.LoggingMessage
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface LoggingService {

    @PostMapping(
        value = ["/logging"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun post(@RequestBody log: Log): Mono<LoggingMessage>

    @GetMapping(
        value = ["/logging/{serverId}"],
        produces = [MediaType.APPLICATION_NDJSON_VALUE]
    )
    fun logging(
        @PathVariable(name = "serverId") serverId: Long,
        @RequestParam(name = "from", required = true) from: Long,
        @RequestParam(name = "to", required = true) to: Long
    ): Flux<LoggingMessage>
}