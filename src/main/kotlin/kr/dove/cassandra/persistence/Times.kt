package kr.dove.cassandra.persistence

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import java.time.LocalDateTime

abstract class Times {
    @CreatedBy
    var created: LocalDateTime = LocalDateTime.now()
        private set
    @LastModifiedBy
    var modified: LocalDateTime = LocalDateTime.now()
        private set
}