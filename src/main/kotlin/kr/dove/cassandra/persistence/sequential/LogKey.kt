package kr.dove.cassandra.persistence.sequential

import org.springframework.data.cassandra.core.cql.Ordering
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn

//  PRIMARY KEY ((server_id, time_boundary), timestamp)
@PrimaryKeyClass
data class LogKey(
    //  If you designate partition key on the serverId column only,
    //  There is a high possibility that the rings with the corresponding partition key will become a hotspot due to the influx of requests to a particular server.
    //  To avoid this, you must specify the timeBoundary column together with the serverId column as a partition key. (it helps to distribute data into several rings)
    //  You should be aware of the number of partition keys will continue to increase.
    @PrimaryKeyColumn(name = "server_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    val serverId: Long,

    @PrimaryKeyColumn(name = "time_boundary", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    val timeBoundary: Long,

    //  specify timestamp column as cluster key so that data are sorted in descending order for the row key.
    //  Search using comparison operators such as "<", ">"
    @PrimaryKeyColumn(name = "timestamp", ordinal = 2, ordering = Ordering.DESCENDING, type = PrimaryKeyType.CLUSTERED)
    val timestamp: Long,
)