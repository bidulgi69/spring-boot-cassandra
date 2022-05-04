package kr.dove.cassandra.persistence.denormalize

import org.springframework.data.cassandra.core.mapping.PrimaryKey

//  You can search the table for a job based on name column. (partition key: name)
//  But what if you want to search name based on job column? 🤔
data class Worker(
    @PrimaryKey val name: String,
    val job: String,
)
