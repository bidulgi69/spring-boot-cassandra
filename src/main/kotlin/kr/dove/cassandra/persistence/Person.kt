package kr.dove.cassandra.persistence

import org.springframework.data.annotation.Version
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

@Table(value = "persons")
data class Person(
    @PrimaryKey val key: PersonKey,
    var lastname: String,
    var age: Int,
    @Version val version: Int = 0,
): Times()