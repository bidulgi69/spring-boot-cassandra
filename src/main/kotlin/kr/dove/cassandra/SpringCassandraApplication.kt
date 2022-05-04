package kr.dove.cassandra

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringCassandraApplication

fun main(args: Array<String>) {
	runApplication<SpringCassandraApplication>(*args)
}
