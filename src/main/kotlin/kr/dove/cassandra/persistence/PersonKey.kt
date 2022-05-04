package kr.dove.cassandra.persistence

import org.springframework.data.cassandra.core.cql.Ordering
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import java.io.Serializable
import java.time.LocalDateTime
import java.util.UUID

@PrimaryKeyClass
data class PersonKey(
    //  hashed by murmur3
    @PrimaryKeyColumn(name = "id", ordinal = 0, ordering = Ordering.DESCENDING, type = PrimaryKeyType.PARTITIONED)
    val id: UUID = UUID.randomUUID(),

    @PrimaryKeyColumn(name = "firstname", ordinal = 1)
    var firstname: String,

    @PrimaryKeyColumn(name = "birth", ordinal = 2)
    var date: LocalDateTime,
) : Serializable