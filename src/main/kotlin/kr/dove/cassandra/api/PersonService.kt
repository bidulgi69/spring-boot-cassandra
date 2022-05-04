package kr.dove.cassandra.api

import kr.dove.cassandra.persistence.Person
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

interface PersonService {

    @GetMapping(
        value = ["/person/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getById(@PathVariable(name = "id") id: UUID): Mono<Person>

    @PostMapping(
        value = ["/person"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun post(@RequestBody person: Person): Mono<Person>

    @DeleteMapping(
        value = ["/person/{id}"],
        produces = ["application/json"]
    )
    fun delete(@PathVariable(name = "id") id: UUID): Mono<Boolean>

    @GetMapping(
        value = ["/persons"],
        produces = [MediaType.APPLICATION_NDJSON_VALUE]
    )
    fun persons(): Flux<Person>

    @GetMapping(
        value = ["/person/name/{name}"],
        produces = [MediaType.APPLICATION_NDJSON_VALUE]
    )
    fun getByName(
        @PathVariable(name = "name") name: String,
        @RequestParam(name = "switch") switch: Int,
    ): Flux<Person>
}