package kr.dove.cassandra.persistence.denormalize

import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

//  For the dataset with the partition key "name"
//  When you want to execute a query based on the job column
//  There is a method for generating a secondary index on the job column ( >0.7v )
//  In earlier versions of Cassandra, where the secondary index did not exist,
//  Through denormalization, as shown below data class, the job column is designated as the partition key.
//  It causes duplicate data to be stored.
//  But denormalization is recommended because scale-out is not difficult in Cassandra.
//  In addition, disk additional costs are the cheapest compared to memory and CPU additional costs.
@Table(value = "jobs")
data class Job(
    @PrimaryKey val job: String,
    val worker: String,
)
