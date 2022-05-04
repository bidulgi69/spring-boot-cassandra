package kr.dove.cassandra.service

import kr.dove.cassandra.api.PersonService
import kr.dove.cassandra.persistence.Person
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate
import org.springframework.data.cassandra.core.query.Criteria
import org.springframework.data.cassandra.core.query.Query
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
class PersonServiceImpl(
    private val template: ReactiveCassandraTemplate,
) : PersonService {

    //  Every select query should be based on the partition key.
    override fun getById(id: UUID): Mono<Person> {
        return template
            .selectOne(
                """
                    select * from persons where id = $id
                """.trimIndent(),
                Person::class.java
            )
    }

    //  You can execute insert and update query using "cql> insert into persons() values()" (if primary key equals)
    override fun post(person: Person): Mono<Person> {
        return template.insert(
            person
        )
    }

    //  marking tombstone
    //  will be deleted when compaction in SSTable occurs or the GC works.
    //  Cassandra internally stores data sequentially in MemTable.
    //  Therefore, tombstone marked data must also be read and passed.
    //  It may occur performance degradation, so this should be considered.
    override fun delete(id: UUID): Mono<Boolean> {
        return template.delete(
            Query.query(
                Criteria.where("id").`is`(id)
            ), Person::class.java
        )
    }

    override fun persons(): Flux<Person> {
        return template.select(
            """
                select * from persons
            """.trimIndent(),
            Person::class.java
        )
    }

    //  Search for un-indexed columns can cause performance degradation.
    //  You can add allow filtering to perform queries,
    //  but if you search frequently for that column, you should consider creating a secondary index on that column.
    //  However, the secondary index is Anti-Pattern in Cassandra, so abuse should be avoided.
    /*
     *  e.g.    cqlsh> create index firstname_idx on persons (firstname);
     */
    override fun getByName(name: String, switch: Int): Flux<Person> {
        return when (switch) {
            0 -> template.select(
                """
                    select * from persons where firstname = '$name' allow filtering
                """.trimIndent(),
                Person::class.java
            )
            1 -> template.select(
                """
                    select * from persons where lastname = '$name' allow filtering
                """.trimIndent(),
                Person::class.java
            )
            else -> Flux.empty()
        }
    }
}
